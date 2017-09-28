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

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import net.impacthub.app.model.callback.OnCollapsingToolbarOffsetChangeListener;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class SimpleOffsetChangeListenerAdapter implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState;
    private Toolbar mToolbar;
    private OnCollapsingToolbarOffsetChangeListener mOffsetChangeListener;

    public SimpleOffsetChangeListenerAdapter(@NonNull Toolbar toolbar) {
        mToolbar = toolbar;
    }

    public void setOffsetChangeListener(OnCollapsingToolbarOffsetChangeListener offsetChangeListener) {
        mOffsetChangeListener = offsetChangeListener;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (Math.abs(verticalOffset) >= (appBarLayout.getTotalScrollRange() - mToolbar.getHeight())) {

            if (mCurrentState != State.COLLAPSED && mOffsetChangeListener != null) {
                mOffsetChangeListener.onCollapsed(verticalOffset);
                mCurrentState = State.COLLAPSED;
            }
        } else {
            if (mCurrentState != State.EXPANDED && mOffsetChangeListener != null) {
                mOffsetChangeListener.onExpanded(verticalOffset);
                mCurrentState = State.EXPANDED;
            }
        }
    }

    private enum State {
        COLLAPSED,
        EXPANDED
    }
}
