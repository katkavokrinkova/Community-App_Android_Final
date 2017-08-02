package net.impacthub.members.ui.features.message;

import android.os.Bundle;

import net.impacthub.members.R;
import net.impacthub.members.ui.base.BaseChildFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MessageFragment extends BaseChildFragment {

    public static MessageFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getContentView() {
        return R.layout.messages;
    }
}
