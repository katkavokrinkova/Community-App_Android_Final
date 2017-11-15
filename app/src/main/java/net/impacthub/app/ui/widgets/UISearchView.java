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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.utilities.KeyboardUtils;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/25/2017.
 */

public class UISearchView extends FrameLayout {

    private EditText mSearchField;
    private ImageView mClearBtn;

    public UISearchView(@NonNull Context context) {
        super(context);
        initialize(context);
    }

    public UISearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public UISearchView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UISearchView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.layout_search_bar, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSearchField = (EditText) findViewById(R.id.text_global_search_term);
        mClearBtn = (ImageView) findViewById(R.id.button_clear);
        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String typedQuery = editable.toString();
                if(typedQuery.isEmpty()) {
                    mClearBtn.setVisibility(View.GONE);
                } else {
                    mClearBtn.setVisibility(View.VISIBLE);
                }
                if (mSearchActionListener != null) {
                    mSearchActionListener.onTextChanged(typedQuery);
                }
            }
        });
        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean searchPressed = actionId == EditorInfo.IME_ACTION_SEND;
                if(searchPressed) {
                    KeyboardUtils.hideNativeKeyboard(getContext(), mSearchField);
                    if (mSearchActionListener != null) {
                        mSearchActionListener.onSearch(getSearchText());
                    }
                }
                return searchPressed;
            }
        });
        mClearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchField.setText(null);
                KeyboardUtils.hideNativeKeyboard(getContext(), mSearchField);
            }
        });
    }

    public String getSearchText() {
        return mSearchField.getText().toString();
    }
    public void setSearchActionListener(OnSearchActionListener searchActionListener) {
        mSearchActionListener = searchActionListener;
    }

    private OnSearchActionListener mSearchActionListener;

    public interface OnSearchActionListener {

        void onSearch(String searchValue);

        void onTextChanged(String query);
    }
}
