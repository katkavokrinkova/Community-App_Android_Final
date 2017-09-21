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

package net.impacthub.app.ui.common;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoaderHelper {

    private static final String TAG = ImageLoaderHelper.class.getSimpleName();

    public static void loadImage(Context context, Object url, ImageView iv) {
        if (url != null) {
            Glide.with(context.getApplicationContext()).load(url).into(iv);
        }
//        if (url != null && !url.isEmpty()) {
//            Picasso picasso = new Picasso.Builder(context)
//                    .listener(new Picasso.Listener() {
//                        @Override
//                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                            if(BuildConfig.DEBUG) Log.e(TAG, exception.getMessage());
//                        }
//                    }).build();
//            picasso.load(url).fit().into(iv);
//        }
    }

//    public static void loadImage(Context context, @DrawableRes int resId, ImageView iv) {
//        if (resId != 0) {
//            Picasso.with(context).load(resId).into(iv);
//        }
//    }
}