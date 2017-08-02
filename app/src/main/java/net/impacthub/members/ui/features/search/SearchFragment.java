package net.impacthub.members.ui.features.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.ui.base.BaseChildFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class SearchFragment  extends BaseChildFragment {

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setStatusBarColor(R.color.colorWhite);
    }
}
