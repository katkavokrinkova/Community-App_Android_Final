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

package net.impacthub.app.usecase.features.conversations;

import net.impacthub.app.mapper.conversations.ConversationMapper;
import net.impacthub.app.model.features.conversations.ConversationMessages;
import net.impacthub.app.model.features.conversations.ProcessedMessages;
import net.impacthub.app.model.features.conversations.ReadSet;
import net.impacthub.app.presenter.rx.AbstractBigFunction;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/21/2017.
 */

public class GetProcessedMessagesUseCase extends BaseUseCaseGenerator<Single<ProcessedMessages>, ProcessedMessages> {

    private final String mConversationId;

    public GetProcessedMessagesUseCase(String conversationId) {
        mConversationId = conversationId;
    }

    @Override
    public Single<ProcessedMessages> getUseCase() {
        return Single.zip(
                new GetMessagesUseCase(mConversationId).getUseCase(),
                new MarkConversationReadUseCase(mConversationId).getUseCase(),
                new AbstractBigFunction<String, ConversationMessages, ReadSet, ProcessedMessages>(getUserAccount().getUserId()) {
                    @Override
                    protected ProcessedMessages apply(ConversationMessages conversationMessages, ReadSet readSet, String subject) {
                        return new ConversationMapper().map(conversationMessages, subject);
                    }
                });
    }
}
