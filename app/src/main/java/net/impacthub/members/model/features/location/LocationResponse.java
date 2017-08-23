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

package net.impacthub.members.model.features.location;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/23/2017.
 */

public class LocationResponse {

    private Results[] results;
    private String status;

    public Results[] getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LocationResponse [results = " + results + ", status = " + status + "]";
    }
}
