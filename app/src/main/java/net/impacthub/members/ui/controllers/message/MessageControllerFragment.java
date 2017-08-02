package net.impacthub.members.ui.controllers.message;

import android.support.v4.app.Fragment;

import net.impacthub.members.ui.base.BaseControllerFragment;
import net.impacthub.members.ui.features.message.MessageFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MessageControllerFragment extends BaseControllerFragment {
    @Override
    protected Fragment createFragment() {
        return MessageFragment.newInstance();
    }
}
