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


import net.impacthub.members.mapper.chatter.ChatterMapper;
import net.impacthub.members.model.vo.chatter.ChatterVO;
import net.impacthub.members.model.features.chatterfeed.ChatterFeedResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.groups.ChatterFeedUseCase;

import java.util.List;

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
}