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

package net.impacthub.members.ui.features.messages.contacts.binders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnActiveContactActionClickListener;
import net.impacthub.members.model.vo.contacts.ContactVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;
import net.impacthub.members.ui.widgets.CircleImageView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ActiveContactsListAdapter extends BaseListAdapter<ActiveContactsListAdapter.ActiveContactViewHolder, ContactVO> {

    private final OnActiveContactActionClickListener mItemActionListener;

    protected ActiveContactsListAdapter(LayoutInflater inflater, OnActiveContactActionClickListener listener) {
        super(inflater);
        mItemActionListener = listener;
    }

    @Override
    public ActiveContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActiveContactViewHolder(getLayoutInflater().inflate(R.layout.item_layout_member_active, parent, false));
    }

    @Override
    public void onBindViewHolder(ActiveContactViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class ActiveContactViewHolder extends RecyclerViewHolder<ContactVO> implements View.OnClickListener {

        final View container;
        final CircleImageView memberImage;
        final TextView name;
        final TextView profession;
        final TextView locations;

        final TextView declineButton;
        final ImageView messageContact;

        ActiveContactViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            memberImage = (CircleImageView) container.findViewById(R.id.member_image);
            name = (TextView) container.findViewById(R.id.name);
            profession = (TextView) container.findViewById(R.id.profession);
            locations = (TextView) container.findViewById(R.id.locations);
            declineButton = (TextView) container.findViewById(R.id.button_decline_contact);
            messageContact = (ImageView) container.findViewById(R.id.button_message_contact);
            declineButton.setOnClickListener(this);
            messageContact.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(ContactVO itemData) {
            MemberVO item = itemData.mMember;
            if (item != null) {
                name.setText(item.mFullName);
                profession.setText(item.mProfession);
                locations.setText(item.mLocation);
                ImageLoaderHelper.loadImage(memberImage.getContext(), buildUrl(item.mProfilePicURL), memberImage);
            }
        }

        @Override
        public void onClick(View view) {
            ContactVO contactVO = getItem(getAdapterPosition());
            switch (view.getId()) {
                case R.id.button_message_contact:
                    if (mItemActionListener != null) {
                        mItemActionListener.onOpenConversation();
                    }
                    break;
                case R.id.button_decline_contact:
                    if (mItemActionListener != null) {
                        mItemActionListener.onDeclineContact();
                    }
                    break;
                default:
                    if (mItemActionListener != null) {
                        mItemActionListener.onItemClick(contactVO);
                    }
            }
        }
    }
}
