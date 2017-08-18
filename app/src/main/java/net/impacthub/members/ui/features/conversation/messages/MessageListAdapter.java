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

package net.impacthub.members.ui.features.conversation.messages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.features.messages.MessageItem;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.DateTimeAgoHelper;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class MessageListAdapter extends BaseListAdapter<RecyclerView.ViewHolder, MessageItem> {

    private static final int LAYOUTS_RES[] = {
            0,
            R.layout.message_standalone_from_me_item,
            R.layout.message_standalone_item,
            R.layout.message_start_from_me_item,
            R.layout.message_start_item,
            R.layout.message_middle_from_me_item,
            R.layout.message_middle_item,
            R.layout.message_end_from_me_item,
            R.layout.message_end_item
    };

    private final static int TYPE_UNDEFINED = 0;
    private final static int TYPE_1 = 1;
    private final static int TYPE_2 = 2;
    private final static int TYPE_3 = 3;
    private final static int TYPE_4 = 4;
    private final static int TYPE_5 = 5;
    private final static int TYPE_6 = 6;
    private final static int TYPE_7 = 7;
    private final static int TYPE_8 = 8;

    protected MessageListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public int getItemViewType(int position) {
        switch (getItem(position).getMessageType()) {
            case STANDALONE_FROM_ME:
                return TYPE_1;
            case STANDALONE:
                return TYPE_2;
            case START_FROM_ME:
                return TYPE_3;
            case START:
                return TYPE_4;
            case MIDDLE_FROM_ME:
                return TYPE_5;
            case MIDDLE:
                return TYPE_6;
            case END_FROM_ME:
                return TYPE_7;
            case END:
                return TYPE_8;
            default:
                return TYPE_UNDEFINED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resId = LAYOUTS_RES[viewType];
        return new MessageTextViewHolder(getLayoutInflater().inflate(resId, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        MessageItem item = getItem(position);
        ((MessageTextViewHolder) holder).bindViewsWith(item);
    }

    class MessageTextViewHolder extends RecyclerViewHolder<MessageItem> {

        ImageView senderimage;
        TextView sentDate;
        TextView message;

        MessageTextViewHolder(View itemView) {
            super(itemView);
            senderimage = (ImageView) itemView.findViewById(R.id.sender_image);
            sentDate = (TextView)itemView.findViewById(R.id.date_time);
            message = (TextView) itemView.findViewById(R.id.message);
        }

        @Override
        protected void bindViewsWith(MessageItem itemData) {
            message.setText(itemData.getMessage());
            if(sentDate != null) {
                new DateTimeAgoHelper(sentDate, itemData.getSendDate());
            }
            if(senderimage != null) {
                Context context = itemView.getContext();
                ImageLoaderHelper.loadImage(context, itemData.getSenderImageUrl(), senderimage);
            }
        }
    }

    class MessageDateViewHolder extends RecyclerViewHolder<MessageItem> {

        ImageView senderimage;
        TextView sentDate;
        TextView message;

        MessageDateViewHolder(View itemView) {
            super(itemView);
            senderimage = (ImageView) itemView.findViewById(R.id.sender_image);
            sentDate = (TextView)itemView.findViewById(R.id.date_time);
            message = (TextView) itemView.findViewById(R.id.message);
        }

        @Override
        protected void bindViewsWith(MessageItem itemData) {
            message.setText(itemData.getMessage());
            if(sentDate != null) {
                new DateTimeAgoHelper(sentDate, itemData.getSendDate());
            }
            if(senderimage != null) {
                Context context = itemView.getContext();
                ImageLoaderHelper.loadImage(context, itemData.getSenderImageUrl(), senderimage);
            }
        }
    }

    class MessageProfilePictureViewHolder extends RecyclerViewHolder<MessageItem> {

        ImageView senderimage;
        TextView sentDate;
        TextView message;

        MessageProfilePictureViewHolder(View itemView) {
            super(itemView);
            senderimage = (ImageView) itemView.findViewById(R.id.sender_image);
            sentDate = (TextView)itemView.findViewById(R.id.date_time);
            message = (TextView) itemView.findViewById(R.id.message);
        }

        @Override
        protected void bindViewsWith(MessageItem itemData) {
            message.setText(itemData.getMessage());
            if(sentDate != null) {
                new DateTimeAgoHelper(sentDate, itemData.getSendDate());
            }
            if(senderimage != null) {
                Context context = itemView.getContext();
                ImageLoaderHelper.loadImage(context, itemData.getSenderImageUrl(), senderimage);
            }
        }
    }
}
