package net.impacthub.members.ui.features.profile;

import android.os.Bundle;

import net.impacthub.members.R;
import net.impacthub.members.ui.base.BaseChildFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class ProfileFragment  extends BaseChildFragment {

    public static ProfileFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }
}
