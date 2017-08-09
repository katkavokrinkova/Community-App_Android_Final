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
import net.impacthub.members.model.features.conversations.Conversations;
import net.impacthub.members.model.features.conversations.ConversationsResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class ConversationMapper {

    public List<ConversationDTO> map(ConversationsResponse response) {
        List<ConversationDTO> conversationDTOs = new LinkedList<>();
        if (response != null) {
            Conversations[] conversations = response.getConversations();
            if (conversations != null) {
                for (int i = 0; i < conversations.length; i++) {
                    Conversations conversation = conversations[i];
                    if (conversation != null) {
                        conversationDTOs.add(new ConversationDTO());
                    }
                }
            }
        }
        return conversationDTOs;
    }
}
