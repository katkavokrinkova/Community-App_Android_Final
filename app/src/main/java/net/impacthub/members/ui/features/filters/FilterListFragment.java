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

package net.impacthub.members.ui.features.filters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.features.filters.SeparatedFilters;
import net.impacthub.members.presenter.features.filters.FiltersUiContract;
import net.impacthub.members.presenter.features.filters.FiltersUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/21/2017.
 */

public class FilterListFragment extends BaseChildFragment<FiltersUiPresenter> implements FiltersUiContract {

    @BindView(R.id.filter_items) protected RecyclerView mFilterItems;

    private FiltersAdapter mAdapter;

    public static FilterListFragment newInstance() {

        Bundle args = new Bundle();

        FilterListFragment fragment = new FilterListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FiltersUiPresenter onCreatePresenter() {
        return new FiltersUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_filters_list;
    }

    @OnClick(R.id.button_clear)
    protected void onClearSelections() {
        mAdapter.clearFilters();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.filter);
        mFilterItems.setHasFixedSize(true);
        mAdapter = new FiltersAdapter(getLayoutInflater(getArguments()));
        mFilterItems.setAdapter(mAdapter);
        getPresenter().getFiltersList();
    }

    @Override
    public void onLoadFilters(SeparatedFilters response) {
        mAdapter.setItems(response.getCities());
    }
}
