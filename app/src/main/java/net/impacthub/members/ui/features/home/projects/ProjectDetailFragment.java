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

package net.impacthub.members.ui.features.home.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.delegate.TabsDelegate;
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

public class ProjectDetailFragment extends BaseChildFragment {

    public static final String TITLES[] = {"FEED", "OBJECTIVES", "MEMBERS", "JOBS"};

    private static final String EXTRA_GOAL_NAME = "net.impacthub.members.ui.features.home.goals.EXTRA_GOAL_NAME";
    private static final String EXTRA_GOAL_IMAGE_URL = "net.impacthub.members.ui.features.home.goals.EXTRA_GOAL_IMAGE_URL";

    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.tabs) protected TabLayout mProjectTab;
    @BindView(R.id.pager) protected ViewPager mProjectPages;

    private AppPagerAdapter mPagerAdapter;

    private ViewBinder<List<MemberDTO>> mViewBinder3;

    public static ProjectDetailFragment newInstance(ProjectDTO projectDTO) {

        Bundle args = new Bundle();

        ProjectDetailFragment fragment = new ProjectDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_category_detail_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpToolbar("Change Me");

        ImageLoaderHelper.loadImage(getContext(), buildUrl("imageURL"), mImageDetail);

        mPagerAdapter = new AppPagerAdapter(getContext());
        mPagerAdapter.addVieBinder(new AboutViewBinder());
        mPagerAdapter.addVieBinder(new AboutViewBinder());
        mPagerAdapter.addVieBinder(mViewBinder3 = new MembersViewBinder(new OnListItemClickListener<MemberDTO>() {
            @Override
            public void onItemClick(MemberDTO model) {
                addChildFragment(MemberDetailFragment.newInstance(model), "FRAG_MEMBER_DETAIL");
            }
        }));
        mPagerAdapter.addVieBinder(new AboutViewBinder());

        mProjectPages.setAdapter(mPagerAdapter);
        mProjectPages.setOffscreenPageLimit(mPagerAdapter.getCount());

        mProjectTab.setupWithViewPager(mProjectPages);

        new TabsDelegate().setUp(mProjectTab, TITLES);
    }
}
