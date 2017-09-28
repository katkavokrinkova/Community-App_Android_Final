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

package net.impacthub.app.ui.features.home.goals.binders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.ui.binder.ViewBinderAdapter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class AboutViewBinder extends ViewBinderAdapter {

    private final String mAboutTitle;
    private final String mAboutDescription;

    public AboutViewBinder(String aboutTitle, String aboutDescription) {
        mAboutTitle = aboutTitle;
        mAboutDescription = aboutDescription;
    }

    @Override
    public View getView(Context context, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_about_goal, new LinearLayout(context), false);
        ((TextView) view.findViewById(R.id.text_info_title)).setText(mAboutTitle);
        ((TextView) view.findViewById(R.id.text_event_description)).setText(mAboutDescription);
        return view;
    }
}
