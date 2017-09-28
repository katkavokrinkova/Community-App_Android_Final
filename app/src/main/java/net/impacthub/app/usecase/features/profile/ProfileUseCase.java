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

package net.impacthub.app.usecase.features.profile;

import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.members.MembersResponse;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class ProfileUseCase extends BaseUseCaseGenerator<Single<MemberVO>, MembersResponse> {

    @Override
    public Single<MemberVO> getUseCase() {
        return Single.fromCallable(new Callable<MembersResponse>() {
            @Override
            public MembersResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createGetProfileRequest(getUserAccount().getUserId()), MembersResponse.class);
            }
        }).map(new Function<MembersResponse, MemberVO>() {
                    @Override
                    public MemberVO apply(@NonNull MembersResponse membersResponse) throws Exception {
                        return new MembersMapper().map(membersResponse);
                    }
                });
    }
}
