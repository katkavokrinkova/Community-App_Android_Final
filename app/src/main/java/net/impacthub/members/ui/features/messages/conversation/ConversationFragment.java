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

package net.impacthub.members.ui.features.messages.conversation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.messages.MessageVO;
import net.impacthub.members.model.vo.notifications.NotificationType;
import net.impacthub.members.model.features.conversations.ProcessedMessages;
import net.impacthub.members.model.features.push.PushQuery;
import net.impacthub.members.presenter.features.messages.ConversationUiContract;
import net.impacthub.members.presenter.features.messages.ConversationUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.ImageLoaderHelper;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class ConversationFragment extends BaseChildFragment<ConversationUiPresenter> implements ConversationUiContract {

    private static final String EXTRA_CONVERSATION_ID = "net.impacthub.members.ui.features.conversation.messages.EXTRA_CONVERSATION_ID";
    private static final String EXTRA_CONVERSATION_DISPLAY_NAME = "net.impacthub.members.ui.features.conversation.messages.EXTRA_CONVERSATION_DISPLAY_NAME";
    private static final String EXTRA_CONVERSATION_PROFILE_IMAGE = "net.impacthub.members.ui.features.conversation.messages.EXTRA_CONVERSATION_PROFILE_IMAGE";
    private static final String EXTRA_CONVERSATION_RECIPIENT_ID = "net.impacthub.members.ui.features.conversation.messages.EXTRA_CONVERSATION_RECIPIENT_ID";

    @BindView(R.id.message_items) protected RecyclerView mMessageList;
    @BindView(R.id.message_entry) protected EditText mMessageField;

    private String mConversationID;
    private ConversationListAdapter mAdapter;
    private String mInReplyTo;

    public static ConversationFragment newInstance(MessageVO model) {
        
        Bundle args = new Bundle();
        args.putString(EXTRA_CONVERSATION_ID, model.mConversationId);
        args.putString(EXTRA_CONVERSATION_DISPLAY_NAME, model.mDisplayName);
        args.putString(EXTRA_CONVERSATION_PROFILE_IMAGE, model.mImageURL);
        args.putString(EXTRA_CONVERSATION_RECIPIENT_ID, model.mRecipientUserId);
        ConversationFragment fragment = new ConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ConversationUiPresenter onCreatePresenter() {
        return new ConversationUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        mConversationID = arguments.getString(EXTRA_CONVERSATION_ID);
        String displayName = arguments.getString(EXTRA_CONVERSATION_DISPLAY_NAME);
        String profileImage = arguments.getString(EXTRA_CONVERSATION_PROFILE_IMAGE);

        if (mToolbar != null) {
            ImageView displayImage = mToolbar.findViewById(R.id.my_image);
            TextView displayNameTxt = mToolbar.findViewById(R.id.my_name);
            ImageLoaderHelper.loadImage(getContext(), buildUrl(profileImage), displayImage);
            displayNameTxt.setText(displayName);
            mToolbar.setNavigationOnClickListener(mBackListener);
        }

        mMessageList.setHasFixedSize(true);
        mAdapter = new ConversationListAdapter(getLayoutInflater(getArguments()));
        mMessageList.setAdapter(mAdapter);

        mMessageField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean sendPressed = actionId == EditorInfo.IME_ACTION_SEND;
                if(sendPressed) {
                    sendMessage(mMessageField.getText().toString());
                }
                return sendPressed;
            }
        });

        getPresenter().getMessageConversations(mConversationID, getUserAccount().getUserId());
    }

    private void sendMessage(String message) {
        String fromUserId = getUserAccount().getUserId();
        String toUserIds = getArguments().getString(EXTRA_CONVERSATION_RECIPIENT_ID);
        String pushType = NotificationType.TYPE_PRIVATE_MESSAGE.getType();

        PushQuery pushQuery = new PushQuery(fromUserId, toUserIds, pushType, mConversationID);

        getPresenter().sendMessage(mConversationID, pushQuery, message, mInReplyTo);
    }

    @Override
    public void onLoadMessages(ProcessedMessages processedMessages) {
        mAdapter.setItems(processedMessages.getMessages());
        mInReplyTo = processedMessages.getInReplyTo();
    }

    @Override
    public void onClearTextField() {
        mMessageField.setText(null);
    }
}
