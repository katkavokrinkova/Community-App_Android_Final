package net.impacthub.app.ui.features.home.members;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.members.MemberStatus;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;
import net.impacthub.app.ui.widgets.CircleImageView;

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
            Context context = memberImage.getContext();
            name.setText(item.mFullName);
            profession.setText(item.mProfession);
            locations.setText(item.mLocation);
            ImageLoaderHelper.loadImage(context, buildUrl(item.mProfilePicURL), memberImage);

            switch (item.mMemberStatus) {
                case MemberStatus.APPROVED:
                    ImageLoaderHelper.loadImage(context, R.mipmap.comment_bubble_small, iconMemberStatus);
                    break;
                case MemberStatus.OUTSTANDING:
                    ImageLoaderHelper.loadImage(context, R.mipmap.member_waiting, iconMemberStatus);
                    break;
                case MemberStatus.APPROVE_DECLINE_BY_ME:
                    ImageLoaderHelper.loadImage(context, R.mipmap.member_waiting, iconMemberStatus);
                    break;
                case MemberStatus.NOT_CONTACTED:
                    ImageLoaderHelper.loadImage(context, R.mipmap.comment_bubble_small, iconMemberStatus);
                    break;
                case MemberStatus.DECLINED:
                default:
                    ImageLoaderHelper.loadImage(context, 0, iconMemberStatus);
            }
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v.getId(), getItem(getAdapterPosition()));
            }
        }
    }
}
