package net.impacthub.members.model.members;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Members {

    @SerializedName("records")
    @Expose
    private List<Member> members;

    public List<Member> getMembers() {
        return members;
    }
}