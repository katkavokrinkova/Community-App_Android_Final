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

package net.impacthub.members.usecase.features.members;

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.rx.AbstractBigFunction;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;
import net.impacthub.members.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

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

public class GetMemberByUserIdUseCase extends BaseUseCaseGenerator<Single<MemberVO>, MembersResponse> {

    private final String mUserId;

    public GetMemberByUserIdUseCase(String userId) {
        mUserId = userId;
    }

    @Override
    public Single<MemberVO> getUseCase() {
        return new ProfileUseCase().getUseCase()
                .flatMap(new Function<MemberVO, SingleSource<MemberVO>>() {
                    @Override
                    public SingleSource<MemberVO> apply(@NonNull MemberVO memberVO) throws Exception {
                        String contactId = memberVO.mContactId;
                        return Single.zip(
                                new DMGetContactsUseCase(contactId).getUseCase(),
                                Single.fromCallable(new Callable<MembersResponse>() {
                                    @Override
                                    public MembersResponse call() throws Exception {
                                        return getApiCall().getResponse(getSoqlRequestFactory().createGetProfileRequest(mUserId), MembersResponse.class);
                                    }
                                }),
                                new AbstractBigFunction<String, ContactsResponse, MembersResponse, MemberVO>(contactId) {
                                    @Override
                                    protected MemberVO apply(ContactsResponse response, MembersResponse membersResponse, String subject) {
                                        return new MembersMapper().mapMemberContact(response, membersResponse, subject);
                                    }
                                }
                        );
                    }
                });
    }
}
