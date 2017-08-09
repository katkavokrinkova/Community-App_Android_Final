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

package net.impacthub.members.usecase.features.jobs;

import net.impacthub.members.model.features.jobs.JobsResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;
import net.impacthub.members.utilities.DateUtils;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class JobsUseCase extends BaseUseCaseGenerator<Single<JobsResponse>, JobsResponse> {

    @Override
    public Single<JobsResponse> getUseCase() {
        return Single.fromCallable(new Callable<JobsResponse>() {
            @Override
            public JobsResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createJobsRequest(0, 100, DateUtils.getShortDate()), JobsResponse.class);
            }
        });
    }
}
