package net.impacthub.app.ui.controllers.home;

import android.support.v4.app.Fragment;

import net.impacthub.app.ui.base.BaseControllerFragment;
import net.impacthub.app.ui.features.home.HomeFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class HomeControllerFragment extends BaseControllerFragment {

    @Override
    protected Fragment createFragment() {
        return HomeFragment.newInstance();
    }
}
