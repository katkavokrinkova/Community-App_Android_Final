package net.impacthub.members.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    public static void startOtherWebActivity(Context context, String linkURL) {
        if (linkURL == null || linkURL.isEmpty()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkURL));
        context.startActivity(intent);
    }
}
