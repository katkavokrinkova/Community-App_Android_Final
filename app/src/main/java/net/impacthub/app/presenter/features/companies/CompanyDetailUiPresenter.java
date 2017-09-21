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

package net.impacthub.app.presenter.features.companies;

import net.impacthub.app.mapper.companies.CompaniesMapper;
import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.mapper.projects.ProjectMapper;
import net.impacthub.app.model.features.companies.services.ServicesResponse;
import net.impacthub.app.model.features.contacts.ContactsResponse;
import net.impacthub.app.model.features.projects.ProjectResponse;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.SimpleItem;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.AbstractBigFunction;
import net.impacthub.app.presenter.rx.AbstractFunction;
import net.impacthub.app.usecase.features.companies.CompanyMembersUseCase;
import net.impacthub.app.usecase.features.companies.CompanyProjectsUseCase;
import net.impacthub.app.usecase.features.companies.CompanyServicesUseCase;
import net.impacthub.app.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

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

public class CompanyDetailUiPresenter extends UiPresenter<CompanyDetailUiContract> {

    public CompanyDetailUiPresenter(CompanyDetailUiContract uiContract) {
        super(uiContract);
    }

    public void loadDetails(String companyId) {
        subscribeWith(new CompanyServicesUseCase(companyId).getUseCase(), new DisposableSingleObserver<ServicesResponse>() {
                    @Override
                    public void onSuccess(@NonNull ServicesResponse response) {
                        List<ListItemType> listItemTypes = new CompaniesMapper().mapAsListItemType(response);
                        listItemTypes.add(0, new SimpleItem<String>("Our Services", 0));
                        getUi().onLoadCompanyServices(listItemTypes);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getUi().onError(e);
                    }
                });
        subscribeWith(new CompanyProjectsUseCase(companyId).getUseCase(), new DisposableSingleObserver<ProjectResponse>() {
            @Override
            public void onSuccess(@NonNull ProjectResponse response) {
                List<ProjectVO> projectDTOs = new ProjectMapper().map(response);
                getUi().onLoadProjects(projectDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        Single<List<MemberVO>> single = new ProfileUseCase().getUseCase()
                .flatMap(new AbstractFunction<String, MemberVO, SingleSource<? extends List<MemberVO>>>(companyId) {
                    @Override
                    protected SingleSource<? extends List<MemberVO>> apply(MemberVO response, String subject) throws Exception {
                        String contactId = response.mContactId;
                        return Single.zip(
                                new DMGetContactsUseCase(contactId).getUseCase(),
                                new CompanyMembersUseCase(subject).getUseCase(),
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
