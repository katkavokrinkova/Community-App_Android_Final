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

package net.impacthub.app.model.features.chatterfeed.comment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/25/2017.
 */

public class CommentResponse {

    private Body body;
    private String isDeleteRestricted;
    private Parent parent;
    private String type;
    private ClientInfo clientInfo;
    private String url;
    private FeedElement feedElement;
    private String id;
    private MyLike myLike;
    private Likes likes;
    private String relativeCreatedDate;
    private Capabilities capabilities;
    private User user;
    private String createdDate;
    private ModerationFlags moderationFlags;
    private String likesMessage;

    public String getCreatedDate() {
        return createdDate;
    }

    public User getUser() {
        return user;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "CommentResponse [body = " + body + ", isDeleteRestricted = " + isDeleteRestricted + ", parent = " + parent + ", type = " + type + ", clientInfo = " + clientInfo + ", url = " + url + ", feedElement = " + feedElement + ", id = " + id + ", myLike = " + myLike + ", likes = " + likes + ", relativeCreatedDate = " + relativeCreatedDate + ", capabilities = " + capabilities + ", user = " + user + ", createdDate = " + createdDate + ", moderationFlags = " + moderationFlags + ", likesMessage = " + likesMessage + "]";
    }
}
