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

public class NotificationPayloadVO {

    private String conversationId;
    private String profilePicUrl;
    private String type;

    public String getConversationId() {
        return conversationId;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "NotificationPayloadVO [conversationId = " + conversationId + ", profilePicUrl = " + profilePicUrl + ", type = " + type + "]";
    }
}
