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

package net.impacthub.members.ui.features.home.groups;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.ui.base.BaseListMapAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

class GroupsListAdapter extends BaseListMapAdapter<RecyclerView.ViewHolder, GroupDTO> {

    GroupsListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = getLayoutInflater().inflate(R.layout.item_groups_layout, parent, false);
        return new GroupViewHolder(container);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GroupViewHolder) holder).bindViewsWith(getItem(position));
    }

    class GroupViewHolder extends ViewHolder {

        final ImageView groupImage;
        final TextView groupName;
        final TextView location;
        final TextView memberCount;

        GroupViewHolder(View itemView) {
            super(itemView);
            groupImage = (ImageView) itemView.findViewById(R.id.image_group);
            groupName = (TextView) itemView.findViewById(R.id.text_group_name);
            location = (TextView) itemView.findViewById(R.id.locations);
            memberCount = (TextView) itemView.findViewById(R.id.member_count);
        }

        @Override
        protected void bindViewsWith(GroupDTO itemData) {
            Context context = groupImage.getContext();
            groupName.setText(itemData.mName);
            location.setText(itemData.mCities);
            memberCount.setText(itemData.mMemberCount);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), groupImage);
        }
    }
}
