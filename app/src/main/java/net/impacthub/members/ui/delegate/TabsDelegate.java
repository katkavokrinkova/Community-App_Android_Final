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

package net.impacthub.members.ui.delegate;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.impacthub.members.R;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class TabsDelegate {

    public void setUp(@NonNull TabLayout tabLayout, String[] titles) {
        LayoutInflater inflater = LayoutInflater.from(tabLayout.getContext());
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
            if (tabAt != null) {
                tabAt.setCustomView(createTabTitle(inflater, titles[i]));
            }
        }
    }

    public TextView createTabTitle(LayoutInflater inflater, String label) {
        TextView tabTitleView = (TextView) inflater.inflate(R.layout.tab_title_textview, new LinearLayout(inflater.getContext()), false);
        tabTitleView.setText(label);
        return tabTitleView;
    }
}
