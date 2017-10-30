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

package net.impacthub.app.usecase.features.groups;

import net.impacthub.app.mapper.chatter.ChatterMapper;
import net.impacthub.app.model.features.chatterfeed.ChatterFeedResponse;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class ChatterFeedUseCase extends BaseUseCaseGenerator<Single<List<ChatterVO>>, ChatterFeedResponse> {

    private final String mFeedId;

    public ChatterFeedUseCase(String feedId) {
        mFeedId = feedId;
    }

    @Override
    public Single<List<ChatterVO>> getUseCase() {
        return Single.fromCallable(new FeedElementsCallable())
                .map(new Function<ChatterFeedResponse, List<ChatterVO>>() {
                    @Override
                    public List<ChatterVO> apply(ChatterFeedResponse chatterFeedResponse) throws Exception {
                        return new ChatterMapper().map(chatterFeedResponse);
                    }
                });
    }

    private class FeedElementsCallable implements Callable<ChatterFeedResponse> {
        @Override
        public ChatterFeedResponse call() throws Exception {
            return getApiCall().getResponse(getSoqlRequestFactory().createChatterFeedRequest(getUserAccount().getCommunityId(), mFeedId), ChatterFeedResponse.class);
        }
    }
}