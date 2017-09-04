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

package net.impacthub.members.model.features.groups.chatter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/24/2017.
 */

public class Groups {

    private String additionalLabel;
    private String emailToChatterAddress;
    private String visibility;
    private String myRole;
    private Community community;
    private String type;
    private Photo photo;
    private String url;
    private String memberCount;
    private Motif motif;
    private String canHaveChatterGuests;
    private String id;
    private String name;
    private Owner owner;
    private MySubscription mySubscription;

    public String getAdditionalLabel() {
        return additionalLabel;
    }

    public String getEmailToChatterAddress() {
        return emailToChatterAddress;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getMyRole() {
        return myRole;
    }

    public Community getCommunity() {
        return community;
    }

    public String getType() {
        return type;
    }

    public Photo getPhoto() {
        return photo;
    }

    public String getUrl() {
        return url;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public Motif getMotif() {
        return motif;
    }

    public String getCanHaveChatterGuests() {
        return canHaveChatterGuests;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }

    public MySubscription getMySubscription() {
        return mySubscription;
    }

    @Override
    public String toString() {
        return "Groups [additionalLabel = " + additionalLabel + ", emailToChatterAddress = " + emailToChatterAddress + ", visibility = " + visibility + ", myRole = " + myRole + ", community = " + community + ", type = " + type + ", photo = " + photo + ", url = " + url + ", memberCount = " + memberCount + ", motif = " + motif + ", canHaveChatterGuests = " + canHaveChatterGuests + ", id = " + id + ", name = " + name + ", owner = " + owner + ", mySubscription = " + mySubscription + "]";
    }
}
