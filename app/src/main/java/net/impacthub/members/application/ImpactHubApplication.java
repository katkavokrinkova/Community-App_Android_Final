package net.impacthub.members.application;

import android.app.Application;
import android.os.Bundle;

import com.salesforce.androidsdk.analytics.security.Encryptor;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.push.PushNotificationInterface;

import net.impacthub.members.ui.MainTabsActivity;


public class ImpactHubApplication extends Application {

    @Override
    public void onCreate() {
        SalesforceSDKManager.initNative(getApplicationContext(), new SalesforceKeyInterface(), MainTabsActivity.class);
        SalesforceSDKManager.getInstance().setPushNotificationReceiver(new PushNotificationInterface() {
            @Override
            public void onPushMessageReceived(Bundle message) {

            }
        });
        super.onCreate();
    }

    private class SalesforceKeyInterface implements SalesforceSDKManager.KeyInterface {

        // TODO this was just copied from the example - see if there is anything better
        @Override
        public String getKey(String name) {
            return Encryptor.hash(name + "12s9adpahk;n12-97sdainkasd=012", name + "12kl0dsakj4-cxh1qewkjasdol8");
        }
    }
}
