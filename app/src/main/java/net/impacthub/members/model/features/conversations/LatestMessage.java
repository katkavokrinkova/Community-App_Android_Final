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

package net.impacthub.members.model.features.conversations;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class LatestMessage {

    private Sender sender;
    private String id;
    private Body body;
    private String conversationUrl;
    private Recipients[] recipients;
    private String sentDate;
    private String conversationId;
    private String url;
    private SendingCommunity sendingCommunity;

    public Sender getSender() {
        return sender;
    }

    public String getId() {
        return id;
    }

    public Body getBody() {
        return body;
    }

    public String getConversationUrl() {
        return conversationUrl;
    }

    public Recipients[] getRecipients() {
        return recipients;
    }

    public String getSentDate() {
        return sentDate;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getUrl() {
        return url;
    }

    public SendingCommunity getSendingCommunity() {
        return sendingCommunity;
    }
}
