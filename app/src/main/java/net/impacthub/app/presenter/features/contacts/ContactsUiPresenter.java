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

package net.impacthub.app.presenter.features.contacts;

import android.util.Pair;

import com.google.gson.Gson;

import net.impacthub.app.mapper.contacts.ContactsMapper;
import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.contacts.ContactRecords;
import net.impacthub.app.model.features.contacts.ContactsResponse;
import net.impacthub.app.model.features.members.MembersResponse;
import net.impacthub.app.model.vo.contacts.ContactsWrapper;
import net.impacthub.app.model.vo.contacts.DeclineContactBody;
import net.impacthub.app.model.vo.contacts.UpdateContactBody;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.AbstractFunction;
import net.impacthub.app.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.app.usecase.features.contacts.DeleteDMRequest;
import net.impacthub.app.usecase.features.contacts.UpdateDMRequestStatusUseCase;
import net.impacthub.app.usecase.features.members.GetMembersByContactIdsUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;
import net.impacthub.app.utilities.StringUtils;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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

    public ContactsUiPresenter(ContactsUiContract uiContract) {
        super(uiContract);
    }

    public void getContacts() {

        Single<ContactsWrapper> wrapperSingle = new ProfileUseCase().getUseCase()
                .flatMap(new Function<MemberVO, SingleSource<ContactsWrapper>>() {
                    @Override
                    public SingleSource<ContactsWrapper> apply(MemberVO memberVO) throws Exception {
                        String contactId = memberVO.mContactId;
                        return new DMGetContactsUseCase(contactId).getUseCase()
                                .flatMap(new AbstractFunction<String, ContactsResponse, Single<ContactsWrapper>>(contactId) {

                                    @Override
                                    protected Single<ContactsWrapper> apply(ContactsResponse response, String subject) throws Exception {
                                        Set<String> idSet = new HashSet<>();
                                        if (response != null) {
                                            ContactRecords[] records = response.getRecords();
                                            if (records != null) {
                                                for (ContactRecords record : records) {
                                                    String contactFrom__c = record.getContactFrom__c();
                                                    String contactTo__c = record.getContactTo__c();
                                                    idSet.add(contactFrom__c);
                                                    idSet.add(contactTo__c);
                                                }
                                            }
                                        }
                                        String contactIds = StringUtils.join(",", new LinkedList<>(idSet));
                                        return new GetMembersByContactIdsUseCase(contactIds).getUseCase()
                                                .map(new AbstractFunction<Pair<ContactsResponse, String>, MembersResponse, ContactsWrapper>(Pair.create(response, subject)) {
                                                    @Override
                                                    protected ContactsWrapper apply(MembersResponse response, Pair<ContactsResponse, String> subject) throws Exception {
                                                        return new ContactsMapper().mapContactMembers(subject.first, new MembersMapper().mapMembers(response), subject.second);
                                                    }
                                                });
                                    }
                                });
                    }
                });
        getUi().onShowProgressBar(true);
        subscribeWith(wrapperSingle, new DisposableSingleObserver<ContactsWrapper>() {

            @Override
            public void onSuccess(@NonNull ContactsWrapper contactsWrapper) {
                getUi().onLoadApprovedContacts(contactsWrapper.getApprovedContacts());
                getUi().onLoadOutstandingContacts(contactsWrapper.getOutstandingContacts());
                getUi().onLoadDeclinedContacts(contactsWrapper.getDeclinedContacts());
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
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
        } catch (Exception e) {
        }
    }

    public void disconnectContact(String contactId) {
        try {
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
        } catch (Exception e) {
        }
    }
}
