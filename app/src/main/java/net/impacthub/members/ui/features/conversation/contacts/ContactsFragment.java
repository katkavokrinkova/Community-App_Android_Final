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

package net.impacthub.members.ui.features.conversation.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.members.binders.AboutViewBinder;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class ContactsFragment extends BaseChildFragment {

    private static final String TITLES[] = {"ACTIVE", "PENDING", "REJECTED"};

    @BindView(R.id.tabs) protected TabLayout mContactsTab;
    @BindView(R.id.pager) protected ViewPager mContactPages;

    private ViewBinder<List<ListItemType>> mViewBinder1;
    private ViewBinder<List<ListItemType>> mViewBinder2;
    private ViewBinder<List<ListItemType>> mViewBinder3;

    public static ContactsFragment newInstance() {

        Bundle args = new Bundle();

        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list_with_tabs;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_contact);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext());
//
        adapter.addVieBinder(mViewBinder1 = new AboutViewBinder());
        adapter.addVieBinder(mViewBinder2 = new AboutViewBinder());
        adapter.addVieBinder(mViewBinder3 = new AboutViewBinder());
//
        mContactPages.setAdapter(adapter);
        mContactPages.setOffscreenPageLimit(adapter.getCount());
        mContactsTab.setupWithViewPager(mContactPages);

        new TabsDelegate().setUp(mContactsTab, TITLES);
    }
}
