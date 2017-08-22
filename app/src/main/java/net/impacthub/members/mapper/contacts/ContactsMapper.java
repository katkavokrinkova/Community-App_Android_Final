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

package net.impacthub.members.mapper.contacts;

import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.vo.contacts.ContactVO;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ContactsMapper {

    public void map(ContactsResponse response) {

    }

    public List<ContactVO> mapContactMembers(ContactsResponse contactsResponse, MembersResponse membersResponse, String userId) {
        return null;
    }
}
