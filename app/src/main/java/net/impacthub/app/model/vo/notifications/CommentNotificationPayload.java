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
 * @date 11/17/2017.
 */

public class CommentNotificationPayload extends BaseNotificationPayload {

    private String relatedGroup;
    private String feedElementId;
    private String id;

    public String getRelatedGroup() {
        return relatedGroup;
    }

    public String getFeedElementId() {
        return feedElementId;
    }

    public String getId() {
        return id;
    }
}
