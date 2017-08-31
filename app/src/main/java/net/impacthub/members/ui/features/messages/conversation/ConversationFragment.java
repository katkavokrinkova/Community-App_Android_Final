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

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import net.impacthub.members.R;
import net.impacthub.members.model.features.conversations.ProcessedMessages;
import net.impacthub.members.model.features.push.PushBody;
import net.impacthub.members.model.vo.conversations.ConversationVO;
import net.impacthub.members.model.vo.conversations.RecipientVO;
import net.impacthub.members.model.vo.notifications.NotificationType;
import net.impacthub.members.presenter.features.messages.ConversationUiContract;
import net.impacthub.members.presenter.features.messages.ConversationUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.widgets.drawables.RoundedDrawable;
import net.impacthub.members.utilities.DrawableUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private String mInReplyTo = "";

    public static ConversationFragment newInstance(ConversationVO model) {
        
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

    @OnClick(R.id.button_send)
    protected void onSendButtonClicked() {
        sendMessage(mMessageField.getText().toString());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_conversation);
        Bundle arguments = getArguments();
        mConversationID = arguments.getString(EXTRA_CONVERSATION_ID);

        mMessageList.setHasFixedSize(true);
        mAdapter = new ConversationListAdapter(getLayoutInflater(getArguments()));
        mMessageList.setAdapter(mAdapter);

        ConversationUiPresenter presenter = getPresenter();
        presenter.getMessageConversations(mConversationID);
    }

    private void sendMessage(String message) {
        String fromUserId = getUserAccount().getUserId();
        String toUserIds = getArguments().getString(EXTRA_CONVERSATION_RECIPIENT_ID);
        String pushType = NotificationType.TYPE_PRIVATE_MESSAGE.getType();

        PushBody pushQuery = new PushBody(fromUserId, toUserIds, pushType, mConversationID);

        getPresenter().sendMessage(mConversationID, pushQuery, message, mInReplyTo);
    }

    @Override
    public void onLoadMessages(ProcessedMessages processedMessages) {
        mAdapter.setItems(processedMessages.getMessages());
        List<RecipientVO> recipients = processedMessages.getRecipients();
        if (recipients != null && recipients.size() > 0) {
            RecipientVO recipientVO = recipients.get(0);
            setUpToolbar(recipientVO.mDisplayName);

            Glide.with(getContext().getApplicationContext()).asBitmap().load(recipientVO.mImageURL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (mToolbar != null) {
                        RoundedDrawable roundedDrawable = new RoundedDrawable(resource);
                        roundedDrawable.setOval(true);
                        int thumbnailSize = getResources().getDimensionPixelOffset(R.dimen.toolbar_thumbnail_size);
                        Drawable drawable = DrawableUtils.resize(getResources(), roundedDrawable.toBitmap(), thumbnailSize, thumbnailSize);
                        mToolbar.setLogo(drawable);
                    }
                }
            });
        }
        mInReplyTo = processedMessages.getInReplyTo();
    }

    @Override
    public void onClearTextField() {
        mMessageField.setText(null);
    }
}
