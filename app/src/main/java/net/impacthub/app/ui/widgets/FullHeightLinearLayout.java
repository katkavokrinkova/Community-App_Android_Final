package net.impacthub.app.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class FullHeightLinearLayout extends LinearLayout {

    private int mHeight;

    public FullHeightLinearLayout(Context context) {
        super(context);
        initialize(context);
    }

    public FullHeightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public FullHeightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        mHeight = context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), mHeight);
    }
}
