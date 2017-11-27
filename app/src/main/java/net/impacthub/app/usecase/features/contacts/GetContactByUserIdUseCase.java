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

package net.impacthub.app.usecase.features.contacts;

import net.impacthub.app.mapper.contacts.ContactsMapper;
import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.contacts.ContactsResponse;
import net.impacthub.app.model.features.members.MembersResponse;
import net.impacthub.app.model.vo.contacts.ContactVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.rx.AbstractBigFunction;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;
import net.impacthub.app.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/21/2017.
 */

public class GetContactByUserIdUseCase extends BaseUseCaseGenerator<Single<ContactVO>, MembersResponse> {

    private final String mUserId;

    public GetContactByUserIdUseCase(String userId) {
        mUserId = userId;
    }

    @Override
    public Single<ContactVO> getUseCase() {
        return new ProfileUseCase().getUseCase()
                .flatMap(new Function<MemberVO, SingleSource<ContactVO>>() {
                    @Override
                    public SingleSource<ContactVO> apply(@NonNull MemberVO memberVO) throws Exception {
                        String contactId = memberVO.mContactId;
                        return Single.zip(
                                new DMGetContactsUseCase(contactId).getUseCase(),
                                Single.fromCallable(new Callable<MembersResponse>() {
                                    @Override
                                    public MembersResponse call() throws Exception {
                                        return getApiCall().getResponse(getSoqlRequestFactory().createGetProfileRequest(mUserId), MembersResponse.class);
                                    }
                                }),
                                new AbstractBigFunction<String, ContactsResponse, MembersResponse, ContactVO>(contactId) {
                                    @Override
                                    protected ContactVO apply(ContactsResponse response, MembersResponse membersResponse, String subject) {
                                        return new ContactsMapper().mapMemberContact(response, membersResponse, subject);
                                    }
                                }
                        );
                    }
                });
    }
}
