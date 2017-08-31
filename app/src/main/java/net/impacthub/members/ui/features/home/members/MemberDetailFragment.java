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

    public static final String EXTRA_MEMBER_USER_ID = "net.impacthub.members.ui.features.home.members.EXTRA_MEMBER_USER_ID";

    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBar;
    @BindView(R.id.tabs) protected TabLayout mDetailsTab;
    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.container_connect) protected View mConnectContainer;
    @BindView(R.id.locations) protected TextView mLocation;
    @BindView(R.id.text_profession) protected TextView mProfession;
    @BindView(R.id.text_status_update) protected TextView mStatusUpdate;
//
    @BindView(R.id.button_twitter) protected ImageButton mButtonTwitter;
    @BindView(R.id.button_facebook) protected ImageButton mButtonFacebook;
    @BindView(R.id.button_linkedin) protected ImageButton mButtonLinkedin;
    @BindView(R.id.button_instagram) protected ImageButton mButtonInsta;

    @BindView(R.id.pager) protected ViewPager mPager;
    private AppPagerAdapter mPagerAdapter;

    public static MemberDetailFragment newInstance(String userId) {

        Bundle args = new Bundle();
        args.putString(EXTRA_MEMBER_USER_ID, userId);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_member_detail);
        String userId = getArguments().getString(EXTRA_MEMBER_USER_ID);
        getPresenter().getMember(userId);

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

    }

    @Override
    public void onDestroyView() {
        mAppBar.removeOnOffsetChangedListener(mOffsetChangeListenerAdapter);
        super.onDestroyView();
    }

    @Override
    public void onLoadMember(MemberVO memberVO) {
        getPresenter().loadDetails(memberVO.mContactId, memberVO.mStatusUpdate);

        mLocation.setText(memberVO.mLocation);
        mProfession.setText(memberVO.mProfession);

        mStatusUpdate.setText(memberVO.mStatusUpdate);

        setUpToolbar(memberVO.mFullName);

        String twitterFullLink = memberVO.mLinkTwitter;
        if (twitterFullLink != null) {
            twitterFullLink = "https://twitter.com/"+ twitterFullLink.replace("@", "");
        }

        Pair<String, ImageButton> twitterPair = new Pair<>(twitterFullLink, mButtonTwitter);
        Pair<String, ImageButton> facebookPair = new Pair<>(memberVO.mLinkFacebook, mButtonFacebook);
        Pair<String, ImageButton> linkedinPair = new Pair<>(memberVO.mLinkLinkedin, mButtonLinkedin);
        Pair<String, ImageButton> instagramPair = new Pair<>(memberVO.mLinkInstagram, mButtonInsta);

        new DetailScreenDelegate().handleButtons(twitterPair, facebookPair, linkedinPair, instagramPair);

        ImageLoaderHelper.loadImage(getContext(), buildUrl(memberVO.mProfilePicURL), mImageDetail);
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
