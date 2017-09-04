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

package net.impacthub.members.presenter.features.notifcations;

import net.impacthub.members.mapper.notifications.NotificationMapper;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.vo.notifications.NotificationVO;
import net.impacthub.members.model.features.notifications.NotificationResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.members.GetMemberByUserIdUseCase;
import net.impacthub.members.usecase.features.notifications.NotificationsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
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
}
