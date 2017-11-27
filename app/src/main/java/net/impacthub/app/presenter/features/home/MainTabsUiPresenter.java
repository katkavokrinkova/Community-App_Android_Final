package net.impacthub.app.presenter.features.home;

import net.impacthub.app.mapper.groups.GroupsMapper;
import net.impacthub.app.mapper.projects.ProjectMapper;
import net.impacthub.app.model.features.projects.ProjectRecords;
import net.impacthub.app.model.features.projects.ProjectResponse;
import net.impacthub.app.model.vo.contacts.ContactVO;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberStatus;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.notifications.ProjectOrGroupWrapper;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.AbstractFunction;
import net.impacthub.app.usecase.features.contacts.GetContactByUserIdUseCase;
import net.impacthub.app.usecase.features.notifications.GroupOrProjectUseCase;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by filippo on 27/11/2017.
 */

public class MainTabsUiPresenter extends UiPresenter<MainTabsUiContract> {

    public MainTabsUiPresenter(MainTabsUiContract uiContract) {
        super(uiContract);
    }

    public void getGroupOrProjectBy(String chatterGroupId, String relatedId) {
        Single<ProjectOrGroupWrapper> single = new GroupOrProjectUseCase(chatterGroupId).getUseCase()
                .map(new AbstractFunction<String, ProjectResponse, ProjectOrGroupWrapper>(relatedId) {
                    @Override
                    protected ProjectOrGroupWrapper apply(ProjectResponse response, String subject) throws Exception {
                        ProjectVO projectVO = null;
                        GroupVO groupVO = null;

                        ProjectRecords[] records = response.getRecords();
                        if (records != null && records.length > 0) {
                            ProjectRecords record = records[0];
                            String directory_style__c = record.getDirectory_Style__c();
                            if ("Group".equalsIgnoreCase(directory_style__c)) {
                                groupVO = new GroupsMapper().mapAsGroup(record);
                                groupVO.mRelatedId = subject;
                            } else if ("Project".equalsIgnoreCase(directory_style__c)) {
                                projectVO = new ProjectMapper().mapAsProject(record);
                                projectVO.mRelatedId = subject;
                            }
                        }

                        return new ProjectOrGroupWrapper(projectVO, groupVO);
                    }
                });
        subscribeWith(single, new DisposableSingleObserver<ProjectOrGroupWrapper>() {
            @Override
            public void onSuccess(@NonNull ProjectOrGroupWrapper projectOrGroupWrapper) {
                GroupVO groupVO = projectOrGroupWrapper.getGroupVO();
                ProjectVO projectVO = projectOrGroupWrapper.getProjectVO();
                if (groupVO != null) {
                    getUi().onLoadGroup(groupVO);
                } else if (projectVO != null) {
                    getUi().onLoadProject(projectVO);
                } else {
                    getUi().onError(new Throwable("Can't load notification."));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    public void getContactMemberBy(String recipientUserId) {
        subscribeWith(new GetContactByUserIdUseCase(recipientUserId).getUseCase(), new DisposableSingleObserver<ContactVO>() {
            @Override
            public void onSuccess(ContactVO contactVO) {
                MemberVO memberVO = contactVO.mMember;
                if(memberVO == null) {
                    getUi().onError(new Throwable("Can't load notification."));
                } else if (MemberStatus.OUTSTANDING == memberVO.mMemberStatus) {
                    getUi().onLoadContact(contactVO);
                } else {
                    getUi().onLoadMember(memberVO);
                }
            }

            @Override
            public void onError(Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
