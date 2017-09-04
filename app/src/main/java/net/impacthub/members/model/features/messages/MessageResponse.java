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

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class MessageResponse {

    private String currentPageUrl;
    private String nextPageUrl;
    private String nextPageToken;
    private Conversations[] conversations;
    private String currentPageToken;

    public String getCurrentPageUrl() {
        return currentPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public Conversations[] getConversations() {
        return conversations;
    }

    public String getCurrentPageToken() {
        return currentPageToken;
    }

    @Override
    public String toString() {
        return "MessageResponse [currentPageUrl = " + currentPageUrl + ", nextPageUrl = " + nextPageUrl + ", nextPageToken = " + nextPageToken + ", conversations = " + conversations + ", currentPageToken = " + currentPageToken + "]";
    }
}
