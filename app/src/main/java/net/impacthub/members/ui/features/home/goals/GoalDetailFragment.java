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

package net.impacthub.members.ui.features.home.goals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.goals.GoalDTO;
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.presenter.features.goals.GoalsDetailUiContract;
import net.impacthub.members.presenter.features.goals.GoalsDetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.groups.binders.GroupsViewBinder;
import net.impacthub.members.ui.features.home.members.MemberDetailFragment;
import net.impacthub.members.ui.features.home.members.binders.AboutViewBinder;
import net.impacthub.members.ui.features.home.members.binders.MembersViewBinder;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class GoalDetailFragment extends BaseChildFragment<GoalsDetailUiPresenter> implements GoalsDetailUiContract {

    public static final String TITLES[] = {"ABOUT", "GROUPS", "MEMBERS"};

    private static final String EXTRA_GOAL_NAME = "net.impacthub.members.ui.features.home.goals.EXTRA_GOAL_NAME";
    private static final String EXTRA_GOAL_IMAGE_URL = "net.impacthub.members.ui.features.home.goals.EXTRA_GOAL_IMAGE_URL";

    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.tabs) protected TabLayout mGoalTab;
    @BindView(R.id.pager) protected ViewPager mGoalPages;

    private AppPagerAdapter mPagerAdapter;

    private ViewBinder<List<GroupDTO>> mViewBinder2;
    private ViewBinder<List<MemberDTO>> mViewBinder3;

    public static GoalDetailFragment newInstance(GoalDTO model) {

        Bundle args = new Bundle();
        args.putString(EXTRA_GOAL_NAME, model.mName);
        args.putString(EXTRA_GOAL_IMAGE_URL, model.mImageURL);
        GoalDetailFragment fragment = new GoalDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected GoalsDetailUiPresenter onCreatePresenter() {
        return new GoalsDetailUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_category_detail_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        String goalName = arguments.getString(EXTRA_GOAL_NAME);
        String imageURL = arguments.getString(EXTRA_GOAL_IMAGE_URL);

        setUpToolbar(goalName);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(imageURL), mImageDetail);

        mPagerAdapter = new AppPagerAdapter(getContext());
        mPagerAdapter.addVieBinder(new AboutViewBinder());
        mPagerAdapter.addVieBinder(mViewBinder2 = new GroupsViewBinder(new OnListItemClickListener<GroupDTO>() {
            @Override
            public void onItemClick(GroupDTO model) {

            }
        }));
        mPagerAdapter.addVieBinder(mViewBinder3 = new MembersViewBinder(new OnListItemClickListener<MemberDTO>() {
            @Override
            public void onItemClick(MemberDTO model) {
                addChildFragment(MemberDetailFragment.newInstance(model), "FRAG_MEMBER_DETAIL");
            }
        }));

        mGoalPages.setAdapter(mPagerAdapter);
        mGoalPages.setOffscreenPageLimit(mPagerAdapter.getCount());

        mGoalTab.setupWithViewPager(mGoalPages);

        new TabsDelegate().setUp(mGoalTab, TITLES);

        getPresenter().loadDetails(goalName);
    }

    @Override
    public void onLoadGroups(List<GroupDTO> groupDTOs) {
        mViewBinder2.bindView(groupDTOs);
    }

    @Override
    public void onLoadMembers(List<MemberDTO> memberDTOs) {
        mViewBinder3.bindView(memberDTOs);
    }
}
