
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

public class Comment {

    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("likes")
    @Expose
    private LikesPage likes;
    @SerializedName("myLike")
    @Expose
    private MyLike myLike;
    @SerializedName("user")
    @Expose
    private Actor user;

    public Body getBody() {
        return body;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getId() {
        return id;
    }

    public LikesPage getLikes() {
        return likes;
    }

    public Object getMyLike() {
        return myLike;
    }

    public Actor getActor() {
        return user;
    }
}
