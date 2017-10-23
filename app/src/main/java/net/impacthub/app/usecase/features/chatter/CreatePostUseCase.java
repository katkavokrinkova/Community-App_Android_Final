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

package net.impacthub.app.usecase.features.chatter;

import com.google.gson.Gson;

import net.impacthub.app.model.features.chatter.GroupPostPayload;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/23/2017.
 */

public class CreatePostUseCase extends BaseUseCaseGenerator<Single<Object>, Object> {

    private final GroupPostPayload mPostPayload;

    public CreatePostUseCase(GroupPostPayload postPayload) {
        mPostPayload = postPayload;
    }

    @Override
    public Single<Object> getUseCase() {
        return Single.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(mPostPayload));
                return getApiCall().getResponse(getSoqlRequestFactory().createGroupPostRequest(getUserAccount().getCommunityId(), jsonObject), Object.class);
            }
        });
    }
}
