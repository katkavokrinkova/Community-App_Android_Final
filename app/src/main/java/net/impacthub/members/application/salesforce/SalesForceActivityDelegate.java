package net.impacthub.members.application.salesforce;

import android.app.Activity;

public interface SalesForceActivityDelegate {

    void onCreate(Activity activity);

    void onResume();

    void onUserInteraction();

    void onPause();

    void onDestroy();
}
