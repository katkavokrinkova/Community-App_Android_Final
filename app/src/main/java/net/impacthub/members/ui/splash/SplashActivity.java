/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.members.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.ui.SalesforceActivity;
import com.salesforce.androidsdk.util.EventsObservable;
import com.salesforce.androidsdk.util.test.EventsObserver;

import net.impacthub.members.application.salesforce.SalesForceActivityDelegate;
import net.impacthub.members.ui.controllers.MainTabsActivity;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.salesforceActivityDelegateProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class SplashActivity extends SalesforceActivity {

    private SalesForceActivityDelegate mSalesForceActivityDelegate = salesforceActivityDelegateProvider();

    @Override
    public void onResume(RestClient client) {
        startActivity(new Intent(this, MainTabsActivity.class));
        //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mSalesForceActivityDelegate.onCreate(this);
//        EventsObservable.get().registerObserver(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mSalesForceActivityDelegate.onResume();
//    }
//
//    @Override
//    public void onUserInteraction() {
//        super.onUserInteraction();
//        mSalesForceActivityDelegate.onUserInteraction();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mSalesForceActivityDelegate.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSalesForceActivityDelegate.onDestroy();
//        EventsObservable.get().unregisterObserver(this);
//    }
//
//    @Override
//    public void onEvent(EventsObservable.Event evt) {
//        switch (evt.getType()) {
//            case AppCreateComplete:
//                //open tabs activity
//                Toast.makeText(this, "app created", Toast.LENGTH_SHORT).show();
//                break;
//            case AuthWebViewCreateComplete:
//                Toast.makeText(this, "showing web auth login screen", Toast.LENGTH_SHORT).show();
//                break;
//            case AuthWebViewPageFinished:
//                Toast.makeText(this, "finished web login", Toast.LENGTH_SHORT).show();
//                break;
//            case MainActivityCreateComplete:
//                Toast.makeText(this, "opening tabs activity", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
}
