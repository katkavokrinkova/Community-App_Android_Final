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

package net.impacthub.members.ui.features.home.members.binders.project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.members.MemberProjectDTO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

class MemberProjectsAapter extends BaseListAdapter<MemberProjectsAapter.MemberProjectViewHolder, MemberProjectDTO> {

    MemberProjectsAapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public MemberProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberProjectViewHolder(getLayoutInflater().inflate(R.layout.item_project_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberProjectViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class MemberProjectViewHolder extends RecyclerViewHolder<MemberProjectDTO> implements View.OnClickListener {

        MemberProjectViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(MemberProjectDTO itemData) {

        }

        @Override
        public void onClick(View v) {

        }
    }
}
