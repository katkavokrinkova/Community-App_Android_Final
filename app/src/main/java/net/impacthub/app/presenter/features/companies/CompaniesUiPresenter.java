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

package net.impacthub.app.presenter.features.companies;

import net.impacthub.app.mapper.companies.CompaniesMapper;
import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.model.features.companies.CompaniesResponse;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.companies.CompaniesUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class CompaniesUiPresenter extends UiPresenter<CompaniesUiContract> {

    private final UseCaseGenerator<Single<CompaniesResponse>> mCompaniesUseCase = new CompaniesUseCase();

    public CompaniesUiPresenter(CompaniesUiContract uiContract) {
        super(uiContract);
    }

    public void getCompanies() {
        getUi().onShowProgressBar(true);
        Single<List<CompanyVO>> companiesSingle = mCompaniesUseCase.getUseCase()
                .map(new Function<CompaniesResponse, List<CompanyVO>>() {
                    @Override
                    public List<CompanyVO> apply(@NonNull CompaniesResponse companiesResponse) throws Exception {
                        return new CompaniesMapper().map(companiesResponse);
                    }
                });
        subscribeWith(companiesSingle, new DisposableSingleObserver<List<CompanyVO>>() {
            @Override
            public void onSuccess(@NonNull List<CompanyVO> companyVOs) {
                getUi().onLoadCompanies(companyVOs);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }
}
