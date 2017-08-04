package net.impacthub.members.model.members;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Affiliation {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("CountOfMembers__c")
    @Expose
    private Float countOfMembers;
    @SerializedName("ImageURL__c")
    @Expose
    private String imageUrl;
    @SerializedName("Group_Desc__c")
    @Expose
    private String groupDesc;
    @SerializedName("Impact_Hub_Cities__c")
    @Expose
    private String impactHubCities;
    @SerializedName("ChatterGroupId__c")
    @Expose
    private String chatterGroupId;
    @SerializedName("Organisation__r")
    @Expose
    private Organisation organisation;
    @SerializedName("Organisation__c")
    @Expose
    private String organisationId;
    @SerializedName("Directory_Style__c")
    @Expose
    private String directoryStyle;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getCountOfMembers() {
        return countOfMembers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public String getImpactHubCities() {
        return impactHubCities;
    }

    public String getChatterGroupId() {
        return chatterGroupId;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public String getDirectoryStyle() {
        return directoryStyle;
    }
}