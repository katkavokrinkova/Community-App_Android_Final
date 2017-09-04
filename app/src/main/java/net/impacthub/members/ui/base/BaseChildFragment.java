package net.impacthub.members.ui.base;

import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.R;
import net.impacthub.members.presenter.base.UiContract;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.utilities.KeyboardUtils;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseChildFragment<P extends UiPresenter<? extends UiContract>> extends BaseFragment<P> {

    private boolean mIsFirstLaunch = true;
    private final UserAccount mUserAccount = userAccountProvider();
    protected final View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popChildFragment();
        }
    };

    protected UserAccount getUserAccount() {
        return mUserAccount;
    }

    protected String buildUrl(String url) {
        if (mUserAccount != null) {
            return url + "?oauth_token=" + mUserAccount.getAuthToken();
        }
        return url;
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
        if(isResumed() && isVisibleToUser) {
            onFragmentVisibilityChanged(true);
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
        getParentFragment().getChildFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out)
//                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
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
}
