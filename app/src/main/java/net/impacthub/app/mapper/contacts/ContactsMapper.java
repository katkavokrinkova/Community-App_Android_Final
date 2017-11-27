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

import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.contacts.ContactsResponse;
import net.impacthub.app.model.features.contacts.ContactRecords;
import net.impacthub.app.model.features.members.MembersResponse;
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
        if (memberVOs != null) {

            Map<String, ContactRecords> contactFromRecordsMap = new HashMap<>();
            Map<String, ContactRecords> contactToRecordsMap = new HashMap<>();

            if (contactsResponse != null) {
                ContactRecords[] records = contactsResponse.getRecords();
                if (records != null) {
                    for (ContactRecords record : records) {
                        contactFromRecordsMap.put(record.getContactFrom__c(), record);
                        contactToRecordsMap.put(record.getContactTo__c(), record);
                    }
                }
            }

            for (MemberVO memberVO : memberVOs) {
                String memberContactId = memberVO.mContactId;
                if (memberContactId.equals(contactId)) {
                    continue;
                }
                ContactVO contactVO = new ContactVO();
                ContactRecords contactRecordTo = contactToRecordsMap.get(memberContactId);

                if (contactRecordTo != null) {

                    String status__c = contactRecordTo.getStatus__c();
                    contactVO.mCreatedDate = contactRecordTo.getCreatedDate();

                    if ("Approved".equalsIgnoreCase(status__c)) {
                        memberVO.mMemberStatus = MemberStatus.APPROVED;
                        memberVO.mDM_ID = contactRecordTo.getId();
                        contactVO.mMember = memberVO;
                        contactsWrapper.getApprovedContacts().add(contactVO);
                    }
                } else {
                    ContactRecords contactRecordFrom = contactFromRecordsMap.get(memberContactId);
                    if (contactRecordFrom != null) {
                        String status__c = contactRecordFrom.getStatus__c();
                        contactVO.mCreatedDate = contactRecordFrom.getCreatedDate();

                        if ("Approved".equalsIgnoreCase(status__c)) {
                            memberVO.mMemberStatus = MemberStatus.APPROVED;
                            memberVO.mDM_ID = contactRecordFrom.getId();
                            contactVO.mMember = memberVO;
                            contactsWrapper.getApprovedContacts().add(contactVO);
                        } else if ("Declined".equalsIgnoreCase(status__c)) {
                            memberVO.mMemberStatus = MemberStatus.DECLINED;
                            memberVO.mDM_ID = contactRecordFrom.getId();
                            contactVO.mMember = memberVO;
                            contactsWrapper.getDeclinedContacts().add(contactVO);
                        } else if ("Outstanding".equalsIgnoreCase(status__c)) {
                            contactVO.mIntroMessage = contactRecordFrom.getIntroduction_Message__c();
                            memberVO.mMemberStatus = MemberStatus.OUTSTANDING;
                            memberVO.mDM_ID = contactRecordFrom.getId();
                            contactVO.mMember = memberVO;
                            contactsWrapper.getOutstandingContacts().add(contactVO);
                        }
                    }
                }
            }
        }
        return contactsWrapper;
    }

    public ContactVO mapMemberContact(ContactsResponse contactsResponse, MembersResponse membersResponse, String subject) {

        Map<String, ContactRecords> contactFromRecordsMap = new HashMap<>();
        Map<String, ContactRecords> contactToRecordsMap = new HashMap<>();

        if (contactsResponse != null) {
            ContactRecords[] records = contactsResponse.getRecords();
            if (records != null) {
                for (ContactRecords record : records) {
                    contactFromRecordsMap.put(record.getContactFrom__c(), record);
                    contactToRecordsMap.put(record.getContactTo__c(), record);
                }
            }
        }

        MemberVO memberVO = new MembersMapper().map(membersResponse);
        String memberContactId = memberVO.mContactId;

        ContactVO contactVO = new ContactVO();
        ContactRecords contactRecordTo = contactToRecordsMap.get(memberContactId);

        if (contactRecordTo != null) {

            String status__c = contactRecordTo.getStatus__c();
            contactVO.mCreatedDate = contactRecordTo.getCreatedDate();

            if ("Approved".equalsIgnoreCase(status__c)) {
                memberVO.mMemberStatus = MemberStatus.APPROVED;
                memberVO.mDM_ID = contactRecordTo.getId();
                contactVO.mMember = memberVO;
            }
        } else {
            ContactRecords contactRecordFrom = contactFromRecordsMap.get(memberContactId);
            if (contactRecordFrom != null) {
                String status__c = contactRecordFrom.getStatus__c();
                contactVO.mCreatedDate = contactRecordFrom.getCreatedDate();

                if ("Approved".equalsIgnoreCase(status__c)) {
                    memberVO.mMemberStatus = MemberStatus.APPROVED;
                    memberVO.mDM_ID = contactRecordFrom.getId();
                    contactVO.mMember = memberVO;
                } else if ("Declined".equalsIgnoreCase(status__c)) {
                    memberVO.mMemberStatus = MemberStatus.DECLINED;
                    memberVO.mDM_ID = contactRecordFrom.getId();
                    contactVO.mMember = memberVO;
                } else if ("Outstanding".equalsIgnoreCase(status__c)) {
                    contactVO.mIntroMessage = contactRecordFrom.getIntroduction_Message__c();
                    memberVO.mMemberStatus = MemberStatus.OUTSTANDING;
                    memberVO.mDM_ID = contactRecordFrom.getId();
                    contactVO.mMember = memberVO;
                }
            }
        }
        return contactVO;
    }
}
