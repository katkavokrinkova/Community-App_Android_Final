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

package net.impacthub.app.model.vo.notifications;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import net.impacthub.app.R;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public enum  NotificationType {

    TYPE_COMMENT("Comment", "comment", R.mipmap.ic_notification_comment),
    TYPE_POST_MENTION("PostMention", "mention", R.mipmap.ic_notification_mentioned),
    TYPE_LIKE_POST("LikePost", "likePost", R.mipmap.ic_notification_like),
    TYPE_LIKE_COMMENT("LikeComment", "likePost", R.mipmap.ic_notification_like),
    TYPE_PRIVATE_MESSAGE("PrivateMessage", "message", R.mipmap.ic_notification_message),
    TYPE_DM_REQUEST_SENT("DMRequestSent", "likePost", R.mipmap.ic_notification_contact_request),
    TYPE_DM_REQUEST_APPROVED("DMRequestApproved", "likePost", R.mipmap.ic_notification_request_accepted),
    TYPE_UNKNOWN("Unknown", "Unknown", 0);

    private final int mIcon;
    private final String mType;
    private final String mPushType;

    NotificationType(String type, String pushType, @DrawableRes int resId) {
        mType = type;
        mIcon = resId;
        mPushType = pushType;
    }

    public String getType() {
        return mType;
    }

    public String getPushType() {
        return mPushType;
    }

    public int getIcon() {
        return mIcon;
    }

    public static NotificationType fromString(String type) {
        NotificationType theType = NotificationType.TYPE_UNKNOWN;
        NotificationType[] values = values();
        for (NotificationType value : values) {
            if (TextUtils.equals(type, value.getType())) {
                theType = value;
                break;
            }
        }
        return theType;
    }
    public static NotificationType fromPushTypeString(String pushType) {
        NotificationType theType = NotificationType.TYPE_UNKNOWN;
        NotificationType[] values = values();
        for (NotificationType value : values) {
            if (TextUtils.equals(pushType, value.getPushType())) {
                theType = value;
                break;
            }
        }
        return theType;
    }

}
