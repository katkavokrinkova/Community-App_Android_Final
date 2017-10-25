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
import net.impacthub.app.model.vo.chatter.ChatComment;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/1/2017.
 */

public class ChatterCommentListAdapter extends BaseListAdapter<ChatterCommentListAdapter.CommentViewHolder, ChatComment> {

    protected ChatterCommentListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(getLayoutInflater().inflate(R.layout.item_layout_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class CommentViewHolder extends RecyclerViewHolder<ChatComment> implements View.OnClickListener {

        final ImageView memberImage;
        final TextView displayName;
        final TextView displayDate;
        final TextView displayComment;

        CommentViewHolder(View itemView) {
            super(itemView);
            memberImage = (ImageView) itemView.findViewById(R.id.member_image);
            displayName = (TextView) itemView.findViewById(R.id.name);
            displayDate = (TextView) itemView.findViewById(R.id.date);
            displayComment = (TextView) itemView.findViewById(R.id.comment);
            memberImage.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(ChatComment itemData) {
            Context context = memberImage.getContext();
            displayName.setText(itemData.mDisplayName);
            displayComment.setText(itemData.mCommentTxt);
            displayDate.setText(itemData.mDate);
//            likeCount.setText(String.valueOf(itemData.mLikeCount));
//            commentCount.setText(String.valueOf(itemData.mCommentCount));
//
//            likeIndicatorImage.setImageResource(itemData.mIsLikedByMe ? R.mipmap.like_red : R.mipmap.like_grey);
//
//            new DateTimeAgoHelper(displayDate, itemData.mDate);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), memberImage);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(view.getId(), getItem(position), position);
            }
        }
    }
}
