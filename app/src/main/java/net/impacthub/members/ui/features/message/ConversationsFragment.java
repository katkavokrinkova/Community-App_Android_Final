package net.impacthub.members.ui.features.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.conversations.ConversationDTO;
import net.impacthub.members.presenter.features.conversations.ConversationsPresenter;
import net.impacthub.members.presenter.features.conversations.ConversationsUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class ConversationsFragment extends BaseChildFragment<ConversationsPresenter> implements OnListItemClickListener<ConversationDTO>,ConversationsUiContract {

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
                        showToast("Compose clicked");
                        return true;
                }
                return false;
            }
        });
        mConversationList.setHasFixedSize(true);
        mAdapter = new MessageListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(this);
        mConversationList.setAdapter(mAdapter);

        getPresenter().getConversations();
    }

    @Override
    public void onItemClick(ConversationDTO model) {
        showToast("Ehllow");
    }

    @Override
    public void onLoadConversations(List<ConversationDTO> conversations) {
        mAdapter.setItems(conversations);
    }
}
