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

package net.impacthub.members.presenter.features.members;

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.features.members.Affiliations;
import net.impacthub.members.model.features.members.Skills;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.pojo.SimpleItem;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractFunction;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.members.AboutMemberUseCase;
import net.impacthub.members.usecase.features.members.GetMemberByUserIdUseCase;
import net.impacthub.members.usecase.features.members.MemberSkillsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

public class MemberDetailUiPresenter extends UiPresenter<MemberDetailUiContract> {

    public MemberDetailUiPresenter(MemberDetailUiContract uiContract) {
        super(uiContract);
    }

    public void loadDetails(String memberId, String statusUpdate) {

        Single<List<ListItemType>> listSingle = new MemberSkillsUseCase(memberId).getUseCase()
                .map(new AbstractFunction<String, Skills, List<ListItemType>>(statusUpdate) {
                    @Override
                    protected List<ListItemType> apply(Skills response, String subject) throws Exception {
                        List<ListItemType> listItemTypes = new MembersMapper().mapAsListItemType(response);
                        listItemTypes.add(0, new SimpleItem<String>("My Skills", 0));
                        listItemTypes.add(0, new SimpleItem<String>("About", 0));
                        listItemTypes.add(1, new SimpleItem<String>(subject, 1));
                        return listItemTypes;
                    }
                });
        subscribeWith(listSingle, new DisposableSingleObserver<List<ListItemType>>() {

            @Override
            public void onSuccess(@NonNull List<ListItemType> listItemTypes) {
                getUi().onLoadExtraInfo(listItemTypes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        UseCaseGenerator<Single<Affiliations>> mAboutUseCase = new AboutMemberUseCase(memberId);
        subscribeWith(mAboutUseCase.getUseCase(), new DisposableSingleObserver<Affiliations>() {
            @Override
            public void onSuccess(@NonNull Affiliations response) {
                List<ProjectVO> memberProjectDTOs = new MembersMapper().mapProjects(response);
                getUi().onLoadProjects(memberProjectDTOs);
                List<GroupVO> memberGroupDTOs = new MembersMapper().mapGroups(response);
                getUi().onLoadGroups(memberGroupDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    public void getMember(String contactID) {
        getUi().onChangeStatus(true);
        subscribeWith(new GetMemberByUserIdUseCase(contactID).getUseCase(), new DisposableSingleObserver<MemberVO>() {
            @Override
            public void onSuccess(@NonNull MemberVO memberVO) {
                getUi().onLoadMember(memberVO);
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
