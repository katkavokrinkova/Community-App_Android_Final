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

import net.impacthub.members.model.dto.conversations.ConversationDTO;
import net.impacthub.members.model.features.conversations.Body;
import net.impacthub.members.model.features.conversations.Conversations;
import net.impacthub.members.model.features.conversations.ConversationsResponse;
import net.impacthub.members.model.features.conversations.LatestMessage;
import net.impacthub.members.model.features.conversations.Photo;
import net.impacthub.members.model.features.conversations.Recipients;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class ConversationMapper {

    public List<ConversationDTO> map(ConversationsResponse response, String userId) {
        List<ConversationDTO> conversationDTOs = new LinkedList<>();
        if (response != null) {
            Conversations[] conversations = response.getConversations();
            if (conversations != null) {
                for (Conversations conversation : conversations) {
                    if (conversation != null) {
                        ConversationDTO conversationDTO = new ConversationDTO();
                        conversationDTO.mConversationId = conversation.getId();
                        Boolean read = conversation.getRead();
                        conversationDTO.mIsRead = read != null ? read : true;
                        LatestMessage latestMessage = conversation.getLatestMessage();
                        if (latestMessage != null) {
                            conversationDTO.mDate = latestMessage.getSentDate();
                            Body body = latestMessage.getBody();
                            if (body != null) {
                                conversationDTO.mText = body.getText();
                            }
                            Recipients[] recipients = latestMessage.getRecipients();
                            if (recipients != null) {
                                for (int i = 0; i < recipients.length; i++) {
                                    Recipients recipient = recipients[i];
                                    if (recipient != null) {
                                        String recipientId = recipient.getId();
                                        if (recipientId != null && !recipientId.equals(userId)) {
                                            conversationDTO.mDisplayName = recipient.getDisplayName();
                                            Photo photo = recipient.getPhoto();
                                            if (photo != null) {
                                                conversationDTO.mImageURL = photo.getSmallPhotoUrl();
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        conversationDTOs.add(conversationDTO);
                    }
                }
            }
        }
        return conversationDTOs;
    }
}
