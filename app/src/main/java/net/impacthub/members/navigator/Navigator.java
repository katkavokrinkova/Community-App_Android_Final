package net.impacthub.members.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class Navigator {

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<?> theActivity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, theActivity), requestCode);
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }
}
