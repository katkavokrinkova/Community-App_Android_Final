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

package net.impacthub.app.ui.features.home.companies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.presenter.features.companies.CompaniesUiContract;
import net.impacthub.app.presenter.features.companies.CompaniesUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;
import net.impacthub.app.ui.features.filters.FilterActivity;
import net.impacthub.app.ui.widgets.UISearchView;
import net.impacthub.app.utilities.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static net.impacthub.app.ui.features.filters.FilterActivity.EXTRA_FILTER_DATA;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class CompaniesFragment extends BaseChildFragment<CompaniesUiPresenter> implements CompaniesUiContract, OnListItemClickListener<CompanyVO> {

    @BindView(R.id.list_items) protected RecyclerView mCompanyList;
    @BindView(R.id.search_from_list) protected UISearchView mSearchView;
    @BindView(R.id.filter_tick) protected ImageView mFilterTick;

    private CompaniesListAdapter mAdapter;
    private FilterData mFilterData;

    public static CompaniesFragment newInstance() {

        Bundle args = new Bundle();

        CompaniesFragment fragment = new CompaniesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected CompaniesUiPresenter onCreatePresenter() {
        return new CompaniesUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_list_with_fixed_searchbar;
    }

    @OnClick(R.id.filter_button)
    protected void onFilterClick() {
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        intent.putExtra(EXTRA_FILTER_DATA, mFilterData);
        startActivityForResult(intent, FilterActivity.FILTER_REQUEST_CODE);
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.companies);
        mCompanyList.setHasFixedSize(true);
        mAdapter = new CompaniesListAdapter(getIHLayoutInflater());
        mAdapter.setItemClickListener(this);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mCompanyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCompanyList.addItemDecoration(new LinearItemsMarginDecorator(offset));
        mCompanyList.setAdapter(mAdapter);

        getPresenter().getCompanies();

        mSearchView.setSearchActionListener(new UISearchView.OnSearchActionListener() {
            @Override
            public void onSearch(String searchValue) {
                showToast("Already filtered");
            }

            @Override
            public void onTextChanged(String query) {
                mAdapter.filterSearch(query);
            }
        });

        mFilterData = new FilterData();
        mFilterData.getFilters().put(FilterData.KEY_FILTER_CITY, new ArrayList<String>());
        mFilterData.getFilters().put(FilterData.KEY_FILTER_SECTOR, new ArrayList<String>());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FilterActivity.FILTER_REQUEST_CODE) {
            mFilterData = (FilterData) data.getSerializableExtra(EXTRA_FILTER_DATA);
            getPresenter().handleFilters(mFilterData);
        }
    }

    @Override
    public void onItemClick(int viewId, CompanyVO model, int position) {
        addChildFragment(CompanyDetailFragment.newInstance(model), "FRAG_COMPANY_DETAIL");
    }

    @Override
    public void onLoadCompanies(List<CompanyVO> companies) {
        mAdapter.setItems(companies);
    }

    @Override
    public void onShowTick(Map<String, List<String>> filters) {
        ViewUtils.visible(mFilterTick);
        mAdapter.applyFilters(filters);
    }

    @Override
    public void onHideTick() {
        ViewUtils.gone(mFilterTick);
        mAdapter.resetFilters();
    }
}
