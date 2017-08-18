
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

public class ConversationMessages {

    @SerializedName("conversationUrl")
    @Expose
    private String conversationUrl;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;
    @SerializedName("messages")
    @Expose
    private Messages messages;
    @SerializedName("read")
    @Expose
    private Boolean read;

    public String getConversationUrl() {
        return conversationUrl;
    }

    public void setConversationUrl(String conversationUrl) {
        this.conversationUrl = conversationUrl;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

}
