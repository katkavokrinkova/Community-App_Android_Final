package net.impacthub.app.ui.features.filters;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.features.filters.Filter;
import net.impacthub.app.model.features.filters.SeparatedFilters;
import net.impacthub.app.model.vo.filters.FilterVO;
import net.impacthub.app.presenter.features.filters.FiltersUiContract;
import net.impacthub.app.presenter.features.filters.FiltersUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.widgets.TypefaceTextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class FilterFragment extends BaseChildFragment<FiltersUiPresenter> implements FiltersUiContract {

    private final List<Filter> mCities = new LinkedList<>();
    private final List<Filter> mSectors = new LinkedList<>();

    public static FilterFragment newInstance() {

        Bundle args = new Bundle();

        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FiltersUiPresenter onCreatePresenter() {
        return new FiltersUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_filters;
    }

    @OnClick(R.id.city_bar)
    protected void onCityFilterClick() {
        addChildFragment(FilterListFragment.newInstance(new FilterVO(mCities)), "FRAG_FILTER_LIST");
    }

    @OnClick(R.id.sector_bar)
    protected void onSectorFilterClick() {
        addChildFragment(FilterListFragment.newInstance(new FilterVO(mSectors)), "FRAG_FILTER_LIST");
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(mCloseFilterListener);
        TypefaceTextView doneButton = (TypefaceTextView) rootView.findViewById(R.id.done);
        doneButton.setOnClickListener(mCloseFilterListener);
        getPresenter().getFiltersList();
    }

    private final View.OnClickListener mCloseFilterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    @Override
    public void onLoadFilters(SeparatedFilters response) {
        mCities.clear();
        mSectors.clear();
        mCities.addAll(response.getCities());
        mSectors.addAll(response.getSectors());
    }
}
