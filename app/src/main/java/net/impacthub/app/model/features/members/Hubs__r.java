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

package net.impacthub.app.model.features.members;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/27/2017.
 */

public class Hubs__r {

    private String done;
    private HubRecords[] records;
    private String totalSize;

    public String getDone() {
        return done;
    }

    public HubRecords[] getRecords() {
        return records;
    }

    public String getTotalSize() {
        return totalSize;
    }

    @Override
    public String toString() {
        return "Hubs__r [done = " + done + ", records = " + records + ", totalSize = " + totalSize + "]";
    }
}
