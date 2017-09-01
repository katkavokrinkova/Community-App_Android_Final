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

package net.impacthub.members.model.vo.conversations;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/1/2017.
 */

public class MessageInReplyToBody {

    private final String body;
    private final String inReplyTo;

    public MessageInReplyToBody(String body, String inReplyTo) {
        this.body = body;
        this.inReplyTo = inReplyTo;
    }
}
