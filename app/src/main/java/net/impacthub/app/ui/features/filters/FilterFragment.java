package net.impacthub.app.ui.features.filters;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.filters.FilterBarVO;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.filters.FilterDataDispatcher;
import net.impacthub.app.model.vo.filters.FilterVO;
import net.impacthub.app.model.vo.filters.FiltersWrapper;
import net.impacthub.app.presenter.features.filters.FiltersUiContract;
import net.impacthub.app.presenter.features.filters.FiltersUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.widgets.TypefaceTextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class FilterFragment extends BaseChildFragment<FiltersUiPresenter> implements FiltersUiContract {

    private final Map<String, List<FilterVO>> mFiltersMap = new ConcurrentHashMap<>();

    @BindView(R.id.list_items) protected RecyclerView mFilterList;

    private FilterAdapter mFilterAdapter;

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

    @OnClick(R.id.button_clear_all)
    protected void onClearSelections() {
        for (Map.Entry<String, List<String>> entry : getFilterData().getFilters().entrySet()) {
            entry.getValue().clear();
        }
        refreshFilterBar();
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(mCloseFilterListener);
        TypefaceTextView doneButton = (TypefaceTextView) rootView.findViewById(R.id.done);
        doneButton.setOnClickListener(mCloseFilterListener);

        List<FilterBarVO> filterBarVOs = new LinkedList<>();

        Map<String, List<String>> filters = getFilterData().getFilters();
        if (filters != null) {
            for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
                String filterName = entry.getKey();
                FilterBarVO filterBarVO = new FilterBarVO(filterName);
                filterBarVO.setSelectedFilters(entry.getValue());
                filterBarVOs.add(filterBarVO);
                getPresenter().getFiltersByName(filterName);
            }
        }

        mFilterList.setHasFixedSize(true);
        mFilterList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFilterAdapter = new FilterAdapter(getLayoutInflater(getArguments()));

        mFilterAdapter.setItems(filterBarVOs);
        mFilterAdapter.setItemClickListener(new OnListItemClickListener<FilterBarVO>() {
            @Override
            public void onItemClick(int viewId, FilterBarVO model) {
                String filterName = model.getFilterName();
                List<FilterVO> filterVOs = mFiltersMap.get(filterName);
                List<String> selectedFilters = getFilterData().getFilters().get(filterName);
                if (selectedFilters == null) {
                    selectedFilters = new LinkedList<String>();
                }
                getPresenter().loadFilters(filterVOs, selectedFilters, filterName);
            }
        });
        mFilterList.setAdapter(mFilterAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFilterBar();
    }

    private void refreshFilterBar() {
        List<FilterBarVO> filterBarVOs = new LinkedList<>();
        Set<Map.Entry<String, List<String>>> entries = getFilterData().getFilters().entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            String filterName = entry.getKey();
            FilterBarVO filterBarVO = new FilterBarVO(filterName);
            filterBarVO.setSelectedFilters(entry.getValue());
            filterBarVOs.add(filterBarVO);
        }
        mFilterAdapter.setItems(filterBarVOs);
    }

    private FilterData getFilterData() {
        FilterDataDispatcher dataDispatcher = (FilterDataDispatcher) getActivity();
        return dataDispatcher.getFilterData();
    }

    private final View.OnClickListener mCloseFilterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    @Override
    public void onLoadFilters(String key, List<FilterVO> filterVOs) {
        mFiltersMap.put(key, filterVOs);
    }

    @Override
    public void onLoadFilterList(String filterName, List<FilterVO> filterVOs) {
        addChildFragment(FilterListFragment.newInstance(new FiltersWrapper(filterName, filterVOs)), "FRAG_FILTER_LIST");
    }
}
