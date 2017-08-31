package net.impacthub.members.ui.features.home.members;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.vo.members.MemberStatusType;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.features.members.MembersPresenter;
import net.impacthub.members.presenter.features.members.MembersUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;
import net.impacthub.members.ui.features.filters.FilterActivity;
import net.impacthub.members.ui.modal.ModalActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MembersFragment extends BaseChildFragment<MembersPresenter> implements MembersUiContract, OnListItemClickListener<MemberVO> {

    public static final String KEY_LIST_STATE = "list_state";
    @BindView(R.id.list_items) protected RecyclerView mMembersList;

    private MembersListAdapter mAdapter;
    private Parcelable mState;

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

    @OnClick(R.id.filter_button)
    protected void onFilterClick() {
        startActivityForResult(new Intent(getActivity(), FilterActivity.class), 1234);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.members);

        mMembersList.setHasFixedSize(true);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mMembersList.addItemDecoration(new LinearItemsMarginDecorator(offset, offset, 0, offset));

//        if (mAdapter == null) {
//            mAdapter = new MembersListAdapter(getActivity().getLayoutInflater());
//            mAdapter.setItemClickListener(this);
//            getPresenter().loadMembers();
//        }
        mAdapter = new MembersListAdapter(getActivity().getLayoutInflater());
        mAdapter.setItemClickListener(this);
        mMembersList.setAdapter(mAdapter);
        getPresenter().loadMembers();

//        if (mState != null) {
//            mMembersList.getLayoutManager().onRestoreInstanceState(mState);
//        } else {
//            getPresenter().loadMembers();
//        }
    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            mState = savedInstanceState.getParcelable(KEY_LIST_STATE);
//        }
//        super.onViewStateRestored(savedInstanceState);
//    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        mState = mMembersList.getLayoutManager().onSaveInstanceState();
//        outState.putParcelable(KEY_LIST_STATE, mState);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234) {
        showToast("Back from filters...");
        } else {
            getPresenter().loadMembers();
        }
    }

    @Override
    public void onItemClick(int viewId, MemberVO member) {
        switch (viewId) {
            case R.id.image_member_status:
                MemberStatusType statusType = MemberStatusType.fromStatus(member.mMemberStatus);
                switch (statusType) {
                    case NOT_CONTACTED:
                        Intent intent = new Intent(getActivity(), ModalActivity.class);
                        intent.putExtra(ModalActivity.MODAL_TYPE_CONNECT, true);
                        intent.putExtra(ModalActivity.EXTRA_CONTACT_ID, member.mContactId);
                        startActivityForResult(intent, 1122);
                        break;
                    case APPROVED:

                        break;
                    case APPROVE_DECLINE:
                        addChildFragment(MemberDetailFragment.newInstance(member.mUserId), "FRAG_MEMBER_DETAIL");
                        break;
                }
                break;
            default:
                addChildFragment(MemberDetailFragment.newInstance(member.mUserId), "FRAG_MEMBER_DETAIL");
        }
    }

    @Override
    public void onLoadMembers(List<MemberVO> memberDTOs) {
        mAdapter.setItems(memberDTOs);
    }
}
