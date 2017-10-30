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

package net.impacthub.app.ui.delegate;

import android.content.Context;

import net.impacthub.app.model.storage.PreferenceStorage;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/27/2017.
 */

public class ShortcutBadgerHelper {

    public final static String KEY_BADGE_COUNT = "net.impacthub.app.ui.delegate.KEY_BADGE_COUNT";

    public static void applyBadgeCount(Context context, int badgeCount) {
        if (ShortcutBadger.applyCount(context, badgeCount)) {
            PreferenceStorage.saveValue(context, KEY_BADGE_COUNT, badgeCount);
        }
    }

    public static void increaseBadgeCount(Context context) {
        int value = PreferenceStorage.getValue(context, KEY_BADGE_COUNT);
        applyBadgeCount(context, ++value);
    }

    public static void decreaseBadgeCount(Context context) {
        int value = PreferenceStorage.getValue(context, KEY_BADGE_COUNT);
        applyBadgeCount(context, --value);
    }

    public static void clearBadgeCount(Context context) {
        if (ShortcutBadger.removeCount(context)) {
            PreferenceStorage.saveValue(context, KEY_BADGE_COUNT, 0);
        }
    }
}
