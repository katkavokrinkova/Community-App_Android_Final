package net.impacthub.members.ui.features.home.members;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.callback.OnTabVisibilityChangeListener;
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.model.features.members.Member;
import net.impacthub.members.model.pojo.ListItem;
import net.impacthub.members.navigator.Navigator;
import net.impacthub.members.presenter.features.members.MemberDetailPresenter;
import net.impacthub.members.presenter.features.members.MemberDetailUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.features.home.members.binders.about.AboutViewBinder;
import net.impacthub.members.ui.features.home.groups.binders.GroupsViewBinder;
import net.impacthub.members.ui.features.home.projects.binders.ProjectsViewBinder;
import net.impacthub.members.utilities.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class MemberDetailFragment extends BaseChildFragment<MemberDetailPresenter> implements MemberDetailUiContract {

    private static final String TAG = MemberDetailFragment.class.getSimpleName();

    private final UserAccount mUserAccount = userAccountProvider();

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
//    @BindView(R.id.location) protected TextView mLocation;
//    @BindView(R.id.text_profession) protected TextView mProfession;
//    @BindView(R.id.about) protected TextView mAboutMe;
//
//    @BindView(R.id.button_twitter) protected ImageButton mButtonTwitter;
//    @BindView(R.id.button_facebook) protected ImageButton mButtonFacebook;
//    @BindView(R.id.button_linkedin) protected ImageButton mButtonLinkedin;
//    @BindView(R.id.button_instagram) protected ImageButton mButtonInsta;

    @BindView(R.id.view_pager) protected ViewPager mPager;
//    @BindView(R.id.done) protected TypefaceTextView mDone;

    private AppPagerAdapter mPagerAdapter;

    private String mLinkTwitter;
    private String mLinkFacebook;
    private String mLinkLinkedin;
    private String mLinkInsta;
    private String mAbout;

    public static MemberDetailFragment newInstance(Member member) {

        Bundle args = new Bundle();
        args.putString(EXTRA_MEMBER_PROFILE_PICTURE, member.getProfilePic());
        args.putString(EXTRA_MEMBER_ID, member.getId());
        args.putString(EXTRA_MEMBER_INSTAGRAM, member.getInstagram());
        args.putString(EXTRA_MEMBER_FACEBOOK, member.getFacebook());
        args.putString(EXTRA_MEMBER_TWITTER, member.getTwitter());
        args.putString(EXTRA_MEMBER_LINKEDIN, member.getLinkedIn());
        args.putString(EXTRA_MEMBER_FULL_NAME, member.getFirstName() + " " + member.getLastName());
        args.putString(EXTRA_MEMBER_LOCATION, member.getImpactHubCities());
        args.putString(EXTRA_MEMBER_ABOUT_ME, member.getAboutMe());
        args.putString(EXTRA_MEMBER_PROFESSION, member.getProfession());
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
        return R.layout.fragment_category_detail_pager;
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
//
//    @OnClick(R.id.image_arrow_collapse_content)
//    protected void onCollapse() {
//        mAppBar.setExpanded(false, true);
//    }

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
//        mFullName.setText(fullName);
//        mLocation.setText(arguments.getString(EXTRA_MEMBER_LOCATION));
//        mProfession.setText(arguments.getString(EXTRA_MEMBER_PROFESSION));
        mAbout = arguments.getString(EXTRA_MEMBER_ABOUT_ME);
//        mAboutMe.setText(mAbout);

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

//        if ((mLinkTwitter != null && mLinkTwitter.isEmpty()) || mLinkTwitter == null ) {
//            ViewUtils.gone(mButtonTwitter);
//        }
//
//        if ((mLinkFacebook != null && mLinkFacebook.isEmpty()) || mLinkFacebook == null ) {
//            ViewUtils.gone(mButtonFacebook);
//        }
//
//        if ((mLinkLinkedin != null && mLinkLinkedin.isEmpty()) || mLinkLinkedin == null ) {
//            ViewUtils.gone(mButtonLinkedin);
//        }
//
//        if ((mLinkInsta != null && mLinkInsta.isEmpty()) || mLinkInsta == null ) {
//            ViewUtils.gone(mButtonInsta);
//        }

        ImageLoaderHelper.loadImage(getContext(), profilePic + "?oauth_token=" + mUserAccount.getAuthToken(), mImageDetail);

        mAppBar.addOnOffsetChangedListener(mOffsetChangedListener);

//        mAppBar.setExpanded(true);

        if (mPagerAdapter == null) {
            mPagerAdapter = new AppPagerAdapter(getContext());
            mPagerAdapter.addVieBinder(new AboutViewBinder());
            mPagerAdapter.addVieBinder(new ProjectsViewBinder(new OnListItemClickListener<ProjectDTO>() {
                @Override
                public void onItemClick(ProjectDTO model) {
                    showToast("Hello project!!!");
                }
            }));
            mPagerAdapter.addVieBinder(new GroupsViewBinder(new OnListItemClickListener<GroupDTO>() {
                @Override
                public void onItemClick(GroupDTO model) {
                    showToast("Hello group");
                }
            }));
        }

        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(mPagerAdapter.getCount());

        mDetailsTab.setupWithViewPager(mPager);

        for (int i = 0; i < mDetailsTab.getTabCount(); i++) {
            TabLayout.Tab tabAt = mDetailsTab.getTabAt(i);
            if (tabAt != null) {
                tabAt.setCustomView(createTabTitle(TITLES[i]));
            }
        }

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
        mAppBar.removeOnOffsetChangedListener(mOffsetChangedListener);
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

    private State state = State.IDLE;

    AppBarLayout.OnOffsetChangedListener mOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            if (verticalOffset == 0) {
                if (state != State.EXPANDED) {
                    state = State.EXPANDED;
                    handleState();
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != State.COLLAPSED) {
                    state = State.COLLAPSED;
                    handleState();
                }
            } else {
                if (state != State.IDLE) {
                    state = State.IDLE;
                    handleState();
                }
            }

//            if (mAppBar != null && mToolbar != null) {
//                int absoluteVerticalOffset = Math.abs(verticalOffset);
//                int totalScrollRange = mAppBar.getTotalScrollRange();
//                int height = mToolbar.getHeight();
//                float toolbarTranslationOffset = Math.abs((absoluteVerticalOffset * (height * 1.0f / totalScrollRange)) - height);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
//                params.topMargin = (int) -toolbarTranslationOffset;
//                mToolbar.setLayoutParams(params);
//
//                OnTabVisibilityChangeListener listener = (OnTabVisibilityChangeListener) getActivity();
//                listener.onVisibilityChanged(absoluteVerticalOffset, totalScrollRange);
//            }
        }
    };

    private void handleState() {
        switch (state) {
            case COLLAPSED:
                mToolbar.getMenu().findItem(R.id.actionRequestContact).setVisible(true);
                break;
            case EXPANDED:
                mToolbar.getMenu().findItem(R.id.actionRequestContact).setVisible(false);
                break;
        }
    }

    private enum State {
        COLLAPSED,
        EXPANDED,
        IDLE
    }
}
