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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnChatterFeedItemClickListener;
import net.impacthub.members.model.vo.chatter.ChatterVO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.DateTimeAgoHelper;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class ChatterFeedListAdapter extends BaseListAdapter<ChatterFeedListAdapter.ChatterFeedViewHolder, ChatterVO> {

    private OnChatterFeedItemClickListener mFeedItemClickListener;

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

    public void setFeedItemClickListener(OnChatterFeedItemClickListener clickListener) {
        mFeedItemClickListener = clickListener;
    }

    class ChatterFeedViewHolder extends RecyclerViewHolder<ChatterVO> implements View.OnClickListener {

        final ImageView memberImage;
        final ImageView likeIndicatorImage;
        final ImageView commentIndicatorImage;
        final TextView displayName;
        final TextView displayDate;
        final TextView displayComment;
        final TextView likeCount;
        final TextView commentCount;

        ChatterFeedViewHolder(View itemView) {
            super(itemView);
            memberImage = (ImageView) itemView.findViewById(R.id.member_image);
            likeIndicatorImage = (ImageView) itemView.findViewById(R.id.like_indicator);
            commentIndicatorImage = (ImageView) itemView.findViewById(R.id.comment_indicator);
            displayName = (TextView) itemView.findViewById(R.id.name);
            displayDate = (TextView) itemView.findViewById(R.id.date);
            displayComment = (TextView) itemView.findViewById(R.id.comment);
            likeCount = (TextView) itemView.findViewById(R.id.like_count);
            commentCount = (TextView) itemView.findViewById(R.id.comment_count);
            memberImage.setOnClickListener(this);
            likeIndicatorImage.setOnClickListener(this);
            commentIndicatorImage.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(ChatterVO itemData) {
            Context context = memberImage.getContext();
            displayName.setText(itemData.mDisplayName);
            displayComment.setText(itemData.mComment);
            likeCount.setText(String.valueOf(itemData.mLikeCount));
            commentCount.setText(String.valueOf(itemData.mCommentCount));

            likeIndicatorImage.setImageResource(itemData.mIsLikedByMe ? R.mipmap.like_red : R.mipmap.like_grey);

            new DateTimeAgoHelper(displayDate, itemData.mDate);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), memberImage);
        }

        @Override
        public void onClick(View view) {
            if (mFeedItemClickListener != null) {
                Context context = view.getContext();
                        ChatterVO chatterDTO = getItem(getAdapterPosition());
                switch (view.getId()) {
                    case R.id.member_image:
                        mFeedItemClickListener.onProfileImageClicked(chatterDTO);
                        break;
                    case R.id.comment_indicator:
                        Toast.makeText(context, "Opening Comments", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.like_indicator:
                        Toast.makeText(context, "Liking post", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }
}
