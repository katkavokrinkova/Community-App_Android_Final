
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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Motif {

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("largeIconUrl")
    @Expose
    private String largeIconUrl;
    @SerializedName("mediumIconUrl")
    @Expose
    private String mediumIconUrl;
    @SerializedName("smallIconUrl")
    @Expose
    private String smallIconUrl;
    @SerializedName("svgIconUrl")
    @Expose
    private Object svgIconUrl;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLargeIconUrl() {
        return largeIconUrl;
    }

    public void setLargeIconUrl(String largeIconUrl) {
        this.largeIconUrl = largeIconUrl;
    }

    public String getMediumIconUrl() {
        return mediumIconUrl;
    }

    public void setMediumIconUrl(String mediumIconUrl) {
        this.mediumIconUrl = mediumIconUrl;
    }

    public String getSmallIconUrl() {
        return smallIconUrl;
    }

    public void setSmallIconUrl(String smallIconUrl) {
        this.smallIconUrl = smallIconUrl;
    }

    public Object getSvgIconUrl() {
        return svgIconUrl;
    }

    public void setSvgIconUrl(Object svgIconUrl) {
        this.svgIconUrl = svgIconUrl;
    }

}
