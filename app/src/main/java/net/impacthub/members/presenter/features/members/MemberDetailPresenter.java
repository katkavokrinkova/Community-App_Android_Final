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
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.dto.members.SkillsDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.model.features.members.Affiliations;
import net.impacthub.members.model.features.members.Skill;
import net.impacthub.members.model.features.members.Skills;
import net.impacthub.members.model.pojo.ListItem;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.members.AboutMemberUseCase;
import net.impacthub.members.usecase.features.members.MemberSkillsUseCase;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

public class MemberDetailPresenter extends UiPresenter<MemberDetailUiContract> {

    public MemberDetailPresenter(MemberDetailUiContract uiContract) {
        super(uiContract);
    }

    public void loadDetails(String memberId) {
        UseCaseGenerator<Single<Skills>> mSkillsUseCase = new MemberSkillsUseCase(memberId);
        subscribeWith(mSkillsUseCase.getUseCase(), new DisposableSingleObserver<Skills>() {
            @Override
            public void onSuccess(@NonNull Skills skills) {
                List<ListItem<?>> infoList = new LinkedList<>();

                ListItem<String> titleSkills = new ListItem<>(ListItem.TYPE_ONE);
                titleSkills.setModel("My Skills");
                infoList.add(titleSkills);
                new MembersMapper().appendSkills(infoList, skills);
                getUi().onLoadExtraInfo(infoList);
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
                List<ProjectDTO> memberProjectDTOs = new MembersMapper().mapProjects(response);
                getUi().onLoadProjects(memberProjectDTOs);
                List<GroupDTO> memberGroupDTOs = new MembersMapper().mapGroups(response);
                getUi().onLoadGroups(memberGroupDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
