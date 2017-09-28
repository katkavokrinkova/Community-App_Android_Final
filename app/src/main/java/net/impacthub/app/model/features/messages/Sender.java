
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

package net.impacthub.app.model.features.messages;

public class Sender {

    private String lastName;
    private String additionalLabel;
    private String isInThisCommunity;
    private String communityNickname;
    private String type;
    private String companyName;
    private Photo photo;
    private String url;
    private Motif motif;
    private String userType;
    private String id;
    private String isActive;
    private String title;
    private Reputation reputation;
    private String name;
    private String[] stamps;
    private String displayName;
    private String firstName;
    private MySubscription mySubscription;

    public String getLastName() {
        return lastName;
    }

    public String getAdditionalLabel() {
        return additionalLabel;
    }

    public String getIsInThisCommunity() {
        return isInThisCommunity;
    }

    public String getCommunityNickname() {
        return communityNickname;
    }

    public String getType() {
        return type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Photo getPhoto() {
        return photo;
    }

    public String getUrl() {
        return url;
    }

    public Motif getMotif() {
        return motif;
    }

    public String getUserType() {
        return userType;
    }

    public String getId() {
        return id;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getTitle() {
        return title;
    }

    public Reputation getReputation() {
        return reputation;
    }

    public String getName() {
        return name;
    }

    public String[] getStamps() {
        return stamps;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public MySubscription getMySubscription() {
        return mySubscription;
    }

    @Override
    public String toString() {
        return "Sender [lastName = " + lastName + ", additionalLabel = " + additionalLabel + ", isInThisCommunity = " + isInThisCommunity + ", communityNickname = " + communityNickname + ", type = " + type + ", companyName = " + companyName + ", photo = " + photo + ", url = " + url + ", motif = " + motif + ", userType = " + userType + ", id = " + id + ", isActive = " + isActive + ", title = " + title + ", reputation = " + reputation + ", name = " + name + ", stamps = " + stamps + ", displayName = " + displayName + ", firstName = " + firstName + ", mySubscription = " + mySubscription + "]";
    }
}
