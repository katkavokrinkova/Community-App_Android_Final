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

import java.util.List;

public class ProcessedMessages {

    private final List<MessageItem> messages;
    private final List<String> recipients;
    private final String inReplyTo;
    private final boolean fromSentMessage;

    private ProcessedMessages(Builder builder) {
        messages = builder.messages;
        recipients = builder.recipients;
        inReplyTo = builder.inReplyTo;
        fromSentMessage = builder.fromSentMessage;
    }

    public List<MessageItem> getMessages() {
        return messages;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public boolean isFromSentMessage() {
        return fromSentMessage;
    }

    public static final class Builder {
        private List<MessageItem> messages;
        private List<String> recipients;
        private String inReplyTo;
        private boolean fromSentMessage;

        public Builder() {}

        public Builder messages(List<MessageItem> messages) {
            this.messages = messages;
            return this;
        }

        public Builder recipients(List<String> recipients) {
            this.recipients = recipients;
            return this;
        }

        public Builder inReplyTo(String inReplyTo) {
            this.inReplyTo = inReplyTo;
            return this;
        }

        public Builder fromSentMessage(boolean fromSentMessage) {
            this.fromSentMessage = fromSentMessage;
            return this;
        }

        public ProcessedMessages build() {
            return new ProcessedMessages(this);
        }
    }
}
