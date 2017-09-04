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

package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/31/2017.
 */

public class ExtendedRecyclerView extends RecyclerView {

    public ExtendedRecyclerView(Context context) {
        super(context);
    }

    public ExtendedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnItemTouchListener(mTouchListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnItemTouchListener(mTouchListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // Explicitly call computeScroll() to make the Scroller compute itself
            computeScroll();
        }
        return super.onInterceptTouchEvent(ev);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        if (e.getAction() == MotionEvent.ACTION_DOWN && getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
//            stopScroll();
//            return false;
//        }
//        return super.onInterceptTouchEvent(e);
//    }

    private final OnItemTouchListener mTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            if (e.getAction() == MotionEvent.ACTION_DOWN && rv.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) childView.performClick();
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    };
}
