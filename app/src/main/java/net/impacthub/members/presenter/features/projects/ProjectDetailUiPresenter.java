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
import net.impacthub.members.model.features.chatterfeed.ChatterFeedResponse;
import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.jobs.JobsResponse;
import net.impacthub.members.model.features.objectives.ObjectivesResponse;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.vo.chatter.ChatterVO;
import net.impacthub.members.model.vo.jobs.JobVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractBigFunction;
import net.impacthub.members.presenter.rx.AbstractFunction;
import net.impacthub.members.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.members.usecase.features.groups.ChatterFeedUseCase;
import net.impacthub.members.usecase.features.members.GetMemberByUserIdUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;
import net.impacthub.members.usecase.features.projects.ProjectJobsUseCase;
import net.impacthub.members.usecase.features.projects.ProjectMembersUseCase;
import net.impacthub.members.usecase.features.projects.ProjectObjectivesUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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

        Single<List<ChatterVO>> chatterSingle = new ChatterFeedUseCase(feedId).getUseCase()
                .map(new Function<ChatterFeedResponse, List<ChatterVO>>() {
                    @Override
                    public List<ChatterVO> apply(@NonNull ChatterFeedResponse chatterFeedResponse) throws Exception {
                        return new ChatterMapper().map(chatterFeedResponse);
                    }
                });
        getUi().onShowProgressBar(true);
        subscribeWith(chatterSingle, new DisposableSingleObserver<List<ChatterVO>>() {
            @Override
            public void onSuccess(@NonNull List<ChatterVO> chatterVOs) {
                getUi().onLoadChatterFeed(chatterVOs);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });

        Single<List<ListItemType>> listSingle = new ProjectObjectivesUseCase(projectId).getUseCase()
                .map(new Function<ObjectivesResponse, List<ListItemType>>() {
                    @Override
                    public List<ListItemType> apply(@NonNull ObjectivesResponse objectivesResponse) throws Exception {
                        return new ObjectivesMapper().mapAsListItem(objectivesResponse);
                    }
                });

        subscribeWith(listSingle, new DisposableSingleObserver<List<ListItemType>>() {

            @Override
            public void onSuccess(@NonNull List<ListItemType> listItemTypes) {
                getUi().onLoadObjectives(listItemTypes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        Single<List<MemberVO>> single = new ProfileUseCase().getUseCase()
                .flatMap(new AbstractFunction<String, MemberVO, SingleSource<? extends List<MemberVO>>>(projectId) {
                    @Override
                    protected SingleSource<? extends List<MemberVO>> apply(MemberVO response, String subject) throws Exception {
                        String contactId = response.mContactId;
                        return Single.zip(
                                new DMGetContactsUseCase(contactId).getUseCase(),
                                new ProjectMembersUseCase(subject).getUseCase(),
                                new AbstractBigFunction<String, ContactsResponse, List<MemberVO>, List<MemberVO>>(contactId) {
                                    @Override
                                    protected List<MemberVO> apply(ContactsResponse response, List<MemberVO> memberVOs, String subject) {
                                        return new MembersMapper().mapMembersList(memberVOs, response, subject);
                                    }
                                }
                        );
                    }
                });

        subscribeWith(single, new DisposableSingleObserver<List<MemberVO>>() {
            @Override
            public void onSuccess(@NonNull List<MemberVO> memberVOs) {
                getUi().onLoadMembers(memberVOs);
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

    public void getMemberBy(String id) {
        getUi().onShowProgressBar(true);
        subscribeWith(new GetMemberByUserIdUseCase(id).getUseCase(), new DisposableSingleObserver<MemberVO>() {
            @Override
            public void onSuccess(@NonNull MemberVO memberVO) {
                getUi().onLoadMember(memberVO);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }
}
