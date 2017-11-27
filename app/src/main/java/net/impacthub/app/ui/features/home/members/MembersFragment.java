package net.impacthub.app.ui.features.home.members;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.pojo.Refreshable;
import net.impacthub.app.model.vo.conversations.ConversationVO;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.members.MemberStatusType;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.members.MembersPresenter;
import net.impacthub.app.presenter.features.members.MembersUiContract;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.EndlessRecyclerOnScrollListener;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;
import net.impacthub.app.ui.common.UIRefreshManager;
import net.impacthub.app.ui.features.filters.FilterActivity;
import net.impacthub.app.ui.features.messages.conversation.ConversationFragment;
import net.impacthub.app.ui.widgets.UISearchView;
import net.impacthub.app.utilities.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static net.impacthub.app.ui.features.filters.FilterActivity.EXTRA_FILTER_DATA;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MembersFragment extends BaseChildFragment<MembersPresenter> implements MembersUiContract, OnListItemClickListener<MemberVO>,Refreshable {

    public static final String KEY_LIST_STATE = "list_state";

    @BindView(R.id.list_items) protected RecyclerView mMembersList;
    @BindView(R.id.search_from_list) protected UISearchView mSearchView;
    @BindView(R.id.filter_tick) protected ImageView mFilterTick;

    private MembersListAdapter mAdapter;
    private int mOffset;
    private Parcelable mState;
    private FilterData mFilterData;

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
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        intent.putExtra(EXTRA_FILTER_DATA, mFilterData);
        startActivityForResult(intent, FilterActivity.FILTER_REQUEST_CODE);
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

        mMembersList.addOnScrollListener(mOnScrollListener);
        //getPresenter().loadMembers();

        if (mState != null) {
            mMembersList.getLayoutManager().onRestoreInstanceState(mState);
        } else {
            getPresenter().loadMembers(mOffset = 0);
        }

        mSearchView.setSearchActionListener(new UISearchView.OnSearchActionListener() {
            @Override
            public void onSearch(String searchValue) {
//                if (!mAdapter.validateDTOByKeyword(searchValue)) {
                    getPresenter().searchMemberWith(searchValue, 0);
//                }
            }

            @Override
            public void onTextChanged(String query) {
                mAdapter.filterSearch(query);
            }
        });

        mFilterData = new FilterData();
        mFilterData.getFilters().put(FilterData.KEY_FILTER_HUB, new ArrayList<String>());
        mFilterData.getFilters().put(FilterData.KEY_FILTER_SECTOR, new ArrayList<String>());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FilterActivity.FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mFilterData = (FilterData) data.getSerializableExtra(EXTRA_FILTER_DATA);
            getPresenter().handleFilters(mFilterData);
        } else if(requestCode == 1122 && resultCode == Activity.RESULT_OK) {
            getPresenter().loadMembers(mOffset = 0);
        }
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
    public void onItemClick(int viewId, MemberVO member, int position) {
        switch (viewId) {
            case R.id.image_member_status:
                MemberStatusType statusType = MemberStatusType.fromStatus(member.mMemberStatus);
                switch (statusType) {
                    case NOT_CONTACTED:
                        Intent intent = new Intent(getActivity(), ConnectMemberActivity.class);
                        intent.putExtra(ConnectMemberActivity.EXTRA_CONTACT_CONNECT_ID, member.mContactId);
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
    public void onLoadMembers(boolean clearList, List<MemberVO> memberDTOs, boolean done) {
        if(clearList) mAdapter.clearItems();
        mOffset += memberDTOs.size();
        mAdapter.appendItems(memberDTOs);
        mOnScrollListener.canLoadMore(done);
        mAdapter.filterSearch(mSearchView.getSearchText());
    }

    @Override
    public void onLoadSearchedMembers(List<MemberVO> memberDTOs) {
        mAdapter.appendItemsAsFiltered(memberDTOs, mSearchView.getSearchText());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIRefreshManager.getInstance().addRefreshable(UIRefreshManager.REFRESH_ID_MEMBERS_LIST, this);
    }

    @Override
    public void onDestroy() {
        UIRefreshManager.getInstance().removeRefreshable(UIRefreshManager.REFRESH_ID_MEMBERS_LIST, this);
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        getPresenter().loadMembers(mOffset = 0);
    }

    @Override
    public void onShowTick(Map<String, List<String>> filters) {
        ViewUtils.visible(mFilterTick);
        mAdapter.applyFilters(filters);
    }

    @Override
    public void onHideTick() {
        ViewUtils.gone(mFilterTick);
        mAdapter.resetFilters();
    }

    @Override
    public void onLoadingStateChanged(boolean loading) {
        mOnScrollListener.setLoading(loading);
    }

    @Override
    public List<MemberVO> getMembers() {
        return mAdapter.getFilteredItems();
    }

    private final EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadMore() {
            getPresenter().loadMembers(mOffset);
        }
    };
}
