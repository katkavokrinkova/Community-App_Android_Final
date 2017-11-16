package net.impacthub.app.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnReSelectListener;
import net.impacthub.app.presenter.base.UiContract;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.ui.common.UserAccountDelegate;
import net.impacthub.app.utilities.KeyboardUtils;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseChildFragment<P extends UiPresenter<? extends UiContract>> extends BaseFragment<P> implements OnReSelectListener {

    private FragmentManager mChildFragmentManager;
    private boolean mIsFirstLaunch = true;

    protected final View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popChildFragment();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChildFragmentManager = getParentFragment().getChildFragmentManager();
    }

    protected UserAccount getUserAccount() {
        return UserAccountDelegate.getAccountManager();
    }

    protected String buildUrl(String url) {
        return UserAccountDelegate.buildUrl(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean userVisibleHint = getUserVisibleHint();
        if(userVisibleHint && !mIsFirstLaunch) {
            onFragmentVisibilityChanged(true);
        } else {
            mIsFirstLaunch = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isResumed()) {
            onFragmentVisibilityChanged(isVisibleToUser);
        }
    }

    @CallSuper
    protected void onFragmentVisibilityChanged(boolean isVisible) {}

    protected void setUpToolbar(String title) {
        if(mToolbar != null) {
            mToolbar.setCustomTitle(title);
            mToolbar.setNavigationOnClickListener(mBackListener);
        }
    }

    protected void setUpToolbar(@StringRes int title) {
        if(mToolbar != null) {
            mToolbar.setCustomTitle(getString(title));
            mToolbar.setNavigationOnClickListener(mBackListener);
        }
    }

    protected void setUpToolbar(@StringRes int title, @ColorInt int textColor) {
        if(mToolbar != null) {
            mToolbar.setCustomTitle(getString(title), textColor);
            mToolbar.setNavigationOnClickListener(mBackListener);
        }
    }

    protected void addChildFragment(Fragment fragment, String tag) {
        if (mChildFragmentManager != null) {
            mChildFragmentManager.beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .setTransition(FragmentTransaction.TRANSIT_NONE)
//                .setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out)
//                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragment_container, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    protected boolean popChildFragment() {
        KeyboardUtils.hideNativeKeyboard(getActivity(), getView());
        FragmentManager manager = getParentFragment().getChildFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
            return true;
        }
        return false;
    }

    @Override
    @CallSuper
    public void onTabReselected() {
        //showToast("Tab reselected....");
    }
}
