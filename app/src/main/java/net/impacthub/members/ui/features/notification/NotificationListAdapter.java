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

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.notifications.NotificationDTO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.DateTimeAgoHelper;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;
import net.impacthub.members.ui.widgets.CircleImageView;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

class NotificationListAdapter extends BaseListAdapter<NotificationListAdapter.ViewHolder, NotificationDTO> {

    private final UserAccount mUserAccount = userAccountProvider();
    private final LayoutInflater mLayoutInflater;
    private OnListItemClickListener<NotificationDTO> mItemClickListener;

    NotificationListAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = mLayoutInflater.inflate(R.layout.item_notification_layout, parent, false);
        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    void setItemClickListener(@NonNull OnListItemClickListener<NotificationDTO> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerViewHolder<NotificationDTO> implements View.OnClickListener {

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
        protected void bindViewsWith(NotificationDTO item) {
            notificationMessage.setText(item.mMessage);
            new DateTimeAgoHelper(elapsedTime, item.mCreatedDate);
            ImageLoaderHelper.loadImage(profileImage.getContext(), item.mProfilePicUrl + "?oauth_token=" + mUserAccount.getAuthToken(), profileImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
