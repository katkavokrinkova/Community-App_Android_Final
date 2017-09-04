package net.impacthub.members.application.salesforce;

import android.content.Context;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;

class ClientManagerFactory {

    ClientManager createFrom(SalesforceSDKManager manager) {

        String accountType = manager.getAccountType();
        Context appContext = manager.getAppContext();
        ClientManager.LoginOptions loginOptions = manager.getLoginOptions();
        boolean logoutWhenTokenRevoked = manager.shouldLogoutWhenTokenRevoked();

        return new ClientManager(
                appContext,
                accountType,
                loginOptions,
                logoutWhenTokenRevoked);
    }
}
