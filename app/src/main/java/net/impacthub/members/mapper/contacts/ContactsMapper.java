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

import android.util.Log;

import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.contacts.Records;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.vo.contacts.ContactVO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class ContactsMapper {

    public void map(ContactsResponse response) {

    }

    public List<ContactVO> mapContactMembers(ContactsResponse contactsResponse, MembersResponse membersResponse, String contactId) {
        List<ContactVO> contactVOs = new LinkedList<>();
        if (contactsResponse != null) {
            Records[] records = contactsResponse.getRecords();
            if (records != null) {
                for (Records record : records) {
                    ContactVO contactVO = new ContactVO();
                    String contactTo__c = record.getContactTo__c();
                    
                    Log.d("FILIPPO-CONTACTS", record.getId() + " -- " + contactId + "Contract from [ " + record.getContactFrom__c() +", " + contactTo__c + " ] || Status -> " + record.getStatus__c() );
                    contactVOs.add(contactVO);
                }
            }
        }
        return contactVOs;
    }
}
