package net.impacthub.members.presenter.features.conversations;

import net.impacthub.members.presenter.base.UiPresenter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public class ConversationsPresenter extends UiPresenter<ConversationsUiContract> {

    public ConversationsPresenter(ConversationsUiContract uiContract) {
        super(uiContract);
    }

    public void getConversations() {
        getUi().onLoadConversations("Hello");
    }
}
