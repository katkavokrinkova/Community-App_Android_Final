package net.impacthub.app.ui.controllers;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnBackListener;
import net.impacthub.app.model.callback.OnReSelectListener;
import net.impacthub.app.model.vo.contacts.ContactVO;
import net.impacthub.app.model.vo.conversations.ConversationVO;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.notifications.CommentNotificationPayload;
import net.impacthub.app.model.vo.notifications.DMContactNotificationPayload;
import net.impacthub.app.model.vo.notifications.LikePostNotificationPayload;
import net.impacthub.app.model.vo.notifications.MessageNotificationPayload;
import net.impacthub.app.model.vo.notifications.ReceivedNotification;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.features.home.MainTabsUiContract;
import net.impacthub.app.presenter.features.home.MainTabsUiPresenter;
import net.impacthub.app.ui.base.BaseActivity;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.controllers.home.HomeControllerFragment;
import net.impacthub.app.ui.controllers.messages.MessagesControllerFragment;
import net.impacthub.app.ui.controllers.notification.NotificationControllerFragment;
import net.impacthub.app.ui.controllers.profile.ProfileControllerFragment;
import net.impacthub.app.ui.controllers.search.SearchControllerFragment;
import net.impacthub.app.ui.features.home.groups.GroupDetailFragment;
import net.impacthub.app.ui.features.home.members.MemberDetailFragment;
import net.impacthub.app.ui.features.home.projects.ProjectDetailFragment;
import net.impacthub.app.ui.features.messages.contacts.ViewMoreContactFragment;
import net.impacthub.app.ui.features.messages.conversation.ConversationFragment;
import net.impacthub.app.ui.features.notification.NotificationFragment;
import net.impacthub.app.ui.widgets.ExtendedViewPager;
import net.impacthub.app.utilities.ColorUtils;
import net.impacthub.app.utilities.DrawableUtils;
import net.impacthub.app.utilities.KeyboardUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MainTabsActivity extends BaseActivity<MainTabsUiPresenter> implements MainTabsUiContract {

    private static final String TAG = MainTabsActivity.class.getSimpleName();

    public static final String EXTRA_PUSH_NOTIFICATION_FROM_NOTIFICATION = "net.impacthub.app.ui.controllers.EXTRA_PUSH_NOTIFICATION_FROM_NOTIFICATION";
    public static final String EXTRA_PUSH_NOTIFICATION_MODEL = "net.impacthub.app.ui.controllers.EXTRA_PUSH_NOTIFICATION_MODEL";

    private final static int sIcons[] = {
            R.mipmap.tab_bar_home,
            R.mipmap.tab_bar_search,
            R.mipmap.tab_bar_notifications,
            R.mipmap.tab_bar_messages,
            R.mipmap.tab_bar_profile,
    };

    private ExtendedViewPager mPager;
    private TabLayout mTabLayout;
    private boolean mPushNeedshandling;

    @Override
    protected MainTabsUiPresenter onCreatePresenter() {
        return new MainTabsUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main_tabs;
    }

    @Override
    protected void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
        handlePushNotification(intent);
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPager = (ExtendedViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.navbar);

        MainTabsPagerAdapter adapter = new MainTabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeControllerFragment());
        adapter.addFragment(new SearchControllerFragment());
        adapter.addFragment(new NotificationControllerFragment());
        adapter.addFragment(new MessagesControllerFragment());
        adapter.addFragment(new ProfileControllerFragment());

        mPager.setPagingEnabled(false);
        mPager.setOffscreenPageLimit(adapter.getCount());
        mPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mPager, true);

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                KeyboardUtils.hideNativeKeyboard(MainTabsActivity.this, findViewById(android.R.id.content));
                int position = tab.getPosition();
                mPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
                KeyboardUtils.hideNativeKeyboard(MainTabsActivity.this, findViewById(android.R.id.content));
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
                } else {
                    OnReSelectListener activeFragment = (OnReSelectListener) manager.getFragments().get(0);
                    activeFragment.onTabReselected();
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
        handlePushNotification(getIntent());

        if (mPushNeedshandling) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handlePushNotification(getIntent());
                }
            }, 500);
        }
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper());


    private void handlePushNotification(Intent intent) {
        if (!intent.getBooleanExtra(EXTRA_PUSH_NOTIFICATION_FROM_NOTIFICATION, false)) {
            return;
        }
        int tabPosition = mTabLayout.getSelectedTabPosition();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            Fragment fragment = null;
            if (fragments.size() > tabPosition) {
                fragment = fragments.get(tabPosition);
            } else if (fragments.size() > 0) {
                fragment = fragments.get(0);
            }
            if (fragment != null) {
                mPushNeedshandling = false;
                FragmentManager manager = fragment.getChildFragmentManager();
                BaseChildFragment topFragment = (BaseChildFragment) manager.getFragments().get(0);
                if (topFragment != null) {

                    ReceivedNotification extra = (ReceivedNotification) intent.getSerializableExtra(EXTRA_PUSH_NOTIFICATION_MODEL);
                    switch (extra.getPayloadType()) {
                        case ReceivedNotification.PAYLOAD_TYPE_PRIVATE_MESSAGE:
                            MessageNotificationPayload mnp = extra.getNotificationPayloadVO();
                            ConversationVO conversationVO = new ConversationVO();
                            conversationVO.mConversationId = mnp.getConversationId();
                            topFragment.addChildFragment(ConversationFragment.newInstance(conversationVO), "FRAG_MESSAGE_THREAD");
                            break;
                        case ReceivedNotification.PAYLOAD_TYPE_SEND_APPROVE_REQUEST:
                            DMContactNotificationPayload dmcp = extra.getNotificationPayloadVO();
                            getPresenter().getContactMemberBy(dmcp.getRelatedId());
                            break;
                        case ReceivedNotification.PAYLOAD_TYPE_LIKE_POST:
                            LikePostNotificationPayload  lpnp = extra.getNotificationPayloadVO();
                            getPresenter().getGroupOrProjectBy(lpnp.getRelatedGroup(), lpnp.getPostId());
                            break;
                        case ReceivedNotification.PAYLOAD_TYPE_LIKE_COMMENT:
                        case ReceivedNotification.PAYLOAD_TYPE_COMMENT:
                            CommentNotificationPayload cnp = extra.getNotificationPayloadVO();
                            getPresenter().getGroupOrProjectBy(cnp.getRelatedGroup(), cnp.getId());
                            break;
                        default:
                            topFragment.addChildFragment(NotificationFragment.newInstance(), "FRAG_NOTIFICATION_DETAIL");
                    }
                }
            } else {
                mPushNeedshandling = true;
            }
        }
    }

    @Override
    public void onLoadProject(ProjectVO projectVO) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            Fragment fragment = fragments.get(mTabLayout.getSelectedTabPosition());

            FragmentManager manager = fragment.getChildFragmentManager();
            BaseChildFragment topFragment = (BaseChildFragment) manager.getFragments().get(0);
            if (topFragment != null) {
                topFragment.addChildFragment(ProjectDetailFragment.newInstance(projectVO), "FRAG_PROJECT_DETAIL");
            }
        }
    }

    @Override
    public void onLoadGroup(GroupVO groupVO) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            Fragment fragment = fragments.get(mTabLayout.getSelectedTabPosition());

            FragmentManager manager = fragment.getChildFragmentManager();
            BaseChildFragment topFragment = (BaseChildFragment) manager.getFragments().get(0);
            if (topFragment != null) {
                topFragment.addChildFragment(GroupDetailFragment.newInstance(groupVO), "FRAG_GROUP_DETAIL");
            }
        }
    }

    @Override
    public void onLoadContact(ContactVO contactVO) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            Fragment fragment = fragments.get(mTabLayout.getSelectedTabPosition());

            FragmentManager manager = fragment.getChildFragmentManager();
            BaseChildFragment topFragment = (BaseChildFragment) manager.getFragments().get(0);
            if (topFragment != null) {
                topFragment.addChildFragment(ViewMoreContactFragment.newInstance(contactVO), "FRAG_VIEW_MORE_CONTACT");
            }
        }
    }

    @Override
    public void onLoadMember(MemberVO memberVO) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            Fragment fragment = fragments.get(mTabLayout.getSelectedTabPosition());

            FragmentManager manager = fragment.getChildFragmentManager();
            BaseChildFragment topFragment = (BaseChildFragment) manager.getFragments().get(0);
            if (topFragment != null) {
                topFragment.addChildFragment(MemberDetailFragment.newInstance(memberVO), "FRAG_MEMBER_DETAIL");
            }
        }
    }

    private class MainTabsPagerAdapter extends FragmentStatePagerAdapter {

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
            if (position == 0) {
                super.onBackPressed();
            } else {
                TabLayout.Tab tabAt = mTabLayout.getTabAt(0);
                if (tabAt != null) {
                    tabAt.select();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }
}
