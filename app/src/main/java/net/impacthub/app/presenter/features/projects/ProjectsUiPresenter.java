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

package net.impacthub.app.presenter.features.projects;

import net.impacthub.app.mapper.projects.ProjectMapper;
import net.impacthub.app.model.features.projects.ProjectResponse;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;
import net.impacthub.app.usecase.features.projects.AllProjectsUseCase;
import net.impacthub.app.usecase.features.projects.YourProjectsUseCase;

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

    private final UseCaseGenerator<Single<MemberVO>> mProfileUseCase = new ProfileUseCase();
    private final UseCaseGenerator<Single<ProjectResponse>> mAllProjectsUseCase = new AllProjectsUseCase();

    public ProjectsUiPresenter(ProjectsUiContract uiContract) {
        super(uiContract);
    }

    public void getProjects() {
        Single<List<ProjectVO>> allProjectsSingle = mAllProjectsUseCase.getUseCase()
                .map(new Function<ProjectResponse, List<ProjectVO>>() {
                    @Override
                    public List<ProjectVO> apply(@NonNull ProjectResponse projectResponse) throws Exception {
                        return new ProjectMapper().map(projectResponse);
                    }
                });
        getUi().onShowProgressBar(true);
        subscribeWith(allProjectsSingle, new DisposableSingleObserver<List<ProjectVO>>() {
            @Override
            public void onSuccess(@NonNull List<ProjectVO> projectVOs) {
                getUi().onLoadAllProjects(projectVOs);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });

        Single<List<ProjectVO>> yourProjectsSingle = mProfileUseCase.getUseCase()
                .flatMap(new Function<MemberVO, SingleSource<? extends ProjectResponse>>() {
                    @Override
                    public SingleSource<? extends ProjectResponse> apply(@NonNull MemberVO memberVO) throws Exception {
                        return new YourProjectsUseCase(memberVO.mContactId).getUseCase();
                    }
                })
                .map(new Function<ProjectResponse, List<ProjectVO>>() {
                    @Override
                    public List<ProjectVO> apply(@NonNull ProjectResponse projectResponse) throws Exception {
                        return new ProjectMapper().map(projectResponse);
                    }
                });
        subscribeWith(yourProjectsSingle, new DisposableSingleObserver<List<ProjectVO>>() {
            @Override
            public void onSuccess(@NonNull List<ProjectVO> projectVOs) {
                getUi().onLoadYourProjects(projectVOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

    }
}
