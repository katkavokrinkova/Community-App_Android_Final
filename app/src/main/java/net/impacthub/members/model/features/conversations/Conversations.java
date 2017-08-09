
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

public class Conversations {

    private String id;
    private LatestMessage latestMessage;
    private String read;
    private String url;
    private Members[] members;

    public String getId() {
        return id;
    }

    public LatestMessage getLatestMessage() {
        return latestMessage;
    }

    public String getRead() {
        return read;
    }

    public String getUrl() {
        return url;
    }

    public Members[] getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "Conversations [id = " + id + ", latestMessage = " + latestMessage + ", read = " + read + ", url = " + url + ", members = " + members + "]";
    }
}
