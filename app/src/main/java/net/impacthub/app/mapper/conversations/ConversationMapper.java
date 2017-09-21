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

package net.impacthub.app.mapper.conversations;

import net.impacthub.app.model.features.conversations.ConversationMessages;
import net.impacthub.app.model.features.conversations.Member;
import net.impacthub.app.model.features.conversations.Message;
import net.impacthub.app.model.features.conversations.MessageItem;
import net.impacthub.app.model.features.conversations.Photo;
import net.impacthub.app.model.features.conversations.ProcessedMessages;
import net.impacthub.app.model.vo.conversations.RecipientVO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class ConversationMapper {

    private final ConversationTypeEvaluator mMessageTypeEvaluator = new ConversationTypeEvaluator();

    public ProcessedMessages map(ConversationMessages conversationMessages, String userId) {
        return new ProcessedMessages.Builder()
                .messages(mapMessages(conversationMessages, userId))
                .recipients(mapRecipientIds(conversationMessages, userId))
                .inReplyTo(conversationMessages.getMessages().getMessages().get(0).getId())
                .build();
    }

    private List<RecipientVO> mapRecipientIds(ConversationMessages conversationMessages, String userId) {
        Map<String, RecipientVO> recipientVOMap = new HashMap<>();

        List<Member> members = conversationMessages.getMembers();
        if (members != null) {
            for (Member member : members) {
                RecipientVO recipientVO = new RecipientVO();
                String id = member.getId();
                recipientVO.mId = id;
                recipientVO.mDisplayName = member.getDisplayName();
                Photo photo = member.getPhoto();
                if (photo != null) {
                    recipientVO.mImageURL = photo.getSmallPhotoUrl();
                }
                recipientVOMap.put(id, recipientVO);
            }
        }
        recipientVOMap.remove(userId);
        return new LinkedList<>(recipientVOMap.values());
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
