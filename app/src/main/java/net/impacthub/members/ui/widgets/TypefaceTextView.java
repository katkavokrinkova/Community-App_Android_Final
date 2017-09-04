package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class TypefaceTextView extends AppCompatTextView {

    private final TypefaceResolver mTypefaceResolver = new TypefaceResolver();

    public TypefaceTextView(Context context) {
        super(context);
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypefaceResolver.setTypeface(this, attrs);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTypefaceResolver.setTypeface(this, attrs);
    }
}
