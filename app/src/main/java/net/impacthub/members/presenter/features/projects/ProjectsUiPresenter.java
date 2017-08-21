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

package net.impacthub.members.presenter.features.projects;

import net.impacthub.members.mapper.projects.ProjectMapper;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.features.members.Records;
import net.impacthub.members.model.features.projects.ProjectResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;
import net.impacthub.members.usecase.features.projects.AllProjectsUseCase;
import net.impacthub.members.usecase.features.projects.YourProjectsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/15/2017.
 */

public class ProjectsUiPresenter extends UiPresenter<ProjectsUiContract> {

    private final UseCaseGenerator<Single<MembersResponse>> mProfileUseCase = new ProfileUseCase();
    private final UseCaseGenerator<Single<ProjectResponse>> mAllProjectsUseCase = new AllProjectsUseCase();

    public ProjectsUiPresenter(ProjectsUiContract uiContract) {
        super(uiContract);
    }

    public void getProjects() {
        subscribeWith(mAllProjectsUseCase.getUseCase(), new DisposableSingleObserver<ProjectResponse>() {
            @Override
            public void onSuccess(@NonNull ProjectResponse response) {
                List<ProjectVO> projectDTOs = new ProjectMapper().map(response);
                getUi().onLoadAllProjects(projectDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        Single<ProjectResponse> single = mProfileUseCase.getUseCase()
                .flatMap(new Function<MembersResponse, SingleSource<ProjectResponse>>() {
                    @Override
                    public SingleSource<ProjectResponse> apply(@NonNull MembersResponse profileResponse) throws Exception {
                        Records record = profileResponse.getRecords()[0];
                        return new YourProjectsUseCase(record.getId()).getUseCase();
                    }
                });
        subscribeWith(single, new DisposableSingleObserver<ProjectResponse>() {
            @Override
            public void onSuccess(@NonNull ProjectResponse projectResponse) {
                List<ProjectVO> projectDTOs = new ProjectMapper().map(projectResponse);
                getUi().onLoadYourProjects(projectDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

    }
}
