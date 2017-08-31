/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.members.ui.cache;

import android.support.v4.app.Fragment;

import net.impacthub.members.ui.controllers.home.HomeControllerFragment;
import net.impacthub.members.ui.controllers.messages.MessagesControllerFragment;
import net.impacthub.members.ui.controllers.notification.NotificationControllerFragment;
import net.impacthub.members.ui.controllers.profile.ProfileControllerFragment;
import net.impacthub.members.ui.controllers.search.SearchControllerFragment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/31/2017.
 */

public enum FragmentCache {

    INSTANCE;

    private Map<Integer, Fragment> mFragmentCache = new ConcurrentHashMap<>();
    
    public Fragment getFragment(int position) {
        Fragment fragment = mFragmentCache.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomeControllerFragment();
                    break;
                case 1:
                    fragment = new SearchControllerFragment();
                    break;
                case 2:
                    fragment = new NotificationControllerFragment();
                    break;
                case 3:
                    fragment = new MessagesControllerFragment();
                    break;
                default:
                    fragment = new ProfileControllerFragment();
            }
            mFragmentCache.put(position, fragment);
        }
        return fragment;
    }
}
