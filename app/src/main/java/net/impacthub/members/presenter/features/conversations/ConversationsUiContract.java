package net.impacthub.members.presenter.features.conversations;

import net.impacthub.members.model.dto.conversations.ConversationDTO;
import net.impacthub.members.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public interface ConversationsUiContract extends ErrorHandlerUiContract {

    void onLoadConversations(List<ConversationDTO> conversations);
}
