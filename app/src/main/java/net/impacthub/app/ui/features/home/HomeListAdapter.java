package net.impacthub.app.ui.features.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.home.HomeMenuItem;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

class HomeListAdapter extends BaseListAdapter<HomeListAdapter.ViewHolder, HomeMenuItem> {

    HomeListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = getLayoutInflater().inflate(R.layout.item_layout_home_menu, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeMenuItem item = getItem(position);
        holder.bindViewsWith(item);
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
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v.getId(), getItem(position), position);
            }
        }
    }
}
