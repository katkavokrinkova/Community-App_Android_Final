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

package net.impacthub.app.ui.features.home.groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.presenter.features.groups.GroupPresenter;
import net.impacthub.app.presenter.features.groups.GroupUiContract;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.binder.ViewBinder;
import net.impacthub.app.ui.common.AppPagerAdapter;
import net.impacthub.app.ui.features.filters.FilterActivity;
import net.impacthub.app.ui.features.home.groups.binders.GroupsViewBinder;
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

public class GroupsFragment extends BaseChildFragment<GroupPresenter> implements GroupUiContract, OnListItemClickListener<GroupVO> {

    private static final String TITLES[] = {"ALL", "GROUPS YOU MANAGE", "YOUR GROUPS"};

    @BindView(R.id.tabs) protected TabLayout mGroupsTab;
    @BindView(R.id.pager) protected ViewPager mGroupsPages;
    @BindView(R.id.search_from_list) protected UISearchView mSearchView;
    @BindView(R.id.filter_tick) protected ImageView mFilterTick;

    private ViewBinder<List<GroupVO>> mViewBinder1;
    private ViewBinder<List<GroupVO>> mViewBinder2;
    private ViewBinder<List<GroupVO>> mViewBinder3;

    private GroupsListAdapter mListAdapter1, mListAdapter2, mListAdapter3;

    private FilterData mFilterData;

    public static GroupsFragment newInstance() {

        Bundle args = new Bundle();

        GroupsFragment fragment = new GroupsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected GroupPresenter onCreatePresenter() {
        return new GroupPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list_with_tabs;
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

        setUpToolbar(R.string.label_groups);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext(), TITLES);
//

        mListAdapter1 = new GroupsListAdapter(getLayoutInflater(getArguments()));
        mListAdapter1.setItemClickListener(this);
        mListAdapter2 = new GroupsListAdapter(getLayoutInflater(getArguments()));
        mListAdapter2.setItemClickListener(this);
        mListAdapter3 = new GroupsListAdapter(getLayoutInflater(getArguments()));
        mListAdapter3.setItemClickListener(this);

        adapter.addVieBinder(mViewBinder1 = new GroupsViewBinder(mListAdapter1));
        adapter.addVieBinder(mViewBinder2 = new GroupsViewBinder(mListAdapter2));
        adapter.addVieBinder(mViewBinder3 = new GroupsViewBinder(mListAdapter3));

        mGroupsPages.setAdapter(adapter);
        mGroupsPages.setOffscreenPageLimit(adapter.getCount());
        mGroupsTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mGroupsTab.setupWithViewPager(mGroupsPages);

        getPresenter().getGroups();

        mSearchView.setSearchActionListener(new UISearchView.OnSearchActionListener() {
            @Override
            public void onSearch(String searchValue) {

            }

            @Override
            public void onTextChanged(String query) {
                mListAdapter1.filterSearch(query);
                mListAdapter2.filterSearch(query);
                mListAdapter3.filterSearch(query);
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
    public void onItemClick(int viewId, GroupVO model) {
        addChildFragment(GroupDetailFragment.newInstance(model), "FRAG_GROUP_DETAIL");
    }

    @Override
    public void onLoadAllGroups(List<GroupVO> groupList) {
        mViewBinder1.bindView(groupList);
    }

    @Override
    public void onLoadYourGroups(List<GroupVO> groupList) {
        mViewBinder3.bindView(groupList);
    }

    @Override
    public void onShowTick(Map<String, List<String>> filters) {
        ViewUtils.visible(mFilterTick);
        mListAdapter1.applyFilters(filters);
        mListAdapter2.applyFilters(filters);
        mListAdapter3.applyFilters(filters);
    }

    @Override
    public void onHideTick() {
        ViewUtils.gone(mFilterTick);
        mListAdapter1.resetFilters();
        mListAdapter2.resetFilters();
        mListAdapter3.resetFilters();
    }
}
