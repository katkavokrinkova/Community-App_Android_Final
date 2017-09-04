
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

package net.impacthub.members.model.features.chatterfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor {

    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private Photo photo;

    public String getDisplayName() {
        return displayName;
    }

    public Object getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public Object getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public Photo getPhoto() {
        return photo;
    }
}
