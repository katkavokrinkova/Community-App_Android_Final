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

package net.impacthub.members.model.features.events;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventRecords {

    private String Event_Visibility__c;
    private String Event_ZipCode__c;
    private String Event_Organiser_Type__c;
    private String Event_Country__c;
    private String Directory__c;
    private String Event_City__c;
    private String OwnerId;
    private String Event_Classification__c;
    private String LastModifiedDate;
    private String Event_RegisterLink__c;
    private String Event_End_DateTime__c;
    private String Event_Sector__c;
    private String Event_Start_DateTime__c;
    private Organiser__r Organiser__r;
    private String Name;
    private String Event_SubType__c;
    private String Event_Street__c;
    private String Event_Type__c;
    private String Event_Quantity__c;
    private String Event_Discount_Code__c;
    private String CreatedDate;
    private String Organiser__c;
    private String Id;
    private Attributes attributes;
    private String Event_Description__c;
    private String Event_Image_URL__c;

    public String getEvent_Visibility__c() {
        return Event_Visibility__c;
    }

    public String getEvent_ZipCode__c() {
        return Event_ZipCode__c;
    }

    public String getEvent_Organiser_Type__c() {
        return Event_Organiser_Type__c;
    }

    public String getEvent_Country__c() {
        return Event_Country__c;
    }

    public String getDirectory__c() {
        return Directory__c;
    }

    public String getEvent_City__c() {
        return Event_City__c;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public String getEvent_Classification__c() {
        return Event_Classification__c;
    }

    public String getLastModifiedDate() {
        return LastModifiedDate;
    }

    public String getEvent_RegisterLink__c() {
        return Event_RegisterLink__c;
    }

    public String getEvent_End_DateTime__c() {
        return Event_End_DateTime__c;
    }

    public String getEvent_Sector__c() {
        return Event_Sector__c;
    }

    public String getEvent_Start_DateTime__c() {
        return Event_Start_DateTime__c;
    }

    public net.impacthub.members.model.features.events.Organiser__r getOrganiser__r() {
        return Organiser__r;
    }

    public String getName() {
        return Name;
    }

    public String getEvent_SubType__c() {
        return Event_SubType__c;
    }

    public String getEvent_Street__c() {
        return Event_Street__c;
    }

    public String getEvent_Type__c() {
        return Event_Type__c;
    }

    public String getEvent_Quantity__c() {
        return Event_Quantity__c;
    }

    public String getEvent_Discount_Code__c() {
        return Event_Discount_Code__c;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getOrganiser__c() {
        return Organiser__c;
    }

    public String getId() {
        return Id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getEvent_Description__c() {
        return Event_Description__c;
    }

    public String getEvent_Image_URL__c() {
        return Event_Image_URL__c;
    }

    @Override
    public String toString() {
        return "GroupsRecords [Event_Visibility__c = " + Event_Visibility__c + ", Event_ZipCode__c = " + Event_ZipCode__c + ", Event_Organiser_Type__c = " + Event_Organiser_Type__c + ", Event_Country__c = " + Event_Country__c + ", Directory__c = " + Directory__c + ", Event_City__c = " + Event_City__c + ", OwnerId = " + OwnerId + ", Event_Classification__c = " + Event_Classification__c + ", LastModifiedDate = " + LastModifiedDate + ", Event_RegisterLink__c = " + Event_RegisterLink__c + ", Event_End_DateTime__c = " + Event_End_DateTime__c + ", Event_Sector__c = " + Event_Sector__c + ", Event_Start_DateTime__c = " + Event_Start_DateTime__c + ", Organiser__r = " + Organiser__r + ", Name = " + Name + ", Event_SubType__c = " + Event_SubType__c + ", Event_Street__c = " + Event_Street__c + ", Event_Type__c = " + Event_Type__c + ", Event_Quantity__c = " + Event_Quantity__c + ", Event_Discount_Code__c = " + Event_Discount_Code__c + ", CreatedDate = " + CreatedDate + ", Organiser__c = " + Organiser__c + ", Id = " + Id + ", attributes = " + attributes + ", Event_Description__c = " + Event_Description__c + "]";
    }
}
