package net.impacthub.members.application.salesforce;

import android.app.Activity;
import android.app.Application;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.app.SalesforceSDKManager.KeyInterface;
import com.salesforce.androidsdk.push.PushNotificationInterface;

public abstract class SalesforceApplication<T extends Activity> extends Application {

    private final Class<T> mMainActivityClass;
    private final PushNotificationInterface mNotificationInterface;
    private final KeyInterface mKeyInterface;

    public SalesforceApplication(Class<T> mainActivityClass) {
        this(mainActivityClass, null, new SalesforceKeyInterface());
    }

    public SalesforceApplication(Class<T> mainActivityClass, PushNotificationInterface notificationInterface) {
        this(mainActivityClass, notificationInterface, new SalesforceKeyInterface());
    }

    private SalesforceApplication(Class<T> mainActivityClass, PushNotificationInterface notificationInterface, KeyInterface keyInterface) {
        mMainActivityClass = mainActivityClass;
        mNotificationInterface = notificationInterface;
        mKeyInterface = keyInterface;
    }

    @Override
    public void onCreate() {
        SalesforceSDKManager.initNative(getApplicationContext(), mKeyInterface, mMainActivityClass);

        if(mNotificationInterface != null) {
            SalesforceSDKManager.getInstance().setPushNotificationReceiver(mNotificationInterface);
        }

        super.onCreate();
    }
}
