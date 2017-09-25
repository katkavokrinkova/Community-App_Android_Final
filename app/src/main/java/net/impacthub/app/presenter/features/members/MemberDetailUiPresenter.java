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

package net.impacthub.app.presenter.features.members;

import com.google.gson.Gson;

import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.members.Affiliations;
import net.impacthub.app.model.features.members.Skills;
import net.impacthub.app.model.pojo.FilterableString;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.SimpleItem;
import net.impacthub.app.model.vo.contacts.UpdateContactBody;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.AbstractFunction;
import net.impacthub.app.presenter.rx.DisposableSingleObserverAdapter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.contacts.UpdateDMRequestStatusUseCase;
import net.impacthub.app.usecase.features.members.AboutMemberUseCase;
import net.impacthub.app.usecase.features.members.MemberSkillsUseCase;

import org.json.JSONObject;

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

    public void loadDetails(String memberId, String aboutMe) {

        Single<List<ListItemType>> listSingle = new MemberSkillsUseCase(memberId).getUseCase()
                .map(new AbstractFunction<String, Skills, List<ListItemType>>(aboutMe) {
                    @Override
                    protected List<ListItemType> apply(Skills response, String subject) throws Exception {
                        List<ListItemType> listItemTypes = new MembersMapper().mapAsListItemType(response);
                        listItemTypes.add(0, new SimpleItem<>(new FilterableString("About Me"), 0));
                        listItemTypes.add(1, new SimpleItem<>(new FilterableString(subject), 1));
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

    public void updateContactRequest(String id, String pushUserId, String status, int code) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(new UpdateContactBody(id, status, pushUserId)));
            subscribeWith(new UpdateDMRequestStatusUseCase(jsonObject).getUseCase(), new DisposableSingleObserverAdapter<Integer, Object>(code) {
                @Override
                protected void onSuccess(Object o, Integer subject) {
                    if ("Success".equals(o)) {
                        switch (subject) {
                            case 0:
                                getUi().onMemberApproved();
                                break;
                            default:
                                getUi().onMemberDeclined();
                        }
                    } else {
                        getUi().onError(new Throwable(o.toString()));
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    getUi().onError(e);
                }
            });
        } catch (Exception e) {
        }
    }
}
