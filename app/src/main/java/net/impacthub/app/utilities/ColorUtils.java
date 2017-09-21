package net.impacthub.app.utilities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public final class ColorUtils {

    private ColorUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    public static ColorStateList getColorStateList(Context context, @ColorRes int resId) {
        return ContextCompat.getColorStateList(context, resId);
    }
}
