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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.members.SkillsDTO;
import net.impacthub.members.model.pojo.ListItem;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/11/2017.
 */

class MemberInfoListAdapter extends BaseListAdapter<RecyclerView.ViewHolder, ListItem<?>> {

    MemberInfoListAdapter(LayoutInflater inflater) {
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
            case ListItem.TYPE_ONE:
                viewHolder = new TitleViewHolder(getLayoutInflater().inflate(R.layout.item_layout_info_title, parent, false));
                break;
            case ListItem.TYPE_TWO:
                viewHolder = new AboutDescriptionViewHolder(getLayoutInflater().inflate(R.layout.layout_description_info, parent, false));
                break;
            default:
                viewHolder = new SkillDetailViewHolder(getLayoutInflater().inflate(R.layout.item_member_info_skill_set, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ListItem.TYPE_ONE:
                ListItem<String> titleItem = (ListItem<String>) getItem(position);
                ((TitleViewHolder) holder).bindViewsWith(titleItem.getModel());
                break;
            case ListItem.TYPE_TWO:
                ListItem<String> descriptionItem = (ListItem<String>) getItem(position);
                ((AboutDescriptionViewHolder) holder).bindViewsWith(descriptionItem.getModel());
                break;
            case ListItem.TYPE_THREE:
                ListItem<SkillsDTO> skillItems = (ListItem<SkillsDTO>) getItem(position);
                ((SkillDetailViewHolder) holder).bindViewsWith(skillItems.getModel());
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
            description = (TextView) itemView.findViewById(R.id.text_description);
        }

        @Override
        protected void bindViewsWith(String itemData) {
            description.setText(itemData);
        }
    }

    private class SkillDetailViewHolder extends RecyclerViewHolder<SkillsDTO>{

        final TextView title;
        final TextView description;

        SkillDetailViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_info_title);
            description = (TextView) itemView.findViewById(R.id.text_description);
        }

        @Override
        protected void bindViewsWith(SkillsDTO itemData) {
            title.setText(itemData.mTitle);
            description.setText(itemData.mDescription);
        }
    }
}
