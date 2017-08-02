package net.impacthub.members.application.salesforce;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;

class ClientManagerFactory {

    ClientManager createFrom(SalesforceSDKManager manager) {
        return new ClientManager(
                manager.getAppContext(),
                manager.getAccountType(),
                manager.getLoginOptions(),
                manager.shouldLogoutWhenTokenRevoked());
    }
}
