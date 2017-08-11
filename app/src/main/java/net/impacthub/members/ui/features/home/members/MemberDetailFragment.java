package net.impacthub.members.ui.features.home.members;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnTabVisibilityChangeListener;
import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.dto.members.MemberProjectDTO;
import net.impacthub.members.model.features.members.Member;
import net.impacthub.members.presenter.features.members.MemberDetailPresenter;
import net.impacthub.members.presenter.features.members.MemberDetailUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.features.home.members.binders.about.AboutViewBinder;
import net.impacthub.members.ui.features.home.members.binders.project.GroupsViewBinder;
import net.impacthub.members.ui.features.home.members.binders.project.ProjectsViewBinder;
import net.impacthub.members.ui.widgets.TypefaceTextView;
import net.impacthub.members.utilities.ViewUtils;

import java.util.LinkedList;
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

    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBar;
    @BindView(R.id.tabs) protected TabLayout mDetailsTab;
    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.view_pager) protected ViewPager mPager;
    @BindView(R.id.done) protected TypefaceTextView mDone;

    private MembersDetailPagerAdapter mPagerAdapter;

    public static MemberDetailFragment newInstance(Member member) {

        Bundle args = new Bundle();
        args.putString(EXTRA_MEMBER_PROFILE_PICTURE, member.getProfilePic());
        args.putString(EXTRA_MEMBER_ID, member.getId());
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

    @OnClick(R.id.image_arrow_collapse_content)
    protected void onCollapse() {
        mAppBar.setExpanded(false, true);
    }

    @OnClick(R.id.done)
    protected void onDone(){
        showToast("Just Done!");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.member_image);

//        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("ABOUT")));
//        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("PROJECTS")));
//        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("GROUPS")));

        Bundle arguments = getArguments();
        String memberId = arguments.getString(EXTRA_MEMBER_ID);
        String profilePic = arguments.getString(EXTRA_MEMBER_PROFILE_PICTURE);

        ImageLoaderHelper.loadImage(getContext(), profilePic + "?oauth_token=" + mUserAccount.getAuthToken(), mImageDetail);

        //ImageLoaderHelper.loadImage(getContext(), "http://cdn.wallpapersafari.com/13/28/EcwOC1.jpg", mImageDetail);

        mAppBar.addOnOffsetChangedListener(mOffsetChangedListener);
        mAppBar.setExpanded(true);

        mPagerAdapter = new MembersDetailPagerAdapter();
        mPagerAdapter.addVieBinder(new AboutViewBinder("About"));
        mPagerAdapter.addVieBinder(new ProjectsViewBinder());
        mPagerAdapter.addVieBinder(new GroupsViewBinder());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(mPagerAdapter.getCount());

        mDetailsTab.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position > 0) ViewUtils.gone(mDone); else ViewUtils.visible(mDone);
            }
        });

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
    public void onLoadProjects(List<MemberProjectDTO> projectDTOs) {
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

    private class MembersDetailPagerAdapter extends PagerAdapter {

        private final List<ViewBinder> mBindersList = new LinkedList<>();

        public void addVieBinder(ViewBinder viewBinder) {
            mBindersList.add(viewBinder);
            notifyDataSetChanged();
        }

        ViewBinder getItemAt(int position) {
            return mBindersList.get(position);
        }

        @Override
        public int getCount() {
            return mBindersList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mBindersList.get(position).getView(getContext(), getLayoutInflater(getArguments()), position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    AppBarLayout.OnOffsetChangedListener mOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            if (mAppBar != null && mToolbar != null) {
                int absoluteVerticalOffset = Math.abs(verticalOffset);
                int totalScrollRange = mAppBar.getTotalScrollRange();
                int height = mToolbar.getHeight();

                OnTabVisibilityChangeListener listener = (OnTabVisibilityChangeListener) getActivity();
                listener.onVisibilityChanged(absoluteVerticalOffset, totalScrollRange);

                float toolbarTranslationOffset = Math.abs((absoluteVerticalOffset * (height * 1.0f / totalScrollRange)) - height);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
                params.topMargin = (int) -toolbarTranslationOffset;
                mToolbar.setLayoutParams(params);
            }
        }
    };
}
