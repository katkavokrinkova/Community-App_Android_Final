package net.impacthub.app.ui.features.home.members;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.conversations.ConversationVO;
import net.impacthub.app.model.vo.members.MemberStatusType;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.members.MembersPresenter;
import net.impacthub.app.presenter.features.members.MembersUiContract;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;
import net.impacthub.app.ui.features.filters.FilterActivity;
import net.impacthub.app.ui.features.messages.conversation.ConversationFragment;
import net.impacthub.app.ui.modal.ModalActivity;
import net.impacthub.app.ui.widgets.UISearchView;

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
    @BindView(R.id.search_from_list) protected UISearchView mSearchView;

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
        return R.layout.fragment_list_with_fixed_searchbar;
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
    protected void bindView(View rootView) {
        super.bindView(rootView);
        setUpToolbar(R.string.members);

        mMembersList.setHasFixedSize(true);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);

        FragmentActivity activity = getActivity();
        mMembersList.addItemDecoration(new LinearItemsMarginDecorator(offset));

//        if (mAdapter == null) {
//            mAdapter = new MembersListAdapter(getActivity().getLayoutInflater());
//            mAdapter.setItemClickListener(this);
//            getPresenter().loadMembers();
//        }
        mAdapter = new MembersListAdapter(activity.getLayoutInflater());
        mAdapter.setItemClickListener(this);
        mMembersList.setLayoutManager(new LinearLayoutManager(activity));
        mMembersList.setAdapter(mAdapter);
        //getPresenter().loadMembers();

        if (mState != null) {
            mMembersList.getLayoutManager().onRestoreInstanceState(mState);
        } else {
            getPresenter().loadMembers();
        }

        mSearchView.setSearchActionListener(new UISearchView.OnSearchActionListener() {
            @Override
            public void onSearch(String searchValue) {
            }

            @Override
            public void onTextChanged(String query) {
                mAdapter.filter(query);
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mState = savedInstanceState.getParcelable(KEY_LIST_STATE);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mState = mMembersList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(KEY_LIST_STATE, mState);
        super.onSaveInstanceState(outState);
    }

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
                        ConversationVO model = new ConversationVO();
                        model.mDisplayName = member.mFullName;
                        model.mImageURL = member.mProfilePicURL;
                        model.mRecipientUserId = member.mUserId;
                        addChildFragment(ConversationFragment.newInstance(model), "FRAG_MESSAGE_THREAD");
                        break;
                    case APPROVE_DECLINE:
                        addChildFragment(MemberDetailFragment.newInstance(member), "FRAG_MEMBER_DETAIL");
                        break;
                }
                break;
            default:
                addChildFragment(MemberDetailFragment.newInstance(member), "FRAG_MEMBER_DETAIL");
        }
    }

    @Override
    public void onLoadMembers(List<MemberVO> memberDTOs) {
        mAdapter.setItems(memberDTOs);
    }
}
