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

package net.impacthub.app.presenter.features.messages;

import net.impacthub.app.model.features.conversations.ProcessedMessages;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public interface ConversationUiContract extends ErrorHandlerUiContract {

    void onLoadMessages(ProcessedMessages processedMessages);

    void onClearTextField();

    void onDismissConversation();
}
