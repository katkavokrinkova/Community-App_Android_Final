
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

import java.util.List;

public class CommentsPage {

    @SerializedName("items")
    @Expose
    private List<Comment> items = null;
    @SerializedName("nextPageUrl")
    @Expose
    private String nextPageUrl;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<Comment> getComments() {
        return items;
    }

    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    public Integer getTotal() {
        return total;
    }
}
