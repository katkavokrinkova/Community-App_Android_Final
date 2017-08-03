package net.impacthub.members.ui.widgets;


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