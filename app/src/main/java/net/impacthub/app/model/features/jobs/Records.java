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

package net.impacthub.app.model.features.jobs;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class Records {

    private String Job_Type__c;
    private String Related_Impact_Goal__c;
    private String Company__c;
    private String Contact__c;
    private String Name;
    private String Applications_Close_Date__c;
    private String Job_Application_URL__c;
    private String Salary2__c;
    private String Sector__c;
    private String Id;
    private Attributes attributes;
    private String Description__c;
    private Company Company__r;
    private String Location__c;

    public String getJob_Type__c() {
        return Job_Type__c;
    }

    public String getRelated_Impact_Goal__c() {
        return Related_Impact_Goal__c;
    }

    public String getCompany__c() {
        return Company__c;
    }

    public String getContact__c() {
        return Contact__c;
    }

    public String getName() {
        return Name;
    }

    public String getApplications_Close_Date__c() {
        return Applications_Close_Date__c;
    }

    public String getJob_Application_URL__c() {
        return Job_Application_URL__c;
    }

    public String getSalary__c() {
        return Salary2__c;
    }

    public String getSector__c() {
        return Sector__c;
    }

    public String getId() {
        return Id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getDescription__c() {
        return Description__c;
    }

    public Company getCompany__r() {
        return Company__r;
    }

    public String getLocation__c() {
        return Location__c;
    }

    @Override
    public String toString() {
        return "GroupsRecords [Job_Type__c = " + Job_Type__c + ", Related_Impact_Goal__c = " + Related_Impact_Goal__c + ", Company__c = " + Company__c + ", Contact__c = " + Contact__c + ", Name = " + Name + ", Applications_Close_Date__c = " + Applications_Close_Date__c + ", Job_Application_URL__c = " + Job_Application_URL__c + ", Salary__c = " + Salary2__c + ", Sector__c = " + Sector__c + ", Id = " + Id + ", attributes = " + attributes + ", Description__c = " + Description__c + ", Company__r = " + Company__r + ", Location__c = " + Location__c + "]";
    }
}
