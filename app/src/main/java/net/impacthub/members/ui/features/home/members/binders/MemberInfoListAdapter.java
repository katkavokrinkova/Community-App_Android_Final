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

package net.impacthub.members.ui.features.home.members.binders;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.vo.members.SkillsVO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/11/2017.
 */

public class MemberInfoListAdapter extends BaseListAdapter<RecyclerView.ViewHolder, ListItemType> {

    public MemberInfoListAdapter(LayoutInflater inflater) {
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
                viewHolder = new AboutDescriptionViewHolder(getLayoutInflater().inflate(R.layout.layout_description_info, parent, false));
                break;
            default:
                viewHolder = new SkillDetailViewHolder(getLayoutInflater().inflate(R.layout.item_layout_info_pair_set, parent, false));
        }
        return viewHolder;
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
                String descriptionItem = (String) model;
                ((AboutDescriptionViewHolder) holder).bindViewsWith(descriptionItem);
                break;
            case 2:
                SkillsVO skillItem = (SkillsVO) model;
                ((SkillDetailViewHolder) holder).bindViewsWith(skillItem);
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

    private class AboutDescriptionViewHolder extends RecyclerViewHolder<String>{

        final TextView description;

        AboutDescriptionViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.text_event_description);
            int padding = itemView.getResources().getDimensionPixelOffset(R.dimen.default_content_large_gap);
            description.setPadding(padding, padding, padding, padding);
        }

        @Override
        protected void bindViewsWith(String itemData) {
            description.setText(itemData != null ? Html.fromHtml(itemData) : null);
        }
    }

    private class SkillDetailViewHolder extends RecyclerViewHolder<SkillsVO>{

        final TextView title;
        final TextView description;

        SkillDetailViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_info_title);
            description = (TextView) itemView.findViewById(R.id.text_event_description);
        }

        @Override
        protected void bindViewsWith(SkillsVO itemData) {
            title.setText(itemData.mTitle);
            description.setText(itemData.mDescription);
        }
    }
}
