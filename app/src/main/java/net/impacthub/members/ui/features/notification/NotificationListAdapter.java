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

package net.impacthub.members.ui.features.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.notifications.NotificationVO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.DateTimeAgoHelper;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;
import net.impacthub.members.ui.widgets.CircleImageView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

class NotificationListAdapter extends BaseListAdapter<NotificationListAdapter.ViewHolder, NotificationVO> {

    NotificationListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = getLayoutInflater().inflate(R.layout.item_layout_notification, parent, false);
        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class ViewHolder extends RecyclerViewHolder<NotificationVO> implements View.OnClickListener {

        final CircleImageView profileImage;
        final TextView notificationMessage;
        final TextView elapsedTime;
        final ImageView notificationIcon;

        ViewHolder(View itemView) {
            super(itemView);
            profileImage = (CircleImageView) itemView.findViewById(R.id.image_profile_picture);
            notificationMessage = (TextView) itemView.findViewById(R.id.text_notification_message);
            elapsedTime = (TextView) itemView.findViewById(R.id.text_elapsed_time);
            notificationIcon = (ImageView) itemView.findViewById(R.id.image_notification_type);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(NotificationVO item) {
            Context context = profileImage.getContext();
            notificationMessage.setText(item.mMessage);
            new DateTimeAgoHelper(elapsedTime, item.mCreatedDate);
            ImageLoaderHelper.loadImage(context, item.mNotificationIcon, notificationIcon);
            ImageLoaderHelper.loadImage(context, buildUrl(item.mProfilePicUrl), profileImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
