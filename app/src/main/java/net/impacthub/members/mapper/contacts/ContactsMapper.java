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
import net.impacthub.members.model.features.contacts.Records;
import net.impacthub.members.model.vo.contacts.ContactsWrapper;
import net.impacthub.members.model.vo.contacts.ContactVO;
import net.impacthub.members.model.vo.members.MemberVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ContactsMapper {

    public ContactsWrapper mapContactMembers(ContactsResponse contactsResponse, List<MemberVO> memberVOs, String contactId) {
        ContactsWrapper contactsWrapper = new ContactsWrapper();
        if (contactsResponse != null) {
            Records[] records = contactsResponse.getRecords();
            if (records != null && records.length > 0) {
                Map<String, MemberVO> memberVOMap = mapListAsMapWithId(memberVOs);
                memberVOMap.remove(contactId);
                for (Records record : records) {

                    String contactTo__c = record.getContactTo__c();
                    String contactFrom__c = record.getContactFrom__c();

                    String status = record.getStatus__c();

                    ContactVO contactVO = new ContactVO();
                    contactVO.mDM_Id = record.getId();
                    contactVO.mCreatedDate = record.getCreatedDate();

                    if ("Approved".equalsIgnoreCase(status)) {
                        if (contactId.equals(contactTo__c)) {
                            contactVO.mMember = memberVOMap.get(contactFrom__c);
                        } else {
                            contactVO.mMember = memberVOMap.get(contactTo__c);
                        }
                        contactsWrapper.getApprovedContacts().add(contactVO);
                    } else if ("Declined".equalsIgnoreCase(status) && contactId.equals(contactTo__c)) {
                        contactVO.mMember = memberVOMap.get(contactFrom__c);
                        contactsWrapper.getDeclinedContacts().add(contactVO);
                    } else if ("Outstanding".equalsIgnoreCase(status) && contactId.equals(contactTo__c)) {
                        contactVO.mIntroMessage = record.getIntroduction_Message__c();
                        contactVO.mMember = memberVOMap.get(contactFrom__c);
                        contactsWrapper.getOutstandingContacts().add(contactVO);
                    }
                }
            }
        }
        return contactsWrapper;
    }

    private Map<String, MemberVO> mapListAsMapWithId(List<MemberVO> memberVOs) {
        Map<String, MemberVO> map = new HashMap<>();
        for (MemberVO memberVO : memberVOs) {
            map.put(memberVO.mContactId, memberVO);
        }
        return map;
    }
}
