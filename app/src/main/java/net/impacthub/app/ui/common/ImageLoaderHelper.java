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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class ImageLoaderHelper {

    private static final String TAG = ImageLoaderHelper.class.getSimpleName();

    public static void loadImage(Context context, Object url, ImageView iv) {
        Glide.with(context.getApplicationContext()).load(url).into(iv);
    }

//    public static void loadImage(Context context, String url, ImageView iv) {
//        if (url != null) {
//            Picasso.with(context.getApplicationContext()).load(url).into(iv);
//        } else {
//            iv.setImageResource(0);
//        }
////        if (url != null && !url.isEmpty()) {
////            Picasso picasso = new Picasso.Builder(context)
////                    .listener(new Picasso.Listener() {
////                        @Override
////                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
////                            if(BuildConfig.DEBUG) Log.e(TAG, exception.getMessage());
////                        }
////                    }).build();
////            picasso.load(url).fit().into(iv);
////        }
//    }

    public static void getImageAsBitmap(Context context, String url, final ImageFetchListener listener) {
        Glide.with(context.getApplicationContext()).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (listener != null) {
                    listener.onImageReady(resource);
                }
            }
        });
    }

    public interface ImageFetchListener {

        void onImageReady(Bitmap bitmap);
    }
}