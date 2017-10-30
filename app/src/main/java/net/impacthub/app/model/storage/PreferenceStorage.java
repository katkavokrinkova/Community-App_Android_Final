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

package net.impacthub.app.model.storage;

import android.content.Context;
import android.content.SharedPreferences;

import net.impacthub.app.R;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/27/2017.
 */

public class PreferenceStorage {

    public static void saveValue(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.impact_hub_account_type), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value).apply();
    }

    public static int getValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.impact_hub_account_type), Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
}
