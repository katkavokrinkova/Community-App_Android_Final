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

package net.impacthub.members.ui.features.home.jobs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.pojo.SimpleItem;
import net.impacthub.members.model.vo.jobs.JobDescriptionVO;
import net.impacthub.members.model.vo.jobs.JobVO;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.presenter.features.jobs.JobsDetailUiContract;
import net.impacthub.members.presenter.features.jobs.JobsDetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewScrollListener;
import net.impacthub.members.ui.features.home.projects.ProjectDetailFragment;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class JobDetailFragment extends BaseChildFragment<JobsDetailUiPresenter> implements JobsDetailUiContract {

    public static final String EXTRA_JOB_ID = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_ID";
    public static final String EXTRA_JOB_NAME = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_NAME";
    public static final String EXTRA_JOB_IMAGE_URL = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_IMAGE_URL";
    public static final String EXTRA_JOB_LOCATION = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_LOCATION";
    public static final String EXTRA_JOB_MEMBER_COUNT = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_MEMBER_COUNT";
    public static final String EXTRA_JOB_MEMBER_SALARY = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_MEMBER_SALARY";
    public static final String EXTRA_JOB_MEMBER_DESCRIPTION = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_MEMBER_DESCRIPTION";

    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.list_items) protected RecyclerView mJobDetailList;
    @BindView(R.id.done) protected TextView mApplyButton;

    private JobDetailListAdapter mAdapter;

    public static JobDetailFragment newInstance(JobVO jobDTO) {

        Bundle args = new Bundle();

        args.putString(EXTRA_JOB_ID, jobDTO.mJobId);
        args.putString(EXTRA_JOB_NAME, jobDTO.mName);
        args.putString(EXTRA_JOB_IMAGE_URL, jobDTO.mBannerImageURL);
        args.putString(EXTRA_JOB_LOCATION, jobDTO.mLocation);
        args.putString(EXTRA_JOB_MEMBER_COUNT, jobDTO.mMemberCount);
        args.putString(EXTRA_JOB_MEMBER_SALARY, jobDTO.mSalary);
        args.putString(EXTRA_JOB_MEMBER_DESCRIPTION, jobDTO.mDescription);

        JobDetailFragment fragment = new JobDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected JobsDetailUiPresenter onCreatePresenter() {
        return new JobsDetailUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_detail_job;
    }

    @OnClick(R.id.done)
    protected void onApplyClicked() {
        showToast("Applying for job");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        String jobId = arguments.getString(EXTRA_JOB_ID);
        String jobName = arguments.getString(EXTRA_JOB_NAME);
        String jobImage = arguments.getString(EXTRA_JOB_IMAGE_URL);

        String jobLocation = arguments.getString(EXTRA_JOB_LOCATION);
        String jobMemberCount = arguments.getString(EXTRA_JOB_MEMBER_COUNT);
        String jobSalary = arguments.getString(EXTRA_JOB_MEMBER_SALARY);
        String jobDescription = arguments.getString(EXTRA_JOB_MEMBER_DESCRIPTION);

        setUpToolbar(jobName);
        mApplyButton.setText("Apply for this job");

        List<ListItemType> listItemTypes = new LinkedList<>();
        listItemTypes.add(new SimpleItem<String>("JOBS DESCRIPTION", 0));
        listItemTypes.add(new SimpleItem<JobDescriptionVO>(new JobDescriptionVO(jobLocation, jobMemberCount, jobSalary), 1));
        listItemTypes.add(new SimpleItem<String>(jobDescription, 2));

        mAdapter = new JobDetailListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(new OnListItemClickListener<ListItemType>() {
            @Override
            public void onItemClick(ListItemType model) {
                ProjectVO projectVO = (ProjectVO) model.getModel();
                addChildFragment(ProjectDetailFragment.newInstance(projectVO), "FRAG_PROJECT_DETAIL");
            }
        });
        mAdapter.setItems(listItemTypes);

        mJobDetailList.setHasFixedSize(true);
        mJobDetailList.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onHide() {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mApplyButton.getLayoutParams();
                int fabBottomMargin = lp.bottomMargin;
                mApplyButton.animate().translationY(mApplyButton.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
            }

            @Override
            public void onShow() {
                mApplyButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        });
        mJobDetailList.setAdapter(mAdapter);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(jobImage), mImageDetail);

        getPresenter().getProjects(jobId);
    }

    @Override
    public void onLoadRelatedProjects(List<ListItemType> listItemTypes) {
        mAdapter.appendItem(new SimpleItem<String>("RELATED JOBS", 0));
        mAdapter.appendItems(listItemTypes);
    }
}
