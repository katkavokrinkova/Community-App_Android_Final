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

package net.impacthub.members.presenter.features.goals;

import net.impacthub.members.mapper.groups.GroupsMapper;
import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.groups.GroupsResponse;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractBigFunction;
import net.impacthub.members.presenter.rx.AbstractFunction;
import net.impacthub.members.usecase.features.contacts.DMRequestUseCase;
import net.impacthub.members.usecase.features.goals.GoalGroupsUseCase;
import net.impacthub.members.usecase.features.goals.GoalMembersUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class GoalsDetailUiPresenter extends UiPresenter<GoalsDetailUiContract> {

    public GoalsDetailUiPresenter(GoalsDetailUiContract uiContract) {
        super(uiContract);
    }

    public void loadDetails(String goalName) {
        subscribeWith(new GoalGroupsUseCase(goalName).getUseCase(), new DisposableSingleObserver<GroupsResponse>() {
            @Override
            public void onSuccess(@NonNull GroupsResponse goalsResponse) {
                List<GroupVO> groupDTOs = new GroupsMapper().mapGroups(goalsResponse);
                getUi().onLoadGroups(groupDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });


        Single<List<MemberVO>> single = new ProfileUseCase().getUseCase()
                .flatMap(new AbstractFunction<String, MemberVO, SingleSource<? extends List<MemberVO>>>(goalName) {
                    @Override
                    protected SingleSource<? extends List<MemberVO>> apply(MemberVO response, String subject) throws Exception {
                        String contactId = response.mContactId;
                        return Single.zip(
                                new DMRequestUseCase(contactId).getUseCase(),
                                new GoalMembersUseCase(subject).getUseCase(),
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
    }
}
