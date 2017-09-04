package net.impacthub.members.ui.controllers.filter;

import android.support.v4.app.Fragment;

import net.impacthub.members.ui.base.BaseControllerFragment;
import net.impacthub.members.ui.features.filters.FilterFragment;

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
