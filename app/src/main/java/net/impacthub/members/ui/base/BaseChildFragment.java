package net.impacthub.members.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
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

    protected void setUpToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if(toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popChildFragment();
                }
            });
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
