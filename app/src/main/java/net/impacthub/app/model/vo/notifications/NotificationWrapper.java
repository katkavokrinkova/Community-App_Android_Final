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

package net.impacthub.app.model.vo.notifications;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/27/2017.
 */

public class NotificationWrapper {

    private final List<NotificationVO> mNotificationVOS;
    private final int mUnreadNotificationCount;

    public NotificationWrapper(List<NotificationVO> notificationVOS, int unreadNotificationCount) {
        mNotificationVOS = notificationVOS;
        mUnreadNotificationCount = unreadNotificationCount;
    }

    public int getmUnreadNotificationCount() {
        return mUnreadNotificationCount;
    }

    public List<NotificationVO> getNotificationVOS() {
        return mNotificationVOS;
    }
}
