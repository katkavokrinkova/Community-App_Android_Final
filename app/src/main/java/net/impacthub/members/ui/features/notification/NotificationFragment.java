package net.impacthub.members.ui.features.notification;

import android.os.Bundle;

import net.impacthub.members.R;
import net.impacthub.members.ui.base.BaseChildFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class NotificationFragment extends BaseChildFragment {

    public static NotificationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getContentView() {
        return R.layout.fragment_notifications;
    }
}
