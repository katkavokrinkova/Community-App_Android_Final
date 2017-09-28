
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

package net.impacthub.app.model.features.messages;

public class Conversations {

    private String id;
    private LatestMessage latestMessage;
    private Boolean read;
    private String url;
    private Members[] members;

    public String getId() {
        return id;
    }

    public LatestMessage getLatestMessage() {
        return latestMessage;
    }

    public Boolean  getRead() {
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
