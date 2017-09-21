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

package net.impacthub.app.model.features.contacts;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class Records {

    private String Name;
    private String ContactTo__c;
    private String Introduction_Message__c;
    private String CreatedDate;
    private String Id;
    private String ContactFrom__c;
    private Attributes attributes;
    private String Status__c;

    public String getName() {
        return Name;
    }

    public String getContactTo__c() {
        return ContactTo__c;
    }

    public String getIntroduction_Message__c() {
        return Introduction_Message__c;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getId() {
        return Id;
    }

    public String getContactFrom__c() {
        return ContactFrom__c;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getStatus__c() {
        return Status__c;
    }

    @Override
    public String toString() {
        return "GroupsRecords [Name = " + Name + ", ContactTo__c = " + ContactTo__c + ", Introduction_Message__c = " + Introduction_Message__c + ", CreatedDate = " + CreatedDate + ", Id = " + Id + ", ContactFrom__c = " + ContactFrom__c + ", attributes = " + attributes + ", Status__c = " + Status__c + "]";
    }
}
