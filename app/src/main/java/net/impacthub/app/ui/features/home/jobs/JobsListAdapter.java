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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.jobs.JobVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class JobsListAdapter extends BaseListAdapter<JobsListAdapter.JobViewHolder, JobVO> {

    public JobsListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JobViewHolder(getLayoutInflater().inflate(R.layout.item_layout_jobs, parent, false));
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class JobViewHolder extends RecyclerViewHolder<JobVO> implements View.OnClickListener {

        final TextView jobTitle;
        final TextView jobCompany;
        final TextView location;
        final TextView jobType;
        final ImageView jobLogoImage;

        JobViewHolder(View itemView) {
            super(itemView);
            jobTitle = (TextView) itemView.findViewById(R.id.text_job_name);
            jobCompany = (TextView) itemView.findViewById(R.id.text_job_company);
            location = (TextView) itemView.findViewById(R.id.locations);
            jobType = (TextView) itemView.findViewById(R.id.text_job_type);
            jobLogoImage = (ImageView) itemView.findViewById(R.id.image_job_logo);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(JobVO itemData) {
            Context context = jobLogoImage.getContext();
            jobTitle.setText(itemData.mName);
            jobCompany.setText(itemData.mCompanyName);
            location.setText(itemData.mLocation);
            jobType.setText(itemData.mJobType);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mLogoURL), jobLogoImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v.getId(), getItem(getAdapterPosition()));
            }
        }
    }
}
