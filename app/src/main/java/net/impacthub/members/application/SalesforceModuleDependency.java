package net.impacthub.members.application;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;

public class SalesforceModuleDependency {

    public static ClientManager clientManagerProvider() {
        SalesforceSDKManager manager = SalesforceSDKManager.getInstance();
        return new ClientManager(
                manager.getAppContext(),
                manager.getAccountType(),
                manager.getLoginOptions(),
                manager.shouldLogoutWhenTokenRevoked());
    }

    public static RestRequestFactory restRequestFactoryProvider() {
        return new RestRequestFactoryImpl();
    }

    public static UserAccount userAccountProvider() {
        return SalesforceSDKManager.getInstance().getUserAccountManager().getCurrentUser();
    }
}
