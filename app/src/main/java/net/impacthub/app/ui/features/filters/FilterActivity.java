package net.impacthub.app.ui.features.filters;

import android.content.Intent;
import android.os.Bundle;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnBackListener;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.filters.FilterDataDispatcher;
import net.impacthub.app.ui.base.BaseActivity;
import net.impacthub.app.ui.controllers.filter.FilterControllerFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class FilterActivity extends BaseActivity implements FilterDataDispatcher {

    public static final String EXTRA_FILTER_DATA = "net.impacthub.app.ui.features.filters.EXTRA_FILTER_DATA";
    public static final int FILTER_REQUEST_CODE = 0x1234;

    private FilterData mFilterData;

    @Override
    protected int getContentView() {
        return R.layout.common_container;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFilterData = (FilterData) getIntent().getSerializableExtra(EXTRA_FILTER_DATA);
        replaceFragment(R.id.common_container, new FilterControllerFragment(), "FILTER_FRAG");
    }

    @Override
    public void onBackPressed() {
        OnBackListener onBackListener = (OnBackListener) getSupportFragmentManager().getFragments().get(0);
        if (!onBackListener.onBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public FilterData getFilterData() {
        return mFilterData;
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra(EXTRA_FILTER_DATA, mFilterData);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
