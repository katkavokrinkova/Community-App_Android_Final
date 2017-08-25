/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.members.presenter.features.contacts;

import com.google.gson.Gson;

import net.impacthub.members.mapper.contacts.ContactsMapper;
import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.features.members.Records;
import net.impacthub.members.model.vo.contacts.ContactVO;
import net.impacthub.members.model.vo.contacts.DeclineContactBody;
import net.impacthub.members.model.vo.contacts.UpdateContactBody;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractBigFunction;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.contacts.DMRequestUseCase;
import net.impacthub.members.usecase.features.contacts.DeleteDMRequest;
import net.impacthub.members.usecase.features.contacts.UpdateDMRequestStatusUseCase;
import net.impacthub.members.usecase.features.members.MembersUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ContactsUiPresenter extends UiPresenter<ContactsUiContract> {

    private final UseCaseGenerator<Single<MembersResponse>> mProfileUseCase = new ProfileUseCase();

    public ContactsUiPresenter(ContactsUiContract uiContract) {
        super(uiContract);
    }

    public void getContacts() {
        Single<Map<String, List<ContactVO>>> listSingle = mProfileUseCase.getUseCase()
                .flatMap(new Function<MembersResponse, SingleSource<? extends Map<String, List<ContactVO>>>>() {
                    @Override
                    public SingleSource<? extends Map<String, List<ContactVO>>> apply(@NonNull MembersResponse membersResponse) throws Exception {
                        Records record = membersResponse.getRecords()[0];
                        String contactId = record.getId();
                        return Single.zip(
                                new DMRequestUseCase(contactId).getUseCase(),
                                new MembersUseCase().getUseCase(),
                                new AbstractBigFunction<String, ContactsResponse, MembersResponse, Map<String, List<ContactVO>>> (contactId) {
                                    @Override
                                    protected Map<String, List<ContactVO>> apply(ContactsResponse response, MembersResponse membersResponse, String subject) {
                                        return new ContactsMapper().mapContactMembers(response, membersResponse, subject);
                                    }
                                }
                        );
                    }
                });
        getUi().onChangeStatus(true);
        subscribeWith(listSingle, new DisposableSingleObserver<Map<String, List<ContactVO>>>() {

            @Override
            public void onSuccess(@NonNull Map<String, List<ContactVO>> stringListMap) {
                getUi().onLoadApprovedContacts(stringListMap.get("Approved"));
                getUi().onLoadOutstandingContacts(stringListMap.get("Outstanding"));
                getUi().onLoadDeclinedContacts(stringListMap.get("Declined"));
                getUi().onChangeStatus(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onChangeStatus(false);
            }
        });
    }

    public void updateContactRequest(String id, String pushUserId, String status) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(new UpdateContactBody(id, status, pushUserId)));
            subscribeWith(new UpdateDMRequestStatusUseCase(jsonObject).getUseCase(), new DisposableSingleObserver<Object>() {
                @Override
                public void onSuccess(@NonNull Object o) {
                    getContacts();
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    getUi().onError(e);
                }
            });
        }catch (Exception e){}
    }

    public void declineContact(String contactId) {
        try{
            JSONObject jsonObject = new JSONObject(new Gson().toJson(new DeclineContactBody(contactId)));
            subscribeWith(new DeleteDMRequest(jsonObject).getUseCase(), new DisposableSingleObserver<Object>() {
                @Override
                public void onSuccess(@NonNull Object o) {
                    getContacts();
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    getUi().onError(e);
                }
            });
        }catch(Exception e){}
    }
}
