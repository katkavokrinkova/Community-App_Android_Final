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

package net.impacthub.members.ui.features.home.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.presenter.features.groups.GroupPresenter;
import net.impacthub.members.presenter.features.groups.GroupUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.groups.binders.GroupsViewBinder;

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

    private ViewBinder<List<GroupVO>> mViewBinder1;
    private ViewBinder<List<GroupVO>> mViewBinder2;
    private ViewBinder<List<GroupVO>> mViewBinder3;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_groups);


        AppPagerAdapter adapter = new AppPagerAdapter(getContext());
//
        adapter.addVieBinder(mViewBinder1 = new GroupsViewBinder(this));
        adapter.addVieBinder(mViewBinder2 = new GroupsViewBinder(this));
        adapter.addVieBinder(mViewBinder3 = new GroupsViewBinder(this));
//
        mGroupsPages.setAdapter(adapter);
        mGroupsPages.setOffscreenPageLimit(adapter.getCount());
        mGroupsTab.setupWithViewPager(mGroupsPages);

        new TabsDelegate().setUp(mGroupsTab, TITLES);

        getPresenter().getGroups();
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
