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

package net.impacthub.app.ui.features.home.chatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.DateTimeAgoHelper;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class ChatterFeedListAdapter extends BaseListAdapter<ChatterFeedListAdapter.ChatterFeedViewHolder, ChatterVO> {

    public ChatterFeedListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public ChatterFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatterFeedViewHolder(getLayoutInflater().inflate(R.layout.item_layout_chatterfeed, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatterFeedViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class ChatterFeedViewHolder extends RecyclerViewHolder<ChatterVO> implements View.OnClickListener {

        final ImageView memberImage;
        final ImageView likeIndicatorImage;
        final ImageView commentIndicatorImage;
        final TextView displayName;
        final TextView displayDate;
        final TextView displayComment;
        final View likeBar;
        final View commentBar;
        final TextView likeCount;
        final TextView commentCount;

        ChatterFeedViewHolder(View itemView) {
            super(itemView);
            memberImage = (ImageView) itemView.findViewById(R.id.member_image);
            likeBar = itemView.findViewById(R.id.like_bar);
            commentBar = itemView.findViewById(R.id.comment_bar);
            likeIndicatorImage = (ImageView) itemView.findViewById(R.id.like_indicator);
            commentIndicatorImage = (ImageView) itemView.findViewById(R.id.comment_indicator);
            displayName = (TextView) itemView.findViewById(R.id.name);
            displayDate = (TextView) itemView.findViewById(R.id.date);
            displayComment = (TextView) itemView.findViewById(R.id.comment);
            likeCount = (TextView) itemView.findViewById(R.id.like_count);
            commentCount = (TextView) itemView.findViewById(R.id.comment_count);

            memberImage.setOnClickListener(this);
            commentBar.setOnClickListener(this);
            likeBar.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(ChatterVO itemData) {
            Context context = memberImage.getContext();
            displayName.setText(itemData.mDisplayName);
            displayComment.setText(itemData.mComment);
            likeCount.setText(String.valueOf(itemData.mLikeCount));
            commentCount.setText(String.valueOf(itemData.mComments.getComments().size()));

            likeIndicatorImage.setImageResource(itemData.mIsLikedByMe ? R.mipmap.like_red : R.mipmap.like_grey);

            new DateTimeAgoHelper(displayDate, itemData.mDate);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), memberImage);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view.getId(), getItem(getAdapterPosition()));
            }
        }
    }
}
