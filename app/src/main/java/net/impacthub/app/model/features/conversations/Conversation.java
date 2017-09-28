
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

public class Conversation {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("latestMessage")
    @Expose
    private Message latestMessage;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;
    @SerializedName("read")
    @Expose
    private boolean read;
    @SerializedName("url")
    @Expose
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message getLatestMessage() {
        return latestMessage;
    }

    public List<Member> getMembers() {
        return members;
    }

    public boolean isRead() {
        return read;
    }

    public String getUrl() {
        return url;
    }
}
