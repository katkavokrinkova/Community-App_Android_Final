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

package net.impacthub.app.presenter.features.groups;


import net.impacthub.app.model.features.push.PushBody;
import net.impacthub.app.model.pojo.ChatterWrapper;
import net.impacthub.app.model.vo.chatter.ChatComment;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.vo.chatter.CommentVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.notifications.NotificationType;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.AbstractFunction;
import net.impacthub.app.presenter.rx.DisposableSingleObserverAdapter;
import net.impacthub.app.usecase.features.chatter.LikePostUseCase;
import net.impacthub.app.usecase.features.chatter.UnlikePostUseCase;
import net.impacthub.app.usecase.features.groups.ChatterFeedUseCase;
import net.impacthub.app.usecase.features.members.GetMemberByUserIdUseCase;
import net.impacthub.app.usecase.features.push.SendPushUseCase;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class ChatterFeedPresenter extends UiPresenter<ChatterFeedUiContract> {

    private final List<String> mCommentIdList = new LinkedList<>();

    public ChatterFeedPresenter(ChatterFeedUiContract uiContract) {
        super(uiContract);
    }

    public void loadChatterFeed(String feedId, String chatterRelatedId) {
        getUi().onShowProgressBar(true);
        subscribeWith(new ChatterFeedUseCase(feedId).getUseCase(), new DisposableSingleObserverAdapter<String, List<ChatterVO>>(chatterRelatedId) {

            @Override
            protected void onSuccess(List<ChatterVO> chatterVOS, String subject) {
                getUi().onLoadChatterFeed(chatterVOS);

                for (int i = 0, size = chatterVOS.size(); i < size; i++) {
                    ChatterVO chatterVO = chatterVOS.get(i);
                    if (chatterVO.mCommentId.equals(subject)) {
                        getUi().onScrollToComment(i);
                    } else {
                        CommentVO comments = chatterVO.mComments;
                        if (comments != null) {
                            List<ChatComment> chatComments = comments.getComments();
                            for (int j = 0, count = chatComments.size(); j < count; j++) {
                                ChatComment chatComment = chatComments.get(j);
                                if (chatComment.mChatCommentId.equals(subject)) {
                                    getUi().onScrollToCommentAndOpenCommentReplies(chatterVO, i, j);
                                }
                            }
                        }
                    }
                }
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

    public void likePost(String toUserIds, String commentID, int position) {
        getUi().onShowProgressBar(true);
        if (mCommentIdList.contains(commentID)) {
            getUi().onShowProgressBar(false);
            getUi().onError(new Throwable("Already processing like..."));
            return;
        }
        mCommentIdList.add(commentID);
        ChatterWrapper chatterWrapper = new ChatterWrapper(commentID, position);

        String type = NotificationType.TYPE_LIKE_POST.getType();
        PushBody pushQuery = new PushBody(getUserAccount().getUserId(), toUserIds, type, commentID);

        Single<Object> useCase = new LikePostUseCase(commentID).getUseCase()
                .flatMap(new AbstractFunction<PushBody, Object, SingleSource<?>>(pushQuery) {
                    @Override
                    protected SingleSource<?> apply(Object response, PushBody subject) throws Exception {
                        return new SendPushUseCase(subject).getUseCase();
                    }
                });
        subscribeWith(useCase, new DisposableSingleObserverAdapter<ChatterWrapper, Object>(chatterWrapper) {

            @Override
            protected void onSuccess(Object o, ChatterWrapper subject) {
                getUi().onPostLiked(subject.getPosition());
                mCommentIdList.remove(subject.getCommentID());
                getUi().onShowProgressBar(false);
            }

            @Override
            protected void onError(Throwable e, ChatterWrapper subject) {
                getUi().onShowProgressBar(false);
                getUi().onError(e);
                mCommentIdList.remove(subject.getCommentID());
            }
        });
    }

    public void unlikePost(String commentID, int position) {
        getUi().onShowProgressBar(true);
        if (mCommentIdList.contains(commentID)) {
            getUi().onShowProgressBar(false);
            getUi().onError(new Throwable("Already processing like..."));
            return;
        }
        mCommentIdList.add(commentID);
        ChatterWrapper chatterWrapper = new ChatterWrapper(commentID, position);
        subscribeWith(new UnlikePostUseCase(commentID).getUseCase(), new DisposableSingleObserverAdapter<ChatterWrapper, Object>(chatterWrapper) {

            @Override
            protected void onSuccess(Object o, ChatterWrapper subject) {
                getUi().onPostUnLiked(subject.getPosition());
                mCommentIdList.remove(subject.getCommentID());
                getUi().onShowProgressBar(false);
            }

            @Override
            protected void onError(Throwable e, ChatterWrapper subject) {
                getUi().onShowProgressBar(false);
                getUi().onError(e);
                mCommentIdList.remove(subject.getCommentID());
            }
        });
    }
}