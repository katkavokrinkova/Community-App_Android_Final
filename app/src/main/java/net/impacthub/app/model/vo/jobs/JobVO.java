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

package net.impacthub.app.model.vo.jobs;

import net.impacthub.app.model.pojo.Searchable;
import net.impacthub.app.utilities.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class JobVO implements Searchable {

    public String mJobId;
    public String mName;
    public String mJobType;
    public String mLocation;
    public String mCompanyName;
    public String mLogoURL;
    public String mDescription;
    public String mSalary;
    public String mMemberCount;
    public String mBannerImageURL;
    public String mWebsite;
    public String mCompanyC;
    public String mAccountId;
    public String mSector;

    @Override
    public boolean isSearchable(String query) {
        return TextUtils.contains(query, mName, mCompanyName, mLocation, mCompanyC, mJobType);
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        return false;
    }
}
