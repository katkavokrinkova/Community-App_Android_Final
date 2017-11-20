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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/8/2017.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean mLoading = false; // True if we are still waiting for the last set of data to load
    private boolean mCanLoadMore = true;

    private static final int VISIBLE_ITEM_THRESHOLD = 10;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (mLinearLayoutManager == null) {
            mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        }

        int totalItemCount = mLinearLayoutManager.getItemCount();
        int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (mCanLoadMore && !mLoading && totalItemCount <= (lastVisibleItem + VISIBLE_ITEM_THRESHOLD)) {
            onLoadMore();
            mLoading = true;
        }
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    public void canLoadMore(boolean done) {
        mCanLoadMore = !done;
    }

    public abstract void onLoadMore();
}