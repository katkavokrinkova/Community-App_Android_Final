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

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.contacts.Records;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.vo.contacts.ContactVO;
import net.impacthub.members.model.vo.members.MemberVO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ContactsMapper {

    public Map<String, List<ContactVO>> mapContactMembers(ContactsResponse contactsResponse, MembersResponse membersResponse, String contactId) {
        Map<String, List<ContactVO>> contactsMap = new LinkedHashMap<>();
        if (contactsResponse != null) {
            Records[] records = contactsResponse.getRecords();
            if (records != null && records.length > 0) {
                List<MemberVO> memberVOs = new MembersMapper().mapMembers(membersResponse);
                Map<String, MemberVO> memberVOMap = mapListAsMapWithId(memberVOs);
                memberVOMap.remove(contactId);
                for (Records record : records) {

                    String contactTo__c = record.getContactTo__c();
                    String contactFrom__c = record.getContactFrom__c();

                    String status = record.getStatus__c();
                    List<ContactVO> contactVOList = contactsMap.get(status);
                    if (contactVOList == null) {
                        contactVOList = new LinkedList<>();
                    }

                    ContactVO contactVO = new ContactVO();

                    if("Approved".equalsIgnoreCase(status)) {
                        if (!contactId.equals(contactTo__c)) {
                            contactVO.mMember = memberVOMap.get(contactTo__c);
                        } else {
                            contactVO.mMember = memberVOMap.get(contactFrom__c);
                        }
                        contactVOList.add(contactVO);
                    } else if("Declined".equalsIgnoreCase(status) && contactId.equals(contactTo__c)) {
                        contactVO.mMember = memberVOMap.get(contactFrom__c);
                        contactVOList.add(contactVO);
                    } else if("Outstanding".equalsIgnoreCase(status)  && contactId.equals(contactTo__c)) {
                        contactVO.mIntroMessage = record.getIntroduction_Message__c();
                        contactVO.mMember = memberVOMap.get(contactFrom__c);
                        contactVOList.add(contactVO);
                    }
                    contactsMap.put(status, contactVOList);
                }
            }
        }
        return contactsMap;
    }

    private Map<String, MemberVO> mapListAsMapWithId(List<MemberVO> memberVOs) {
        Map<String, MemberVO> map = new HashMap<>();
        for (MemberVO memberVO : memberVOs) {
            map.put(memberVO.mMemberId, memberVO);
        }
        return map;
    }
}
