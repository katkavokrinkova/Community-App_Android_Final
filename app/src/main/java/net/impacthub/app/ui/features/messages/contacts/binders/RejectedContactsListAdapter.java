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

package net.impacthub.app.ui.features.messages.contacts.binders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.contacts.ContactVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;
import net.impacthub.app.ui.widgets.CircleImageView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class RejectedContactsListAdapter extends BaseListAdapter<RejectedContactsListAdapter.RejectedMemberViewHolder, ContactVO> {

    public RejectedContactsListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public RejectedMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RejectedMemberViewHolder(getLayoutInflater().inflate(R.layout.item_layout_member_rejected, parent, false));
    }

    @Override
    public void onBindViewHolder(RejectedMemberViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class RejectedMemberViewHolder extends RecyclerViewHolder<ContactVO> implements View.OnClickListener {

        final View container;
        final CircleImageView memberImage;
        final TextView name;
        final TextView profession;
        final TextView locations;

        final ImageView buttonAcceptContact;

        RejectedMemberViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            memberImage = (CircleImageView) container.findViewById(R.id.member_image);
            name = (TextView) container.findViewById(R.id.name);
            profession = (TextView) container.findViewById(R.id.profession);
            locations = (TextView) container.findViewById(R.id.locations);
            buttonAcceptContact = (ImageView) container.findViewById(R.id.button_accept_contact);
            buttonAcceptContact.setOnClickListener(this);
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
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view.getId(), getItem(getAdapterPosition()));
            }
        }
    }
}
