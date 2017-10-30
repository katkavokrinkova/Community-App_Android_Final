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

package net.impacthub.app.usecase.features.notifications;

import net.impacthub.app.mapper.notifications.NotificationMapper;
import net.impacthub.app.model.features.notifications.NotificationResponse;
import net.impacthub.app.model.vo.notifications.NotificationWrapper;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class NotificationsUseCase extends BaseUseCaseGenerator<Single<NotificationWrapper>, NotificationResponse> {

    @Override
    public Single<NotificationWrapper> getUseCase() {
        return Single.fromCallable(new Callable<NotificationResponse>() {
            @Override
            public NotificationResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createNotificationsRequest(getUserAccount().getUserId()), NotificationResponse.class);
            }
        }).map(new Function<NotificationResponse, NotificationWrapper>() {
            @Override
            public NotificationWrapper apply(NotificationResponse notificationResponse) throws Exception {
                return new NotificationMapper().wrapNotifications(notificationResponse);
            }
        });
    }
}
