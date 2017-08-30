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
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.pojo.SimpleItem;
import net.impacthub.members.model.vo.groups.GroupVO;
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

    public static final String EXTRA_MEMBER_PROFILE_PICTURE = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_PROFILE_PICTURE";
    public static final String EXTRA_MEMBER_ID = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_ID";
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
    @BindView(R.id.container_connect) protected View mConnectContainer;
//    @BindView(R.id.text_full_name) protected TextView mFullName;
    @BindView(R.id.locations) protected TextView mLocation;
    @BindView(R.id.text_profession) protected TextView mProfession;
    @BindView(R.id.text_status_update) protected TextView mStatusUpdate;
//
    @BindView(R.id.button_twitter) protected ImageButton mButtonTwitter;
    @BindView(R.id.button_facebook) protected ImageButton mButtonFacebook;
    @BindView(R.id.button_linkedin) protected ImageButton mButtonLinkedin;
    @BindView(R.id.button_instagram) protected ImageButton mButtonInsta;

    @BindView(R.id.pager) protected ViewPager mPager;
//    @BindView(R.id.done) protected TypefaceTextView mDone;

    private AppPagerAdapter mPagerAdapter;

    public static MemberDetailFragment newInstance(MemberVO member) {

        Bundle args = new Bundle();
        args.putString(EXTRA_MEMBER_ID, member.mContactId);
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

        mStatusUpdate.setText(arguments.getString(EXTRA_MEMBER_STATUS_UPDATE));

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

        String twitterFullLink = arguments.getString(EXTRA_MEMBER_TWITTER);
        if (twitterFullLink != null) {
            twitterFullLink = "https://twitter.com/"+ twitterFullLink.replace("@", "");
        }

        Pair<String, ImageButton> twitterPair = new Pair<>(twitterFullLink, mButtonTwitter);
        Pair<String, ImageButton> facebookPair = new Pair<>(arguments.getString(EXTRA_MEMBER_FACEBOOK), mButtonFacebook);
        Pair<String, ImageButton> linkedinPair = new Pair<>(arguments.getString(EXTRA_MEMBER_LINKEDIN), mButtonLinkedin);
        Pair<String, ImageButton> instagramPair = new Pair<>(arguments.getString(EXTRA_MEMBER_INSTAGRAM), mButtonInsta);

        new DetailScreenDelegate().handleButtons(twitterPair, facebookPair, linkedinPair, instagramPair);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(profilePic), mImageDetail);

        mAppBar.addOnOffsetChangedListener(mOffsetChangeListenerAdapter);

        mPagerAdapter = new AppPagerAdapter(getContext());
        mPagerAdapter.addVieBinder(new AboutViewBinder(new MemberInfoListAdapter(getLayoutInflater(getArguments()))));
        mPagerAdapter.addVieBinder(new ProjectsViewBinder(new OnListItemClickListener<ProjectVO>() {
            @Override
            public void onItemClick(int viewId, ProjectVO model) {
                addChildFragment(ProjectDetailFragment.newInstance(model), "FRAG_PROJECT_DETAIL");
            }
        }));
        mPagerAdapter.addVieBinder(new GroupsViewBinder(new OnListItemClickListener<GroupVO>() {
            @Override
            public void onItemClick(int viewId, GroupVO model) {
                addChildFragment(GroupDetailFragment.newInstance(model), "FRAG_GROUP_DETAIL");
            }
        }));

        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(mPagerAdapter.getCount());

        mDetailsTab.setupWithViewPager(mPager);

        new TabsDelegate().setUp(mDetailsTab, TITLES);

        getPresenter().loadDetails(memberId);
    }

    @Override
    public void onDestroyView() {
        mAppBar.removeOnOffsetChangedListener(mOffsetChangeListenerAdapter);
        super.onDestroyView();
    }

    @Override
    public void onLoadProjects(List<ProjectVO> projectDTOs) {
        if (mPagerAdapter != null) {
            ViewBinder viewBinder = mPagerAdapter.getItemAt(1);
            if (viewBinder != null) {
                viewBinder.bindView(projectDTOs);
            }
        }
    }

    @Override
    public void onLoadGroups(List<GroupVO> groupDTOs) {
        if (mPagerAdapter != null) {
            ViewBinder viewBinder = mPagerAdapter.getItemAt(2);
            if (viewBinder != null) {
                viewBinder.bindView(groupDTOs);
            }
        }
    }

    @Override
    public void onLoadExtraInfo(List<ListItemType> listItemTypes) {
        if (mPagerAdapter != null) {
            ViewBinder viewBinder = mPagerAdapter.getItemAt(0);
            if (viewBinder != null) {

                listItemTypes.add(0, new SimpleItem<String>("About", 0));
                listItemTypes.add(1, new SimpleItem<String>(getArguments().getString(EXTRA_MEMBER_ABOUT_ME), 1));

                viewBinder.bindView(listItemTypes);
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
