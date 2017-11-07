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

package net.impacthub.app.ui.features.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.model.vo.events.EventVO;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;
import net.impacthub.app.ui.features.home.members.MemberBinderDelegate;
import net.impacthub.app.ui.widgets.CircleImageView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/6/2017.
 */

class SearchResultListAdapter extends BaseListAdapter<RecyclerView.ViewHolder, ListItemType> {

    SearchResultListAdapter(LayoutInflater inflater) {
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
                viewHolder = new MemberViewHolder(getLayoutInflater().inflate(R.layout.item_layout_member, parent, false));
                break;
            case 1:
                viewHolder = new GroupViewHolder(getLayoutInflater().inflate(R.layout.item_layout_groups, parent, false));
                break;
            case 2:
                viewHolder = new ProjectViewHolder(getLayoutInflater().inflate(R.layout.item_layout_project, parent, false));
                break;
            case 3:
                viewHolder = new CompanyViewHolder(getLayoutInflater().inflate(R.layout.item_layout_company, parent, false));
                break;
            default:
                viewHolder = new EventViewHolder(getLayoutInflater().inflate(R.layout.item_layout_event, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemType item = getItem(position);
        switch (item.getItemType()) {
            case 0:
                ((MemberViewHolder) holder).bindViewsWith((MemberVO) item.getModel());
                break;
            case 1:
                ((GroupViewHolder) holder).bindViewsWith((GroupVO) item.getModel());
                break;
            case 2:
                ((ProjectViewHolder) holder).bindViewsWith((ProjectVO) item.getModel());
                break;
            case 3:
                ((CompanyViewHolder) holder).bindViewsWith((CompanyVO) item.getModel());
                break;
            case 4:
                ((EventViewHolder) holder).bindViewsWith((EventVO) item.getModel());
                break;
        }
    }

    private class EventViewHolder extends RecyclerViewHolder<EventVO> implements View.OnClickListener {

        final ImageView eventImage;
        final TextView eventTitle;
        final TextView eventDate;
        final TextView eventCity;
        final TextView eventTime;

        EventViewHolder(View itemView) {
            super(itemView);
            eventImage = (ImageView) itemView.findViewById(R.id.image_banner);
            eventTitle = (TextView) itemView.findViewById(R.id.text_event_title);
            eventDate = (TextView) itemView.findViewById(R.id.text_event_date);
            eventCity = (TextView) itemView.findViewById(R.id.text_event_city);
            eventTime = (TextView) itemView.findViewById(R.id.text_event_time);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(EventVO itemData) {
            Context context = eventImage.getContext();
            eventTitle.setText(itemData.mName);
            eventDate.setText(itemData.mDate);
            eventCity.setText(itemData.mCity);
            eventTime.setText(itemData.mTime);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), eventImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v.getId(), getItem(position), position);
            }
        }
    }

    private class ProjectViewHolder extends RecyclerViewHolder<ProjectVO> implements View.OnClickListener {

        final ImageView projectImage;
        final TextView projectTitle;
        final TextView projectOrganizationName;
        final TextView location;
        final TextView memberCount;

        ProjectViewHolder(View itemView) {
            super(itemView);
            projectImage = (ImageView) itemView.findViewById(R.id.image_banner);
            projectTitle = (TextView) itemView.findViewById(R.id.text_project_title);
            projectOrganizationName = (TextView) itemView.findViewById(R.id.text_project_organization_name);
            location = (TextView) itemView.findViewById(R.id.locations);
            memberCount = (TextView) itemView.findViewById(R.id.member_count);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(ProjectVO itemData) {
            Context context = projectImage.getContext();
            projectTitle.setText(itemData.mName);
            projectOrganizationName.setText(itemData.mOrganizationName);
            location.setText(itemData.mLocation);
            memberCount.setText(String.valueOf(itemData.mMemberCount));
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), projectImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v.getId(), getItem(position), position);
            }
        }
    }

    private class MemberViewHolder extends RecyclerViewHolder<MemberVO> implements View.OnClickListener {

        final CircleImageView memberImage;
        final TextView name;
        final TextView profession;
        final TextView locations;
        final ImageView iconMemberStatus;

        MemberViewHolder(View itemView) {
            super(itemView);
            memberImage = (CircleImageView) itemView.findViewById(R.id.member_image);
            name = (TextView) itemView.findViewById(R.id.name);
            profession = (TextView) itemView.findViewById(R.id.profession);
            locations = (TextView) itemView.findViewById(R.id.locations);
            iconMemberStatus = (ImageView) itemView.findViewById(R.id.image_member_status);
            iconMemberStatus.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(MemberVO item) {
            MemberBinderDelegate.bind(memberImage.getContext(), item, name, profession, locations, memberImage, iconMemberStatus);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v.getId(), getItem(position), position);
            }
        }
    }

    class GroupViewHolder extends RecyclerViewHolder<GroupVO> implements View.OnClickListener {

        final ImageView groupImage;
        final TextView groupName;
        final TextView location;
        final TextView memberCount;

        GroupViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            groupImage = (ImageView) itemView.findViewById(R.id.image_group);
            groupName = (TextView) itemView.findViewById(R.id.text_group_name);
            location = (TextView) itemView.findViewById(R.id.locations);
            memberCount = (TextView) itemView.findViewById(R.id.member_count);
        }

        @Override
        protected void bindViewsWith(GroupVO itemData) {
            Context context = groupImage.getContext();
            groupName.setText(itemData.mName);
            location.setText(itemData.mCities);
            memberCount.setText(String.valueOf(itemData.mMemberCount));
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), groupImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v.getId(), getItem(position), position);
            }
        }
    }

    private class CompanyViewHolder extends RecyclerViewHolder<CompanyVO> implements View.OnClickListener {

        final ImageView companyBannerLogo;
        final ImageView companyBannerImage;
        final TextView companyName;
        final TextView companySector;
        final TextView  memberCount;
        final TextView  locations;

        CompanyViewHolder(View itemView) {
            super(itemView);
            companyBannerLogo = (ImageView) itemView.findViewById(R.id.image_company_logo);
            companyBannerImage = (ImageView) itemView.findViewById(R.id.image_company_banner);
            companyName = (TextView) itemView.findViewById(R.id.text_company_name);
            companySector = (TextView) itemView.findViewById(R.id.text_company_sector);
            memberCount = (TextView) itemView.findViewById(R.id.member_count);
            locations = (TextView) itemView.findViewById(R.id.locations);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(CompanyVO item) {
            Context context = companyBannerImage.getContext();
            companyName.setText(item.mCompanyName);
            companySector.setText(item.mCompanySector);
            memberCount.setText(item.mCompanyMemberCount);
            locations.setText(item.mCompanyLocation);
            ImageLoaderHelper.loadImage(context, buildUrl(item.mCompanyLogo), companyBannerLogo);
            ImageLoaderHelper.loadImage(context, buildUrl(item.mCompanyBanner), companyBannerImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v.getId(), getItem(position), position);
            }
        }
    }
}
