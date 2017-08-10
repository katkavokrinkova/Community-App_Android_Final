package net.impacthub.members.ui.controllers;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnBackListener;
import net.impacthub.members.model.callback.OnTabVisibilityChangeListener;
import net.impacthub.members.ui.base.BaseActivity;
import net.impacthub.members.ui.controllers.home.HomeControllerFragment;
import net.impacthub.members.ui.controllers.conversations.ConversationControllerFragment;
import net.impacthub.members.ui.controllers.notification.NotificationControllerFragment;
import net.impacthub.members.ui.controllers.profile.ProfileControllerFragment;
import net.impacthub.members.ui.controllers.search.SearchControllerFragment;
import net.impacthub.members.ui.widgets.ExtendedViewPager;
import net.impacthub.members.utilities.ColorUtils;
import net.impacthub.members.utilities.DrawableUtils;
import net.impacthub.members.utilities.ViewUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MainTabsActivity extends BaseActivity implements OnTabVisibilityChangeListener {

    private static final String TAG = MainTabsActivity.class.getSimpleName();

    private final static int sIcons[] = {
        R.mipmap.tab_bar_home,
        R.mipmap.tab_bar_search,
        R.mipmap.tab_bar_notifications,
        R.mipmap.tab_bar_messages,
        R.mipmap.tab_bar_profile,
    };

    private LinearLayout mMainContainer;
    private ExtendedViewPager mPager;
    private TabLayout mTabLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_main_tabs;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainContainer = (LinearLayout) findViewById(R.id.main_container_layout);
        mPager = (ExtendedViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.navbar);

        MainTabsPagerAdapter adapter = new MainTabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeControllerFragment());
        adapter.addFragment(new SearchControllerFragment());
        adapter.addFragment(new NotificationControllerFragment());
        adapter.addFragment(new ConversationControllerFragment());
        adapter.addFragment(new ProfileControllerFragment());

        mPager.setPagingEnabled(false);
        mPager.setOffscreenPageLimit(adapter.getCount());
        mPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mPager, true);

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
                int position = tab.getPosition();

                Fragment fragment = getSupportFragmentManager().getFragments().get(position);

                FragmentManager manager = fragment.getChildFragmentManager();
                int entryCount = manager.getBackStackEntryCount();

                if (entryCount > 0) {
                    for (int i = 0; i < entryCount; i++) {
                        manager.popBackStackImmediate();
                    }
                    if (entryCount > 1) {
                        manager.beginTransaction().show(manager.getFragments().get(0)).commit();
                    }
                }
            }
        });

        ColorStateList stateList = ColorUtils.getColorStateList(this, R.color.selector_color_tab_icon_state);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i);
            if (tabAt != null) {
                tabAt.setIcon(DrawableUtils.tintDrawableWithState(this, sIcons[i], stateList));
            }
        }
    }

    @Override
    public void onVisibilityChanged(boolean shown, float offset, float totalOffset) {

        int height = mTabLayout.getHeight();
        float tabTranslationOffset = Math.abs((offset * (height * 1.0f / totalOffset)) - height);

        float bottom = mMainContainer.getPaddingBottom();
        Log.d(TAG, "Offset -> " + tabTranslationOffset);

        //mTabLayout.setTop((int) tabTranslationOffset);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTabLayout.getLayoutParams();
        params.topMargin = (int) - tabTranslationOffset;
        mTabLayout.setLayoutParams(params);
        mTabLayout.setTranslationY(tabTranslationOffset);

        //mTabLayout.layout(mTabLayout.getLeft(), (int) tabTranslationOffset, mTabLayout.getRight(), mTabLayout.getBottom());
    }

    @Override
    public void onReset() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTabLayout.getLayoutParams();
        params.topMargin = 0;
        mTabLayout.setLayoutParams(params);
        mTabLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private class MainTabsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new LinkedList<>();

        MainTabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    @Override
    public void onBackPressed() {
        int position = mTabLayout.getSelectedTabPosition();
        OnBackListener onBackListener = (OnBackListener) getSupportFragmentManager().getFragments().get(position);
        if (!onBackListener.onBack()) {
            super.onBackPressed();
        }
    }
}
