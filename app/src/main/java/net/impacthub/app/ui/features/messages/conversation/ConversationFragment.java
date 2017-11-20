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

package net.impacthub.app.ui.features.messages.conversation;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.impacthub.app.R;
import net.impacthub.app.model.features.conversations.ProcessedMessages;
import net.impacthub.app.model.features.push.PushBody;
import net.impacthub.app.model.vo.conversations.ConversationVO;
import net.impacthub.app.model.vo.conversations.RecipientVO;
import net.impacthub.app.model.vo.notifications.MessageNotificationPayload;
import net.impacthub.app.model.vo.notifications.NotificationType;
import net.impacthub.app.model.vo.notifications.ReceivedNotification;
import net.impacthub.app.presenter.features.messages.ConversationUiContract;
import net.impacthub.app.presenter.features.messages.ConversationUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.PushNotificationObservable;
import net.impacthub.app.ui.widgets.drawables.RoundedDrawable;
import net.impacthub.app.utilities.DrawableUtils;
import net.impacthub.app.utilities.MediaPlayerUtils;
import net.impacthub.app.utilities.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class ConversationFragment extends BaseChildFragment<ConversationUiPresenter> implements ConversationUiContract, PushNotificationObservable.PushNotificationObserver {

    private static final String EXTRA_CONVERSATION_ID = "net.impacthub.members.ui.features.conversation.messages.EXTRA_CONVERSATION_ID";
//    private static final String EXTRA_CONVERSATION_DISPLAY_NAME = "net.impacthub.members.ui.features.conversation.messages.EXTRA_CONVERSATION_DISPLAY_NAME";
//    private static final String EXTRA_CONVERSATION_PROFILE_IMAGE = "net.impacthub.members.ui.features.conversation.messages.EXTRA_CONVERSATION_PROFILE_IMAGE";
    private static final String EXTRA_CONVERSATION_RECIPIENT_ID = "net.impacthub.members.ui.features.conversation.messages.EXTRA_CONVERSATION_RECIPIENT_ID";

    @BindView(R.id.message_items) protected RecyclerView mMessageList;
    @BindView(R.id.message_entry) protected EditText mMessageField;
    @BindView(R.id.button_send) protected ImageView mSendButton;

    private String mConversationID;
    private ConversationListAdapter mAdapter;
    private String mInReplyTo;
    private String mToUserId;

    public static ConversationFragment newInstance(ConversationVO model) {

        Bundle args = new Bundle();
        args.putString(EXTRA_CONVERSATION_ID, model.mConversationId);
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
        mSendButton.setEnabled(false);
        sendMessage(mMessageField.getText().toString());
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.label_conversation);
        Bundle arguments = getArguments();
        mConversationID = arguments.getString(EXTRA_CONVERSATION_ID);

        mMessageList.setHasFixedSize(true);
        mAdapter = new ConversationListAdapter(getIHLayoutInflater());
        mMessageList.setAdapter(mAdapter);

        getPresenter().getMessageConversations(mConversationID, true);
    }

    @Override
    protected void onFragmentVisibilityChanged(boolean isVisible) {
        super.onFragmentVisibilityChanged(isVisible);
        if(isVisible) {
            PushNotificationObservable.getInstance().setNotificationObserver(this);
        } else {
            PushNotificationObservable.getInstance().setNotificationObserver(null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        PushNotificationObservable.getInstance().setNotificationObserver(null);
    }

    private void sendMessage(String message) {
        String fromUserId = getUserAccount().getUserId();
        String toUserIds = mToUserId != null ? mToUserId : getArguments().getString(EXTRA_CONVERSATION_RECIPIENT_ID);
        String pushType = NotificationType.TYPE_PRIVATE_MESSAGE.getType();

        PushBody pushQuery = new PushBody(fromUserId, toUserIds, pushType, mConversationID);

        if (mInReplyTo != null && !mInReplyTo.isEmpty()) {
            getPresenter().sendMessage(mConversationID, pushQuery, message, mInReplyTo);
        } else {
            getPresenter().sendMessageByUserId(pushQuery, message, toUserIds);
        }
    }

    @Override
    public void onLoadMessages(ProcessedMessages processedMessages) {
        mAdapter.setItems(processedMessages.getMessages());
        List<RecipientVO> recipients = processedMessages.getRecipients();
        if (recipients != null && recipients.size() > 0) {
            RecipientVO recipientVO = recipients.get(0);
            setUpToolbar(recipientVO.mDisplayName);
            ImageLoaderHelper.getImageAsBitmap(getContext(), recipientVO.mImageURL, new ImageLoaderHelper.ImageFetchListener() {
                @Override
                public void onImageReady(Bitmap bitmap) {
                    if (mToolbar != null) {
                        RoundedDrawable roundedDrawable = new RoundedDrawable(bitmap);
                        roundedDrawable.setOval(true);
                        int thumbnailSize = getResources().getDimensionPixelOffset(R.dimen.toolbar_thumbnail_size);
                        Drawable drawable = DrawableUtils.resize(getResources(), roundedDrawable.toBitmap(), thumbnailSize, thumbnailSize);
                        mToolbar.setLogo(drawable);
                    }
                }
            });
        }
        mInReplyTo = processedMessages.getInReplyTo();
        mToUserId = processedMessages.getToUserId();
    }

    @Override
    public void onClearTextField() {
        mSendButton.setEnabled(true);
        mMessageField.setText(null);
    }

    @Override
    public void onDismissConversation() {
        popChildFragment();
    }

    @Override
    public void onEnableSendButton() {
        mSendButton.setEnabled(true);
    }

    @Override
    public boolean onConsumePushNotification(ReceivedNotification notification) {
        MessageNotificationPayload mnp = notification.getNotificationPayloadVO();
        boolean isSameConversation = TextUtils.equals(mConversationID, mnp.getConversationId());
        if(isSameConversation) {
            getPresenter().getMessageConversations(mConversationID, false);
            MediaPlayerUtils.get().play(getContext(), "sounds/msg_notification_sound.mp3");
        }
        return isSameConversation;
    }
}
