package net.impacthub.members.ui.features.home.members;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.members.Member;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;
import net.impacthub.members.ui.widgets.CircleImageView;
import net.impacthub.members.ui.widgets.ImageLoaderHelper;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

class MembersListAdapter extends BaseListAdapter<MembersListAdapter.ViewHolder, Member> {

    private final UserAccount mUserAccount = userAccountProvider();
    private final LayoutInflater mLayoutInflater;
    private OnListItemClickListener<Member> mItemClickListener;

    MembersListAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = mLayoutInflater.inflate(R.layout.member_item, parent, false);
        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    void setItemClickListener(@NonNull OnListItemClickListener<Member> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerViewHolder<Member> implements View.OnClickListener {

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
        protected void bindViewsWith(Member item) {
            name.setText(item.getFirstName() + ' ' + item.getLastName());
            profession.setText(item.getProfession());
            locations.setText(item.getImpactHubCities());
            ImageLoaderHelper.loadImage(memberImage.getContext(), item.getProfilePic() + "?oauth_token=" + mUserAccount.getAuthToken(), memberImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
