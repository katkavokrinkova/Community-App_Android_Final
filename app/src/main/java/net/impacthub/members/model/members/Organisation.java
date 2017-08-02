package net.impacthub.members.model.members;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organisation {

    @SerializedName("Name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
}