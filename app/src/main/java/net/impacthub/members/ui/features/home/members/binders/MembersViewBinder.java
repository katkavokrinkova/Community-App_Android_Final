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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;
import net.impacthub.members.ui.features.home.members.MembersListAdapter;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class MembersViewBinder implements ViewBinder<List<MemberDTO>> {

    private final OnListItemClickListener<MemberDTO> mItemClickListener;
    private MembersListAdapter mAdapter;

    public MembersViewBinder(OnListItemClickListener<MemberDTO> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public View getView(Context context, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.common_list_layout, new LinearLayout(context), false);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MembersListAdapter(inflater);
        mAdapter.setItemClickListener(mItemClickListener);
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        recyclerView.addItemDecoration(new LinearItemsMarginDecorator(offset));
        recyclerView.setAdapter(mAdapter);
        return recyclerView;
    }

    @Override
    public void bindView(List<MemberDTO> model) {
        mAdapter.setItems(model);
    }
}
