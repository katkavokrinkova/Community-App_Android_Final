package net.impacthub.members.ui.features.home.members;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.callback.OnTabVisibilityChangeListener;
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.model.pojo.ListItem;
import net.impacthub.members.navigator.Navigator;
import net.impacthub.members.presenter.features.members.MemberDetailPresenter;
import net.impacthub.members.presenter.features.members.MemberDetailUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.SimpleOffsetChangeListenerAdapter;
import net.impacthub.members.ui.delegate.DetailScreenDelegate;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.groups.binders.GroupsViewBinder;
import net.impacthub.members.ui.features.home.members.binders.AboutViewBinder;
import net.impacthub.members.ui.features.home.projects.ProjectDetailFragment;
import net.impacthub.members.ui.features.home.projects.binders.ProjectsViewBinder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class MemberDetailFragment extends BaseChildFragment<MemberDetailPresenter> implements MemberDetailUiContract {

    private static final String TAG = MemberDetailFragment.class.getSimpleName();

    public static final String TITLES[] = {"ABOUT", "PROJECTS", "GROUPS"};

    public static final String EXTRA_MEMBER_PROFILE_PICTURE = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_PROFILE_PICTURE";
    public static final String EXTRA_MEMBER_ID = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_ID";
    public static final String EXTRA_MEMBER_INSTAGRAM = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_INSTAGRAM";
    public static final String EXTRA_MEMBER_FACEBOOK = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_FACEBOOK";
    public static final String EXTRA_MEMBER_TWITTER = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_TWITTER";
    public static final String EXTRA_MEMBER_LINKEDIN = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_LINKEDIN";
    public static final String EXTRA_MEMBER_FULL_NAME = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_FULL_NAME";
    public static final String EXTRA_MEMBER_LOCATION = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_LOCATION";
    public static final String EXTRA_MEMBER_ABOUT_ME = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_ABOUT_ME";
    public static final String EXTRA_MEMBER_PROFESSION = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_PROFESSION";

    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBar;
    @BindView(R.id.tabs) protected TabLayout mDetailsTab;
    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.container_connect) protected View mConnectContainer;
//    @BindView(R.id.text_full_name) protected TextView mFullName;
    @BindView(R.id.locations) protected TextView mLocation;
    @BindView(R.id.text_profession) protected TextView mProfession;
    @BindView(R.id.about) protected TextView mAboutMe;
//
    @BindView(R.id.button_twitter) protected ImageButton mButtonTwitter;
    @BindView(R.id.button_facebook) protected ImageButton mButtonFacebook;
    @BindView(R.id.button_linkedin) protected ImageButton mButtonLinkedin;
    @BindView(R.id.button_instagram) protected ImageButton mButtonInsta;

    @BindView(R.id.pager) protected ViewPager mPager;
