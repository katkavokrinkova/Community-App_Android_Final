package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.utilities.ViewUtils;

public class TypefaceToolbar extends Toolbar {

    private TextView mNewTitle;

    public TypefaceToolbar(Context context) {
        super(context);
        initialise(context);
    }

    public TypefaceToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise(context);
    }

    public TypefaceToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context);
    }

    private void initialise(Context context) {
        mNewTitle = (TextView) inflate(context, R.layout.toolbar_title_layout, null);
        mNewTitle.setText(getTitle());
        mNewTitle.setMaxLines(1);
        mNewTitle.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(mNewTitle, params);
        getTitleTextView().setVisibility(GONE);
        setTitle(null);
    }

    @Override
    public void setTitle(@StringRes int resId) {

    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @NonNull
    private TextView getTitleTextView() {
        for (int index = 0; index < getChildCount(); index++) {
            TextView title = getTextViewAt(getChildAt(index));
            if (title != null) return title;
        }
        return new TextView(getContext());
    }

    @Nullable
    private TextView getTextViewAt(View titleCandidate) {
        if (titleCandidate instanceof TextView) {
            TextView title = getTitleTextView((TextView) titleCandidate);
            if (title != null) return title;
        }
        return null;
    }

    @Nullable
    private TextView getTitleTextView(TextView titleCandidate) {
        if (titleCandidate.getText().equals(getTitle())) {
            return titleCandidate;
        }
        return null;
    }

    public void setCustomTitle(String title) {
        ViewUtils.visible(mNewTitle);
        mNewTitle.setText(title);
    }

    public void setCustomTitle(String title, @ColorInt int titleColor) {
        ViewUtils.visible(mNewTitle);
        mNewTitle.setTextColor(titleColor);
        mNewTitle.setText(title);
    }
}