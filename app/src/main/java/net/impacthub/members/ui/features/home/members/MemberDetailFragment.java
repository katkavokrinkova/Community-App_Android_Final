package net.impacthub.members.ui.features.home.members;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnTabVisibilityChangeListener;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.widgets.TypefaceTextView;
import net.impacthub.members.utilities.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Lightful on 03/08/2017.
 */

public class MemberDetailFragment extends BaseChildFragment {

    private static final String TAG = MemberDetailFragment.class.getSimpleName();

    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBar;
    @BindView(R.id.tabs) protected TabLayout mDetailsTab;
    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.done) protected TypefaceTextView mDone;

    private boolean mChanged;
    private boolean mIsExpnaded;

    public static MemberDetailFragment newInstance() {

        Bundle args = new Bundle();

        MemberDetailFragment fragment = new MemberDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_category_detail_pager;
    }

    @OnClick(R.id.done)
    protected void onDone(){
        mIsExpnaded = !mIsExpnaded;
        mAppBar.setExpanded(mIsExpnaded, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.member_image);
        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("ABOUT")));
        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("PROJECTS")));
        mDetailsTab.addTab(mDetailsTab.newTab().setCustomView(createTabTitle("GROUPS")));
        ImageLoaderHelper.loadImage(getContext(), "http://cdn.wallpapersafari.com/13/28/EcwOC1.jpg", mImageDetail);
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int absoluteVerticalOffset = Math.abs(verticalOffset);

                int totalScrollRange = mAppBar.getTotalScrollRange();

                    OnTabVisibilityChangeListener listener = (OnTabVisibilityChangeListener) getActivity();
                    listener.onVisibilityChanged(false, absoluteVerticalOffset, totalScrollRange);

                int height = mToolbar.getHeight();
                float toolbarTranslationOffset = Math.abs((absoluteVerticalOffset * (height * 1.0f / totalScrollRange)) - height);
                Log.d(TAG, "Vertical offset -> " + toolbarTranslationOffset + " ::  Height -> " + height);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
                params.topMargin = (int) -toolbarTranslationOffset;
                mToolbar.setLayoutParams(params);
                //mToolbar.setTranslationY(toolbarTranslationOffset);

//                float tabTranslationOffset = absoluteVerticalOffset * (mDetailsTab.getHeight() * 1.0f / totalScrollRange);
//                float toolbarTranslationOffset = totalScrollRange * (mToolbar.getHeight() * 1.0f / absoluteVerticalOffset);
//
//
//                mToolbar.setY(toolbarTranslationOffset);

                    //mToolbar.setTranslationY(toolbarTranslationOffset);
//                if(verticalOffset == 0) {
//                    mChanged = false;
//                    ViewUtils.gone(mToolbar);
//                } else if(!mChanged) {
//                    OnTabVisibilityChangeListener listener = (OnTabVisibilityChangeListener) getActivity();
//                    listener.onVisibilityChanged(true, verticalOffset);
//                    mChanged = true;
//                    ViewUtils.visible(mToolbar);
//                }
            }
        });
        mAppBar.setExpanded(true);
    }

    @Override
    public void onPause() {
        OnTabVisibilityChangeListener listener = (OnTabVisibilityChangeListener) getActivity();
        listener.onReset();
        super.onPause();
    }
}
