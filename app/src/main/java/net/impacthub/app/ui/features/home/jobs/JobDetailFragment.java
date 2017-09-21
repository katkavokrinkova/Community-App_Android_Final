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

package net.impacthub.app.ui.features.home.jobs;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.SimpleItem;
import net.impacthub.app.model.vo.jobs.JobDescriptionVO;
import net.impacthub.app.model.vo.jobs.JobVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.navigator.Navigator;
import net.impacthub.app.presenter.features.jobs.JobsDetailUiContract;
import net.impacthub.app.presenter.features.jobs.JobsDetailUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.features.home.projects.ProjectDetailFragment;

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
    public static final String EXTRA_JOB_WEBSITE = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_WEBSITE";
    public static final String EXTRA_JOB_ACCOUNT_ID = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_ACCOUNT_ID";
    public static final String EXTRA_JOB_COMPANY_C = "net.impacthub.members.ui.features.home.jobs.EXTRA_JOB_COMPANY_C";

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
        args.putString(EXTRA_JOB_WEBSITE, jobDTO.mWebsite);
        args.putString(EXTRA_JOB_ACCOUNT_ID, jobDTO.mAccountId);
        args.putString(EXTRA_JOB_COMPANY_C, jobDTO.mCompanyC);

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
        Navigator.startOtherWebActivity(getContext(), getArguments().getString(EXTRA_JOB_WEBSITE));
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        Bundle arguments = getArguments();

        String jobId = arguments.getString(EXTRA_JOB_ID);
        String jobName = arguments.getString(EXTRA_JOB_NAME);
        String jobImage = arguments.getString(EXTRA_JOB_IMAGE_URL);

        String jobLocation = arguments.getString(EXTRA_JOB_LOCATION);
        String jobAccountId = arguments.getString(EXTRA_JOB_ACCOUNT_ID);
        String jobCompanyC = arguments.getString(EXTRA_JOB_COMPANY_C);
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
            public void onItemClick(int viewId, ListItemType model) {
                ProjectVO projectVO = (ProjectVO) model.getModel();
                addChildFragment(ProjectDetailFragment.newInstance(projectVO), "FRAG_PROJECT_DETAIL");
            }
        });
        mAdapter.setItems(listItemTypes);

        mJobDetailList.setHasFixedSize(true);
        mJobDetailList.setAdapter(mAdapter);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(jobImage), mImageDetail);

        getPresenter().getProjects(jobId, jobLocation, jobAccountId, jobCompanyC);
    }

    @Override
    public void onLoadRelatedProjects(List<ListItemType> listItemTypes) {
        mAdapter.appendItems(listItemTypes);
    }
}
