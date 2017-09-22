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

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.presenter.features.companies.CompaniesUiContract;
import net.impacthub.app.presenter.features.companies.CompaniesUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class CompaniesFragment extends BaseChildFragment<CompaniesUiPresenter> implements CompaniesUiContract, OnListItemClickListener<CompanyVO> {

    @BindView(R.id.list_items) protected RecyclerView mCompanyList;
    private CompaniesListAdapter mAdapter;

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

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.companies);
        mCompanyList.setHasFixedSize(true);
        mAdapter = new CompaniesListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(this);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mCompanyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCompanyList.addItemDecoration(new LinearItemsMarginDecorator(offset));
        mCompanyList.setAdapter(mAdapter);

        getPresenter().getCompanies();
    }

    @Override
    public void onItemClick(int viewId, CompanyVO model) {
        addChildFragment(CompanyDetailFragment.newInstance(model), "FRAG_COMPANY_DETAIL");
    }

    @Override
    public void onLoadCompanies(List<CompanyVO> companies) {
        mAdapter.setItems(companies);
    }
}
