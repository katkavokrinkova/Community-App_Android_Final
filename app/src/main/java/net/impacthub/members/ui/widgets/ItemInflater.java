package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ItemInflater {

    public View inflate(@LayoutRes int layoutRes, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(layoutRes, parent, false);
    }
}
