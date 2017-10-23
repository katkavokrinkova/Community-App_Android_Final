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

import net.impacthub.app.model.features.chatter.GroupPostPayload;
import net.impacthub.app.model.features.chatter.MessageSegment;
import net.impacthub.app.model.features.chatter.PostBody;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.features.chatter.CreatePostUseCase;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/23/2017.
 */

public class ChatterUiPresenter extends UiPresenter<ChatterUiContract> {

    public ChatterUiPresenter(ChatterUiContract uiContract) {
        super(uiContract);
    }

    public void createPost(String message, String groupID) {

        PostBody postBody = new PostBody(new MessageSegment[]{new MessageSegment("Text", message)});
        GroupPostPayload postPayload = new GroupPostPayload(postBody, "FeedItem", groupID);
        subscribeWith(new CreatePostUseCase(postPayload).getUseCase(), new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@NonNull Object o) {
                getUi().onError(new Throwable(o.toString()));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });


    }
}
