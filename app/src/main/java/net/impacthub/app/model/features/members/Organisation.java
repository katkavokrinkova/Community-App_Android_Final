package net.impacthub.app.model.features.members;

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