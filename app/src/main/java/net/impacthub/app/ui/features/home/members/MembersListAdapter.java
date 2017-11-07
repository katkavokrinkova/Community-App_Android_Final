package net.impacthub.app.ui.features.home.members;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MembersListAdapter extends BaseListAdapter<MembersListAdapter.MemberViewHolder, MemberVO> {

    public MembersListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberViewHolder(getLayoutInflater().inflate(R.layout.item_layout_member, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class MemberViewHolder extends RecyclerViewHolder<MemberVO> implements View.OnClickListener {

        final ImageView memberImage;
        final TextView name;
        final TextView profession;
        final TextView locations;
        final ImageView iconMemberStatus;

        MemberViewHolder(View itemView) {
            super(itemView);
            memberImage = (ImageView) itemView.findViewById(R.id.member_image);
            name = (TextView) itemView.findViewById(R.id.name);
            profession = (TextView) itemView.findViewById(R.id.profession);
            locations = (TextView) itemView.findViewById(R.id.locations);
            iconMemberStatus = (ImageView) itemView.findViewById(R.id.image_member_status);
            iconMemberStatus.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(MemberVO item) {
            MemberBinderDelegate.bind(memberImage.getContext(), item, name, profession, locations, memberImage, iconMemberStatus);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v.getId(), getItem(position), position);
            }
        }
    }
}
