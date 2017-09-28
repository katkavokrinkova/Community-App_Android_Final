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

package net.impacthub.app.ui.common.binders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.impacthub.app.R;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.binder.ViewBinder;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

public class AboutViewBinder implements ViewBinder<List<ListItemType>> {

    private final BaseListAdapter<RecyclerView.ViewHolder, ListItemType> mAdapter;

    public AboutViewBinder(BaseListAdapter<RecyclerView.ViewHolder, ListItemType> adapter) {
        mAdapter = adapter;
    }

    @Override
    public View getView(Context context, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.common_list_layout_with_padding, new LinearLayout(context), false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        return recyclerView;
    }

    @Override
    public void bindView(List<ListItemType> model) {
        mAdapter.setItems(model);
    }
}
