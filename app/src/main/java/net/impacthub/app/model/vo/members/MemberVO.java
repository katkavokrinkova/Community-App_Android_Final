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

import net.impacthub.app.model.pojo.Filterable;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class MemberVO implements Filterable {

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

    @Override
    public boolean isFilterable(String query) {
        return mFullName.toLowerCase().contains(query) || mProfession.toLowerCase().contains(query);
    }
}
