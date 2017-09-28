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

package net.impacthub.app.model.vo.contacts;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public class ConnectMemberBody {

    private final String fromContactId;
    private final String toContactId;
    private final String introMessage;

    public ConnectMemberBody(String fromContactId, String toContactId, String introMessage) {
        this.fromContactId = fromContactId;
        this.toContactId = toContactId;
        this.introMessage = introMessage;
    }
}
