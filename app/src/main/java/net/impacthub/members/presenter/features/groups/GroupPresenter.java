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
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.features.groups.GroupsResponse;
import net.impacthub.members.model.features.profile.ProfileResponse;
import net.impacthub.members.model.features.profile.Records;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.groups.GroupsUseCase;
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

    private final UseCaseGenerator<Single<ProfileResponse>> mProfileUseCase = new ProfileUseCase();

    public GroupPresenter(GroupUiContract uiContract) {
        super(uiContract);
    }

    public void getGroups() {
        getUi().onChangeStatus(true);
        Single<GroupsResponse> single = mProfileUseCase.getUseCase()
                .flatMap(new Function<ProfileResponse, SingleSource<GroupsResponse>>() {
                    @Override
                    public SingleSource<GroupsResponse> apply(@NonNull ProfileResponse profileResponse) throws Exception {
                        Records record = profileResponse.getRecords()[0];
                        return new GroupsUseCase(record.getId()).getUseCase();
                    }
                });
        subscribeWith(single, new DisposableSingleObserver<GroupsResponse>() {
            @Override
            public void onSuccess(@NonNull GroupsResponse response) {
                List<GroupDTO> groupList = new GroupsMapper().map(response);
                getUi().onLoadGroups(groupList);
                getUi().onChangeStatus(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onChangeStatus(false);
            }
        });
    }
}
