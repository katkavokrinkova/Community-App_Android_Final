package net.impacthub.members.usecase;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestRequest;

import java.io.IOException;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.clientManagerProvider;

public class ApiCall<T> {

    private final ClientManager mClientManager;
    private final Gson mGson = new Gson();

    public ApiCall() {
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
