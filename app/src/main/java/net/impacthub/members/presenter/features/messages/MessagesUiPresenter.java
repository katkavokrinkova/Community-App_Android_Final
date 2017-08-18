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

package net.impacthub.members.presenter.features.messages;

import net.impacthub.members.mapper.messages.MessageMapper;
import net.impacthub.members.model.features.messages.ConversationMessages;
import net.impacthub.members.model.features.messages.Id;
import net.impacthub.members.model.features.messages.ProcessedMessages;
import net.impacthub.members.model.features.messages.ReadSet;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractBigFunction;
import net.impacthub.members.usecase.features.messages.GetMessagesUseCase;
import net.impacthub.members.usecase.features.messages.MarkConversationReadUseCase;
import net.impacthub.members.usecase.features.messages.SendMessageUseCase;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class MessagesUiPresenter extends UiPresenter<MessageUiContract> {

    public MessagesUiPresenter(MessageUiContract uiContract) {
        super(uiContract);
    }

    public void getMessageConversations(String conversationID, String userID) {
        getUi().onChangeStatus(true);
        Single<ProcessedMessages> messagesSingle = Single.zip(
                new GetMessagesUseCase(conversationID).getUseCase(),
                new MarkConversationReadUseCase(conversationID).getUseCase(),
                new AbstractBigFunction<String, ConversationMessages, ReadSet, ProcessedMessages>(userID) {
                    @Override
                    protected ProcessedMessages apply(ConversationMessages conversationMessages, ReadSet readSet, String subject) {
                        return new MessageMapper().map(conversationMessages, subject);
                    }
                });
        subscribeWith(messagesSingle, new DisposableSingleObserver<ProcessedMessages>() {
            @Override
            public void onSuccess(@NonNull ProcessedMessages processedMessages) {
                getUi().onChangeStatus(false);
                getUi().onLoadMessages(processedMessages);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onChangeStatus(false);
            }
        });
    }

    public void sendMessage(String message, String inReplyTo) {
        Single<ProcessedMessages> messagesSingle = new SendMessageUseCase(message, inReplyTo).getUseCase()
                .map(new Function<Id, ProcessedMessages>() {
                    @Override
                    public ProcessedMessages apply(@NonNull Id id) throws Exception {
                        return new ProcessedMessages.Builder().fromSentMessage(true).build();
                    }
                });
        subscribeWith(messagesSingle, new DisposableSingleObserver<ProcessedMessages>() {
            @Override
            public void onSuccess(@NonNull ProcessedMessages processedMessages) {
                getUi().onLoadMessages(processedMessages);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
