
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

public class Motif {

    private String mediumIconUrl;
    private String color;
    private String svgIconUrl;
    private String smallIconUrl;
    private String largeIconUrl;

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

}
