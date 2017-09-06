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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.vo.jobs.JobVO;
import net.impacthub.members.presenter.features.jobs.JobsUiPresenter;
import net.impacthub.members.presenter.features.jobs.JobsUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class JobsFragment extends BaseChildFragment<JobsUiPresenter> implements OnListItemClickListener<JobVO>,JobsUiContract {

    @BindView(R.id.list_items) protected RecyclerView mJobsList;

    private JobsListAdapter mAdapter;

    public static JobsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        JobsFragment fragment = new JobsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected JobsUiPresenter onCreatePresenter() {
        return new JobsUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.jobs);
        mJobsList.setHasFixedSize(true);
        mAdapter = new JobsListAdapter(LayoutInflater.from(getContext()));
        mAdapter.setItemClickListener(this);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mJobsList.addItemDecoration(new LinearItemsMarginDecorator(offset, offset, 0 ,offset));
        mJobsList.setAdapter(mAdapter);

        getPresenter().getJobs();
    }

    @Override
    public void onItemClick(int viewId, JobVO model) {
        addChildFragment(JobDetailFragment.newInstance(model), "FRAG_JOB_DETAIL");
    }

    @Override
    public void onLoadJobs(List<JobVO> jobs) {
        mAdapter.setItems(jobs);
    }
}
