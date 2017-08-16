package net.impacthub.members.model.features.members;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberResponse {

    @SerializedName("records")
    @Expose
    private List<Member> members;

    public List<Member> getMembers() {
        return members;
    }
}