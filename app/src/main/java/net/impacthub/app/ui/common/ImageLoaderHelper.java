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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageLoaderHelper {

    private static final String TAG = ImageLoaderHelper.class.getSimpleName();

    public static void loadImage(Context context, int url, ImageView iv) {
        if (url != 0) {
            Picasso.with(context.getApplicationContext()).load(url).into(iv);
        }
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        if (url != null) {
            Picasso.with(context.getApplicationContext()).load(url).into(iv);
        } else {
            iv.setImageResource(0);
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

    public static void getImageAsBitmap(Context context, String url, final ImageFetchListener listener) {
        Picasso.with(context.getApplicationContext()).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (listener != null) {
                    listener.onImageReady(bitmap);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    public interface ImageFetchListener {

        void onImageReady(Bitmap bitmap);
    }
}