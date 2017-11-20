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

package net.impacthub.app.ui.delegate;

import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;

import net.impacthub.app.navigator.Navigator;
import net.impacthub.app.utilities.ViewUtils;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class DetailScreenDelegate {

    public void handleButtons(Pair<String, ImageButton>... buttonPairs) {
        for (Pair<String, ImageButton> buttonPair : buttonPairs) {
            String link = buttonPair.first;
            ImageButton button = buttonPair.second;
            if ((link != null && link.isEmpty()) || link == null ) {
                ViewUtils.gone(button);
            } else {
                button.setTag(link);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String link = (String) view.getTag();
                        Navigator.startOtherWebActivity(view.getContext(), link);
                    }
                });
                ViewUtils.visible(button);
            }
        }
    }
}
