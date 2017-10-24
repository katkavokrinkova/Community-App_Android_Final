package net.impacthub.app.ui.features.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.home.HomeMenuItem;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;
import net.impacthub.app.ui.features.home.companies.CompaniesFragment;
import net.impacthub.app.ui.features.home.events.EventsFragment;
import net.impacthub.app.ui.features.home.goals.GoalsFragment;
import net.impacthub.app.ui.features.home.groups.GroupsFragment;
import net.impacthub.app.ui.features.home.jobs.JobsFragment;
import net.impacthub.app.ui.features.home.members.MembersFragment;
import net.impacthub.app.ui.features.home.projects.ProjectsFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class HomeFragment extends BaseChildFragment implements OnListItemClickListener<HomeMenuItem> {

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);
        setUpToolbar(R.string.home, Color.WHITE);

        FragmentActivity activity = getActivity();
        RecyclerView homeMenuList = (RecyclerView) rootView.findViewById(R.id.list_items);
        homeMenuList.setHasFixedSize(true);
        homeMenuList.addItemDecoration(new LinearItemsMarginDecorator(activity.getResources().getDimensionPixelSize(R.dimen.default_home_item_gap_margin)));
        homeMenuList.setLayoutManager(new LinearLayoutManager(activity));
        HomeListAdapter adapter = new HomeListAdapter(activity.getLayoutInflater());
        adapter.setItemClickListener(this);
        homeMenuList.setAdapter(adapter);
        List<HomeMenuItem> homeMenuItems = new LinkedList<>();
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_members, getString(R.string.label_members), 0));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_companies, getString(R.string.label_companies), 1));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_groups, getString(R.string.label_groups), 2));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_goals, getString(R.string.label_goals), 3));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_jobs, getString(R.string.label_jobs), 4));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_events, getString(R.string.label_events), 5));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_projects, getString(R.string.label_projects), 6));
        adapter.setItems(homeMenuItems);
    }

    @Override
    public void onItemClick(int viewId, HomeMenuItem model, int position) {
        switch (model.getPosition()) {
            case 0:
                addChildFragment(MembersFragment.newInstance(), "FRAG_MEMBERS");
                break;
            case 1:
                addChildFragment(CompaniesFragment.newInstance(), "FRAG_COMPANIES");
                break;
            case 2:
                addChildFragment(GroupsFragment.newInstance(), "FRAG_GROUPS");
                break;
            case 3:
                addChildFragment(GoalsFragment.newInstance(), "FRAG_GOALS");
                break;
            case 4:
                addChildFragment(JobsFragment.newInstance(), "FRAG_JOBS");
                break;
            case 5:
                addChildFragment(EventsFragment.newInstance(), "FRAG_EVENTS");
                break;
            case 6:
                addChildFragment(ProjectsFragment.newInstance(), "FRAG_PROJECTS");
                break;
        }
    }
}
