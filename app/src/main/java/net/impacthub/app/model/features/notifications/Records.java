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

package net.impacthub.app.model.features.notifications;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class Records {

    private String Name;
    private String ProfilePicURL__c;
    private String FromUserId__c;
    private String RelatedId__c;
    private String Message__c;
    private Boolean isRead__c;
    private String Sent__c;
    private String CreatedDate;
    private String ChatterGroupId__c;
    private String Type__c;
    private String Id;
    private Attributes attributes;

    public String getName() {
        return Name;
    }

    public String getProfilePicURL__c() {
        return ProfilePicURL__c;
    }

    public String getFromUserId__c() {
        return FromUserId__c;
    }

    public String getRelatedId__c() {
        return RelatedId__c;
    }

    public String getMessage__c() {
        return Message__c;
    }

    public Boolean getIsRead__c() {
        return isRead__c;
    }

    public String getSent__c() {
        return Sent__c;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getChatterGroupId__c() {
        return ChatterGroupId__c;
    }

    public String getType__c() {
        return Type__c;
    }

    public String getId() {
        return Id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public String toString()
    {
        return "GroupsRecords [Name = "+Name+", ProfilePicURL__c = "+ProfilePicURL__c+", FromUserId__c = "+FromUserId__c+", RelatedId__c = "+RelatedId__c+", Message__c = "+Message__c+", isRead__c = "+isRead__c+", Sent__c = "+Sent__c+", CreatedDate = "+CreatedDate+", ChatterGroupId__c = "+ChatterGroupId__c+", Type__c = "+Type__c+", Id = "+Id+", attributes = "+attributes+"]";
    }
}
