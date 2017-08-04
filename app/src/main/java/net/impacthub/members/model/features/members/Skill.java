package net.impacthub.members.model.features.members;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skill {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Skill_Description__c")
    @Expose
    private String skillDescription;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSkillDescription() {
        return skillDescription;
    }
}