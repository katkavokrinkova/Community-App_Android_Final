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

package net.impacthub.members.ui.features.home.goals;

import android.os.Bundle;

import net.impacthub.members.ui.base.BaseChildFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class GoalsFragment extends BaseChildFragment {

    public static GoalsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        GoalsFragment fragment = new GoalsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getContentView() {
        return 0;
    }
}
