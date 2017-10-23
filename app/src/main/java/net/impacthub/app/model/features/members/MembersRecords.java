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

package net.impacthub.app.model.features.members;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class MembersRecords {

    private String Twitter__c;
    private String Directory_Summary__c;
    private String Skills__c;
    private String Profession__c;
    private String ProfilePic__c;
    private String Instagram__c;
    private String User__c;
    private String LastName;
    private String Name;
    private String Sector__c;
    private String How_Do_You_Most_Identify_with_Your_Curre__c;
    private String Interested_SDG__c;
    private String Linked_In__c;
    private String Status_Update__c;
    private String FirstName;
    private String Id;
    private Attributes attributes;
    private String Facebook__c;
    private String AboutMe__c;
    private String Impact_Hub_Cities__c;
    private Hubs__r Hubs__r;
    private Account Account;

    public Hubs__r getHubs__r() {
        return Hubs__r;
    }

    public String getTwitter__c() {
        return Twitter__c;
    }

    public String getDirectory_Summary__c() {
        return Directory_Summary__c;
    }

    public String getSkills__c() {
        return Skills__c;
    }

    public String getProfession__c() {
        return Profession__c;
    }

    public String getProfilePic__c() {
        return ProfilePic__c;
    }

    public String getInstagram__c() {
        return Instagram__c;
    }

    public String getUser__c() {
        return User__c;
    }

    public String getLastName() {
        return LastName;
    }

    public String getHow_Do_You_Most_Identify_with_Your_Curre__c() {
        return How_Do_You_Most_Identify_with_Your_Curre__c;
    }

    public String getInterested_SDG__c() {
        return Interested_SDG__c;
    }

    public String getLinked_In__c() {
        return Linked_In__c;
    }

    public String getStatus_Update__c() {
        return Status_Update__c;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getId() {
        return Id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getFacebook__c() {
        return Facebook__c;
    }

    public String getImpact_Hub_Cities__c() {
        return Impact_Hub_Cities__c;
    }

    public String getAboutMe__c() {
        return AboutMe__c;
    }

    public String getName() {
        return Name;
    }

    public String getSector__c() {
        return Sector__c;
    }

    public Account getAccount() {
        return Account;
    }

    @Override
    public String toString() {
        return "GroupsRecords [Twitter__c = " + Twitter__c + ", Directory_Summary__c = " + Directory_Summary__c + ", Skills__c = " + Skills__c + ", Profession__c = " + Profession__c + ", ProfilePic__c = " + ProfilePic__c + ", Instagram__c = " + Instagram__c + ", User__c = " + User__c + ", LastName = " + LastName + ", How_Do_You_Most_Identify_with_Your_Curre__c = " + How_Do_You_Most_Identify_with_Your_Curre__c + ", Interested_SDG__c = " + Interested_SDG__c + ", Linked_In__c = " + Linked_In__c + ", Status_Update__c = " + Status_Update__c + ", FirstName = " + FirstName + ", Id = " + Id + ", attributes = " + attributes + ", Facebook__c = " + Facebook__c + ", About_Me__c = " + AboutMe__c + ", Impact_Hub_Cities__c = " + Impact_Hub_Cities__c + "]";
    }
}
