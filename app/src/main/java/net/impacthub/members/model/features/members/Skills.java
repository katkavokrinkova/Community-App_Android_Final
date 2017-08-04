package net.impacthub.members.model.members;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Skills {

    @SerializedName("records")
    @Expose
    private List<Skill> skills = null;

    public List<Skill> getSkills() {
        return skills;
    }
}