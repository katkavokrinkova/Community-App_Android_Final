package net.impacthub.app.ui.features.messages;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.conversations.ConversationVO;
import net.impacthub.app.model.vo.messages.MessageVO;
import net.impacthub.app.presenter.features.conversations.MessagesUiPresenter;
import net.impacthub.app.presenter.features.conversations.MessagesUiContract;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.features.messages.contacts.ContactsFragment;
import net.impacthub.app.ui.features.messages.conversation.ConversationFragment;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class MessagesFragment extends BaseChildFragment<MessagesUiPresenter> implements OnListItemClickListener<MessageVO>,MessagesUiContract {

    @BindView(R.id.list_items) protected RecyclerView mConversationList;

    private MessagesListAdapter mAdapter;

    public static MessagesFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MessagesUiPresenter onCreatePresenter() {
        return new MessagesUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.messages);
        if (mToolbar != null) {
            mToolbar.inflateMenu(R.menu.menu_message_conversation);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.actionCompose:
                            addChildFragment(ContactsFragment.newInstance(), "FRAG_CONTACTS");
                            return true;
                    }
                    return false;
                }
            });
        }
        if (mAdapter == null) {
            mAdapter = new MessagesListAdapter(getLayoutInflater(getArguments()));
            mAdapter.setItemClickListener(this);
        }
        mConversationList.setHasFixedSize(true);
        mConversationList.setAdapter(mAdapter);
    }

    @Override
    protected void onFragmentVisibilityChanged(boolean isVisible) {
        super.onFragmentVisibilityChanged(isVisible);
        if(isVisible) {
            getPresenter().getConversations();
        }
    }

    @Override
    public void onItemClick(int viewId, MessageVO model, int position) {
        ConversationVO conversationVO = new ConversationVO();
        conversationVO.mConversationId = model.mConversationId;
        conversationVO.mRecipientUserId = model.mRecipientUserId;
        conversationVO.mDisplayName = model.mDisplayName;
        conversationVO.mImageURL = model.mImageURL;
        addChildFragment(ConversationFragment.newInstance(conversationVO), "FRAG_MESSAGE_THREAD");
    }

    @Override
    public void onLoadMessages(List<MessageVO> messageVOList) {
        mAdapter.setItems(messageVOList);
    }
}
