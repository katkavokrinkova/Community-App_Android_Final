package net.impacthub.members.application.salesforce;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ApiVersionStrings;
import com.salesforce.androidsdk.rest.RestRequest;

import android.content.Context;

import java.io.UnsupportedEncodingException;

class RestRequestFactoryImpl implements RestRequestFactory {

    private final Context mAppContext;

    RestRequestFactoryImpl() {
        this(SalesforceSDKManager.getInstance().getAppContext());
    }

    private RestRequestFactoryImpl(Context appContext) {
        mAppContext = appContext;
    }

    @Override
    public RestRequest getForQuery(String sql) throws UnsupportedEncodingException {
        return RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(mAppContext), sql);
    }
}
