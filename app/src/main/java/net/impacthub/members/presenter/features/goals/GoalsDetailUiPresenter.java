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
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.model.features.groups.GroupsResponse;
import net.impacthub.members.model.features.members.MemberResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.goals.GoalGroupsUseCase;
import net.impacthub.members.usecase.features.goals.GoalMembersUseCase;

import java.util.List;

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
                List<GroupDTO> groupDTOs = new GroupsMapper().map(goalsResponse);
                getUi().onLoadGroups(groupDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        subscribeWith(new GoalMembersUseCase(goalName).getUseCase(), new DisposableSingleObserver<MemberResponse>() {
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
