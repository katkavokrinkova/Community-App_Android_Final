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

package net.impacthub.app.ui.features.messages.contacts.binders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.contacts.ContactVO;
import net.impacthub.app.ui.binder.ViewBinder;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/24/2017.
 */

public class ContactsRejectedViewBinder implements ViewBinder<List<ContactVO>> {

    private final OnListItemClickListener<ContactVO> mListener;
    private RejectedContactsListAdapter mAdapter;

    public ContactsRejectedViewBinder(OnListItemClickListener<ContactVO> mListener) {
        this.mListener = mListener;
    }

    @Override
    public View getView(Context context, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.common_simple_list_layout, new LinearLayout(context), false);
        recyclerView.setHasFixedSize(true);
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        recyclerView.addItemDecoration(new LinearItemsMarginDecorator(offset));
        mAdapter = new RejectedContactsListAdapter(inflater);
        mAdapter.setItemClickListener(mListener);
        recyclerView.setAdapter(mAdapter);
        return recyclerView;
    }

    @Override
    public void bindView(List<ContactVO> model) {
        mAdapter.setItems(model);
    }
}
