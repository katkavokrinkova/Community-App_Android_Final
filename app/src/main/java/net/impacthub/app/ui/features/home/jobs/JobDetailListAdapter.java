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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.vo.jobs.JobDescriptionVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class JobDetailListAdapter extends BaseListAdapter<RecyclerView.ViewHolder, ListItemType> {

    protected JobDetailListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case 0:
                viewHolder = new TitleViewHolder(getLayoutInflater().inflate(R.layout.item_layout_info_title, parent, false));
                break;
            case 1:
                viewHolder = new TopDescriptionViewHolder(getLayoutInflater().inflate(R.layout.item_layout_job_top_description, parent, false));
                break;
            case 2:
                viewHolder = new DescriptionViewHolder(getLayoutInflater().inflate(R.layout.item_layout_job_text_description, parent, false));
                break;
            default:
                viewHolder = new JobProjectViewHolder(getLayoutInflater().inflate(R.layout.item_layout_job_project, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object model = getItem(position).getModel();
        switch (getItemViewType(position)) {
            case 0:
                String title = (String) model;
                ((TitleViewHolder) holder).bindViewsWith(title);
                break;
            case 1:
                JobDescriptionVO descriptionVO = (JobDescriptionVO) model;
                ((TopDescriptionViewHolder) holder).bindViewsWith(descriptionVO);
                break;
            case 2:
                String description = (String) model;
                ((DescriptionViewHolder) holder).bindViewsWith(description);
                break;
            case 3:
                ProjectVO projectVO = (ProjectVO) model;
                ((JobProjectViewHolder) holder).bindViewsWith(projectVO);
                break;
        }
    }

    private class TitleViewHolder extends RecyclerViewHolder<String> {

        final TextView title;

        TitleViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_info_title);
        }

        @Override
        protected void bindViewsWith(String itemData) {
            title.setText(itemData);
        }
    }

    private class TopDescriptionViewHolder extends RecyclerViewHolder<JobDescriptionVO> {

        final TextView location;
        final TextView memberCount;
        final TextView salary;

        TopDescriptionViewHolder(View itemView) {
            super(itemView);
            location = (TextView) itemView.findViewById(R.id.locations);
            memberCount = (TextView) itemView.findViewById(R.id.member_count);
            salary = (TextView) itemView.findViewById(R.id.salary);
        }

        @Override
        protected void bindViewsWith(JobDescriptionVO itemData) {
            location.setText(itemData.mJobLocation);
            memberCount.setText(itemData.mJobMemberCount);
            salary.setText(itemData.mJobSalary);
        }
    }

    private class DescriptionViewHolder extends RecyclerViewHolder<String> {

        final TextView description;

        DescriptionViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.text_event_description);
        }

        @Override
        protected void bindViewsWith(String itemData) {
            description.setText(itemData);
        }
    }

    private class JobProjectViewHolder extends RecyclerViewHolder<ProjectVO> implements View.OnClickListener {

        final ImageView projectImage;
        final TextView projectName;
        final TextView projectCompany;
//        final View projectLink;

        JobProjectViewHolder(View itemView) {
            super(itemView);
            projectImage = (ImageView) itemView.findViewById(R.id.image_project_logo);
            projectName = (TextView) itemView.findViewById(R.id.text_project_name);
            projectCompany = (TextView) itemView.findViewById(R.id.text_project_company);
//            projectLink = itemView.findViewById(R.id.container_project_link);
//            projectLink.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(ProjectVO itemData) {
            Context context = projectImage.getContext();
            projectName.setText(itemData.mName);
            projectCompany.setText(itemData.mOrganizationName);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), projectImage);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view.getId(), getItem(getAdapterPosition()));
            }
        }
    }
}