//    @BindView(R.id.done) protected TypefaceTextView mDone;

    private AppPagerAdapter mPagerAdapter;
    private final DetailScreenDelegate mScreenDelegate = new DetailScreenDelegate();

    private String mLinkTwitter;
    private String mLinkFacebook;
    private String mLinkLinkedin;
    private String mLinkInsta;
    private String mAbout;

    public static MemberDetailFragment newInstance(MemberDTO member) {

        Bundle args = new Bundle();
        args.putString(EXTRA_MEMBER_ID, member.mMemberId);
        args.putString(EXTRA_MEMBER_PROFILE_PICTURE, member.mProfilePicURL);
        args.putString(EXTRA_MEMBER_INSTAGRAM, member.mLinkInstagram);
        args.putString(EXTRA_MEMBER_FACEBOOK, member.mLinkFacebook);
        args.putString(EXTRA_MEMBER_TWITTER, member.mLinkTwitter);
        args.putString(EXTRA_MEMBER_LINKEDIN, member.mLinkLinkedin);
        args.putString(EXTRA_MEMBER_FULL_NAME, member.mFullName);
        args.putString(EXTRA_MEMBER_LOCATION, member.mLocation);
        args.putString(EXTRA_MEMBER_ABOUT_ME, member.mAboutMe);
        args.putString(EXTRA_MEMBER_PROFESSION, member.mProfession);
        MemberDetailFragment fragment = new MemberDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MemberDetailPresenter onCreatePresenter() {
        return new MemberDetailPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_member_detail;
    }

    @OnClick(R.id.button_twitter)
    protected void onTwitterClicked() {
        String link = "https://twitter.com/"+ mLinkTwitter.replace("@", "");
        Navigator.startOtherWebActivity(getContext(), link);
    }

    @OnClick(R.id.button_facebook)
    protected void onFacebookClicked() {
        Navigator.startOtherWebActivity(getContext(), mLinkFacebook);
    }

    @OnClick(R.id.button_linkedin)
    protected void onLinkedinClicked() {
        Navigator.startOtherWebActivity(getContext(), mLinkLinkedin);
    }

    @OnClick(R.id.button_instagram)
    protected void onInstaClicked() {
        Navigator.startOtherWebActivity(getContext(), mLinkInsta);
    }

//    @OnClick(R.id.done)
//    protected void onDone(){
//        showToast("Just Done!");
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        String memberId = arguments.getString(EXTRA_MEMBER_ID);
        String profilePic = arguments.getString(EXTRA_MEMBER_PROFILE_PICTURE);

        String fullName = arguments.getString(EXTRA_MEMBER_FULL_NAME);

        mLocation.setText(arguments.getString(EXTRA_MEMBER_LOCATION));
        mProfession.setText(arguments.getString(EXTRA_MEMBER_PROFESSION));
        mAbout = arguments.getString(EXTRA_MEMBER_ABOUT_ME);
        mAboutMe.setText(mAbout);

        setUpToolbar(fullName);
        mToolbar.inflateMenu(R.menu.menu_member_connect);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionRequestContact:
                        showToast("Request Contact!");
                        return true;
                }
                return false;
            }
        });


        mLinkTwitter = arguments.getString(EXTRA_MEMBER_TWITTER);
        mLinkFacebook = arguments.getString(EXTRA_MEMBER_FACEBOOK);
        mLinkLinkedin = arguments.getString(EXTRA_MEMBER_LINKEDIN);
        mLinkInsta = arguments.getString(EXTRA_MEMBER_INSTAGRAM);

        Pair<String, ImageButton> twitterPair = new Pair<>(mLinkTwitter, mButtonTwitter);
        Pair<String, ImageButton> facebookPair = new Pair<>(mLinkFacebook, mButtonFacebook);
        Pair<String, ImageButton> linkedinPair = new Pair<>(mLinkLinkedin, mButtonLinkedin);
        Pair<String, ImageButton> instagramPair = new Pair<>(mLinkInsta, mButtonInsta);

        mScreenDelegate.handleButtons(twitterPair, facebookPair, linkedinPair, instagramPair);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(profilePic), mImageDetail);

        mAppBar.addOnOffsetChangedListener(mOffsetChangeListenerAdapter);

        mPagerAdapter = new AppPagerAdapter(getContext());
        mPagerAdapter.addVieBinder(new AboutViewBinder());
        mPagerAdapter.addVieBinder(new ProjectsViewBinder(new OnListItemClickListener<ProjectDTO>() {
            @Override
            public void onItemClick(ProjectDTO model) {
                addChildFragment(ProjectDetailFragment.newInstance(model), "FRAG_PROJECT_DETAIL");
            }
        }));
        mPagerAdapter.addVieBinder(new GroupsViewBinder(new OnListItemClickListener<GroupDTO>() {
            @Override
            public void onItemClick(GroupDTO model) {
                showToast("Hello group");
            }
        }));

        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(mPagerAdapter.getCount());

        mDetailsTab.setupWithViewPager(mPager);

        new TabsDelegate().setUp(mDetailsTab, TITLES);

        getPresenter().loadDetails(memberId);
    }

    @Override
    public void onPause() {
        OnTabVisibilityChangeListener listener = (OnTabVisibilityChangeListener) getActivity();
        listener.onReset();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        mAppBar.removeOnOffsetChangedListener(mOffsetChangeListenerAdapter);
        super.onDestroyView();
    }

    @Override
    public void onLoadProjects(List<ProjectDTO> projectDTOs) {
        if (mPagerAdapter != null) {
            ViewBinder viewBinder = mPagerAdapter.getItemAt(1);
            if (viewBinder != null) {
                viewBinder.bindView(projectDTOs);
            }
        }
    }

    @Override
    public void onLoadGroups(List<GroupDTO> groupDTOs) {
        if (mPagerAdapter != null) {
            ViewBinder viewBinder = mPagerAdapter.getItemAt(2);
            if (viewBinder != null) {
                viewBinder.bindView(groupDTOs);
            }
        }
    }

    @Override
    public void onLoadExtraInfo(List<ListItem<?>> infoList) {
        if (mPagerAdapter != null) {
            ViewBinder viewBinder = mPagerAdapter.getItemAt(0);
            if (viewBinder != null) {

                ListItem<String> titleAbout = new ListItem<>(ListItem.TYPE_ONE);
                titleAbout.setModel("About");
                infoList.add(0, titleAbout);

                ListItem<String> descriptionAbout = new ListItem<>(ListItem.TYPE_TWO);
                descriptionAbout.setModel(mAbout);
                infoList.add(1, descriptionAbout);

                viewBinder.bindView(infoList);
            }
        }
    }

    private final SimpleOffsetChangeListenerAdapter mOffsetChangeListenerAdapter = new SimpleOffsetChangeListenerAdapter(){
        @Override
        protected void onExpanded(int verticalOffset) {
            mToolbar.getMenu().findItem(R.id.actionRequestContact).setVisible(false);
        }

        @Override
        protected void onCollapsed(int verticalOffset) {
            mToolbar.getMenu().findItem(R.id.actionRequestContact).setVisible(true);
        }
    };
}
