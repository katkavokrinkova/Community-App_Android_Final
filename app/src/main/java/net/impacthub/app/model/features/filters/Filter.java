package net.impacthub.app.model.features.filters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filter {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Grouping__c")
    @Expose
    private String grouping;
    @SerializedName("Id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public String getGrouping() {
        return grouping;
    }

    public String getId() {
        return id;
    }
}