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

package net.impacthub.app.ui.features.filters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.filters.FilterBarVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.RecyclerViewHolder;
import net.impacthub.app.utilities.TextUtils;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/26/2017.
 */

class FilterAdapter extends BaseListAdapter<FilterAdapter.FilterBarHolder, FilterBarVO> {

    FilterAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public FilterBarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterBarHolder(getLayoutInflater().inflate(R.layout.item_filter_bar_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(FilterBarHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class FilterBarHolder extends RecyclerViewHolder<FilterBarVO> implements View.OnClickListener {

        final TextView mFilterName;
        final TextView mFilterValue;

        private FilterBarHolder(View itemView) {
            super(itemView);
            mFilterName = (TextView) itemView.findViewById(R.id.filter_name);
            mFilterValue = (TextView) itemView.findViewById(R.id.filter_selected);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(FilterBarVO itemData) {
            mFilterName.setText(itemData.getFilterName());
            mFilterValue.setText(TextUtils.arraysAsString(itemData.getSelectedFilters()));
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(view.getId(), getItem(position), position);
            }
        }
    }
}
