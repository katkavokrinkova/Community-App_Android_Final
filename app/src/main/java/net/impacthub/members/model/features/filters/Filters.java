package net.impacthub.members.model.features.filters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Filters {

    @SerializedName("records")
    @Expose
    private List<Filter> filters;

    public List<Filter> getFilters() {
        return filters;
    }
}