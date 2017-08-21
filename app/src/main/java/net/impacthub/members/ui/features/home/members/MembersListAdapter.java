package net.impacthub.members.ui.features.home.members;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;
import net.impacthub.members.ui.widgets.CircleImageView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MembersListAdapter extends BaseListAdapter<MembersListAdapter.ViewHolder, MemberVO> {

    public MembersListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = getLayoutInflater().inflate(R.layout.item_layout_member, parent, false);
        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class ViewHolder extends RecyclerViewHolder<MemberVO> implements View.OnClickListener {

        final View container;
        final CircleImageView memberImage;
        final TextView name;
        final TextView  profession;
        final TextView  locations;

        ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            memberImage = (CircleImageView) container.findViewById(R.id.member_image);
            name = (TextView) container.findViewById(R.id.name);
            profession = (TextView) container.findViewById(R.id.profession);
            locations = (TextView) container.findViewById(R.id.locations);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(MemberVO item) {
            name.setText(item.mFullName);
            profession.setText(item.mProfession);
            locations.setText(item.mLocation);
            ImageLoaderHelper.loadImage(memberImage.getContext(), buildUrl(item.mProfilePicURL), memberImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
