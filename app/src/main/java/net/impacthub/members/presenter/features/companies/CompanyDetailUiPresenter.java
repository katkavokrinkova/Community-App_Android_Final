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
import net.impacthub.members.model.features.members.Member;
import net.impacthub.members.model.features.members.MemberResponse;
import net.impacthub.members.model.features.projects.ProjectResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.companies.CompanyMemberUseCase;
import net.impacthub.members.usecase.features.companies.CompanyProjectUseCase;

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
        subscribeWith(new CompanyProjectUseCase(companyId).getUseCase(), new DisposableSingleObserver<ProjectResponse>() {
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
        subscribeWith(new CompanyMemberUseCase(companyId).getUseCase(), new DisposableSingleObserver<MemberResponse>() {
            @Override
            public void onSuccess(@NonNull MemberResponse response) {
                List<MemberDTO> memberDTOs = new MembersMapper().map(response);
                getUi().onLoadMembers(memberDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
