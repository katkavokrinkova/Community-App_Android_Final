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

public class Likes {

    private String total;
    private String previousPageUrl;
    private String currentPageUrl;
    private String nextPageUrl;
    private String[] items;
    private String previousPageToken;
    private String nextPageToken;
    private String currentPageToken;

    @Override
    public String toString() {
        return "Likes [total = " + total + ", previousPageUrl = " + previousPageUrl + ", currentPageUrl = " + currentPageUrl + ", nextPageUrl = " + nextPageUrl + ", items = " + items + ", previousPageToken = " + previousPageToken + ", nextPageToken = " + nextPageToken + ", currentPageToken = " + currentPageToken + "]";
    }
}
