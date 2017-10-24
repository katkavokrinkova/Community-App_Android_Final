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

package net.impacthub.app.usecase.features.push;

import com.google.gson.Gson;

import net.impacthub.app.model.features.push.PushBody;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class SendPushUseCase extends BaseUseCaseGenerator<Single<Object>, Object> {

    private final PushBody mPushBody;

    public SendPushUseCase(PushBody pushBody) {
        mPushBody = pushBody;
    }

    @Override
    public Single<Object> getUseCase() {
        return Single.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(mPushBody));
                return getApiCall().getResponse(getSoqlRequestFactory().createSendPushRequest(jsonObject), Object.class);
            }
        });
    }
}
