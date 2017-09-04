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

package net.impacthub.members.model.features.push;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class PushBody {

    private final String fromUserId;
    private final String toUserIds;
    private final String pushType;
    private final String relatedId;

    public PushBody(String fromUserId, String toUserIds, String pushType, String relatedId) {
        this.fromUserId = fromUserId;
        this.toUserIds = toUserIds;
        this.pushType = pushType;
        this.relatedId = relatedId;
    }
}
