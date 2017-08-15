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

package net.impacthub.members.ui.features.home.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.events.EventDTO;
import net.impacthub.members.ui.base.BaseChildFragment;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/15/2017.
 */

public class EventDetailFragment extends BaseChildFragment {

    @BindView(R.id.list_items) protected RecyclerView mEventDetailList;

    public static EventDetailFragment newInstance(EventDTO model) {

        Bundle args = new Bundle();

        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_category_detail_with_fab;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setUpToolbar(R.string.label_events);
        mEventDetailList.setHasFixedSize(true);
        mEventDetailList.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(getLayoutInflater().inflate(R.layout.item_layout_notification, parent,false)) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 150;
            }
        });
    }
}
