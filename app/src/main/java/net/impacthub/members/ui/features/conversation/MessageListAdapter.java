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

package net.impacthub.members.ui.features.conversation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.conversations.ConversationDTO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.DateTimeAgoHelper;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

class MessageListAdapter extends BaseListAdapter<MessageListAdapter.MessageViewHolder, ConversationDTO> {

    MessageListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(getLayoutInflater().inflate(R.layout.item_layout_message, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class MessageViewHolder extends RecyclerViewHolder<ConversationDTO> implements View.OnClickListener {

        final ImageView profileImage;
        final TextView messageReadIcon;
        final TextView displayName;
        final TextView displayMessage;
        final TextView displayDate;

        MessageViewHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.image_profile_picture);
            messageReadIcon = (TextView) itemView.findViewById(R.id.image_message_read);
            displayName = (TextView) itemView.findViewById(R.id.text_sender);
            displayMessage = (TextView) itemView.findViewById(R.id.text_message);
            displayDate = (TextView) itemView.findViewById(R.id.text_date);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(ConversationDTO itemData) {
            Context context = profileImage.getContext();
            displayName.setText(itemData.mDisplayName);
            displayMessage.setText(itemData.mText);
            new DateTimeAgoHelper(displayDate, itemData.mDate);
            messageReadIcon.setVisibility(itemData.mIsRead ? View.GONE : View.VISIBLE);
            ImageLoaderHelper.loadImage(context, buildUrl(itemData.mImageURL), profileImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
