package net.impacthub.members.ui.features.conversation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.vo.conversations.ConversationVO;
import net.impacthub.members.presenter.features.conversations.ConversationsPresenter;
import net.impacthub.members.presenter.features.conversations.ConversationsUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.features.conversation.contacts.ContactsFragment;
import net.impacthub.members.ui.features.conversation.messages.MessageFragment;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class ConversationsFragment extends BaseChildFragment<ConversationsPresenter> implements OnListItemClickListener<ConversationVO>,ConversationsUiContract {

    @BindView(R.id.list_items) protected RecyclerView mConversationList;

    private MessageListAdapter mAdapter;

    public static ConversationsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ConversationsFragment fragment = new ConversationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ConversationsPresenter onCreatePresenter() {
        return new ConversationsPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.messages);
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
        if (mAdapter == null) {
            mAdapter = new MessageListAdapter(getLayoutInflater(getArguments()));
            mAdapter.setItemClickListener(this);
        }
        mConversationList.setHasFixedSize(true);
        mConversationList.setAdapter(mAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        ConversationsPresenter presenter = getPresenter();
        if (presenter != null && isVisibleToUser) {
            presenter.getConversations(getUserAccount().getUserId());
        }
    }

    @Override
    public void onItemClick(ConversationVO model) {
        addChildFragment(MessageFragment.newInstance(model), "FRAG_MESSAGE_THREAD");
    }

    @Override
    public void onLoadConversations(List<ConversationVO> conversations) {
        mAdapter.setItems(conversations);
    }
}
