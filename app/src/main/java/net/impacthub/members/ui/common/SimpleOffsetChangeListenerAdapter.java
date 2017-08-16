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

import android.support.design.widget.AppBarLayout;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class SimpleOffsetChangeListenerAdapter implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                mCurrentState = State.EXPANDED;
                onExpanded(verticalOffset);
            }
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                mCurrentState = State.COLLAPSED;
                onCollapsed(verticalOffset);
            }
        } else {
            if (mCurrentState != State.IDLE) {
                mCurrentState = State.IDLE;
                onIdle(verticalOffset);
            }
        }
    }

    protected void onExpanded(int verticalOffset) {
    }

    protected void onCollapsed(int verticalOffset) {
    }

    protected void onIdle(int verticalOffset) {
    }

    private enum State {
        COLLAPSED,
        EXPANDED,
        IDLE
    }
}
