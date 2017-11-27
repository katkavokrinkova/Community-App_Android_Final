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

import java.io.Serializable;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/16/2017.
 */

public class ReceivedNotification implements Serializable {

    public static final int PAYLOAD_TYPE_PRIVATE_MESSAGE = 0;
    public static final int PAYLOAD_TYPE_SEND_APPROVE_REQUEST = 1;
    public static final int PAYLOAD_TYPE_COMMENT = 2;
    public static final int PAYLOAD_TYPE_LIKE_POST = 4;
    public static final int PAYLOAD_TYPE_LIKE_COMMENT = 0x05;

    private final int mPayloadType;

    private BaseNotificationPayload mNotificationPayloadVO;
    private String mNotificationTitle;
    private String mNotificationMessage;

    public ReceivedNotification(int payloadType) {
        mPayloadType = payloadType;
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

    public int getPayloadType() {
        return mPayloadType;
    }

    public void setNotificationPayloadVO(BaseNotificationPayload payloadVO) {
        mNotificationPayloadVO = payloadVO;
    }

    public <NP extends BaseNotificationPayload> NP getNotificationPayloadVO() {
        return (NP) mNotificationPayloadVO;
    }
}
