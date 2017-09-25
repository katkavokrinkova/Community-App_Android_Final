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

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.impacthub.app.model.pojo.Refreshable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/25/2017.
 */

public class UIRefreshManager {

    public final static int REFRESH_ID_MEMBERS_LIST = 0x000;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({REFRESH_ID_MEMBERS_LIST})
    public @interface RefreshableIdentifier {
    }

    private final Map<Integer, List<Refreshable>> mRefreshablesMap = new ConcurrentHashMap<>();

    private UIRefreshManager() {
    }

    public static UIRefreshManager getInstance() {
        return HOLDER.INSTANCE;
    }

    private final static class HOLDER {

        private final static UIRefreshManager INSTANCE = new UIRefreshManager();
    }

    public void addRefreshable(@RefreshableIdentifier int identifier, @NonNull Refreshable refreshable) {
        List<Refreshable> refreshables = mRefreshablesMap.get(identifier);
        if (refreshables == null) {
            refreshables = new LinkedList<>();
        }
        refreshables.add(refreshable);
        mRefreshablesMap.put(identifier, refreshables);
    }

    @Nullable
    public List<Refreshable> getRefreshables(@RefreshableIdentifier int identifier) {
        return mRefreshablesMap.get(identifier);
    }

    public void removeRefreshable(@RefreshableIdentifier int identifier, Refreshable refreshable) {
        List<Refreshable> refreshables = mRefreshablesMap.get(identifier);
        if (refreshables != null) {
            refreshables.remove(refreshable);
        }
    }
}
