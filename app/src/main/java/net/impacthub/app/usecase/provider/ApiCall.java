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

package net.impacthub.app.usecase.provider;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestRequest;

import java.io.IOException;

import static net.impacthub.app.application.salesforce.SalesforceModuleDependency.clientManagerProvider;

public class ApiCall<T> {

    private final ClientManager mClientManager;
    private final Gson mGson = new Gson();

    ApiCall() {
        this(clientManagerProvider());
    }

    private ApiCall(ClientManager clientManager) {
        mClientManager = clientManager;
    }

    public T getResponse(RestRequest restRequest, Class<T> type) throws IOException {
        final String pojoString = mClientManager.peekRestClient().sendSync(restRequest).asString();
        try {
            return mGson.fromJson(pojoString, type);
        } catch (JsonSyntaxException ignored) {
            throw new RuntimeException(pojoString);
        }
    }
}
