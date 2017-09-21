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

package net.impacthub.app.usecase.features.location;

import net.impacthub.app.model.features.location.LocationResponse;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/23/2017.
 */

public class GetLocationUseCase extends BaseUseCaseGenerator<Single<LocationResponse>, LocationResponse> {

    private final OkHttpClient client;
    private final String mEncodedURL;

    public GetLocationUseCase(OkHttpClient client, String encodedURL) {
        this.client = client;
        mEncodedURL = encodedURL;
    }

    @Override
    public Single<LocationResponse> getUseCase() {
        return Single.fromCallable(new Callable<LocationResponse>() {
            @Override
            public LocationResponse call() throws Exception {
                Request request = new Request.Builder()
                        .url(String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s", mEncodedURL))
                        .build();
                ResponseBody body = client.newCall(request).execute().body();
                String jsonResponse = "";
                if (body != null) {
                    jsonResponse = body.string();
                }
                LocationResponse response = convertJson(jsonResponse, LocationResponse.class);
                return response;
            }
        });
    }
}
