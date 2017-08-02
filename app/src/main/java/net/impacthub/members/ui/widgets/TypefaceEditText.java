package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


public class TypefaceEditText extends AppCompatEditText {

    private final TypefaceResolver mTypefaceResolver = new TypefaceResolver();

    public TypefaceEditText(Context context) {
        super(context);
    }

    public TypefaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypefaceResolver.setTypeface(this, attrs);
    }

    public TypefaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTypefaceResolver.setTypeface(this, attrs);
    }
}
