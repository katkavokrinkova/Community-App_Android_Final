package net.impacthub.members.ui.features.home.members;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.members.Member;
import net.impacthub.members.presenter.features.members.MembersPresenter;
import net.impacthub.members.presenter.features.members.MembersUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.features.filters.FilterActivity;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MembersFragment extends BaseChildFragment<MembersPresenter> implements MembersUiContract, OnListItemClickListener<Member> {

    private MembersListAdapter mAdapter;

    public static MembersFragment newInstance() {

        Bundle args = new Bundle();

        MembersFragment fragment = new MembersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list;
    }

    @Override
    protected MembersPresenter onCreatePresenter() {
        return new MembersPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.members);
        RecyclerView membersList = (RecyclerView) view.findViewById(R.id.list_items);
        ImageButton filterButton = (ImageButton) view.findViewById(R.id.filter_button);
        membersList.setHasFixedSize(true);
        mAdapter = new MembersListAdapter(getActivity().getLayoutInflater());
        mAdapter.setItemClickListener(this);
        membersList.setAdapter(mAdapter);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), FilterActivity.class), 1234);
            }
        });
        getPresenter().loadMembers();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showToast("Back from filters...");
    }

    @Override
    public void onItemClick(Member model) {
        Toast.makeText(getActivity(), model.getId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMembers(List<Member> response) {
        mAdapter.setItems(response);
    }
}
