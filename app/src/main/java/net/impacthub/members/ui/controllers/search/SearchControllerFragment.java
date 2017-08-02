package net.impacthub.members.ui.controllers.search;

import android.support.v4.app.Fragment;

import net.impacthub.members.ui.base.BaseControllerFragment;
import net.impacthub.members.ui.features.search.SearchFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class SearchControllerFragment extends BaseControllerFragment {
    @Override
    protected Fragment createFragment() {
        return SearchFragment.newInstance();
    }
}
