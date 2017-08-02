package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import net.impacthub.members.R;

import static net.impacthub.members.ui.widgets.ViewModule.itemInflaterProvider;

public class TypefaceToolbar extends Toolbar implements ViewTreeObserver.OnGlobalLayoutListener {

    public TypefaceToolbar(Context context) {
        super(context);
        initialise();
    }

    public TypefaceToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    public TypefaceToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise();
    }

    private void initialise() {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        TextView newTitle = (TextView) itemInflaterProvider().inflate(R.layout.title_textview, this);
        newTitle.setText(getTitle());
        addView(newTitle);
        getTitleTextView().setVisibility(GONE);
    }

    @NonNull private TextView getTitleTextView() {
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
}