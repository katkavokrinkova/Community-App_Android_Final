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

import net.impacthub.app.model.pojo.Searchable;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class NotificationVO implements Searchable {

    public String mMessage;
    public Boolean mIsRead;
    public String mId;
    public String mRelatedId;
    public String mChatterGroupId;
    public String mRecipientUserId;
    public String mDisplayName;
    public String mProfilePicUrl;
    public String mCreatedDate;
    public NotificationType mNotificationType;

    @Override
    public boolean isSearchable(String query) {
        return false;
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        return false;
    }
}
