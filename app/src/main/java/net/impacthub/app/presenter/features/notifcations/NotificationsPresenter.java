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

package net.impacthub.app.presenter.features.notifcations;

import net.impacthub.app.mapper.groups.GroupsMapper;
import net.impacthub.app.mapper.notifications.NotificationMapper;
import net.impacthub.app.mapper.projects.ProjectMapper;
import net.impacthub.app.model.features.projects.ProjectRecords;
import net.impacthub.app.model.features.projects.ProjectResponse;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.notifications.NotificationVO;
import net.impacthub.app.model.features.notifications.NotificationResponse;
import net.impacthub.app.model.vo.notifications.ProjectOrGroupWrapper;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.members.GetMemberByUserIdUseCase;
import net.impacthub.app.usecase.features.notifications.GroupOrProjectUseCase;
import net.impacthub.app.usecase.features.notifications.MarkNotificationReadUseCase;
import net.impacthub.app.usecase.features.notifications.NotificationsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class NotificationsPresenter extends UiPresenter<NotificationsUiContract> {

    private final UseCaseGenerator<Single<NotificationResponse>> mNotificationUseCase = new NotificationsUseCase();

    public NotificationsPresenter(NotificationsUiContract uiContract) {
        super(uiContract);
    }

    public void getNotifications() {
        subscribeWith(mNotificationUseCase.getUseCase(), new DisposableSingleObserver<NotificationResponse>() {
            @Override
            public void onSuccess(@NonNull NotificationResponse response) {
                List<NotificationVO> notificationDTOList = new NotificationMapper().map(response);
                getUi().onLoadNotifications(notificationDTOList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    public void getMemberBy(String id) {
        getUi().onShowProgressBar(true);
        subscribeWith(new GetMemberByUserIdUseCase(id).getUseCase(), new DisposableSingleObserver<MemberVO>() {
            @Override
            public void onSuccess(@NonNull MemberVO memberVO) {
                getUi().onLoadMember(memberVO);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }

    public void getGroupOrProjectBy(String chatterGroupId) {
        getUi().onShowProgressBar(true);
        Single<ProjectOrGroupWrapper> single = new GroupOrProjectUseCase(chatterGroupId).getUseCase()
                .map(new Function<ProjectResponse, ProjectOrGroupWrapper>() {
                    @Override
                    public ProjectOrGroupWrapper apply(@NonNull ProjectResponse projectResponse) throws Exception {

                        ProjectVO projectVO = null;
                        GroupVO groupVO = null;

                        ProjectRecords[] records = projectResponse.getRecords();
                        if (records != null && records.length > 0) {
                            ProjectRecords record = records[0];
                            String directory_style__c = record.getDirectory_Style__c();
                            if ("Group".equalsIgnoreCase(directory_style__c)) {
                                groupVO = new GroupsMapper().mapAsGroup(record);
                            } else if ("Project".equalsIgnoreCase(directory_style__c)) {
                                projectVO = new ProjectMapper().mapAsProject(record);
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
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }

    public void setNotificationRead(String notificationId) {
        subscribeWith(new MarkNotificationReadUseCase(notificationId).getUseCase(), new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(Object o) {
                //Nothing
            }

            @Override
            public void onError(Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
