
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

package net.impacthub.members.model.features.messages;

public class Photo {

    private String fullEmailPhotoUrl;
    private String largePhotoUrl;
    private String smallPhotoUrl;
    private String standardEmailPhotoUrl;
    private String photoVersionId;
    private String mediumPhotoUrl;

    private String url;

    public String getMediumPhotoUrl() {
        return mediumPhotoUrl;
    }

    public String getPhotoVersionId() {
        return photoVersionId;
    }

    public String getSmallPhotoUrl() {
        return smallPhotoUrl;
    }

    public String getStandardEmailPhotoUrl() {
        return standardEmailPhotoUrl;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Photo [fullEmailPhotoUrl = " + fullEmailPhotoUrl + ", largePhotoUrl = " + largePhotoUrl + ", smallPhotoUrl = " + smallPhotoUrl + ", standardEmailPhotoUrl = " + standardEmailPhotoUrl + ", photoVersionId = " + photoVersionId + ", mediumPhotoUrl = " + mediumPhotoUrl + ", url = " + url + "]";
    }
}
