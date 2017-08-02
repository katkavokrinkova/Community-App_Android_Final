package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;


public class FullHeightConstraintLayout extends ConstraintLayout {

    private int mHeight;

    public FullHeightConstraintLayout(Context context) {
        super(context);
        initialize(context);
    }

    public FullHeightConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public FullHeightConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
