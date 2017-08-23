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

package net.impacthub.members.ui.features.messages.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.impacthub.members.R;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ActiveContactsListAdapter extends BaseListAdapter<RecyclerViewHolder<Object>, Object> {

    protected ActiveContactsListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public RecyclerViewHolder<Object> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(getLayoutInflater().inflate(R.layout.item_layout_member_active, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder<Object> holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    private class MyViewHolder extends RecyclerViewHolder<Object> {

        MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindViewsWith(Object itemData) {

        }
    }
}
