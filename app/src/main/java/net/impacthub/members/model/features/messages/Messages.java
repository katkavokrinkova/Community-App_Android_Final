
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

package net.impacthub.members.model.features.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Messages {

    @SerializedName("currentPageToken")
    @Expose
    private Object currentPageToken;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;
    @SerializedName("nextPageUrl")
    @Expose
    private String nextPageUrl;

    public Object getCurrentPageToken() {
        return currentPageToken;
    }

    public void setCurrentPageToken(Object currentPageToken) {
        this.currentPageToken = currentPageToken;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

}
