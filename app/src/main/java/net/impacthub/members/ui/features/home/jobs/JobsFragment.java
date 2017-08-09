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
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.jobs.JobDTO;
import net.impacthub.members.presenter.features.jobs.JobsPresenter;
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

public class JobsFragment extends BaseChildFragment<JobsPresenter> implements OnListItemClickListener<JobDTO>,JobsUiContract {

    @BindView(R.id.list_items) protected RecyclerView mJobsList;

    private JobsListAdapter mAdapter;

    public static JobsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        JobsFragment fragment = new JobsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected JobsPresenter onCreatePresenter() {
        return new JobsPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.jobs);
        mJobsList.setHasFixedSize(true);
        mAdapter = new JobsListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(this);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_medium_gap);
        mJobsList.addItemDecoration(new LinearItemsMarginDecorator(offset, offset, 0, 0));
        mJobsList.setAdapter(mAdapter);

        getPresenter().getJobs();
    }

    @Override
    public void onItemClick(JobDTO model) {
        showToast("Hello");
    }

    @Override
    public void onLoadJobs(List<JobDTO> jobs) {
        mAdapter.setItems(jobs);
    }
}
