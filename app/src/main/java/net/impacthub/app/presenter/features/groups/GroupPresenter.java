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

package net.impacthub.app.presenter.features.groups;

import net.impacthub.app.mapper.groups.GroupsMapper;
import net.impacthub.app.model.features.groups.GroupsResponse;
import net.impacthub.app.model.features.groups.chatter.ChatterResponse;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.groups.AllGroupsUseCase;
import net.impacthub.app.usecase.features.groups.GetMyGroupUseCase;
import net.impacthub.app.usecase.features.groups.YourGroupsUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class GroupPresenter extends UiPresenter<GroupUiContract> {

    private final UseCaseGenerator<Single<MemberVO>> mProfileUseCase = new ProfileUseCase();
    private final UseCaseGenerator<Single<ChatterResponse>> mMyGroupsUseCase = new GetMyGroupUseCase();
    private final UseCaseGenerator<Single<GroupsResponse>> mAllGroupsUseCase = new AllGroupsUseCase();

    public GroupPresenter(GroupUiContract uiContract) {
        super(uiContract);
    }

    public void getGroups() {

        getUi().onShowProgressBar(true);
        Single<List<GroupVO>> listSingle = Single.zip(
                mMyGroupsUseCase.getUseCase(),
                mAllGroupsUseCase.getUseCase(), new BiFunction<ChatterResponse, GroupsResponse, List<GroupVO>>() {
                    @Override
                    public List<GroupVO> apply(@NonNull ChatterResponse myChatterGroupResponse, @NonNull GroupsResponse allGroupResponse) throws Exception {
                        return new GroupsMapper().mapFiltered(myChatterGroupResponse, allGroupResponse);
                    }
                });
        subscribeWith(listSingle, new DisposableSingleObserver<List<GroupVO>>() {

            @Override
            public void onSuccess(@NonNull List<GroupVO> groupVOs) {
                getUi().onShowProgressBar(false);
                getUi().onLoadAllGroups(groupVOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onShowProgressBar(false);
                getUi().onError(e);
            }
        });

        Single<List<GroupVO>> yourGroupsSingle = mProfileUseCase.getUseCase()
                .flatMap(new Function<MemberVO, SingleSource<? extends GroupsResponse>>() {
                    @Override
                    public SingleSource<? extends GroupsResponse> apply(@NonNull MemberVO memberVO) throws Exception {
                        return new YourGroupsUseCase(memberVO.mContactId).getUseCase();
                    }
                })
                .map(new Function<GroupsResponse, List<GroupVO>>() {
                    @Override
                    public List<GroupVO> apply(@NonNull GroupsResponse response) throws Exception {
                        return new GroupsMapper().mapGroups(response);
                    }
                });
        subscribeWith(yourGroupsSingle, new DisposableSingleObserver<List<GroupVO>>() {
            @Override
            public void onSuccess(@NonNull List<GroupVO> groupVOs) {
                getUi().onLoadYourGroups(groupVOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
