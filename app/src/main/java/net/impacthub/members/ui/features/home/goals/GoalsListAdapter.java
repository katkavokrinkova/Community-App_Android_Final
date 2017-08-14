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

package net.impacthub.members.ui.features.home.goals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.goals.GoalDTO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

class GoalsListAdapter extends BaseListAdapter<GoalsListAdapter.GoalsHolder, GoalDTO> {

    GoalsListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public GoalsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GoalsHolder(getLayoutInflater().inflate(R.layout.item_layout_goal, parent, false));
    }

    @Override
    public void onBindViewHolder(GoalsHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class GoalsHolder extends RecyclerViewHolder<GoalDTO> implements View.OnClickListener {

        final ImageView goalImage;
        final TextView goalTitle;
        final TextView goalSummary;

        GoalsHolder(View itemView) {
            super(itemView);
            goalImage = (ImageView) itemView.findViewById(R.id.image_banner);
            goalTitle = (TextView) itemView.findViewById(R.id.text_goal_title);
            goalSummary = (TextView) itemView.findViewById(R.id.text_goal_summary);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(GoalDTO itemData) {
            Context context = goalImage.getContext();
            goalTitle.setText(itemData.mName);
            goalSummary.setText(itemData.mSummary);
//            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), goalImage);
//            ImageLoaderHelper.loadImage(context, "http://cdn.wallpapersafari.com/13/28/EcwOC1.jpg", goalImage);
            ImageLoaderHelper.loadImage(context, "https://www.pixelstalk.net/wp-content/uploads/2016/06/Download-Desktop-Book-HD-Backgrounds.jpg", goalImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
