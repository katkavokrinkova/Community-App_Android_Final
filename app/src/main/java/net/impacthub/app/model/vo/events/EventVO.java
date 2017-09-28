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

import net.impacthub.app.model.pojo.Searchable;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.utilities.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventVO implements Searchable {

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
    public String mSector;

    @Override
    public boolean isSearchable(String query) {
        return TextUtils.contains(query, mName, mOrganizerName, mCountry, mStreet, mSubType);
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        List<String> cityFilters = filters.get(FilterData.KEY_FILTER_CITY);
        List<String> sectorsFilters = filters.get(FilterData.KEY_FILTER_SECTOR);
        return TextUtils.contains(mCity, cityFilters) | TextUtils.contains(mSector, sectorsFilters);
    }
}
