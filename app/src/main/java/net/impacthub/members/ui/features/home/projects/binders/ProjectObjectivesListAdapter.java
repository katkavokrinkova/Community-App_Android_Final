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

package net.impacthub.members.ui.features.home.projects.binders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.objectives.ObjectiveVO;
import net.impacthub.members.model.pojo.ListItem;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

class ProjectObjectivesListAdapter extends BaseListAdapter<RecyclerView.ViewHolder, ListItem<?>> {

    ProjectObjectivesListAdapter(LayoutInflater inflater) {
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
            default:
                viewHolder = new ObjectiveViewHolder(getLayoutInflater().inflate(R.layout.item_layout_objective, parent, false));
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
                ListItem<ObjectiveVO> objectiveItem = (ListItem<ObjectiveVO>) getItem(position);
                ((ObjectiveViewHolder) holder).bindViewsWith(objectiveItem.getModel());
                break;
        }
    }

    private class TitleViewHolder extends RecyclerViewHolder<String> {

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

    private class ObjectiveViewHolder extends RecyclerViewHolder<ObjectiveVO>{

        final TextView objectiveCount;
        final TextView title;
        final TextView summary;
        final View lineCounter;

        ObjectiveViewHolder(View itemView) {
            super(itemView);
            objectiveCount = (TextView) itemView.findViewById(R.id.text_objective_count);
            title = (TextView) itemView.findViewById(R.id.text_objective_title);
            summary = (TextView) itemView.findViewById(R.id.text_objective_summary);
            lineCounter = itemView.findViewById(R.id.line_objective_counter);
        }

        @Override
        protected void bindViewsWith(ObjectiveVO itemData) {
            objectiveCount.setText(String.valueOf(itemData.mCount));
            title.setText(String.valueOf(itemData.mTitle));
            summary.setText(String.valueOf(itemData.mSummary));
            lineCounter.setVisibility(itemData.mHideLastTimeLine ? View.GONE : View.VISIBLE);
        }
    }
}
