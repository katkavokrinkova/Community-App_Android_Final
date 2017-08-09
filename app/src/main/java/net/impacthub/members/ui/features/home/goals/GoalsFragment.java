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

package net.impacthub.members.ui.features.home.goals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.goals.GoalDTO;
import net.impacthub.members.presenter.features.goals.GoalsPresenter;
import net.impacthub.members.presenter.features.goals.GoalsUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class GoalsFragment extends BaseChildFragment<GoalsPresenter> implements OnListItemClickListener<GoalDTO>, GoalsUiContract {

    @BindView(R.id.list_items) protected RecyclerView mGoalsList;

    private GoalsListAdapter mAdapter;

    public static GoalsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        GoalsFragment fragment = new GoalsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected GoalsPresenter onCreatePresenter() {
        return new GoalsPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_list_with_back;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_goals);
        mGoalsList.setHasFixedSize(true);
        mAdapter = new GoalsListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(this);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_medium_gap);
        mGoalsList.addItemDecoration(new LinearItemsMarginDecorator(offset, offset, 0, 0));
        mGoalsList.setAdapter(mAdapter);

        getPresenter().getGoals();
    }

    @Override
    public void onItemClick(GoalDTO model) {
        showToast("Hello");
    }

    @Override
    public void onLoadGoals(List<GoalDTO> goals) {
        mAdapter.setItems(goals);
    }
}
