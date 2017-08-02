package net.impacthub.members.utilities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public final class DrawableUtils {

    private DrawableUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    public static Drawable tintDrawableWithState(Context context, @DrawableRes int resId, ColorStateList stateList) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, resId)).mutate();
        DrawableCompat.setTintList(drawable, stateList);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }
}
