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

package net.impacthub.members.mapper.conversations;

import net.impacthub.members.model.features.conversations.Message;
import net.impacthub.members.model.features.conversations.MessageItem;
import net.impacthub.members.model.features.conversations.MessageType;

import static net.impacthub.members.model.features.conversations.MessageType.END;
import static net.impacthub.members.model.features.conversations.MessageType.END_FROM_ME;
import static net.impacthub.members.model.features.conversations.MessageType.MIDDLE;
import static net.impacthub.members.model.features.conversations.MessageType.MIDDLE_FROM_ME;
import static net.impacthub.members.model.features.conversations.MessageType.STANDALONE;
import static net.impacthub.members.model.features.conversations.MessageType.STANDALONE_FROM_ME;

class ConversationTypeEvaluator {

    private MessageItem mPreviousItem = new MessageItem.Builder().build();

    void setPreviousItem(MessageItem previousItem) {
        mPreviousItem = previousItem;
    }

    MessageType evaluateMessageTypeFrom(Message message, String userId){
        if(isPreviousIdEqualTo(message)){
            updatePreviousMessageType();
            if(isUserIdEqualTo(message, userId)){
                return MessageType.START_FROM_ME;
            }
            return MessageType.START;
        } else if (isUserIdEqualTo(message, userId)){
            return STANDALONE_FROM_ME;
        }
        return STANDALONE;
    }

    private void updatePreviousMessageType() {
        switch(mPreviousItem.getMessageType()){
            case STANDALONE_FROM_ME:
                mPreviousItem.setMessageType(END_FROM_ME);
                break;
            case STANDALONE:
                mPreviousItem.setMessageType(END);
                break;
            case START_FROM_ME:
                mPreviousItem.setMessageType(MIDDLE_FROM_ME);
                break;
            case START:
                mPreviousItem.setMessageType(MIDDLE);
        }
    }

    private boolean isUserIdEqualTo(Message message, String userId) {
        return message.getSender().getId().equals(userId);
    }

    private boolean isPreviousIdEqualTo(Message message) {
        return message.getSender().getId().equals(mPreviousItem.getSenderId());
    }
}