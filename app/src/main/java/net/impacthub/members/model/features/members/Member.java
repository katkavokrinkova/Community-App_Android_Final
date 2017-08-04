package net.impacthub.members.model.members;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("ProfilePic__c")
    @Expose
    private String profilePic;
    @SerializedName("Profession__c")
    @Expose
    private String profession;
    @SerializedName("Impact_Hub_Cities__c")
    @Expose
    private String impactHubCities;
    @SerializedName("User__c")
    @Expose
    private String userId;
    @SerializedName("Skills__c")
    @Expose
    private String skills;
    @SerializedName("About_Me__c")
    @Expose
    private String aboutMe;
    @SerializedName("Twitter__c")
    @Expose
    private String twitter;
    @SerializedName("Instagram__c")
    @Expose
    private String instagram;
    @SerializedName("Facebook__c")
    @Expose
    private String facebook;
    @SerializedName("Linked_In__c")
    @Expose
    private String linkedIn;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getProfession() {
        return profession;
    }

    public String getImpactHubCities() {
        return impactHubCities;
    }

    public String getUserId() {
        return userId;
    }

    public String getSkills() {
        return skills;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getLinkedIn() {
        return linkedIn;
    }
}