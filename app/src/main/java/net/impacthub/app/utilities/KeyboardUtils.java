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

package net.impacthub.app.utilities;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/29/2017.
 */

public final class KeyboardUtils {

    private KeyboardUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    /**
     * This method is used to hide native android soft keyboard
     *
     * @param context the context
     * @param view     the actual view
     */
    public static void hideNativeKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
