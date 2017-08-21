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

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.mapper.projects.ProjectMapper;
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.features.projects.ProjectResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.companies.CompanyMembersUseCase;
import net.impacthub.members.usecase.features.companies.CompanyProjectsUseCase;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class CompanyDetailUiPresenter extends UiPresenter<CompanyDetailUiContract> {

    public CompanyDetailUiPresenter(CompanyDetailUiContract uiContract) {
        super(uiContract);
    }

    public void loadDetails(String companyId) {
        subscribeWith(new CompanyProjectsUseCase(companyId).getUseCase(), new DisposableSingleObserver<ProjectResponse>() {
            @Override
            public void onSuccess(@NonNull ProjectResponse response) {
                List<ProjectDTO> projectDTOs = new ProjectMapper().map(response);
                getUi().onLoadProjects(projectDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
        subscribeWith(new CompanyMembersUseCase(companyId).getUseCase(), new DisposableSingleObserver<MembersResponse>() {
            @Override
            public void onSuccess(@NonNull MembersResponse response) {
                List<MemberDTO> memberDTOs = new MembersMapper().mapMembers(response);
                getUi().onLoadMembers(memberDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
