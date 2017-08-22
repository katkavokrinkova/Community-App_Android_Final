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
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.vo.goals.GoalVO;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.features.goals.GoalsDetailUiContract;
import net.impacthub.members.presenter.features.goals.GoalsDetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.goals.binders.AboutViewBinder;
import net.impacthub.members.ui.features.home.groups.GroupDetailFragment;
import net.impacthub.members.ui.features.home.groups.binders.GroupsViewBinder;
import net.impacthub.members.ui.features.home.members.MemberDetailFragment;
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

    private static final String EXTRA_GOAL_IMAGE_URL = "net.impacthub.members.ui.features.home.goals.EXTRA_GOAL_IMAGE_URL";
    private static final String EXTRA_GOAL_NAME = "net.impacthub.members.ui.features.home.goals.EXTRA_GOAL_NAME";
    private static final String EXTRA_GOAL_SUMMARY = "net.impacthub.members.ui.features.home.goals.EXTRA_GOAL_SUMMARY";
    private static final String EXTRA_GOAL_DESCRIPTION = "net.impacthub.members.ui.features.home.goals.EXTRA_GOAL_DESCRIPTION";

    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.text_title) protected TextView mTitle;
    @BindView(R.id.text_sub_title) protected TextView mSubTitle;
    @BindView(R.id.tabs) protected TabLayout mGoalTab;
    @BindView(R.id.pager) protected ViewPager mGoalPages;

    private ViewBinder<List<GroupVO>> mViewBinder2;
    private ViewBinder<List<MemberVO>> mViewBinder3;

    public static GoalDetailFragment newInstance(GoalVO model) {

        Bundle args = new Bundle();
        args.putString(EXTRA_GOAL_IMAGE_URL, model.mImageURL);
        args.putString(EXTRA_GOAL_NAME, model.mName);
        args.putString(EXTRA_GOAL_SUMMARY, model.mSummary);
        args.putString(EXTRA_GOAL_DESCRIPTION, model.mDescription);
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
        return R.layout.fragment_detail_goal;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        String goalName = arguments.getString(EXTRA_GOAL_NAME);
        String goalSummary = arguments.getString(EXTRA_GOAL_SUMMARY);
        String goalDescription = arguments.getString(EXTRA_GOAL_DESCRIPTION);
        String imageURL = arguments.getString(EXTRA_GOAL_IMAGE_URL);

        setUpToolbar(goalName);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(imageURL), mImageDetail);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext());

        adapter.addVieBinder(new AboutViewBinder(goalName, goalDescription));
        adapter.addVieBinder(mViewBinder2 = new GroupsViewBinder(new OnListItemClickListener<GroupVO>() {
            @Override
            public void onItemClick(GroupVO model) {
                addChildFragment(GroupDetailFragment.newInstance(model), "FRAG_GROUP_DETAIL");
            }
        }));
        adapter.addVieBinder(mViewBinder3 = new MembersViewBinder(new OnListItemClickListener<MemberVO>() {
            @Override
            public void onItemClick(MemberVO model) {
                addChildFragment(MemberDetailFragment.newInstance(model), "FRAG_MEMBER_DETAIL");
            }
        }));

        mTitle.setText(goalName);
        mSubTitle.setText(goalSummary);

        mGoalPages.setAdapter(adapter);
        mGoalPages.setOffscreenPageLimit(adapter.getCount());

        mGoalTab.setupWithViewPager(mGoalPages);

        new TabsDelegate().setUp(mGoalTab, TITLES);

        getPresenter().loadDetails(goalName);
    }

    @Override
    public void onLoadGroups(List<GroupVO> groupDTOs) {
        mViewBinder2.bindView(groupDTOs);
    }

    @Override
    public void onLoadMembers(List<MemberVO> memberDTOs) {
        mViewBinder3.bindView(memberDTOs);
    }
}
