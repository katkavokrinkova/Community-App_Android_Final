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

package net.impacthub.members.presenter.features.groups;


import net.impacthub.members.model.features.chatterfeed.FeedElements;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.groups.ChatterFeedUseCase;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class ChatterFeedPresenter extends UiPresenter<ChatterFeedUiContract> {

    private final UseCaseGenerator<Single<FeedElements>> mObservableGenerator;

    public ChatterFeedPresenter(ChatterFeedUiContract uiContract, String feedId) {
        super(uiContract);
        mObservableGenerator = new ChatterFeedUseCase(feedId);
    }

    public void loadChatterfeed() {
        subscribeWith(mObservableGenerator.getUseCase(), new DisposableSingleObserver<FeedElements>() {
            @Override
            public void onSuccess(@NonNull FeedElements feedElements) {
                getUi().onLoadChatterfeed(feedElements);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}