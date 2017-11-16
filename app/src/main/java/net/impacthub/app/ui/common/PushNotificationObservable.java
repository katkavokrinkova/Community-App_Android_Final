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

package net.impacthub.app.ui.common;

import net.impacthub.app.model.vo.notifications.ReceivedNotification;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/16/2017.
 */

public final class PushNotificationObservable {

    private PushNotificationObserver mNotificationObserver;

    private PushNotificationObservable(){}

    public static PushNotificationObservable getInstance() {
        return HOLDER.INSTANCE;
    }

    private final static class HOLDER {

        private final static PushNotificationObservable INSTANCE = new PushNotificationObservable();
    }

    public synchronized void setNotificationObserver(PushNotificationObserver observer) {
        mNotificationObserver = observer;
    }

    public synchronized PushNotificationObserver getNotificationObserver() {
        return mNotificationObserver;
    }

    public interface PushNotificationObserver {

        boolean onConsumePushNotification(ReceivedNotification notification);
    }
}
