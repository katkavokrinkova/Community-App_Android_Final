
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

package net.impacthub.app.model.features.conversations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {

    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("conversationUrl")
    @Expose
    private String conversationUrl;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("recipients")
    @Expose
    private List<Recipient> recipients = null;
    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("sentDate")
    @Expose
    private String sentDate;
    @SerializedName("url")
    @Expose
    private String url;

    public Body getBody() {
        return body;
    }

    public String getConversationUrl() {
        return conversationUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public Sender getSender() {
        return sender;
    }

    public String getSentDate() {
        return sentDate;
    }

    public String getUrl() {
        return url;
    }
}
