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

package net.impacthub.app.ui.features.messages.contacts;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.contacts.ContactVO;
import net.impacthub.app.model.vo.conversations.ConversationVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.contacts.ContactsUiContract;
import net.impacthub.app.presenter.features.contacts.ContactsUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.binder.ViewBinder;
import net.impacthub.app.ui.common.AppPagerAdapter;
import net.impacthub.app.ui.features.home.members.MemberDetailFragment;
import net.impacthub.app.ui.features.messages.contacts.binders.ActiveContactsListAdapter;
import net.impacthub.app.ui.features.messages.contacts.binders.ContactsApprovedViewBinder;
import net.impacthub.app.ui.features.messages.contacts.binders.ContactsPendingViewBinder;
import net.impacthub.app.ui.features.messages.contacts.binders.ContactsRejectedViewBinder;
import net.impacthub.app.ui.features.messages.contacts.binders.PendingContactsListAdapter;
import net.impacthub.app.ui.features.messages.contacts.binders.RejectedContactsListAdapter;
import net.impacthub.app.ui.features.messages.conversation.ConversationFragment;
import net.impacthub.app.ui.widgets.UISearchView;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class ContactsFragment extends BaseChildFragment<ContactsUiPresenter> implements ContactsUiContract {

    private static final String TITLES[] = {"ACTIVE", "PENDING", "REJECTED"};

    @BindView(R.id.tabs) protected TabLayout mContactsTab;
    @BindView(R.id.pager) protected ViewPager mContactPages;
    @BindView(R.id.search_from_list) protected UISearchView mSearchView;

    private ViewBinder<List<ContactVO>> mViewBinder1;
    private ViewBinder<List<ContactVO>> mViewBinder2;
    private ViewBinder<List<ContactVO>> mViewBinder3;

    private ActiveContactsListAdapter mListAdapter1;
    private PendingContactsListAdapter mListAdapter2;
    private RejectedContactsListAdapter mListAdapter3;

    public static ContactsFragment newInstance() {

        Bundle args = new Bundle();

        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ContactsUiPresenter onCreatePresenter() {
        return new ContactsUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list_with_tabs;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.label_contacts);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext(), TITLES);

        LayoutInflater layoutInflater = getLayoutInflater(getArguments());
        mListAdapter1 = new ActiveContactsListAdapter(layoutInflater);
        mListAdapter1.setItemClickListener(new OnListItemClickListener<ContactVO>() {
            @Override
            public void onItemClick(int viewId, ContactVO model) {
                MemberVO memberVO = model.mMember;
                switch (viewId) {
                    case R.id.button_message_contact:
                        ConversationVO conversationVO = new ConversationVO();
                        conversationVO.mDisplayName = memberVO.mFullName;
                        conversationVO.mImageURL = memberVO.mProfilePicURL;
                        conversationVO.mRecipientUserId = memberVO.mUserId;
                        addChildFragment(ConversationFragment.newInstance(conversationVO), "FRAG_MESSAGE_THREAD");
                        break;
                    case R.id.button_decline_contact:
                        getPresenter().declineContact(memberVO.mDM_ID);
                        break;
                    default:
                        addChildFragment(MemberDetailFragment.newInstance(memberVO), "FRAG_MEMBER_DETAIL");
                }
            }
        });


        adapter.addVieBinder(mViewBinder1 = new ContactsApprovedViewBinder(mListAdapter1));

        mListAdapter2 = new PendingContactsListAdapter(layoutInflater);
        mListAdapter2.setItemClickListener(new OnListItemClickListener<ContactVO>() {
            @Override
            public void onItemClick(int viewId, ContactVO model) {
                MemberVO member = model.mMember;
                switch (viewId) {
                    case R.id.button_accept_contact:
                        if (member != null) {
                            getPresenter().updateContactRequest(model.mMember.mDM_ID, member.mUserId, "Approved");
                        }
                        break;
                    case R.id.button_reject_contact:
                        if (member != null) {
                            getPresenter().updateContactRequest(model.mMember.mDM_ID, member.mUserId, "Declined");
                        }
                        break;
                    case R.id.button_view_more:
                    default:
                        addChildFragment(ViewMoreContactFragment.newInstance(model), "FRAG_VIEW_MORE_CONTACT");
                }
            }
        });

        adapter.addVieBinder(mViewBinder2 = new ContactsPendingViewBinder(mListAdapter2));


        mListAdapter3 = new RejectedContactsListAdapter(layoutInflater);
        mListAdapter3.setItemClickListener(new OnListItemClickListener<ContactVO>() {
            @Override
            public void onItemClick(int viewId, ContactVO model) {
                MemberVO member = model.mMember;
                if (member != null) {
                    getPresenter().updateContactRequest(model.mMember.mDM_ID, member.mUserId, "Approved");
                }
            }
        });

        adapter.addVieBinder(mViewBinder3 = new ContactsRejectedViewBinder(mListAdapter3));

        mContactPages.setAdapter(adapter);
        mContactPages.setOffscreenPageLimit(adapter.getCount());
        mContactsTab.setupWithViewPager(mContactPages);

        mSearchView.setSearchActionListener(new UISearchView.OnSearchActionListener() {
            @Override
            public void onSearch(String searchValue) {

            }

            @Override
            public void onTextChanged(String query) {
                mListAdapter1.filter(query);
                mListAdapter2.filter(query);
                mListAdapter3.filter(query);
            }
        });
        getPresenter().getContacts();
    }

    @Override
    public void onLoadApprovedContacts(List<ContactVO> contactVOs) {
        mViewBinder1.bindView(contactVOs);
    }

    @Override
    public void onLoadOutstandingContacts(List<ContactVO> contactVOs) {
        mViewBinder2.bindView(contactVOs);
    }

    @Override
    public void onLoadDeclinedContacts(List<ContactVO> contactVOs) {
        mViewBinder3.bindView(contactVOs);
    }
}
