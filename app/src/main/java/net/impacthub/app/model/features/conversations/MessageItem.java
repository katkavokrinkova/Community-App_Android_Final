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

import net.impacthub.app.model.pojo.Searchable;
import net.impacthub.app.utilities.TextUtils;

import java.util.List;
import java.util.Map;

public class MessageItem implements Searchable {

    private String senderId;
    private String senderImageUrl;
    private String senderName;
    private String message;
    private boolean isMessageRichText;
    private String sendDate;
    private MessageType messageType;

    private MessageItem(Builder builder) {
        senderId = builder.senderId;
        senderImageUrl = builder.senderImageUrl;
        senderName = builder.senderName;
        message = builder.message;
        isMessageRichText = builder.isMessageRichText;
        sendDate = builder.sendDate;
        messageType = builder.messageType;
    }

    @Override
    public boolean isSearchable(String query) {
        return false;
    }

    public static final class Builder {
        private String senderId;
        private String senderImageUrl;
        private String senderName;
        private String message;
        private boolean isMessageRichText;
        private String sendDate;
        private MessageType messageType;

        public Builder() {
        }

        public Builder senderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder senderImageUrl(String senderImageUrl) {
            this.senderImageUrl = senderImageUrl;
            return this;
        }

        public Builder senderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder isMessageRichText(boolean isMessageRichText) {
            this.isMessageRichText = isMessageRichText;
            return this;
        }

        public Builder sentDate(String sendDate) {
            this.sendDate = sendDate;
            return this;
        }

        public Builder messageType(MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public MessageItem build() {
            return new MessageItem(this);
        }
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMessageRichText() {
        return isMessageRichText;
    }

    public String getSendDate() {
        return sendDate;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        return TextUtils.contains(filters);
    }
}