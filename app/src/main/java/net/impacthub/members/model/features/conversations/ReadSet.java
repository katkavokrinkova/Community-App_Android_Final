
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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadSet {

    @SerializedName("read")
    @Expose
    private Boolean read;

    public Boolean getRead() {
        return read;
    }
}
