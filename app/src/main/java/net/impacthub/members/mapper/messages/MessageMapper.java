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

package net.impacthub.members.mapper.messages;

import net.impacthub.members.model.features.messages.ConversationMessages;
import net.impacthub.members.model.features.messages.Member;
import net.impacthub.members.model.features.messages.Message;
import net.impacthub.members.model.features.messages.MessageItem;
import net.impacthub.members.model.features.messages.ProcessedMessages;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class MessageMapper {

    private final MessageTypeEvaluator mMessageTypeEvaluator = new MessageTypeEvaluator();

    public ProcessedMessages map(ConversationMessages conversationMessages, String userId) {
        return new ProcessedMessages.Builder()
                .messages(mapMessages(conversationMessages, userId))
                .recipients(mapRecipientIds(conversationMessages, userId))
                .inReplyTo(conversationMessages.getMessages().getMessages().get(0).getId())
                .build();
    }

    private List<String> mapRecipientIds(ConversationMessages conversationMessages, String userId) {
        HashSet<String> idSet = new HashSet<>();
        List<Member> members = conversationMessages.getMembers();
        if (members != null) {
            for (Member member : members) {
                idSet.add(member.getId());
            }
        }
        idSet.remove(userId);
        return new LinkedList<>(idSet);
    }

    private List<MessageItem> mapMessages(ConversationMessages conversationMessages, String userId) {
        List<MessageItem> messageItems = new LinkedList<>();
        List<Message> messages = conversationMessages.getMessages().getMessages();
        for (Message message : messages) {
            messageItems.add(getMessageItemFrom(message, userId));
        }
        mMessageTypeEvaluator.setPreviousItem(null);
        return messageItems;
    }

    private MessageItem getMessageItemFrom(Message message, String userId) {
        MessageItem messageItem = new MessageItem.Builder()
                .senderId(message.getSender().getId())
                .message(message.getBody().getText())
                .isMessageRichText(message.getBody().isRichText())
                .senderName(message.getSender().getName())
                .senderImageUrl(message.getSender().getPhoto().getSmallPhotoUrl())
                .sentDate(message.getSentDate())
                .messageType(mMessageTypeEvaluator.evaluateMessageTypeFrom(message, userId))
                .build();
        mMessageTypeEvaluator.setPreviousItem(messageItem);
        return messageItem;
    }
}
