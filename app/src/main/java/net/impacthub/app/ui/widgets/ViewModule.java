package net.impacthub.app.ui.widgets;

import net.impacthub.app.ui.common.ImageLoaderHelper;

public class ViewModule {

    public static ItemInflater itemInflaterProvider() {
        return new ItemInflater();
    }

    public static KeyboardManager keyboardManagerProvider() {
        return new KeyboardManager();
    }

    public static ImageLoaderHelper imageLoaderProvider() {
        return new ImageLoaderHelper();
    }
}