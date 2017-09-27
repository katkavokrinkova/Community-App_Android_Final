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

package net.impacthub.app.model.vo.companies;

import net.impacthub.app.model.pojo.Searchable;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.utilities.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class CompanyVO implements Searchable {

    public String mCompanyId;
    public String mCompanyName;

    public String mLinkInstagram;
    public String mLinkFacebook;
    public String mLinkTwitter;
    public String mLinkLinkedin;

    public String mCompanyDescription;
    public String mCompanySector;
    public String mCompanyLogo;
    public String mCompanyBanner;
    public String mCompanyMemberCount;
    public String mCompanyLocation;
    public String mCompanyWebsite;
    public String mHub;

    @Override
    public boolean isSearchable(String query) {
        return TextUtils.contains(query, mCompanyName, mCompanyLocation, mCompanyWebsite, mCompanyDescription);
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        List<String> hubFilters = filters.get(FilterData.KEY_FILTER_CITY);
        List<String> sectorsFilters = filters.get(FilterData.KEY_FILTER_SECTOR);
        return TextUtils.contains(mCompanyLocation, hubFilters) | TextUtils.contains(mCompanySector, sectorsFilters);
    }
}
