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

public class Southwest {

    private String lng;
    private String lat;

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return "Southwest [lng = " + lng + ", lat = " + lat + "]";
    }
}
