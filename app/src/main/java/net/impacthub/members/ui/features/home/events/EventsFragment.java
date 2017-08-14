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

package net.impacthub.members.ui.features.home.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.events.EventDTO;
import net.impacthub.members.presenter.features.events.EventsUiContract;
import net.impacthub.members.presenter.features.events.EventsUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.features.home.events.binders.AllEventsViewBinder;
import net.impacthub.members.ui.features.home.projects.binders.AllProjectViewBinder;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class EventsFragment extends BaseChildFragment<EventsUiPresenter> implements EventsUiContract {

    public static final String TITLES[] = {"ALL", "EVENTS YOU MANAGE", "YOUR EVENTS"};

    @BindView(R.id.tabs) protected TabLayout mEventsTab;
    @BindView(R.id.pager) protected ViewPager mEventsPager;

    private AppPagerAdapter mPagerAdapter;
    private ViewBinder<List<EventDTO>> mViewBinder1;
    private AllProjectViewBinder mViewBinder2;
    private AllProjectViewBinder mViewBinder3;

    public static EventsFragment newInstance() {

        Bundle args = new Bundle();

        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected EventsUiPresenter onCreatePresenter() {
        return new EventsUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list_with_tabs;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_events);

        mPagerAdapter = new AppPagerAdapter(getContext());
        mViewBinder1 = new AllEventsViewBinder();
        mViewBinder2 = new AllProjectViewBinder();
        mViewBinder3 = new AllProjectViewBinder();
        mPagerAdapter.addVieBinder(mViewBinder1);
        mPagerAdapter.addVieBinder(mViewBinder2);
        mPagerAdapter.addVieBinder(mViewBinder3);
        mEventsPager.setAdapter(mPagerAdapter);
        mEventsPager.setOffscreenPageLimit(mPagerAdapter.getCount());
        mEventsTab.setupWithViewPager(mEventsPager);

        for (int i = 0; i < mEventsTab.getTabCount(); i++) {
            TabLayout.Tab tabAt = mEventsTab.getTabAt(i);
            if (tabAt != null) {
                tabAt.setCustomView(createTabTitle(TITLES[i]));
            }
        }

        getPresenter().getEvents();
    }

    @Override
    public void onLoadEvents(List<EventDTO> eventDTOs) {
        mViewBinder1.bindView(eventDTOs);
    }
}
