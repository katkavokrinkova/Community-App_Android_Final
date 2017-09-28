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

package net.impacthub.app.model.vo.conversations;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/1/2017.
 */

public class MessageByUserIdBody {

    private final String body;
    private final String[] recipients;

    public MessageByUserIdBody(String body, String[] recipients) {
        this.body = body;
        this.recipients = recipients;
    }
}
