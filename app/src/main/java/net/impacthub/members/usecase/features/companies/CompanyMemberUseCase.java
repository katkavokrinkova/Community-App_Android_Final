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

import net.impacthub.members.model.features.members.MemberResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class CompanyMemberUseCase extends BaseUseCaseGenerator<Single<MemberResponse>, MemberResponse> {

    private final String mCompanyId;

    public CompanyMemberUseCase(String companyId) {
        mCompanyId = companyId;
    }

    @Override
    public Single<MemberResponse> getUseCase() {
        return Single.fromCallable(new Callable<MemberResponse>() {
            @Override
            public MemberResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createCompanyMemberDetailRequest(mCompanyId), MemberResponse.class);
            }
        });
    }
}