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

package net.impacthub.members.presenter.features.jobs;

import net.impacthub.members.mapper.projects.ProjectMapper;
import net.impacthub.members.model.features.projects.ProjectResponse;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.jobs.JobProjectsUseCase;

import java.util.List;

import io.reactivex.annotations.NonNull;
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

    public void getProjects(String jobId) {
        subscribeWith(new JobProjectsUseCase(jobId).getUseCase(), new DisposableSingleObserver<ProjectResponse>() {
            @Override
            public void onSuccess(@NonNull ProjectResponse projectResponse) {
                List<ListItemType> itemTypes = new ProjectMapper().mapAsListItemType(projectResponse);
                getUi().onLoadRelatedProjects(itemTypes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
