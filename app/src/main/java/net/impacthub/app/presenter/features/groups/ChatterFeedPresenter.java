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


import net.impacthub.app.mapper.chatter.ChatterMapper;
import net.impacthub.app.model.features.push.PushBody;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.features.chatterfeed.ChatterFeedResponse;
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

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class ChatterFeedPresenter extends UiPresenter<ChatterFeedUiContract> {

    public ChatterFeedPresenter(ChatterFeedUiContract uiContract) {
        super(uiContract);
    }

    public void loadChatterFeed(String feedId) {
        subscribeWith(new ChatterFeedUseCase(feedId).getUseCase(), new DisposableSingleObserver<ChatterFeedResponse>() {
            @Override
            public void onSuccess(@NonNull ChatterFeedResponse feedElements) {
                List<ChatterVO> chatterDTOs = new ChatterMapper().map(feedElements);
                getUi().onLoadChatterFeed(chatterDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
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
        String type = NotificationType.TYPE_LIKE_POST.getType();
        PushBody pushQuery = new PushBody(getUserAccount().getUserId(), toUserIds, type, commentID);

        Single<Object> useCase = new LikePostUseCase(commentID).getUseCase()
                .flatMap(new AbstractFunction<PushBody, Object, SingleSource<?>>(pushQuery) {
                    @Override
                    protected SingleSource<?> apply(Object response, PushBody subject) throws Exception {
                        return new SendPushUseCase(subject).getUseCase();
                    }
                });
        subscribeWith(useCase, new DisposableSingleObserverAdapter<Integer, Object>(position) {

            @Override
            protected void onSuccess(Object o, Integer subject) {
                getUi().onPostLiked(subject);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    public void unlikePost(String likeId, int position) {
        subscribeWith(new UnlikePostUseCase(likeId).getUseCase(), new DisposableSingleObserverAdapter<Integer, Object>(position) {

            @Override
            protected void onSuccess(Object o, Integer subject) {
                getUi().onPostUnLiked(subject);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}