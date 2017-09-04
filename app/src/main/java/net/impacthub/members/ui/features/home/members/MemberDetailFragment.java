package net.impacthub.members.ui.features.home.members;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.vo.conversations.ConversationVO;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.model.vo.members.MemberStatusType;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.presenter.features.members.MemberDetailUiContract;
import net.impacthub.members.presenter.features.members.MemberDetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.SimpleOffsetChangeListenerAdapter;
import net.impacthub.members.ui.common.binders.AboutViewBinder;
import net.impacthub.members.ui.delegate.DetailScreenDelegate;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.groups.GroupDetailFragment;
import net.impacthub.members.ui.features.home.groups.binders.GroupsViewBinder;
import net.impacthub.members.ui.features.home.members.binders.MemberInfoListAdapter;
import net.impacthub.members.ui.features.home.projects.ProjectDetailFragment;
import net.impacthub.members.ui.features.home.projects.binders.ProjectsViewBinder;
import net.impacthub.members.ui.features.messages.conversation.ConversationFragment;
import net.impacthub.members.ui.modal.ModalActivity;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class MemberDetailFragment extends BaseChildFragment<MemberDetailUiPresenter> implements MemberDetailUiContract {

    private static final String TAG = MemberDetailFragment.class.getSimpleName();

    public static final String TITLES[] = {"ABOUT", "PROJECTS", "GROUPS"};

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

    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBar;
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

    private ViewBinder<List<ListItemType>> mViewBinder1;
    private ViewBinder<List<ProjectVO>> mViewBinder2;
    private ViewBinder<List<GroupVO>> mViewBinder3;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        mUserIDValue = arguments.getString(EXTRA_MEMBER_USER_ID);
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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mToolbar.inflateMenu(R.menu.menu_member_connect);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.actionRequestContact:
//                        showToast("Request Contact!");
//                        return true;
//                }
//                return false;
//            }
//        });

        mAppBar.addOnOffsetChangedListener(mOffsetChangeListenerAdapter);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext());

        adapter.addVieBinder(mViewBinder1 = new AboutViewBinder(new MemberInfoListAdapter(LayoutInflater.from(getContext()))));
        adapter.addVieBinder(mViewBinder2 = new ProjectsViewBinder(new OnListItemClickListener<ProjectVO>() {
            @Override
            public void onItemClick(int viewId, ProjectVO model) {
                addChildFragment(ProjectDetailFragment.newInstance(model), "FRAG_PROJECT_DETAIL");
            }
        }));
        adapter.addVieBinder(mViewBinder3 = new GroupsViewBinder(new OnListItemClickListener<GroupVO>() {
            @Override
            public void onItemClick(int viewId, GroupVO model) {
                addChildFragment(GroupDetailFragment.newInstance(model), "FRAG_GROUP_DETAIL");
            }
        }));

        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(adapter.getCount());

        mDetailsTab.setupWithViewPager(mPager);

        new TabsDelegate().setUp(mDetailsTab, TITLES);

        setUpToolbar(mFullNameValue);
        mLocation.setText(mLocationValue);
        mProfession.setText(mProfessionValue);

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
                        getPresenter().updateContactRequest(mDM_ID, mUserIDValue, "Approved", 0);
                    }
                });
                approveDeclineView.findViewById(R.id.button_decline_member).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getPresenter().updateContactRequest(mDM_ID, mUserIDValue, "Declined", 1);
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
                connectView.setText("Connect " + mFullNameValue);
                connectView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ModalActivity.class);
                        intent.putExtra(ModalActivity.MODAL_TYPE_CONNECT, true);
                        intent.putExtra(ModalActivity.EXTRA_CONTACT_ID, mContactIDValue);
                        startActivityForResult(intent, 1122);
                    }
                });
                mMemberStatusContainer.addView(connectView);
                break;
        }
    }

    private void setUpOutstandingView() {
        mMemberStatusContainer.removeAllViews();
        mMemberStatusContainer.addView(LayoutInflater.from(getContext()).inflate(R.layout.item_layout_awaiting_response_member, mMemberStatusContainer, false));
    }

    private void setUpConnectMemberButton() {
        mMemberStatusContainer.removeAllViews();
        TextView contactView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_layout_contact_member, mMemberStatusContainer, false);
        contactView.setText("Contact " + mFullNameValue);
        contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversationVO model = new ConversationVO();
                model.mDisplayName = mFullNameValue;
                model.mImageURL = mImageURLValue;
                model.mRecipientUserId = mUserIDValue;
                addChildFragment(ConversationFragment.newInstance(model), "FRAG_MESSAGE_THREAD");
            }
        });
        mMemberStatusContainer.addView(contactView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1122 && resultCode == Activity.RESULT_OK) {
            setUpOutstandingView();
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
        setUpConnectMemberButton();
    }

    @Override
    public void onMemberDeclined() {
        mMemberStatusContainer.removeAllViews();
    }

    private final SimpleOffsetChangeListenerAdapter mOffsetChangeListenerAdapter = new SimpleOffsetChangeListenerAdapter(){
        @Override
        protected void onExpanded(int verticalOffset) {
            //mToolbar.getMenu().findItem(R.id.actionRequestContact).setVisible(false);
        }

        @Override
        protected void onCollapsed(int verticalOffset) {
            //mToolbar.getMenu().findItem(R.id.actionRequestContact).setVisible(true);
        }
    };
}
