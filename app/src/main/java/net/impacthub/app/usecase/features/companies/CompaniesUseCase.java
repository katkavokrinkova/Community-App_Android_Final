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

package net.impacthub.app.usecase.features.companies;

import net.impacthub.app.mapper.companies.CompaniesMapper;
import net.impacthub.app.model.features.companies.CompaniesResponse;
import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class CompaniesUseCase extends BaseUseCaseGenerator<Single<List<CompanyVO>>, CompaniesResponse> {

    @Override
    public Single<List<CompanyVO>> getUseCase() {
        return Single.fromCallable(new Callable<CompaniesResponse>() {
            @Override
            public CompaniesResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createCompaniesRequest(), CompaniesResponse.class);
            }
        }).map(new Function<CompaniesResponse, List<CompanyVO>>() {
            @Override
            public List<CompanyVO> apply(@NonNull CompaniesResponse companiesResponse) throws Exception {
                return new CompaniesMapper().map(companiesResponse);
            }
        });
    }
}
