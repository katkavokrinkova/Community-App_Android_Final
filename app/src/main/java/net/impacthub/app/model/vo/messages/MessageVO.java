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

package net.impacthub.app.model.vo.messages;

import net.impacthub.app.model.pojo.Filterable;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class MessageVO implements Filterable {

    public String mConversationId;
    public String mDisplayName;
    public String mRecipientUserId;
    public String mImageURL;
    public String mText;
    public String mDate;
    public Boolean mIsRead;

    @Override
    public boolean isFilterable(String query) {
        return false;
    }
}
