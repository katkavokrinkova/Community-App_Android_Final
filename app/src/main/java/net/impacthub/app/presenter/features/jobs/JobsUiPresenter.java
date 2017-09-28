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

package net.impacthub.app.presenter.features.jobs;

import net.impacthub.app.mapper.jobs.JobsMapper;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.jobs.JobVO;
import net.impacthub.app.model.features.jobs.JobsResponse;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.jobs.JobsUseCase;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class JobsUiPresenter extends UiPresenter<JobsUiContract> {

    private final UseCaseGenerator<Single<JobsResponse>> mJobsUseCase = new JobsUseCase();

    public JobsUiPresenter(JobsUiContract uiContract) {
        super(uiContract);
    }

    public void getJobs() {
        getUi().onShowProgressBar(true);
        Single<List<JobVO>> jobsSingle = mJobsUseCase.getUseCase()
                .map(new Function<JobsResponse, List<JobVO>>() {
                    @Override
                    public List<JobVO> apply(@NonNull JobsResponse jobsResponse) throws Exception {
                        return new JobsMapper().map(jobsResponse);
                    }
                });
        subscribeWith(jobsSingle, new DisposableSingleObserver<List<JobVO>>() {
            @Override
            public void onSuccess(@NonNull List<JobVO> jobVOs) {
                getUi().onLoadJobs(jobVOs);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }

    public void handleFilters(FilterData filterData) {
        boolean atLeastOneFilterChecked = false;
        if (filterData != null) {
            Map<String, List<String>> filters = filterData.getFilters();
            if (filters != null) {
                for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
                    List<String> value = entry.getValue();
                    atLeastOneFilterChecked = !value.isEmpty();
                    if(atLeastOneFilterChecked) break;
                }
            }
            if(atLeastOneFilterChecked) {
                getUi().onShowTick(filters);
            } else {
                getUi().onHideTick();
            }
        } else {
            getUi().onHideTick();
        }
    }
}
