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

package net.impacthub.app.usecase.features.projects;

import net.impacthub.app.model.features.projects.ProjectResponse;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/15/2017.
 */

public class YourProjectsUseCase extends BaseUseCaseGenerator<Single<ProjectResponse>, ProjectResponse> {

    private final String mContactId;

    public YourProjectsUseCase(String contactId) {
        mContactId = contactId;
    }

    @Override
    public Single<ProjectResponse> getUseCase() {
        return Single.fromCallable(new Callable<ProjectResponse>() {
            @Override
            public ProjectResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createYourProjectsRequest(mContactId), ProjectResponse.class);
            }
        });
    }
}
