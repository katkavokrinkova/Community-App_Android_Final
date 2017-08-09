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

package net.impacthub.members.usecase.features.goals;

import net.impacthub.members.model.features.goals.GoalsResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class GoalsUseCase extends BaseUseCaseGenerator<Single<GoalsResponse>, GoalsResponse> {

    @Override
    public Single<GoalsResponse> getUseCase() {
        return Single.fromCallable(new Callable<GoalsResponse>() {
            @Override
            public GoalsResponse call() throws Exception {
                return getFiltersApiCall().getResponse(getSoqlRequestFactory().createGoalsRequest(), GoalsResponse.class);
            }
        });
    }
}
