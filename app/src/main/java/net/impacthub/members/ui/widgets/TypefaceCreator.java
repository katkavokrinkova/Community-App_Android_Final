package net.impacthub.members.ui.widgets;

import android.content.res.AssetManager;
import android.graphics.Typeface;

class TypefaceCreator {

    Typeface createFromAsset(AssetManager mgr, String path){
        return Typeface.createFromAsset(mgr, path);
    }
}