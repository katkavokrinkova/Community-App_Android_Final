/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.app.ui.features.filters;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.filters.FilterDataDispatcher;
import net.impacthub.app.model.vo.filters.FilterVO;
import net.impacthub.app.model.vo.filters.FiltersWrapper;
import net.impacthub.app.ui.base.BaseChildFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/21/2017.
 */

public class FilterListFragment extends BaseChildFragment {

    public static final String EXTRA_FILTERS = "net.impacthub.app.ui.features.filters.EXTRA_FILTERS";

    @BindView(R.id.filter_items) protected RecyclerView mFilterItems;

    private FiltersListAdapter mAdapter;
    private String mFilterName;

    public static FilterListFragment newInstance(FiltersWrapper filters) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_FILTERS, filters);
        FilterListFragment fragment = new FilterListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_filters_list;
    }

    @OnClick(R.id.button_clear)
    protected void onClearSelections() {
        mAdapter.clearFilters();
        List<String> filters = getFilterData().getFilters().get(mFilterName);
        if (filters != null) {
            filters.clear();
        }
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.filter);

        FiltersWrapper filters = (FiltersWrapper) getArguments().getSerializable(EXTRA_FILTERS);

        mFilterItems.setHasFixedSize(true);
        mAdapter = new FiltersListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(new OnListItemClickListener<FilterVO>() {
            @Override
            public void onItemClick(int viewId, FilterVO model) {
                List<String> filters = getFilterData().getFilters().get(mFilterName);
                if (filters != null) {
                    String name = model.getName();
                    if (model.isSelected() && !filters.contains(name)) {
                        filters.add(name);
                    } else if (!model.isSelected()) {
                        filters.remove(name);
                    }
                }
            }
        });
        if (filters != null) {
            mFilterName = filters.getFilterName();
            mAdapter.setItems(filters.getFilterVOs());
        }

        mFilterItems.setAdapter(mAdapter);
    }

    private FilterData getFilterData() {
        FilterDataDispatcher dataDispatcher = (FilterDataDispatcher) getActivity();
        return dataDispatcher.getFilterData();
    }
}
