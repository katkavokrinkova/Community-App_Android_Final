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

package net.impacthub.members.presenter.features.companies;

import net.impacthub.members.mapper.companies.CompaniesMapper;
import net.impacthub.members.model.vo.companies.CompanyVO;
import net.impacthub.members.model.features.companies.CompaniesResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.companies.CompaniesUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
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
        getUi().onChangeStatus(true);
        subscribeWith(mCompaniesUseCase.getUseCase(), new DisposableSingleObserver<CompaniesResponse>() {
            @Override
            public void onSuccess(@NonNull CompaniesResponse response) {
                List<CompanyVO> companies = new CompaniesMapper().map(response);
                getUi().onLoadCompanies(companies);
                getUi().onChangeStatus(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onChangeStatus(false);
            }
        });
    }
}
