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

package net.impacthub.members.model.features.groups.chatter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/24/2017.
 */

public class Motif {

    private String mediumIconUrl;
    private String color;
    private String svgIconUrl;
    private String smallIconUrl;
    private String largeIconUrl;

    public String getMediumIconUrl() {
        return mediumIconUrl;
    }

    public String getColor() {
        return color;
    }

    public String getSvgIconUrl() {
        return svgIconUrl;
    }

    public String getSmallIconUrl() {
        return smallIconUrl;
    }

    public String getLargeIconUrl() {
        return largeIconUrl;
    }

    @Override
    public String toString() {
        return "Motif [mediumIconUrl = " + mediumIconUrl + ", color = " + color + ", svgIconUrl = " + svgIconUrl + ", smallIconUrl = " + smallIconUrl + ", largeIconUrl = " + largeIconUrl + "]";
    }
}
