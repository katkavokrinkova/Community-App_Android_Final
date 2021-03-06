
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

package net.impacthub.app.model.features.chatterfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Element {

    @SerializedName("actor")
    @Expose
    private Actor actor;
    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("capabilities")
    @Expose
    private Capabilities capabilities;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    private String id;

    public Actor getActor() {
        return actor;
    }

    public Body getBody() {
        return body;
    }

    public String getId() {
        return id;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
