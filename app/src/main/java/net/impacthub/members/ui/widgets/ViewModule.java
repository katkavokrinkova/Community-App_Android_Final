package net.impacthub.members.ui.widgets;

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