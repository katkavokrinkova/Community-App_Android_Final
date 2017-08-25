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

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnContactPendingRequestActionClickListener;
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

public class PendingContactsListAdapter extends BaseListAdapter<PendingContactsListAdapter.PendingContactViewHolder, ContactVO> {

    private final OnContactPendingRequestActionClickListener mItemActionListener;

    protected PendingContactsListAdapter(LayoutInflater inflater, OnContactPendingRequestActionClickListener listener) {
        super(inflater);
        mItemActionListener = listener;
    }

    @Override
    public PendingContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PendingContactViewHolder(getLayoutInflater().inflate(R.layout.item_layout_member_pending, parent, false));
    }

    @Override
    public void onBindViewHolder(PendingContactViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class PendingContactViewHolder extends RecyclerViewHolder<ContactVO> implements View.OnClickListener {

        final View container;
        final CircleImageView memberImage;
        final TextView name;
        final TextView profession;
        final TextView locations;
        final TextView welcomeMessage;

        final TextView buttonViewMore;
        final ImageView buttonAcceptContact;
        final ImageView buttonRejectContact;

        PendingContactViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            memberImage = (CircleImageView) container.findViewById(R.id.member_image);
            name = (TextView) container.findViewById(R.id.name);
            profession = (TextView) container.findViewById(R.id.profession);
            locations = (TextView) container.findViewById(R.id.locations);
            welcomeMessage = (TextView) container.findViewById(R.id.text_welcome_message);

            buttonViewMore = (TextView) container.findViewById(R.id.button_view_more);
            buttonAcceptContact = (ImageView) container.findViewById(R.id.button_accept_contact);
            buttonRejectContact = (ImageView) container.findViewById(R.id.button_reject_contact);
            buttonViewMore.setOnClickListener(this);
            buttonAcceptContact.setOnClickListener(this);
            buttonRejectContact.setOnClickListener(this);
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
            welcomeMessage.setText(itemData.mIntroMessage);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            ContactVO contactVO = getItem(getAdapterPosition());
            switch (view.getId()) {
                case R.id.button_accept_contact:
                    if (contactVO != null && mItemActionListener != null) {
                        MemberVO member = contactVO.mMember;
                        if (member != null) {
                            mItemActionListener.onUpdateContactRequest(contactVO.mDM_Id, member.mUserId, "Approved");
                        }
                    }
                    break;
                case R.id.button_reject_contact:
                    if (contactVO != null && mItemActionListener != null) {
                        MemberVO member = contactVO.mMember;
                        if (member != null) {
                            mItemActionListener.onUpdateContactRequest(contactVO.mDM_Id, member.mUserId, "Declined");
                        }
                    }
                    break;
                case R.id.button_view_more:
                default:
                    if (mItemActionListener != null) {
                        mItemActionListener.onViewMoreContactRequest(contactVO);
                    }
                    break;
            }
        }
    }
}
