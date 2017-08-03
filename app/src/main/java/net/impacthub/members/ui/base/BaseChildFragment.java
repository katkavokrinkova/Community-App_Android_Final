package net.impacthub.members.ui.base;

import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.presenter.base.UiContract;
import net.impacthub.members.presenter.base.UiPresenter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseChildFragment<P extends UiPresenter<? extends UiContract>> extends BaseFragment<P> {

    protected final View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popChildFragment();
        }
    };

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
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    protected boolean popChildFragment() {
        FragmentManager manager = getParentFragment().getChildFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
            return true;
        }
        return false;
    }
}
