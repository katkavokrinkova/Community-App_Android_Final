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

package net.impacthub.members.usecase.features.groups;

import net.impacthub.members.model.features.chatterfeed.ChatterFeedResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

public class ChatterFeedUseCase extends BaseUseCaseGenerator<Single<ChatterFeedResponse>, ChatterFeedResponse> {

    private final String mFeedId;

    public ChatterFeedUseCase(String feedId) {
        mFeedId = feedId;
    }

    @Override
    public Single<ChatterFeedResponse> getUseCase() {
        return Single.fromCallable(new FeedElementsCallable());
    }

    private class FeedElementsCallable implements Callable<ChatterFeedResponse> {
        @Override
        public ChatterFeedResponse call() throws Exception {
            return getApiCall().getResponse(getSoqlRequestFactory().createChatterFeedRequest(getUserAccount().getCommunityId(), mFeedId), ChatterFeedResponse.class);
        }
    }
}