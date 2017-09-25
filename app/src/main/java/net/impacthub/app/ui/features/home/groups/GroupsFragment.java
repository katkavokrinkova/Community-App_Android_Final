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

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.presenter.features.groups.GroupPresenter;
import net.impacthub.app.presenter.features.groups.GroupUiContract;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.binder.ViewBinder;
import net.impacthub.app.ui.common.AppPagerAdapter;
import net.impacthub.app.ui.features.home.groups.binders.GroupsViewBinder;
import net.impacthub.app.ui.widgets.UISearchView;

import java.util.List;

import butterknife.BindView;

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

    private ViewBinder<List<GroupVO>> mViewBinder1;
    private ViewBinder<List<GroupVO>> mViewBinder2;
    private ViewBinder<List<GroupVO>> mViewBinder3;

    private GroupsListAdapter mListAdapter1, mListAdapter2, mListAdapter3;

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
                mListAdapter1.filter(query);
                mListAdapter2.filter(query);
                mListAdapter3.filter(query);
            }
        });
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
}
