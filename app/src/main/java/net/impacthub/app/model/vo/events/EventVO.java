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

package net.impacthub.app.model.vo.events;

import net.impacthub.app.model.pojo.Filterable;
import net.impacthub.app.utilities.TextUtils;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventVO implements Filterable {

    public String mId;
    public String mName;
    public String mImageURL;
    public boolean mAttending;
    public String mDescription;
    public String mCity;
    public String mDate;
    public String mTime;
    public String mTimeFromTo;
    public String mOrganizerName;
    public String mRegisteredLink;
    public String mType;
    public String mSubType;
    public String mVisibilityPrice;
    public String mZipCode;
    public String mStreet;
    public String mCountry;

    @Override
    public boolean isFilterable(String query) {
        return TextUtils.contains(query, mName, mOrganizerName, mCountry, mStreet, mCity, mSubType);
    }
}
