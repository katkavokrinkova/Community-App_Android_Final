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

package net.impacthub.members.usecase.features.companies;

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class CompanyMembersUseCase extends BaseUseCaseGenerator<Single<List<MemberVO>>, MembersResponse> {

    private final String mCompanyId;

    public CompanyMembersUseCase(String companyId) {
        mCompanyId = companyId;
    }

    @Override
    public Single<List<MemberVO>> getUseCase() {
        return Single.fromCallable(new Callable<MembersResponse>() {
            @Override
            public MembersResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createCompanyMemberDetailRequest(mCompanyId), MembersResponse.class);
            }
        }).map(new Function<MembersResponse, List<MemberVO>>() {
            @Override
            public List<MemberVO> apply(@NonNull MembersResponse membersResponse) throws Exception {
                return new MembersMapper().mapMembers(membersResponse);
            }
        });
    }
}