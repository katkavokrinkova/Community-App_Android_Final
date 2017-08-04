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

package net.impacthub.members.ui.features.home.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.presenter.features.groups.GroupPresenter;
import net.impacthub.members.presenter.features.groups.GroupUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class GroupsFragment extends BaseChildFragment<GroupPresenter> implements GroupUiContract, OnListItemClickListener<GroupDTO> {

    @BindView(R.id.list_items) protected RecyclerView mGroupsList;

    private GroupsListAdapter mAdapter;

    public static GroupsFragment newInstance() {

        Bundle args = new Bundle();

        GroupsFragment fragment = new GroupsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected GroupPresenter onCreatePresenter() {
        return new GroupPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_groups);
        mGroupsList.setHasFixedSize(true);
        mAdapter = new GroupsListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(this);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_medium_gap);
        mGroupsList.addItemDecoration(new LinearItemsMarginDecorator(offset, offset, 0, 0));
        mGroupsList.setAdapter(mAdapter);

        getPresenter().getGroups();
    }

    @Override
    public void onItemClick(GroupDTO model) {
        showToast("Loading group detail...");
    }

    @Override
    public void onLoadGroups(List<GroupDTO> groupList) {
        mAdapter.setItems(groupList);
    }
}
