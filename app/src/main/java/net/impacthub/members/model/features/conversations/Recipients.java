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

package net.impacthub.members.model.features.conversations;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class Recipients {

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

    @Override
    public String toString() {
        return "Recipients [lastName = " + lastName + ", additionalLabel = " + additionalLabel + ", isInThisCommunity = " + isInThisCommunity + ", communityNickname = " + communityNickname + ", type = " + type + ", companyName = " + companyName + ", photo = " + photo + ", url = " + url + ", motif = " + motif + ", userType = " + userType + ", id = " + id + ", isActive = " + isActive + ", title = " + title + ", reputation = " + reputation + ", name = " + name + ", stamps = " + stamps + ", displayName = " + displayName + ", firstName = " + firstName + ", mySubscription = " + mySubscription + "]";
    }
}
