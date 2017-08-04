
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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("mediumPhotoUrl")
    @Expose
    private String mediumPhotoUrl;
    @SerializedName("photoVersionId")
    @Expose
    private String photoVersionId;
    @SerializedName("smallPhotoUrl")
    @Expose
    private String smallPhotoUrl;
    @SerializedName("standardEmailPhotoUrl")
    @Expose
    private String standardEmailPhotoUrl;
    @SerializedName("url")
    @Expose
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
}
