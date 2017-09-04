
/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.members.model.features.conversations;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipient {

    @SerializedName("additionalLabel")
    @Expose
    private String additionalLabel;
    @SerializedName("companyName")
    @Expose
    private Object companyName;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("isInThisCommunity")
    @Expose
    private boolean isInThisCommunity;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("motif")
    @Expose
    private Motif motif;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("stamps")
    @Expose
    private List<Object> stamps = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;

    public String getAdditionalLabel() {
        return additionalLabel;
    }

    public void setAdditionalLabel(String additionalLabel) {
        this.additionalLabel = additionalLabel;
    }

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsInThisCommunity() {
        return isInThisCommunity;
    }

    public void setIsInThisCommunity(boolean isInThisCommunity) {
        this.isInThisCommunity = isInThisCommunity;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Motif getMotif() {
        return motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<Object> getStamps() {
        return stamps;
    }

    public void setStamps(List<Object> stamps) {
        this.stamps = stamps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
