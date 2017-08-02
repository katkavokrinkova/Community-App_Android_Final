package net.impacthub.members.model.members;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Affiliations {

    @SerializedName("records")
    @Expose
    private List<Affiliation> affiliations = null;

    public List<Affiliation> getAffiliations() {
        return affiliations;
    }
}
