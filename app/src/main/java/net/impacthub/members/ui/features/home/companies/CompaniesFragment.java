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

package net.impacthub.members.ui.features.home.companies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.companies.CompanyDTO;
import net.impacthub.members.presenter.features.companies.CompaniesUiContract;
import net.impacthub.members.presenter.features.companies.CompaniesUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class CompaniesFragment extends BaseChildFragment<CompaniesUiPresenter> implements CompaniesUiContract, OnListItemClickListener<CompanyDTO> {

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
        return R.layout.fragment_searchable_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.companies);
        mCompanyList.setHasFixedSize(true);
        mAdapter = new CompaniesListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(this);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mCompanyList.addItemDecoration(new LinearItemsMarginDecorator(offset, offset, 0 ,offset));
        mCompanyList.setAdapter(mAdapter);

        getPresenter().getCompanies();
    }

    @Override
    public void onItemClick(CompanyDTO model) {
        addChildFragment(CompanyDetailFragment.newInstance(model), "FRAG_COMPANY_DETAIL");
    }

    @Override
    public void onLoadCompanies(List<CompanyDTO> companies) {
        mAdapter.setItems(companies);
    }
}
