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

package net.impacthub.app.presenter.features.chatter;

import android.text.TextUtils;

import net.impacthub.app.model.features.chatter.MessageSegment;
import net.impacthub.app.model.features.chatter.PostBody;
import net.impacthub.app.model.features.chatter.PostCommentPayload;
import net.impacthub.app.model.features.push.PushBody;
import net.impacthub.app.model.vo.chatter.ChatComment;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.notifications.NotificationType;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.AbstractFunction;
import net.impacthub.app.usecase.features.chatter.AddCommentUseCase;
import net.impacthub.app.usecase.features.members.GetMemberByUserIdUseCase;
import net.impacthub.app.usecase.features.push.SendPushUseCase;

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
 * @date 10/24/2017.
 */

public class ChatterCommentsUiPresenter extends UiPresenter<ChatterCommentsUiContract> {

    public ChatterCommentsUiPresenter(ChatterCommentsUiContract uiContract) {
        super(uiContract);
    }

    public void addComment(String toUserIds, String commentID, String typedComment) {
        getUi().onShowProgressBar(true);
        if (TextUtils.isEmpty(typedComment)) {
            getUi().onError(new Throwable("Comment should not be empty."));
            getUi().onShowProgressBar(false);
            return;
        }

        PostBody postBody = new PostBody(new MessageSegment[]{new MessageSegment("Text", typedComment)});
        PostCommentPayload payload = new PostCommentPayload(postBody);

        Single<Object> useCase = new AddCommentUseCase(commentID, payload).getUseCase()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<ChatComment>() {
                    @Override
                    public void accept(@NonNull ChatComment o) throws Exception {
                        getUi().onAppendComment(o);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new AbstractFunction<String, ChatComment, SingleSource<?>>(toUserIds) {
                    @Override
                    protected SingleSource<?> apply(ChatComment response, String subject) throws Exception {

                        String type = NotificationType.TYPE_COMMENT.getType();
                        PushBody pushQuery = new PushBody(getUserAccount().getUserId(), subject, type, response.mChatCommentId);
                        return new SendPushUseCase(pushQuery).getUseCase();
                    }
                });
        subscribeWith(useCase, new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@NonNull Object o) {
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }

    public void getMemberBy(String id) {
        getUi().onShowProgressBar(true);
        subscribeWith(new GetMemberByUserIdUseCase(id).getUseCase(), new DisposableSingleObserver<MemberVO>() {
            @Override
            public void onSuccess(@NonNull MemberVO memberVO) {
                getUi().onLoadMember(memberVO);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }
}
