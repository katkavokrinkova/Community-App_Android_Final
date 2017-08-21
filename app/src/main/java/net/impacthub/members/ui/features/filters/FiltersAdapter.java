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

package net.impacthub.members.ui.features.filters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.features.filters.Filter;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/21/2017.
 */

public class FiltersAdapter extends BaseListAdapter<FiltersAdapter.FilterViewHolder, Filter> {

    protected FiltersAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterViewHolder(getLayoutInflater().inflate(R.layout.item_layout_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    void clearFilters() {
        for(int i = 0; i<getItemCount(); i++){
            getItem(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    class FilterViewHolder extends RecyclerViewHolder<Filter> implements View.OnClickListener {

        final TextView filterLabel;
        final CheckBox filterCheckbox;

        FilterViewHolder(View itemView) {
            super(itemView);
            filterLabel = (TextView) itemView.findViewById(R.id.text_filter_label);
            filterCheckbox = (CheckBox) itemView.findViewById(R.id.button_check_option);
            filterCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    getItem(getAdapterPosition()).setSelected(checked);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(Filter itemData) {
            filterLabel.setText(itemData.getName());
            filterCheckbox.setChecked(itemData.isSelected());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Filter filter = getItem(position);
            filter.setSelected(!filter.isSelected());
            notifyItemChanged(position);
        }
    }
}
