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

import java.util.Date;

public class ConversationItem {

    private String id;
    private boolean read;
    private String senderImageUrl;
    private String senderName;
    private String message;
    private boolean isMessageRichText;
    private Date sendDate;

    private ConversationItem(Builder builder) {
        id = builder.id;
        read = builder.read;
        senderImageUrl = builder.senderImageUrl;
        senderName = builder.senderName;
        message = builder.message;
        isMessageRichText = builder.isMessageRichText;
        sendDate = builder.sendDate;
    }

    public static final class Builder {
        private String id;
        private boolean read;
        private String senderImageUrl;
        private String senderName;
        private String message;
        private boolean isMessageRichText;
        private Date sendDate;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder read(boolean read) {
            this.read = read;
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

        public Builder sendDate(Date sendDate) {
            this.sendDate = sendDate;
            return this;
        }

        public ConversationItem build() {
            return new ConversationItem(this);
        }
    }

    public String getId() {
        return id;
    }

    public boolean isRead() {
        return read;
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

    public Date getSendDate() {
        return sendDate;
    }
}