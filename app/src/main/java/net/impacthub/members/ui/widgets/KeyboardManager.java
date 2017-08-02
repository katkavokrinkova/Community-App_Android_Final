package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardManager {

    public void hideKeyboardUsing(View rootView) {
        ((InputMethodManager) rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }
}
