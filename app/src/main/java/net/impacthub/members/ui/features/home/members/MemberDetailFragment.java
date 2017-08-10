package net.impacthub.members.ui.features.home.members;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnTabVisibilityChangeListener;
import net.impacthub.members.model.features.members.Member;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.widgets.TypefaceTextView;

import butterknife.BindView;
import butterknife.OnClick;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class MemberDetailFragment extends BaseChildFragment {

    private static final String TAG = MemberDetailFragment.class.getSimpleName();

    private final UserAccount mUserAccount = userAccountProvider();

    public static final String EXTRA_PROFILE_PICTURE = "EXTRA_PROFILE_PICTURE";

    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBar;
    @BindView(R.id.tabs) protected TabLayout mDetailsTab;
    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.done) protected TypefaceTextView mDone;

    public static MemberDetailFragment newInstance(Member member) {

        Bundle args = new Bundle();
        args.putString(EXTRA_PROFILE_PICTURE, member.getProfilePic());
        MemberDetailFragment fragment = new MemberDetailFragment();
        fragment.setArguments(args);
        return fragment;
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
        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("ABOUT")));
        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("PROJECTS")));
        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("GROUPS")));

        String profilePic = getArguments().getString(EXTRA_PROFILE_PICTURE);

        ImageLoaderHelper.loadImage(getContext(), profilePic + "?oauth_token=" + mUserAccount.getAuthToken(), mImageDetail);

        //ImageLoaderHelper.loadImage(getContext(), "http://cdn.wallpapersafari.com/13/28/EcwOC1.jpg", mImageDetail);

        mAppBar.addOnOffsetChangedListener(mOffsetChangedListener);
        mAppBar.setExpanded(true);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(!isVisibleToUser) {
//            OnTabVisibilityChangeListener listener = (OnTabVisibilityChangeListener) getActivity();
//            listener.onReset();
//        }
//    }

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
