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

package net.impacthub.app.model.vo.members;

import net.impacthub.app.model.pojo.Searchable;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.utilities.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class MemberVO implements Searchable {

    @MemberStatus
    public int mMemberStatus = MemberStatus.NOT_CONTACTED;

    public String mUserId;
    public String mContactId;
    public String mDM_ID;
    public String mFirstName;
    public String mLastName;
    public String mFullName;
    public String mProfilePicURL;
    public String mLinkInstagram;
    public String mLinkFacebook;
    public String mLinkTwitter;
    public String mLinkLinkedin;
    public String mLocation;
    public String mAboutMe;
    public String mProfession;
    public String mStatusUpdate;
    public String mSector;
    public String mHubCities;
    public boolean mRejectable;
    public String mCompanyName;

    @Override
    public boolean isSearchable(String query) {
        return TextUtils.contains(query, mFullName, mProfession);
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        List<String> hubFilters = filters.get(FilterData.KEY_FILTER_HUB);
        List<String> sectorsFilters = filters.get(FilterData.KEY_FILTER_SECTOR);
        return TextUtils.contains(mLocation, hubFilters) | TextUtils.contains(mSector, sectorsFilters);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        MemberVO memberVO = (MemberVO) obj;
        if (TextUtils.equals(this.mUserId, memberVO.mUserId)) {
            return true;
        }
        return false;
    }
}
