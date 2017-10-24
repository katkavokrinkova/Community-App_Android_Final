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

package net.impacthub.app.ui.features.home.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.vo.jobs.JobVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.features.projects.ProjectDetailUiContract;
import net.impacthub.app.presenter.features.projects.ProjectDetailUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.binder.ViewBinder;
import net.impacthub.app.ui.common.AppPagerAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.features.home.chatter.ChatterCommentFragment;
import net.impacthub.app.ui.features.home.chatter.CreatePostActivity;
import net.impacthub.app.ui.features.home.chatter.binder.ChatterViewBinder;
import net.impacthub.app.ui.features.home.jobs.JobDetailFragment;
import net.impacthub.app.ui.features.home.jobs.binders.JobsViewBinder;
import net.impacthub.app.ui.features.home.members.MemberDetailFragment;
import net.impacthub.app.ui.features.home.members.binders.MembersViewBinder;
import net.impacthub.app.ui.features.home.projects.binders.ObjectivesViewBinder;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class ProjectDetailFragment extends BaseChildFragment<ProjectDetailUiPresenter> implements ProjectDetailUiContract {

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

    private ViewBinder<List<ChatterVO>> mViewBinder1;
    private ViewBinder<List<ListItemType>> mViewBinder2;
    private ViewBinder<List<MemberVO>> mViewBinder3;
    private ViewBinder<List<JobVO>> mViewBinder4;
    private String mChatterFeedId;

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
    protected void bindView(View rootView) {
        super.bindView(rootView);

        Bundle arguments = getArguments();
        String projectId = arguments.getString(EXTRA_PROJECT_ID);
        mChatterFeedId = arguments.getString(EXTRA_PROJECT_CHATTER_ID);
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
                            Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                            intent.putExtra(CreatePostActivity.EXTRA_GROUP_ID, mChatterFeedId);
                            startActivity(intent);
                            return true;
                    }
                    return false;
                }
            });
        }

        mTitle.setText(projectName);
        mSubTitle.setText(String.format("by %s", organizationName));

        ImageLoaderHelper.loadImage(getContext(), buildUrl(projectImageURL), mImageDetail);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext(), TITLES);

        mViewBinder1 = new ChatterViewBinder(new OnListItemClickListener<ChatterVO>() {
            @Override
            public void onItemClick(int viewId, ChatterVO model) {
                switch (viewId) {
                    case R.id.member_image:
                        getPresenter().getMemberBy(model.mUserId);
                        break;
                    case R.id.comment_bar:
                        addChildFragment(ChatterCommentFragment.newInstance(model), "FRAG_CHATTER_COMMENTS");
                        break;
                    case R.id.like_bar:
                        if(model.mIsLikedByMe) getPresenter().unlikePost(model.mLikeId);
                        else getPresenter().likePost(model.mCommentId);
                        break;
                }
            }
        });
        mViewBinder2 = new ObjectivesViewBinder();
        mViewBinder3 = new MembersViewBinder(new OnListItemClickListener<MemberVO>() {
            @Override
            public void onItemClick(int viewId, MemberVO model) {
                addChildFragment(MemberDetailFragment.newInstance(model), "FRAG_MEMBER_DETAIL");
            }
        });

        mViewBinder4 = new JobsViewBinder(new OnListItemClickListener<JobVO>() {
            @Override
            public void onItemClick(int viewId, JobVO model) {
                addChildFragment(JobDetailFragment.newInstance(model), "FRAG_JOB_DETAIL");
            }
        });

        adapter.addVieBinder(mViewBinder1);
        adapter.addVieBinder(mViewBinder2);
        adapter.addVieBinder(mViewBinder3);
        adapter.addVieBinder(mViewBinder4);

        mProjectPages.setAdapter(adapter);
        mProjectPages.setOffscreenPageLimit(adapter.getCount());

        mProjectTab.setupWithViewPager(mProjectPages);

//        new TabsDelegate().setUp(mProjectTab, TITLES);

        getPresenter().loadDetails(mChatterFeedId, projectId);
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
    public void onLoadObjectives(List<ListItemType> listItemTypes) {
        mViewBinder2.bindView(listItemTypes);
    }

    @Override
    public void onLoadMember(MemberVO memberVO) {
        addChildFragment(MemberDetailFragment.newInstance(memberVO), "FRAG_MEMBER_DETAIL");
    }
}
