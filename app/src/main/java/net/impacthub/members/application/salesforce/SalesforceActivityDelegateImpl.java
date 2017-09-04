package net.impacthub.members.application.salesforce;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager.RestClientCallback;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.security.PasscodeManager;
import com.salesforce.androidsdk.util.EventsObservable;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.salesforce.androidsdk.accounts.UserAccountManager.USER_SWITCH_INTENT_ACTION;
import static com.salesforce.androidsdk.util.EventsObservable.EventType.MainActivityCreateComplete;
import static com.salesforce.androidsdk.util.EventsObservable.EventType.RenditionComplete;

class SalesforceActivityDelegateImpl extends BroadcastReceiver implements SalesForceActivityDelegate, RestClientCallback {

    private final SalesforceSDKManager mSDKManager;
    private final PasscodeManager mPasscodeManager;
    private final ClientManagerFactory mClientManagerFactory;
    private final IntentFilterFactory mIntentFilterFactory;
    private final EventsObservable mEventsObservable;
    private Activity mActivity;

    SalesforceActivityDelegateImpl() {
        this(   SalesforceSDKManager.getInstance(),
                SalesforceSDKManager.getInstance().getPasscodeManager(),
                new ClientManagerFactory(),
                new IntentFilterFactory(),
                EventsObservable.get());
    }

    private SalesforceActivityDelegateImpl(
            SalesforceSDKManager sdkManager,
            PasscodeManager passcodeManager,
            ClientManagerFactory clientManagerFactory,
            IntentFilterFactory intentFilterFactory,
            EventsObservable eventsObservable) {
        mSDKManager = sdkManager;
        mPasscodeManager = passcodeManager;
        mClientManagerFactory = clientManagerFactory;
        mIntentFilterFactory = intentFilterFactory;
        mEventsObservable = eventsObservable;
    }

    @Override
    public void onCreate(Activity activity) {
        mActivity = activity;
        registerForUserSwitchBroadcasts();
        mEventsObservable.notifyEvent(MainActivityCreateComplete, mActivity);
    }

    @Override
    public void onResume() {
        if (isPasscodeManagerUnlocked()) {
            refreshClientIfNeeded();
        }
    }

    @Override
    public void onUserInteraction() {
        mPasscodeManager.recordUserInteraction();
    }

    @Override
    public void authenticatedRestClient(RestClient client) {
        if (client == null) {
            attemptLogout();
        } else {
            mEventsObservable.notifyEvent(RenditionComplete);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onResume();
    }

    @Override
    public void onPause() {
        mPasscodeManager.onPause(mActivity);
    }

    @Override
    public void onDestroy() {
        mActivity.unregisterReceiver(this);
    }

    private void registerForUserSwitchBroadcasts() {
        mActivity.registerReceiver(this, mIntentFilterFactory.createFrom(USER_SWITCH_INTENT_ACTION));
    }

    private boolean isPasscodeManagerUnlocked() {
        return mPasscodeManager.onResume(mActivity);
    }

    private void refreshClientIfNeeded() {
        mClientManagerFactory.createFrom(mSDKManager).getRestClient(mActivity, this);
    }

    private void attemptLogout() {
        if(mSDKManager.getMainActivityClass() != null) {
            mSDKManager.logout(mActivity);
        }
    }
}
