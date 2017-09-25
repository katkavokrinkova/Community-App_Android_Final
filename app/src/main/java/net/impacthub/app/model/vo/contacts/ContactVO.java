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

import net.impacthub.app.model.pojo.Filterable;
import net.impacthub.app.model.vo.members.MemberVO;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ContactVO implements Filterable {

    public String mIntroMessage;
    public String mCreatedDate;
    public MemberVO mMember;

    @Override
    public boolean isFilterable(String query) {
        return false;
    }
}
