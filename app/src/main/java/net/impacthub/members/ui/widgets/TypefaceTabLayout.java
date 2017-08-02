package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TypefaceTabLayout extends TabLayout {

    private final TypefaceResolver mTypefaceResolver = new TypefaceResolver();
    private final AttributeSet mAttrs;

    public TypefaceTabLayout(Context context) {
        super(context);
        mAttrs = null;
    }

    public TypefaceTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAttrs = attrs;
    }

    public TypefaceTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAttrs = attrs;
    }

    @Override
    public void addTab(@NonNull Tab tab) {
        super.addTab(tab);
        if (mAttrs != null) {
            setTabTypeface(tab);
        }
    }

    /**
     * from https://stackoverflow.com/a/33284990/1089629
     */
    private void setTabTypeface(Tab tab) {
        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

        for (int i = 0; i < tabView.getChildCount(); i++) {
            setTabTypeface(tabView.getChildAt(i));
        }
    }

    private void setTabTypeface(View tabViewChild) {
        if (tabViewChild instanceof TextView) {
            mTypefaceResolver.setTypeface((TextView) tabViewChild, mAttrs);
        }
    }
}
