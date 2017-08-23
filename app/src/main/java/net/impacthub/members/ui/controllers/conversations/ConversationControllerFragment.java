package net.impacthub.members.ui.controllers.conversations;

import android.support.v4.app.Fragment;

import net.impacthub.members.ui.base.BaseControllerFragment;
import net.impacthub.members.ui.features.messages.MessagesFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class ConversationControllerFragment extends BaseControllerFragment {
    @Override
    protected Fragment createFragment() {
        return MessagesFragment.newInstance();
    }
}
