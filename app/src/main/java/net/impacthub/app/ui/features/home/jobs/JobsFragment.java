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

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.jobs.JobVO;
import net.impacthub.app.presenter.features.jobs.JobsUiPresenter;
import net.impacthub.app.presenter.features.jobs.JobsUiContract;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;

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
        return R.layout.fragment_list_with_fixed_searchbar;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.jobs);
        mJobsList.setHasFixedSize(true);
        Context context = getContext();
        mAdapter = new JobsListAdapter(LayoutInflater.from(context));
        mAdapter.setItemClickListener(this);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mJobsList.setLayoutManager(new LinearLayoutManager(context));
        mJobsList.addItemDecoration(new LinearItemsMarginDecorator(offset));
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
