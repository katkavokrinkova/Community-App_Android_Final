package net.impacthub.members.ui.features.filters;

import android.os.Bundle;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnBackListener;
import net.impacthub.members.ui.base.BaseActivity;
import net.impacthub.members.ui.controllers.filter.FilterControllerFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class FilterActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.common_container;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        replaceFragment(R.id.common_container, new FilterControllerFragment(), "FILTER_FRAG");
    }

    @Override
    public void onBackPressed() {
        OnBackListener onBackListener = (OnBackListener) getSupportFragmentManager().getFragments().get(0);
        if (!onBackListener.onBack()) {
            super.onBackPressed();
        }
    }
}
