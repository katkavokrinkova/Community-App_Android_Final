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

import net.impacthub.members.mapper.chatter.ChatterMapper;
import net.impacthub.members.mapper.jobs.JobsMapper;
import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.mapper.objectives.ObjectivesMapper;
import net.impacthub.members.model.vo.chatter.ChatterVO;
import net.impacthub.members.model.vo.jobs.JobVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.features.chatterfeed.FeedElements;
import net.impacthub.members.model.features.jobs.JobsResponse;
import net.impacthub.members.model.features.objectives.ObjectivesResponse;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.pojo.ListItem;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.groups.ChatterFeedUseCase;
import net.impacthub.members.usecase.features.members.GetMemberByUserIdUseCase;
import net.impacthub.members.usecase.features.projects.ProjectJobsUseCase;
import net.impacthub.members.usecase.features.projects.ProjectMembersUseCase;
import net.impacthub.members.usecase.features.projects.ProjectObjectivesUseCase;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class ProjectDetailUiPresenter extends UiPresenter<ProjectDetailUiContract> {

    public ProjectDetailUiPresenter(ProjectDetailUiContract uiContract) {
        super(uiContract);
    }

    public void loadDetails(String feedId, String projectId) {

        subscribeWith(new ChatterFeedUseCase(feedId).getUseCase(), new DisposableSingleObserver<FeedElements>() {
            @Override
            public void onSuccess(@NonNull FeedElements feedElements) {
                List<ChatterVO> chatterDTOs = new ChatterMapper().map(feedElements);
                getUi().onLoadChatterFeed(chatterDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        subscribeWith(new ProjectObjectivesUseCase(projectId).getUseCase(), new DisposableSingleObserver<ObjectivesResponse>() {
            @Override
            public void onSuccess(@NonNull ObjectivesResponse response) {
//                List<ObjectiveVO> objectiveDTOs = new ObjectivesMapper().map(response);
                List<ListItem<?>> infoList = new ObjectivesMapper().mapAsListItem(response);
                getUi().onLoadObjectives(infoList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        subscribeWith(new ProjectMembersUseCase(projectId).getUseCase(), new DisposableSingleObserver<MembersResponse>() {
            @Override
            public void onSuccess(@NonNull MembersResponse memberResponse) {
                List<MemberVO> memberDTOs = new MembersMapper().mapMembers(memberResponse);
                getUi().onLoadMembers(memberDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
        subscribeWith(new ProjectJobsUseCase(projectId).getUseCase(), new DisposableSingleObserver<JobsResponse>() {
            @Override
            public void onSuccess(@NonNull JobsResponse jobsResponse) {
                List<JobVO> jobDTOs = new JobsMapper().map(jobsResponse);
                getUi().onLoadJobs(jobDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    public void loadMember(String userId) {
        subscribeWith(new GetMemberByUserIdUseCase(userId).getUseCase(), new DisposableSingleObserver<MembersResponse>() {
            @Override
            public void onSuccess(@NonNull MembersResponse membersResponse) {
                MemberVO memberDTO = new MembersMapper().map(membersResponse);
                getUi().onLoadMember(memberDTO);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
