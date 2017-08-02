package net.impacthub.members.model.home;

import android.support.annotation.DrawableRes;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/24/2017.
 */

public class HomeMenuItem {

    @DrawableRes private final int mHomeMenuIconRes;
    private final String mHomeMenuTitle;

    public HomeMenuItem(@DrawableRes int homeMenuIconRes, String homeMenuTitle) {
        mHomeMenuIconRes = homeMenuIconRes;
        mHomeMenuTitle = homeMenuTitle;
    }

    public int getHomeMenuIconRes() {
        return mHomeMenuIconRes;
    }

    public String getHomeMenuTitle() {
        return mHomeMenuTitle;
    }
}
