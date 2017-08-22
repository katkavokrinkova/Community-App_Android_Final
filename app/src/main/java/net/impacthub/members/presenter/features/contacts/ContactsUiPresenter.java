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

import net.impacthub.members.mapper.contacts.ContactsMapper;
import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.features.members.Records;
import net.impacthub.members.model.vo.contacts.ContactVO;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.contacts.DMRequestUseCase;
import net.impacthub.members.usecase.features.members.MembersUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
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
        Single<List<ContactVO>> listSingle = mProfileUseCase.getUseCase()
                .flatMap(new Function<MembersResponse, SingleSource<? extends List<ContactVO>>>() {
                    @Override
                    public SingleSource<? extends List<ContactVO>> apply(@NonNull MembersResponse membersResponse) throws Exception {
                        Records record = membersResponse.getRecords()[0];
                        return Single.zip(
                                new DMRequestUseCase(record.getId()).getUseCase(),
                                new MembersUseCase().getUseCase(), new BiFunction<ContactsResponse, MembersResponse, List<ContactVO>>() {
                                    @Override
                                    public List<ContactVO> apply(@NonNull ContactsResponse response, @NonNull MembersResponse membersResponse) throws Exception {
                                        return new ContactsMapper().mapContactMembers(response, membersResponse, getUserAccount().getUserId());
                                    }
                                }
                        );
                    }
                });
        subscribeWith(listSingle, new DisposableSingleObserver<List<ContactVO>>() {
            @Override
            public void onSuccess(@NonNull List<ContactVO> contactVOs) {
                getUi().onLoadContacts(contactVOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
}
