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
import net.impacthub.members.model.pojo.SimpleItem;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.jobs.JobProjectsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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
        Single<List<ListItemType>> jobProjectsSingle = new JobProjectsUseCase(jobId).getUseCase()
                .map(new Function<ProjectResponse, List<ListItemType>>() {
                    @Override
                    public List<ListItemType> apply(@NonNull ProjectResponse projectResponse) throws Exception {
                        List<ListItemType> itemTypes = new ProjectMapper().mapAsListItemType(projectResponse);
                        if (itemTypes != null && !itemTypes.isEmpty()) {
                            itemTypes.add(0, new SimpleItem<String>("RELATED JOBS", 0));
                        }
                        return itemTypes;
                    }
                });
        subscribeWith(jobProjectsSingle, new DisposableSingleObserver<List<ListItemType>>() {
            @Override
            public void onSuccess(@NonNull List<ListItemType> listItemTypes) {
                getUi().onLoadRelatedProjects(listItemTypes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
