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

package net.impacthub.app.model.features.groups.chatter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/24/2017.
 */

public class Photo {

    private String fullEmailPhotoUrl;
    private String largePhotoUrl;
    private String smallPhotoUrl;
    private String standardEmailPhotoUrl;
    private String photoVersionId;
    private String mediumPhotoUrl;
    private String url;

    public String getFullEmailPhotoUrl() {
        return fullEmailPhotoUrl;
    }

    public String getLargePhotoUrl() {
        return largePhotoUrl;
    }

    public String getSmallPhotoUrl() {
        return smallPhotoUrl;
    }

    public String getStandardEmailPhotoUrl() {
        return standardEmailPhotoUrl;
    }

    public String getPhotoVersionId() {
        return photoVersionId;
    }

    public String getMediumPhotoUrl() {
        return mediumPhotoUrl;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Photo [fullEmailPhotoUrl = " + fullEmailPhotoUrl + ", largePhotoUrl = " + largePhotoUrl + ", smallPhotoUrl = " + smallPhotoUrl + ", standardEmailPhotoUrl = " + standardEmailPhotoUrl + ", photoVersionId = " + photoVersionId + ", mediumPhotoUrl = " + mediumPhotoUrl + ", url = " + url + "]";
    }
}
