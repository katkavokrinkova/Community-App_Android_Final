package net.impacthub.app.ui.controllers.filter;

import android.support.v4.app.Fragment;

import net.impacthub.app.ui.base.BaseControllerFragment;
import net.impacthub.app.ui.features.filters.FilterFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class FilterControllerFragment extends BaseControllerFragment {

    @Override
    protected Fragment createFragment() {
        return FilterFragment.newInstance();
    }
}
