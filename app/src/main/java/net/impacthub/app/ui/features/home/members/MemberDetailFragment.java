package net.impacthub.app.ui.features.home.members;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnCollapsingToolbarOffsetChangeListener;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.Refreshable;
import net.impacthub.app.model.vo.conversations.ConversationVO;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberStatus;
import net.impacthub.app.model.vo.members.MemberStatusType;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.features.members.MemberDetailUiContract;
import net.impacthub.app.presenter.features.members.MemberDetailUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.binder.ViewBinder;
import net.impacthub.app.ui.common.AppPagerAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.SimpleOffsetChangeListenerAdapter;
import net.impacthub.app.ui.common.UIRefreshManager;
import net.impacthub.app.ui.common.binders.AboutViewBinder;
import net.impacthub.app.ui.delegate.DetailScreenDelegate;
import net.impacthub.app.ui.features.home.groups.GroupDetailFragment;
import net.impacthub.app.ui.features.home.groups.GroupsListAdapter;
import net.impacthub.app.ui.features.home.groups.binders.GroupsViewBinder;
import net.impacthub.app.ui.features.home.members.binders.MemberInfoListAdapter;
import net.impacthub.app.ui.features.home.projects.ProjectDetailFragment;
import net.impacthub.app.ui.features.home.projects.ProjectsLisAdapter;
import net.impacthub.app.ui.features.home.projects.binders.ProjectsViewBinder;
import net.impacthub.app.ui.features.messages.conversation.ConversationFragment;
import net.impacthub.app.ui.gallery.PicturePreviewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class MemberDetailFragment extends BaseChildFragment<MemberDetailUiPresenter> implements MemberDetailUiContract, OnCollapsingToolbarOffsetChangeListener {

    private static final String TAG = MemberDetailFragment.class.getSimpleName();

    public static final String TITLES[] = {"ABOUT", "PROJECTS", "GROUPS"};

    public static final String EXTRA_MEMBER_NEEDS_LOADING = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_NEEDS_LOADING";
    public static final String EXTRA_MEMBER_USER_ID = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_USER_ID";
    public static final String EXTRA_MEMBER_CONTACT_ID = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_CONTACT_ID";
    public static final String EXTRA_MEMBER_DM_ID = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_DM_ID";
    public static final String EXTRA_MEMBER_STATUS = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_STATUS";
    public static final String EXTRA_MEMBER_PROFILE_PICTURE = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_PROFILE_PICTURE";
    public static final String EXTRA_MEMBER_INSTAGRAM = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_INSTAGRAM";
    public static final String EXTRA_MEMBER_FACEBOOK = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_FACEBOOK";
    public static final String EXTRA_MEMBER_TWITTER = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_TWITTER";
    public static final String EXTRA_MEMBER_LINKEDIN = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_LINKEDIN";
    public static final String EXTRA_MEMBER_FULL_NAME = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_FULL_NAME";
    public static final String EXTRA_MEMBER_LOCATION = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_LOCATION";
    public static final String EXTRA_MEMBER_ABOUT_ME = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_ABOUT_ME";
    public static final String EXTRA_MEMBER_STATUS_UPDATE = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_STATUS_UPDATE";
    public static final String EXTRA_MEMBER_PROFESSION = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_PROFESSION";
    public static final String EXTRA_MEMBER_COMPANY_NAME = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_COMPANY_NAME";

    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBar;
    @BindView(R.id.collapse_toolbar_member_detail) protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.tabs) protected TabLayout mDetailsTab;
    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.container_connect) protected FrameLayout mMemberStatusContainer;
    @BindView(R.id.locations) protected TextView mLocation;
    @BindView(R.id.text_profession) protected TextView mProfession;
    @BindView(R.id.text_status_update) protected TextView mStatusUpdate;
    @BindView(R.id.button_twitter) protected ImageButton mButtonTwitter;
    @BindView(R.id.button_facebook) protected ImageButton mButtonFacebook;
    @BindView(R.id.button_linkedin) protected ImageButton mButtonLinkedin;
    @BindView(R.id.button_instagram) protected ImageButton mButtonInsta;

    @BindView(R.id.pager) protected ViewPager mPager;

    private boolean mLoadMember;
    private String mUserIDValue;
    private String mDM_ID;
    private String mContactIDValue;
    private int mMemberStatus;
    private String mFullNameValue;
    private String mLocationValue;
    private String mProfessionValue;
    private String mStatusUpdateValue;
    private String mAboutMeValue;
    private String mTwitterLinkValue;
    private String mFacebookLinkValue;
    private String mLinkedinLinkValue;
    private String mInstagramLinkValue;
    private String mImageURLValue;
    private String mCompanyName;

    private ViewBinder<List<ListItemType>> mViewBinder1;
    private ViewBinder<List<ProjectVO>> mViewBinder2;
    private ViewBinder<List<GroupVO>> mViewBinder3;

    private SimpleOffsetChangeListenerAdapter mOffsetChangeListenerAdapter;

    public static MemberDetailFragment newInstance(MemberVO member) {

        Bundle args = new Bundle();
        args.putString(EXTRA_MEMBER_USER_ID, member.mUserId);
        args.putString(EXTRA_MEMBER_CONTACT_ID, member.mContactId);
        args.putString(EXTRA_MEMBER_DM_ID, member.mDM_ID);
        args.putInt(EXTRA_MEMBER_STATUS, member.mMemberStatus);
        args.putString(EXTRA_MEMBER_PROFILE_PICTURE, member.mProfilePicURL);
        args.putString(EXTRA_MEMBER_INSTAGRAM, member.mLinkInstagram);
        args.putString(EXTRA_MEMBER_FACEBOOK, member.mLinkFacebook);
        args.putString(EXTRA_MEMBER_TWITTER, member.mLinkTwitter);
        args.putString(EXTRA_MEMBER_LINKEDIN, member.mLinkLinkedin);
        args.putString(EXTRA_MEMBER_FULL_NAME, member.mFullName);
        args.putString(EXTRA_MEMBER_LOCATION, member.mLocation);
        args.putString(EXTRA_MEMBER_ABOUT_ME, member.mAboutMe);
        args.putString(EXTRA_MEMBER_STATUS_UPDATE, member.mStatusUpdate);
        args.putString(EXTRA_MEMBER_PROFESSION, member.mProfession);
        args.putString(EXTRA_MEMBER_COMPANY_NAME, member.mCompanyName);
        MemberDetailFragment fragment = new MemberDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static MemberDetailFragment newInstance(String memberId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_MEMBER_USER_ID, memberId);
        args.putBoolean(EXTRA_MEMBER_NEEDS_LOADING, true);
        MemberDetailFragment fragment = new MemberDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MemberDetailUiPresenter onCreatePresenter() {
        return new MemberDetailUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_member_detail;
    }


    @OnClick(R.id.image_detail)
    void onMemberAvatarClick() {
        Intent intent = new Intent(getActivity(), PicturePreviewActivity.class);
        intent.putExtra(PicturePreviewActivity.EXTRA_TOOLBAR_TITLE, mFullNameValue);
        intent.putExtra(PicturePreviewActivity.EXTRA_IMAGE_URL, buildUrl(mImageURLValue));
        startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        mUserIDValue = arguments.getString(EXTRA_MEMBER_USER_ID);
        mLoadMember = arguments.getBoolean(EXTRA_MEMBER_NEEDS_LOADING, false);
        if(!mLoadMember) {
            mContactIDValue = arguments.getString(EXTRA_MEMBER_CONTACT_ID);
            mDM_ID = arguments.getString(EXTRA_MEMBER_DM_ID);
            mMemberStatus = arguments.getInt(EXTRA_MEMBER_STATUS);
            mFullNameValue = arguments.getString(EXTRA_MEMBER_FULL_NAME);
            mLocationValue = arguments.getString(EXTRA_MEMBER_LOCATION);
            mProfessionValue = arguments.getString(EXTRA_MEMBER_PROFESSION);
            mStatusUpdateValue = arguments.getString(EXTRA_MEMBER_STATUS_UPDATE);
            mAboutMeValue = arguments.getString(EXTRA_MEMBER_ABOUT_ME);
            mTwitterLinkValue = arguments.getString(EXTRA_MEMBER_TWITTER);
            mFacebookLinkValue = arguments.getString(EXTRA_MEMBER_FACEBOOK);
            mLinkedinLinkValue = arguments.getString(EXTRA_MEMBER_LINKEDIN);
            mInstagramLinkValue = arguments.getString(EXTRA_MEMBER_INSTAGRAM);
            mImageURLValue = arguments.getString(EXTRA_MEMBER_PROFILE_PICTURE);
            mCompanyName = arguments.getString(EXTRA_MEMBER_COMPANY_NAME);
        }
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        mOffsetChangeListenerAdapter = new SimpleOffsetChangeListenerAdapter(mToolbar);
        mOffsetChangeListenerAdapter.setOffsetChangeListener(this);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext(), TITLES);

        adapter.addVieBinder(mViewBinder1 = new AboutViewBinder(new MemberInfoListAdapter(LayoutInflater.from(getContext()))));

        LayoutInflater layoutInflater = getIHLayoutInflater();

        ProjectsLisAdapter lisAdapter = new ProjectsLisAdapter(layoutInflater);
        lisAdapter.setItemClickListener(new OnListItemClickListener<ProjectVO>() {
            @Override
            public void onItemClick(int viewId, ProjectVO model, int position) {
                addChildFragment(ProjectDetailFragment.newInstance(model), "FRAG_PROJECT_DETAIL");
            }
        });

        adapter.addVieBinder(mViewBinder2 = new ProjectsViewBinder(lisAdapter));

        GroupsListAdapter groupsListAdapter = new GroupsListAdapter(layoutInflater);
        groupsListAdapter.setItemClickListener(new OnListItemClickListener<GroupVO>() {
            @Override
            public void onItemClick(int viewId, GroupVO model, int position) {
                addChildFragment(GroupDetailFragment.newInstance(model), "FRAG_GROUP_DETAIL");
            }
        });
        adapter.addVieBinder(mViewBinder3 = new GroupsViewBinder(groupsListAdapter));

        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(adapter.getCount());

        mDetailsTab.setupWithViewPager(mPager);

        if(mLoadMember) {
            getPresenter().getMemberBy(mUserIDValue);
        } else {
            bindMemberInfo();
        }
    }

    private void bindMemberInfo() {
        setUpToolbar(mFullNameValue);
        mLocation.setText(mLocationValue);
        mProfession.setText(String.format("%s at %s", mProfessionValue, mCompanyName));

        mStatusUpdate.setText(mStatusUpdateValue);

        String twitterFullLink = mTwitterLinkValue;
        if (twitterFullLink != null) {
            twitterFullLink = "https://twitter.com/"+ twitterFullLink.replace("@", "");
        }

        Pair<String, ImageButton> twitterPair = new Pair<>(twitterFullLink, mButtonTwitter);
        Pair<String, ImageButton> facebookPair = new Pair<>(mFacebookLinkValue, mButtonFacebook);
        Pair<String, ImageButton> linkedinPair = new Pair<>(mLinkedinLinkValue, mButtonLinkedin);
        Pair<String, ImageButton> instagramPair = new Pair<>(mInstagramLinkValue, mButtonInsta);

        new DetailScreenDelegate().handleButtons(twitterPair, facebookPair, linkedinPair, instagramPair);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(mImageURLValue), mImageDetail);

        getPresenter().loadDetails(mContactIDValue, mAboutMeValue);

        MemberStatusType statusType = MemberStatusType.fromStatus(mMemberStatus);

        switch (statusType) {
            case APPROVE_DECLINE:
                mMemberStatusContainer.removeAllViews();
                View approveDeclineView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout_approve_decline_member, mMemberStatusContainer, false);
                approveDeclineView.findViewById(R.id.button_approve_member).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        approveMember();
                    }
                });
                approveDeclineView.findViewById(R.id.button_decline_member).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        declineMember();
                    }
                });
                mMemberStatusContainer.addView(approveDeclineView);
                break;
            case APPROVED:
                setUpConnectMemberButton();
                break;
            case OUTSTANDING:
                setUpOutstandingView();
                break;
            case NOT_CONTACTED:
                mMemberStatusContainer.removeAllViews();
                TextView connectView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_layout_connect_member, mMemberStatusContainer, false);
                connectView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        connectMember();
                    }
                });
                mMemberStatusContainer.addView(connectView);
                break;
        }
    }

    private void declineMember() {
        getPresenter().updateContactRequest(mDM_ID, mUserIDValue, "Declined", 1);
    }

    private void approveMember() {
        getPresenter().updateContactRequest(mDM_ID, mUserIDValue, "Approved", 0);
    }

    private void setUpOutstandingView() {
        mMemberStatusContainer.removeAllViews();
        mMemberStatusContainer.addView(LayoutInflater.from(getContext()).inflate(R.layout.item_layout_awaiting_response_member, mMemberStatusContainer, false));
    }

    private void setUpConnectMemberButton() {
        mMemberStatusContainer.removeAllViews();
        TextView contactView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_layout_contact_member, mMemberStatusContainer, false);
        contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactMember();
            }
        });
        mMemberStatusContainer.addView(contactView);
    }

    private void contactMember() {
        ConversationVO model = new ConversationVO();
        model.mDisplayName = mFullNameValue;
        model.mImageURL = mImageURLValue;
        model.mRecipientUserId = mUserIDValue;
        addChildFragment(ConversationFragment.newInstance(model), "FRAG_MESSAGE_THREAD");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppBar.addOnOffsetChangedListener(mOffsetChangeListenerAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1122 && resultCode == Activity.RESULT_OK) {
            mMemberStatus = MemberStatus.OUTSTANDING;
            setUpOutstandingView();
            reLoadMembersList();
        }
    }

    @Override
    public void onDestroyView() {
        mAppBar.removeOnOffsetChangedListener(mOffsetChangeListenerAdapter);
        super.onDestroyView();
    }

    @Override
    public void onLoadProjects(List<ProjectVO> projectDTOs) {
        mViewBinder2.bindView(projectDTOs);
    }

    @Override
    public void onLoadGroups(List<GroupVO> groupDTOs) {
        mViewBinder3.bindView(groupDTOs);
    }

    @Override
    public void onLoadExtraInfo(List<ListItemType> listItemTypes) {
        mViewBinder1.bindView(listItemTypes);
    }

    @Override
    public void onMemberApproved() {
        mMemberStatus = MemberStatus.APPROVED;
        setUpConnectMemberButton();
        reLoadMembersList();
    }

    private void reLoadMembersList() {
        handleMemberStatusToolbarMenu();
        List<Refreshable> refreshables = UIRefreshManager.getInstance().getRefreshables(UIRefreshManager.REFRESH_ID_MEMBERS_LIST);
        if (refreshables != null) {
            for (Refreshable refreshable : refreshables) {
                refreshable.onRefresh();
            }
        }
    }

    @Override
    public void onMemberDeclined() {
        mMemberStatus = MemberStatus.DECLINED;
        mMemberStatusContainer.removeAllViews();
        reLoadMembersList();
    }

    @Override
    public void onLoadMember(MemberVO member) {
        mContactIDValue = member.mContactId;
        mDM_ID = member.mDM_ID;
        mMemberStatus = member.mMemberStatus;
        mFullNameValue = member.mFullName;
        mLocationValue = member.mLocation;
        mProfessionValue = member.mProfession;
        mStatusUpdateValue = member.mStatusUpdate;
        mAboutMeValue = member.mAboutMe;
        mTwitterLinkValue = member.mLinkTwitter;
        mFacebookLinkValue = member.mLinkFacebook;
        mLinkedinLinkValue = member.mLinkLinkedin;
        mInstagramLinkValue =  member.mLinkInstagram;
        mImageURLValue = member.mProfilePicURL;
        mCompanyName = member.mCompanyName;
        bindMemberInfo();
    }

    @Override
    public void onExpanded(int verticalOffset) {
        //showToast("Expanded");
        mToolbar.getMenu().clear();
    }

    @Override
    public void onCollapsed(int verticalOffset) {
//        showToast("Collapsed");
        handleMemberStatusToolbarMenu();
    }

    private void handleMemberStatusToolbarMenu() {
        mToolbar.getMenu().clear();
        MemberStatusType statusType = MemberStatusType.fromStatus(mMemberStatus);
        switch (statusType) {
            case APPROVED:
                mToolbar.inflateMenu(R.menu.menu_member_contact);
                mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.actionContactMember:
                                contactMember();
                                return true;
                        }
                        return false;
                    }
                });
                break;
            case APPROVE_DECLINE:
                mToolbar.inflateMenu(R.menu.menu_member_approve_decline);
                mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.actionApproveContact:
                                approveMember();
                                return true;
                            case R.id.actionDeclineContact:
                                declineMember();
                                return true;
                        }
                        return false;
                    }
                });
                break;
            case NOT_CONTACTED:
                mToolbar.inflateMenu(R.menu.menu_member_connect);
                mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.actionRequestContact:
                                connectMember();
                                return true;
                        }
                        return false;
                    }
                });
                break;
        }
    }

    private void connectMember() {
        Intent intent = new Intent(getActivity(), ConnectMemberActivity.class);
        intent.putExtra(ConnectMemberActivity.EXTRA_CONTACT_CONNECT_ID, mContactIDValue);
        startActivityForResult(intent, 1122);
    }
}
