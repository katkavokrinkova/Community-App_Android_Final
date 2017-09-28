package net.impacthub.app.application.salesforce;

import com.salesforce.androidsdk.analytics.security.Encryptor;
import com.salesforce.androidsdk.app.SalesforceSDKManager.KeyInterface;

class SalesforceKeyInterface implements KeyInterface {

    // TODO this was just copied from the example - see if there is anything better
    @Override
    public String getKey(String name) {
        return Encryptor.hash(name + "12s9adpahk;n12-97sdainkasd=012", name + "12kl0dsakj4-cxh1qewkjasdol8");
    }
}