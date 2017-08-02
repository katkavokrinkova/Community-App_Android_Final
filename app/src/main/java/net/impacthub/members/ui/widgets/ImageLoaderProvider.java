package net.impacthub.members.ui.widgets;

import com.squareup.picasso.Picasso;

import android.content.Context;

public class ImageLoaderProvider {

    public Picasso with(Context context) {
        return Picasso.with(context);
    }
}