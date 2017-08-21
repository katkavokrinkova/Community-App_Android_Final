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

package net.impacthub.members.presenter.features.groups;

import net.impacthub.members.mapper.groups.GroupsMapper;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.model.features.groups.GroupsResponse;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.features.members.Records;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.groups.AllGroupsUseCase;
import net.impacthub.members.usecase.features.groups.YourGroupsUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class GroupPresenter extends UiPresenter<GroupUiContract> {

    private final UseCaseGenerator<Single<MembersResponse>> mProfileUseCase = new ProfileUseCase();
    private final UseCaseGenerator<Single<GroupsResponse>> mAllGroupsUseCase = new AllGroupsUseCase();

    public GroupPresenter(GroupUiContract uiContract) {
        super(uiContract);
    }

    public void getGroups() {

        subscribeWith(mAllGroupsUseCase.getUseCase(), new DisposableSingleObserver<GroupsResponse>() {
            @Override
            public void onSuccess(@NonNull GroupsResponse response) {
                List<GroupVO> groupList = new GroupsMapper().map(response);
                getUi().onLoadAllGroups(groupList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        Single<GroupsResponse> yourGroupsSingle = mProfileUseCase.getUseCase()
                .flatMap(new Function<MembersResponse, SingleSource<GroupsResponse>>() {
                    @Override
                    public SingleSource<GroupsResponse> apply(@NonNull MembersResponse profileResponse) throws Exception {
                        Records record = profileResponse.getRecords()[0];
                        return new YourGroupsUseCase(record.getId()).getUseCase();
                    }
                });
        subscribeWith(yourGroupsSingle, new DisposableSingleObserver<GroupsResponse>() {
            @Override
            public void onSuccess(@NonNull GroupsResponse response) {
                List<GroupVO> groupList = new GroupsMapper().map(response);
                getUi().onLoadYourGroups(groupList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
