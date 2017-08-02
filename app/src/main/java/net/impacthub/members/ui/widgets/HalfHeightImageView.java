package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class HalfHeightImageView extends AppCompatImageView {

    private int mHeight;

    public HalfHeightImageView(Context context) {
        super(context);
        initialize(context);
    }

    public HalfHeightImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public HalfHeightImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        mHeight = context.getResources().getDisplayMetrics().heightPixels / 2;
        setScaleType(ScaleType.FIT_CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), mHeight);
    }
}
