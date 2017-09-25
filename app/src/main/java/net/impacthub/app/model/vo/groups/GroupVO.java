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

package net.impacthub.app.model.vo.groups;

import net.impacthub.app.model.pojo.Filterable;
import net.impacthub.app.utilities.TextUtils;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class GroupVO implements Filterable {

    public String mName;
    public String mGroupDescription;
    public String mImageURL;
    public String mCities;
    public String mMemberCount;
    public String mChatterGroupId;

    @Override
    public boolean isFilterable(String query) {
        return TextUtils.contains(query, mName, mCities, mGroupDescription);
    }
}
