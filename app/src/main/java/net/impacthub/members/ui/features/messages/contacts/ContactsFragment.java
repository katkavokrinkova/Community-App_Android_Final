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

package net.impacthub.members.ui.features.messages.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.vo.contacts.ContactVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.features.contacts.ContactsUiContract;
import net.impacthub.members.presenter.features.contacts.ContactsUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.members.MemberDetailFragment;
import net.impacthub.members.ui.features.messages.contacts.binders.ContactsApprovedViewBinder;
import net.impacthub.members.ui.features.messages.contacts.binders.ContactsPendingViewBinder;
import net.impacthub.members.ui.features.messages.contacts.binders.ContactsRejectedViewBinder;

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

    private ViewBinder<List<ContactVO>> mViewBinder1;
    private ViewBinder<List<ContactVO>> mViewBinder2;
    private ViewBinder<List<ContactVO>> mViewBinder3;

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

        AppPagerAdapter adapter = new AppPagerAdapter(getContext());

        adapter.addVieBinder(mViewBinder1 = new ContactsApprovedViewBinder(new OnListItemClickListener<ContactVO>() {
            @Override
            public void onItemClick(int viewId, ContactVO model) {
                switch (viewId) {
                    case R.id.button_message_contact:
                        showToast("Opening Conversation");
                        break;
                    case R.id.button_decline_contact:
                        getPresenter().declineContact(model.mMember.mDM_ID);
                        break;
                    default:
                        addChildFragment(MemberDetailFragment.newInstance(model.mMember), "FRAG_MEMBER_DETAIL");
                }
            }
        }));

        adapter.addVieBinder(mViewBinder2 = new ContactsPendingViewBinder(new OnListItemClickListener<ContactVO>() {
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
        }));

        adapter.addVieBinder(mViewBinder3 = new ContactsRejectedViewBinder(new OnListItemClickListener<ContactVO>() {
            @Override
            public void onItemClick(int viewId, ContactVO model) {
                MemberVO member = model.mMember;
                if (member != null) {
                    getPresenter().updateContactRequest(model.mMember.mDM_ID, member.mUserId, "Approved");
                }
            }
        }));

        mContactPages.setAdapter(adapter);
        mContactPages.setOffscreenPageLimit(adapter.getCount());
        mContactsTab.setupWithViewPager(mContactPages);

        new TabsDelegate().setUp(mContactsTab, TITLES);

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
