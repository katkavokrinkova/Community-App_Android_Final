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

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/16/2017.
 */

public class ReceivedNotification {

    private final NotificationPayloadVO mNotificationPayloadVO;
    private String mNotificationTitle;
    private String mNotificationMessage;

    public ReceivedNotification(NotificationPayloadVO payloadVO) {
        mNotificationPayloadVO = payloadVO;
    }

    public void setNotificationTitle(String notificationTitle) {
        mNotificationTitle = notificationTitle;
    }

    public void setNotificationMessage(String notificationMessage) {
        mNotificationMessage = notificationMessage;
    }

    public String getNotificationMessage() {
        return mNotificationMessage;
    }

    public String getNotificationTitle() {
        return mNotificationTitle;
    }

    public NotificationPayloadVO getNotificationPayloadVO() {
        return mNotificationPayloadVO;
    }
}
