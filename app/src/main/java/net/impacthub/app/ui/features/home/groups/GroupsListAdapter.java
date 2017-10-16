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

package net.impacthub.app.ui.features.home.groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class GroupsListAdapter extends BaseListAdapter<GroupsListAdapter.GroupViewHolder, GroupVO> {

    public GroupsListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = getLayoutInflater().inflate(R.layout.item_layout_groups, parent, false);
        return new GroupViewHolder(container);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
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
                mItemClickListener.onItemClick(v.getId(), getItem(getAdapterPosition()));
            }
        }
    }
}
