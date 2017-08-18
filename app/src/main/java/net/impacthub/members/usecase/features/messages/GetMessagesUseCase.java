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

package net.impacthub.members.usecase.features.messages;

import net.impacthub.members.model.features.messages.ConversationMessages;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class GetMessagesUseCase extends BaseUseCaseGenerator<Single<ConversationMessages>, ConversationMessages> {

    private final String mConversationId;

    public GetMessagesUseCase(String conversationId) {
        mConversationId = conversationId;
    }

    @Override
    public Single<ConversationMessages> getUseCase() {
        return Single.fromCallable(new Callable<ConversationMessages>() {
            @Override
            public ConversationMessages call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createConversationMessagesRequest(getUserAccount().getCommunityId(), mConversationId), ConversationMessages.class);
            }
        });
    }


//    private static class ConversationCallableCombiner implements BigFu<ConversationMessages, ReadSet, ProcessedMessages> {
//
//        @Override
//        public ProcessedMessages call(ConversationMessages conversationMessages, ReadSet ignored) {
//            return new ProcessedMessages.Builder()
//                    .messages(new MessageItemListConverter(conversationMessages).invoke())
//                    .recipients(new RecipientListConverter(conversationMessages).invoke())
//                    .inReplyTo(getLastMessageId(conversationMessages))
//                    .build();
//        }
//
//        private String getLastMessageId(final ConversationMessages conversationMessages) {
//            return new FieldExtractor<String>(){
//                @Override
//                String extract() throws ParseException {
//                    return conversationMessages.getMessages().getMessages().get(0).getId();
//                }
//            }.executeWithDefault(null);
//        }
//    }
}
