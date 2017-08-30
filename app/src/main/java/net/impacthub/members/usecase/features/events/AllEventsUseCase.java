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

package net.impacthub.members.usecase.features.events;

import net.impacthub.members.model.features.events.EventsResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;
import net.impacthub.members.utilities.DateUtils;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class AllEventsUseCase extends BaseUseCaseGenerator<Single<EventsResponse>, EventsResponse> {

    @Override
    public Single<EventsResponse> getUseCase() {
        return Single.fromCallable(new Callable<EventsResponse>() {
            @Override
            public EventsResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createAllEventsRequest(DateUtils.getIso_8601Date()), EventsResponse.class);
            }
        });
    }
}
