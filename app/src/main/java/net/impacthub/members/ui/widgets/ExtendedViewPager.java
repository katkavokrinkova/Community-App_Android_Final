package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/31/2017.
 */

public class ExtendedViewPager extends ViewPager {

    private boolean mSwipeEnabled;

    public ExtendedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSwipeEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mSwipeEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mSwipeEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean enabled) {
        this.mSwipeEnabled = enabled;
    }
}