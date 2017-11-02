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

package net.impacthub.app.mapper.contacts;

import net.impacthub.app.model.features.contacts.ContactsResponse;
import net.impacthub.app.model.features.contacts.ContactRecords;
import net.impacthub.app.model.vo.contacts.ContactsWrapper;
import net.impacthub.app.model.vo.contacts.ContactVO;
import net.impacthub.app.model.vo.members.MemberStatus;
import net.impacthub.app.model.vo.members.MemberVO;

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
            ContactRecords[] records = contactsResponse.getRecords();
            if (records != null && records.length > 0) {
                Map<String, MemberVO> memberVOMap = mapListAsMapWithId(memberVOs);
                memberVOMap.remove(contactId);
                for (ContactRecords record : records) {

                    String contactTo__c = record.getContactTo__c();
                    String contactFrom__c = record.getContactFrom__c();

                    String status = record.getStatus__c();

                    ContactVO contactVO = new ContactVO();
                    contactVO.mCreatedDate = record.getCreatedDate();

                    if ("Approved".equalsIgnoreCase(status)) {

                        if (memberVOMap.containsKey(contactTo__c)) {
                            MemberVO memberVO = memberVOMap.get(contactTo__c);
                            if (memberVO != null) {
                                memberVO.mMemberStatus = MemberStatus.APPROVED;
                                memberVO.mDM_ID = record.getId();
                                contactVO.mMember = memberVO;
                                contactsWrapper.getApprovedContacts().add(contactVO);
                            }
                        } else if (memberVOMap.containsKey(contactFrom__c)) {
                            MemberVO memberVO = memberVOMap.get(contactFrom__c);
                            if (memberVO != null) {
                                memberVO.mMemberStatus = MemberStatus.APPROVED;
                                memberVO.mDM_ID = record.getId();
                                memberVO.mRejectable = true;
                                contactVO.mMember = memberVO;
                                contactsWrapper.getApprovedContacts().add(contactVO);
                            }
                        }
                    } else if ("Declined".equalsIgnoreCase(status) && contactId.equals(contactTo__c)) {
                        MemberVO memberVO = memberVOMap.get(contactFrom__c);
                        if (memberVO != null) {
                            memberVO.mMemberStatus = MemberStatus.DECLINED;
                            memberVO.mDM_ID = record.getId();
                            contactVO.mMember = memberVO;
                            contactsWrapper.getDeclinedContacts().add(contactVO);
                        }
                    } else if ("Outstanding".equalsIgnoreCase(status) && contactId.equals(contactTo__c)) {
                        contactVO.mIntroMessage = record.getIntroduction_Message__c();
                        MemberVO memberVO = memberVOMap.get(contactFrom__c);
                        if (memberVO != null) {
                            memberVO.mMemberStatus = MemberStatus.OUTSTANDING;
                            memberVO.mDM_ID = record.getId();
                            contactVO.mMember = memberVO;
                            contactsWrapper.getOutstandingContacts().add(contactVO);
                        }
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
