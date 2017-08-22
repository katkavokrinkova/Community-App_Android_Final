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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.jobs.JobVO;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.presenter.features.jobs.JobsDetailUiContract;
import net.impacthub.members.presenter.features.jobs.JobsDetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.ImageLoaderHelper;

import java.util.List;

import butterknife.BindView;

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
    @BindView(R.id.text_info_title) protected TextView mDetailTitle;
    @BindView(R.id.locations) protected TextView mLocation;
    @BindView(R.id.member_count) protected TextView mMemberCount;
    @BindView(R.id.salary) protected TextView mSalaryTxt;
    @BindView(R.id.text_description) protected TextView mDescription;

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
        return R.layout.fragment_job_detail;
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

        mDetailTitle.setText("JOB DESCRIPTION");
        mLocation.setText(jobLocation);
        mMemberCount.setText(jobMemberCount);
        mSalaryTxt.setText(jobSalary);
        mDescription.setText(jobDescription);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(jobImage), mImageDetail);

        getPresenter().getProjects(jobId);
    }

    @Override
    public void onLoadRelatedProjects(List<ProjectVO> projectVOs) {
        showToast("Size " + projectVOs.size());
    }
}
