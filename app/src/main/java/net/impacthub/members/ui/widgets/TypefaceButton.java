package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class TypefaceButton extends AppCompatButton {

    private final TypefaceResolver mTypefaceResolver = new TypefaceResolver();

    public TypefaceButton(Context context) {
        super(context);
    }

    public TypefaceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypefaceResolver.setTypeface(this, attrs);
    }

    public TypefaceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTypefaceResolver.setTypeface(this, attrs);
    }
}
