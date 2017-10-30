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

package net.impacthub.app.model.vo.home;

import android.support.annotation.DrawableRes;

import net.impacthub.app.model.pojo.Searchable;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/24/2017.
 */

public class HomeMenuItem implements Searchable {

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
    public boolean isSearchable(String query) {
        return false;
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        return false;
    }
}
