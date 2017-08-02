package net.impacthub.members.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import net.impacthub.members.application.salesforce.SalesforceActivityDelegate;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.salesforceActivityDelegateProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private SalesforceActivityDelegate mSalesforceActivityDelegate = salesforceActivityDelegateProvider();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSalesforceActivityDelegate.onCreate(this);
        setContentView(getContentView());
        onActivityCreated(savedInstanceState);
    }

    @CallSuper
    protected void onActivityCreated(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSalesforceActivityDelegate.onResume();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        mSalesforceActivityDelegate.onUserInteraction();
    }

    protected void setStatusBarColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && color > 0) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    protected void replaceFragment(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(containerId, fragment)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSalesforceActivityDelegate.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSalesforceActivityDelegate.onDestroy();
    }

    protected abstract int getContentView();
}
