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

package net.impacthub.app.model.vo.contacts;

import net.impacthub.app.model.pojo.Searchable;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.utilities.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ContactVO implements Searchable {

    public String mIntroMessage;
    public String mCreatedDate;
    public MemberVO mMember;

    @Override
    public boolean isSearchable(String query) {
        return TextUtils.contains(query, mIntroMessage) || mMember.isSearchable(query);
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        return TextUtils.contains(filters);
    }
}
