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
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.groups.AllGroupsUseCase;
import net.impacthub.app.usecase.features.groups.GetMyGroupUseCase;
import net.impacthub.app.usecase.features.groups.GroupsYouManage;
import net.impacthub.app.usecase.features.groups.YourGroupsUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
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

    private static final String KEY_GROUP_YOU_MANAGE = "group_you_manage";
    private static final String KEY_YOUR_GROUPS = "your_groups";

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

        Single<Map<String, List<GroupVO>>> yourGroupsSingle = mProfileUseCase.getUseCase()
                .flatMap(new Function<MemberVO, Single<Map<String, List<GroupVO>>>>() {
                    @Override
                    public Single<Map<String, List<GroupVO>>> apply(@NonNull MemberVO memberVO) throws Exception {
                        String contactId = memberVO.mContactId;
                        return Single.zip(new GroupsYouManage(contactId).getUseCase(), new YourGroupsUseCase(contactId).getUseCase(), new BiFunction<GroupsResponse, GroupsResponse, Map<String, List<GroupVO>>>() {
                            @Override
                            public Map<String, List<GroupVO>> apply(@NonNull GroupsResponse groupsResponse, @NonNull GroupsResponse groupsResponse2) throws Exception {
                                GroupsMapper mapper = new GroupsMapper();
                                List<GroupVO> groupYouManageVOs = mapper.mapGroups(groupsResponse);
                                List<GroupVO> yourGroupVOs = mapper.mapGroups(groupsResponse2);
                                Map<String, List<GroupVO>> map = new HashMap<>();
                                map.put(KEY_GROUP_YOU_MANAGE, groupYouManageVOs);
                                map.put(KEY_YOUR_GROUPS, yourGroupVOs);
                                return map;
                            }
                        });
                    }
                });
        subscribeWith(yourGroupsSingle, new DisposableSingleObserver<Map<String, List<GroupVO>>>() {

            @Override
            public void onSuccess(@NonNull Map<String, List<GroupVO>> stringListMap) {
                getUi().onLoadGroupsYouManage(stringListMap.get(KEY_GROUP_YOU_MANAGE));
                getUi().onLoadYourGroups(stringListMap.get(KEY_YOUR_GROUPS));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    public void handleFilters(FilterData filterData) {
        boolean atLeastOneFilterChecked = false;
        if (filterData != null) {
            Map<String, List<String>> filters = filterData.getFilters();
            if (filters != null) {
                for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
                    List<String> value = entry.getValue();
                    atLeastOneFilterChecked = !value.isEmpty();
                    if(atLeastOneFilterChecked) break;
                }
            }
            if(atLeastOneFilterChecked) {
                getUi().onShowTick(filters);
            } else {
                getUi().onHideTick();
            }
        } else {
            getUi().onHideTick();
        }
    }
}
