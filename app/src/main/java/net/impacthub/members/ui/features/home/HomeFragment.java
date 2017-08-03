package net.impacthub.members.ui.features.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.home.HomeMenuItem;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;
import net.impacthub.members.ui.features.home.members.MembersFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class HomeFragment extends BaseChildFragment implements OnListItemClickListener<Integer> {

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext().getApplicationContext();
        TextView titleView = (TextView) view.findViewById(R.id.text_toolbar_title);
        titleView.setText(context.getString(R.string.home));

        RecyclerView homeMenuList = (RecyclerView) view.findViewById(R.id.list_home_menu);
        homeMenuList.setHasFixedSize(true);
        homeMenuList.addItemDecoration(new LinearItemsMarginDecorator(context.getResources().getDimensionPixelSize(R.dimen.default_home_item_gap_margin)));
        homeMenuList.setLayoutManager(new LinearLayoutManager(context));
        HomeListAdapter adapter = new HomeListAdapter(getActivity().getLayoutInflater());
        adapter.setItemClickListener(this);
        homeMenuList.setAdapter(adapter);
        List<HomeMenuItem> homeMenuItems = new LinkedList<>();
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_members, context.getString(R.string.label_home_menu_members)));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_companies, context.getString(R.string.label_home_menu_companies)));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_groups, context.getString(R.string.label_home_menu_groups)));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_goals, context.getString(R.string.label_home_menu_goals)));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_jobs, context.getString(R.string.label_home_menu_jobs)));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_events, context.getString(R.string.label_home_menu_events)));
        homeMenuItems.add(new HomeMenuItem(R.mipmap.ic_projects, context.getString(R.string.label_home_menu_projects)));
        adapter.setItems(homeMenuItems);
    }

    @Override
    public void onItemClick(Integer model) {
        switch (model) {
            case 0:
                addChildFragment(MembersFragment.newInstance(), "FRAG_MEMBERS");
                break;
        }
    }
}
