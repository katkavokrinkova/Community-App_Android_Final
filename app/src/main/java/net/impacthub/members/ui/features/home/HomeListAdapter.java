package net.impacthub.members.ui.features.home;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.home.HomeMenuItem;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;
import net.impacthub.members.ui.widgets.ImageLoaderHelper;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

class HomeListAdapter extends BaseListAdapter<HomeListAdapter.ViewHolder, HomeMenuItem> {

    private final LayoutInflater mLayoutInflater;
    private OnListItemClickListener<Integer> mItemClickListener;

    HomeListAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_home_menu_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeMenuItem item = getItem(position);
        holder.bindViewsWith(item);
    }

    void setItemClickListener(@NonNull OnListItemClickListener<Integer> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerViewHolder<HomeMenuItem> implements View.OnClickListener {

        final ImageView mMenuImage;
        final TextView mMenuName;

        ViewHolder(View itemView) {
            super(itemView);
            mMenuImage = (ImageView) itemView.findViewById(R.id.image_menu_icon);
            mMenuName = (TextView) itemView.findViewById(R.id.text_menu_label);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(HomeMenuItem itemData) {
            mMenuName.setText(itemData.getHomeMenuTitle());
            ImageLoaderHelper.loadImage(itemView.getContext().getApplicationContext(), itemData.getHomeMenuIconRes(), mMenuImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }


}
