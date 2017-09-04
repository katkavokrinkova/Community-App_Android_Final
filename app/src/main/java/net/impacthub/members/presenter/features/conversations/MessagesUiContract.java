package net.impacthub.members.presenter.features.conversations;

import net.impacthub.members.model.vo.messages.MessageVO;
import net.impacthub.members.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public interface MessagesUiContract extends ErrorHandlerUiContract {

    void onLoadMessages(List<MessageVO> messageVOList);
}
