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

package net.impacthub.members.ui.features.home.companies;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.vo.companies.AboutCompanyVO;
import net.impacthub.members.model.vo.companies.ServiceVO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/24/2017.
 */

public class CompanyInfoListAdapter extends BaseListAdapter<RecyclerView.ViewHolder, ListItemType> {

    public CompanyInfoListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case 0:
                viewHolder = new TitleViewHolder(getLayoutInflater().inflate(R.layout.item_layout_info_title, parent, false));
                break;
            case 1:
                viewHolder = new AboutTopDetailViewHolder(getLayoutInflater().inflate(R.layout.layout_item_company_top_info, parent, false));
                break;
            default:
                viewHolder = new ServiceDetailViewHolder(getLayoutInflater().inflate(R.layout.item_layout_info_pair_set, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void setItems(List<ListItemType> items) {
        appendItems(items);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object model = getItem(position).getModel();
        switch (getItemViewType(position)) {
            case 0:
                String titleItem = (String) model;
                ((TitleViewHolder) holder).bindViewsWith(titleItem);
                break;
            case 1:
                ((AboutTopDetailViewHolder) holder).bindViewsWith((AboutCompanyVO) model);
                break;
            case 2:
                ((ServiceDetailViewHolder) holder).bindViewsWith((ServiceVO) model);
                break;
        }
    }

    private class TitleViewHolder extends RecyclerViewHolder<String>{

        final TextView title;

        TitleViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_info_title);
        }

        @Override
        protected void bindViewsWith(String itemData) {
            title.setText(itemData);
        }
    }

    private class AboutTopDetailViewHolder extends RecyclerViewHolder<AboutCompanyVO> {

        final TextView locations;
        final TextView memberCount;
        final TextView description;

        AboutTopDetailViewHolder(View itemView) {
            super(itemView);
            locations = (TextView) itemView.findViewById(R.id.locations);
            memberCount = (TextView) itemView.findViewById(R.id.member_count);
            description = (TextView) itemView.findViewById(R.id.text_description);
        }

        @Override
        protected void bindViewsWith(AboutCompanyVO itemData) {
            String description = itemData.mDescription;
            locations.setText(itemData.mLocation);
            memberCount.setText(itemData.mMembersCount);
            this.description.setText(description != null ? Html.fromHtml(description) : null);
        }
    }

    private class ServiceDetailViewHolder extends RecyclerViewHolder<ServiceVO> {

        final TextView title;
        final TextView description;

        ServiceDetailViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_info_title);
            description = (TextView) itemView.findViewById(R.id.text_event_description);
        }

        @Override
        protected void bindViewsWith(ServiceVO itemData) {
            title.setText(itemData.mTitle);
            description.setText(itemData.mDescription);
        }
    }
}
