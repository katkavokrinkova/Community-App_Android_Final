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

package net.impacthub.members.model.profile;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class ProfileResponse {

    private String done;
    private Records[] records;
    private String totalSize;


    public String getDone() {
        return done;
    }

    public Records[] getRecords() {
        return records;
    }

    public String getTotalSize() {
        return totalSize;
    }

    @Override
    public String toString() {
        return "ProfileResponse [done = " + done + ", records = " + records + ", totalSize = " + totalSize + "]";
    }
}
