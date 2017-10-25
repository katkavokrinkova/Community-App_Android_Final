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

package net.impacthub.app.model.features.chatter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/23/2017.
 */

public class GroupPostPayload {

    private final PostBody body;
    private final String feedElementType;
    private final String subjectId;

    public GroupPostPayload(PostBody body, String feedElementType, String subjectId) {
        this.body = body;
        this.feedElementType = feedElementType;
        this.subjectId = subjectId;
    }
}
