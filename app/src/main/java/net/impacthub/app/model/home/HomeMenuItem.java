package net.impacthub.app.model.home;

import android.support.annotation.DrawableRes;

import net.impacthub.app.model.pojo.Filterable;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/24/2017.
 */

public class HomeMenuItem implements Filterable {

    @DrawableRes private final int mHomeMenuIconRes;
    private final String mHomeMenuTitle;
    private final int mPosition;

    public HomeMenuItem(@DrawableRes int homeMenuIconRes, String homeMenuTitle, int position) {
        mHomeMenuIconRes = homeMenuIconRes;
        mHomeMenuTitle = homeMenuTitle;
        mPosition = position;
    }

    public int getHomeMenuIconRes() {
        return mHomeMenuIconRes;
    }

    public String getHomeMenuTitle() {
        return mHomeMenuTitle;
    }

    public int getPosition() {
        return mPosition;
    }

    @Override
    public boolean isFilterable(String query) {
        return false;
    }
}
