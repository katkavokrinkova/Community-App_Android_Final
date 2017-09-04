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

import android.text.TextUtils;

import com.google.gson.Gson;

import net.impacthub.members.model.features.conversations.Id;
import net.impacthub.members.model.features.conversations.ProcessedMessages;
import net.impacthub.members.model.features.push.PushBody;
import net.impacthub.members.model.vo.conversations.MessageByUserIdBody;
import net.impacthub.members.model.vo.conversations.MessageInReplyToBody;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractFunction;
import net.impacthub.members.usecase.features.conversations.GetProcessedMessagesUseCase;
import net.impacthub.members.usecase.features.conversations.SendMessageUseCase;
import net.impacthub.members.usecase.features.push.SendPushUseCase;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class ConversationUiPresenter extends UiPresenter<ConversationUiContract> {

    public ConversationUiPresenter(ConversationUiContract uiContract) {
        super(uiContract);
    }

    public void getMessageConversations(String conversationID) {
        getUi().onShowProgressBar(true);
        Single<ProcessedMessages> messagesSingle = new GetProcessedMessagesUseCase(conversationID).getUseCase();
        subscribeWith(messagesSingle, new DisposableSingleObserver<ProcessedMessages>() {
            @Override
            public void onSuccess(@NonNull ProcessedMessages processedMessages) {
                getUi().onShowProgressBar(false);
                getUi().onLoadMessages(processedMessages);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }

    public void sendMessage(String conversationID, PushBody pushQuery, String message, String inReplyTo) {

        if (TextUtils.isEmpty(message)) {
            getUi().onError(new Throwable("Message should not be empty."));
            return;
        }

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(new Gson().toJson(new MessageInReplyToBody(message, inReplyTo)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Single<ProcessedMessages> messagesSingle = new SendMessageUseCase(jsonObject).getUseCase()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<Id>() {
                    @Override
                    public void accept(@NonNull Id id) throws Exception {
                        getUi().onClearTextField();
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new AbstractFunction<PushBody, Id, SingleSource<?>>(pushQuery) {
                    @Override
                    protected SingleSource<?> apply(Id response, PushBody subject) throws Exception {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(subject));
                        return new SendPushUseCase(jsonObject).getUseCase();
                    }
                })
                .flatMap(new AbstractFunction<String, Object, SingleSource<ProcessedMessages>>(conversationID) {
                    @Override
                    protected SingleSource<ProcessedMessages> apply(Object response, String subject) throws Exception {
                        return new GetProcessedMessagesUseCase(subject).getUseCase();
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

    public void sendMessageByUserId(PushBody pushQuery, String message, String toUserIds) {

        if (TextUtils.isEmpty(message)) {
            getUi().onError(new Throwable("Message should not be empty."));
            return;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(new MessageByUserIdBody(message, new String[]{toUserIds})));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Single<Object> sendMessageSingle = new SendMessageUseCase(jsonObject).getUseCase()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<Id>() {
                    @Override
                    public void accept(@NonNull Id id) throws Exception {
                        getUi().onClearTextField();
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new AbstractFunction<PushBody, Id, SingleSource<?>>(pushQuery) {
                    @Override
                    protected SingleSource<?> apply(Id response, PushBody subject) throws Exception {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(subject));
                        return new SendPushUseCase(jsonObject).getUseCase();
                    }
                });
        subscribeWith(sendMessageSingle, new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@NonNull Object response) {
                getUi().onDismissConversation();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
