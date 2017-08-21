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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnChatterFeedItemClickListener;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.vo.chatter.ChatterVO;
import net.impacthub.members.model.vo.jobs.JobVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.model.pojo.ListItem;
import net.impacthub.members.presenter.features.projects.ProjectDetailUiContract;
import net.impacthub.members.presenter.features.projects.ProjectDetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.jobs.JobDetailFragment;
import net.impacthub.members.ui.features.home.jobs.binders.JobsViewBinder;
import net.impacthub.members.ui.features.home.members.MemberDetailFragment;
import net.impacthub.members.ui.features.home.members.binders.MembersViewBinder;
import net.impacthub.members.ui.features.home.projects.binders.ChatterViewBinder;
import net.impacthub.members.ui.features.home.projects.binders.ObjectivesViewBinder;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class ProjectDetailFragment extends BaseChildFragment<ProjectDetailUiPresenter> implements ProjectDetailUiContract, OnChatterFeedItemClickListener {

    public static final String TITLES[] = {"FEED", "OBJECTIVES", "MEMBERS", "JOBS"};

    private static final String EXTRA_PROJECT_ID = "net.impacthub.members.ui.features.home.projects.EXTRA_PROJECT_ID";
    private static final String EXTRA_PROJECT_CHATTER_ID = "net.impacthub.members.ui.features.home.projects.EXTRA_PROJECT_CHATTER_ID";
    private static final String EXTRA_PROJECT_IMAGE_URL = "net.impacthub.members.ui.features.home.projects.EXTRA_PROJECT_IMAGE_URL";
    private static final String EXTRA_PROJECT_NAME = "net.impacthub.members.ui.features.home.projects.EXTRA_PROJECT_NAME";
    private static final String EXTRA_PROJECT_ORGANIZATION_NAME = "net.impacthub.members.ui.features.home.projects.EXTRA_PROJECT_ORGANIZATION_NAME";

    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.text_title) protected TextView mTitle;
    @BindView(R.id.text_sub_title) protected TextView mSubTitle;
    @BindView(R.id.tabs) protected TabLayout mProjectTab;
    @BindView(R.id.pager) protected ViewPager mProjectPages;

    private ViewBinder<List<ListItem<?>>> mViewBinder2;
    private ViewBinder<List<MemberVO>> mViewBinder3;
    private ViewBinder<List<JobVO>> mViewBinder4;
    private ViewBinder<List<ChatterVO>> mViewBinder1;

    private AppPagerAdapter mPagerAdapter;

    public static ProjectDetailFragment newInstance(ProjectVO projectDTO) {

        Bundle args = new Bundle();
        args.putString(EXTRA_PROJECT_ID, projectDTO.mProjectId);
        args.putString(EXTRA_PROJECT_CHATTER_ID, projectDTO.mChatterGroupId);
        args.putString(EXTRA_PROJECT_NAME, projectDTO.mName);
        args.putString(EXTRA_PROJECT_ORGANIZATION_NAME, projectDTO.mOrganizationName);
        args.putString(EXTRA_PROJECT_IMAGE_URL, projectDTO.mImageURL);

        ProjectDetailFragment fragment = new ProjectDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ProjectDetailUiPresenter onCreatePresenter() {
        return new ProjectDetailUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_category_detail_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        String projectId = arguments.getString(EXTRA_PROJECT_ID);
        String feedId = arguments.getString(EXTRA_PROJECT_CHATTER_ID);
        String projectName = arguments.getString(EXTRA_PROJECT_NAME);
        String organizationName = arguments.getString(EXTRA_PROJECT_ORGANIZATION_NAME);
        String projectImageURL = arguments.getString(EXTRA_PROJECT_IMAGE_URL);

        setUpToolbar(projectName);
        if (mToolbar != null) {
            mToolbar.inflateMenu(R.menu.menu_compose_post);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.actionCompose:
                            showToast("Compose clicked");
                            return true;
                    }
                    return false;
                }
            });
        }

        mTitle.setText(projectName);
        mSubTitle.setText(String.format("by %s", organizationName));

        ImageLoaderHelper.loadImage(getContext(), buildUrl(projectImageURL), mImageDetail);

        mPagerAdapter = new AppPagerAdapter(getContext());

        mViewBinder1 = new ChatterViewBinder(this);
        mViewBinder2 = new ObjectivesViewBinder();
        mViewBinder3 = new MembersViewBinder(new OnListItemClickListener<MemberVO>() {
            @Override
            public void onItemClick(MemberVO model) {
                addChildFragment(MemberDetailFragment.newInstance(model), "FRAG_MEMBER_DETAIL");
            }
        });

        mViewBinder4 = new JobsViewBinder(new OnListItemClickListener<JobVO>() {
            @Override
            public void onItemClick(JobVO model) {
                addChildFragment(JobDetailFragment.newInstance(model), "FRAG_JOB_DETAIL");
            }
        });

        mPagerAdapter.addVieBinder(mViewBinder1);
        mPagerAdapter.addVieBinder(mViewBinder2);
        mPagerAdapter.addVieBinder(mViewBinder3);
        mPagerAdapter.addVieBinder(mViewBinder4);

        mProjectPages.setAdapter(mPagerAdapter);
        mProjectPages.setOffscreenPageLimit(mPagerAdapter.getCount());

        mProjectTab.setupWithViewPager(mProjectPages);

        new TabsDelegate().setUp(mProjectTab, TITLES);

        getPresenter().loadDetails(feedId, projectId);
    }

    @Override
    public void onLoadChatterFeed(List<ChatterVO> chatterDTOs) {
        mViewBinder1.bindView(chatterDTOs);
    }

    @Override
    public void onLoadMembers(List<MemberVO> memberDTOs) {
        mViewBinder3.bindView(memberDTOs);
    }

    @Override
    public void onLoadJobs(List<JobVO> jobDTOs) {
        mViewBinder4.bindView(jobDTOs);
    }

    @Override
    public void onLoadObjectives(List<ListItem<?>> infoList) {

        ListItem<String> titleObjective = new ListItem<>(ListItem.TYPE_ONE);
        titleObjective.setModel("GOALS");
        infoList.add(0, titleObjective);

        mViewBinder2.bindView(infoList);
    }

    @Override
    public void onLoadMember(MemberVO memberDTO) {
        addChildFragment(MemberDetailFragment.newInstance(memberDTO), "FRAG_MEMBER_DETAIL");
    }

    @Override
    public void onProfileImageClicked(ChatterVO chatterDTO) {
        getPresenter().loadMember(chatterDTO.mUserId);
    }
}
