package net.impacthub.app.utilities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
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

    public static Drawable tintDrawable(Context context, @DrawableRes int resId, int color) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, resId)).mutate();
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static Drawable resize(Resources resources, Bitmap bitmap, int width, int height) {
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return new BitmapDrawable(resources, bitmapResized);
    }
}
