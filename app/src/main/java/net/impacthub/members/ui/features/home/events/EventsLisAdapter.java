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

package net.impacthub.members.ui.features.home.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.events.EventDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

public class EventsLisAdapter extends BaseListAdapter<EventsLisAdapter.EventViewHolder, EventDTO> {

    public EventsLisAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(getLayoutInflater().inflate(R.layout.item_layout_event, parent, false));
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class EventViewHolder extends RecyclerViewHolder<EventDTO> implements View.OnClickListener {

        final ImageView eventImage;
//        final TextView projectTitle;
//        final TextView projectOrganizationName;
//        final TextView location;
//        final TextView memberCount;

        EventViewHolder(View itemView) {
            super(itemView);
            eventImage = (ImageView) itemView.findViewById(R.id.image_banner);
//            projectTitle = (TextView) itemView.findViewById(R.id.text_project_title);
//            projectOrganizationName = (TextView) itemView.findViewById(R.id.text_project_organization_name);
//            location = (TextView) itemView.findViewById(R.id.locations);
//            memberCount = (TextView) itemView.findViewById(R.id.member_count);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(EventDTO itemData) {
            Context context = eventImage.getContext();
//            projectTitle.setText(itemData.mName);
//            projectOrganizationName.setText(itemData.mOrganizationName);
//            location.setText(itemData.mLocation);
//            memberCount.setText(itemData.mMemberCount);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), eventImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
