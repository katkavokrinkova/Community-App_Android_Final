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

package net.impacthub.members.ui.common;

import android.support.v7.widget.RecyclerView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final int SHOW_THRESHOLD = 20;
    private static final int HIDE_THRESHOLD = 50;
    private int mScrolledDistance = 0;
    private boolean mControlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
            onHide();
            mControlsVisible = false;
            mScrolledDistance = 0;
        } else if (mScrolledDistance < -SHOW_THRESHOLD && !mControlsVisible) {
            onShow();
            mControlsVisible = true;
            mScrolledDistance = 0;
        }

        if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
            mScrolledDistance += dy;
        }
    }

    public abstract void onHide();

    public abstract void onShow();
}