package net.impacthub.app.model.features.filters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.impacthub.app.model.pojo.Filterable;

import java.io.Serializable;

public class Filter implements Serializable, Filterable {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Grouping__c")
    @Expose
    private String grouping;
    @SerializedName("Id")
    @Expose
    private String id;

    private boolean selected;

    public String getName() {
        return name;
    }

    public String getGrouping() {
        return grouping;
    }

    public String getId() {
        return id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isFilterable(String query) {
        return false;
    }
}