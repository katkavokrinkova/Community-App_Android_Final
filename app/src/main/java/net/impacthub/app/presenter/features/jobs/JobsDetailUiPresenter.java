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
import net.impacthub.app.mapper.projects.ProjectMapper;
import net.impacthub.app.model.features.jobs.JobsResponse;
import net.impacthub.app.model.features.projects.ProjectResponse;
import net.impacthub.app.model.pojo.FilterableString;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.SimpleItem;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.features.jobs.JobRelatedJobsUseCase;
import net.impacthub.app.usecase.features.jobs.JobRelatedProjectsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class JobsDetailUiPresenter extends UiPresenter<JobsDetailUiContract> {

    public JobsDetailUiPresenter(JobsDetailUiContract uiContract) {
        super(uiContract);
    }

    public void getProjects(String jobId, String jobLocation, String jobAccountId, String jobCompanyC) {
        Single<List<ListItemType>> listSingle = Single.zip(
                new JobRelatedJobsUseCase(jobId, jobLocation, jobAccountId, jobCompanyC).getUseCase(),
                new JobRelatedProjectsUseCase(jobId).getUseCase(),
                new BiFunction<JobsResponse, ProjectResponse, List<ListItemType>>() {
                    @Override
                    public List<ListItemType> apply(@NonNull JobsResponse jobsResponse, @NonNull ProjectResponse projectResponse) throws Exception {
                        List<ListItemType> projectItemTypes = new ProjectMapper().mapAsListItemType(projectResponse);
                        if (!projectItemTypes.isEmpty()) {
                            projectItemTypes.add(0, new SimpleItem<>(new FilterableString("RELATED PROJECTS"), 0));
                        }
                        List<ListItemType> itemTypes = new JobsMapper().mapAsListItemType(jobsResponse);
                        if (itemTypes != null && !itemTypes.isEmpty()) {
                            itemTypes.add(0, new SimpleItem<>(new FilterableString("RELATED JOBS"), 0));
                            projectItemTypes.addAll(itemTypes);
                        }
                        return projectItemTypes;
                    }
                });
        subscribeWith(listSingle, new DisposableSingleObserver<List<ListItemType>>() {
            @Override
            public void onSuccess(@NonNull List<ListItemType> listItemTypes) {
                getUi().onLoadRelatedItems(listItemTypes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
