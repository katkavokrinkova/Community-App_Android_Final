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

package net.impacthub.app.ui.widgets;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class ExtendedTabLayout extends TabLayout {

    private static final String TAG = ExtendedTabLayout.class.getSimpleName();

    public ExtendedTabLayout(Context context) {
        super(context);
    }

    public ExtendedTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getTabCount() == 0)
            return;
        try {
            ViewGroup tabLayout = (ViewGroup)getChildAt(0);
            int widthOfAllTabs = 0;
            for (int i = 0; i < tabLayout.getChildCount(); i++) {
                widthOfAllTabs += tabLayout.getChildAt(i).getMeasuredWidth();
            }
            setTabMode(widthOfAllTabs <= getMeasuredWidth() ? MODE_FIXED : MODE_SCROLLABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}