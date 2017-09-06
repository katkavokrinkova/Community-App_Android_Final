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

package net.impacthub.members.model.features.companies.services;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/24/2017.
 */

public class Records {

    private String Name;
    private String Service_Description__c;
    private String Id;
    private Attributes attributes;

    public String getName() {
        return Name;
    }

    public String getService_Description__c() {
        return Service_Description__c;
    }

    public String getId() {
        return Id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "GroupsRecords [Name = " + Name + ", Service_Description__c = " + Service_Description__c + ", Id = " + Id + ", attributes = " + attributes + "]";
    }
}
