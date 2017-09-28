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

package net.impacthub.app.model.features.location;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/23/2017.
 */

public class Geometry {

    private Viewport viewport;
    private String location_type;
    private Location location;

    public Viewport getViewport() {
        return viewport;
    }

    public String getLocation_type() {
        return location_type;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Geometry [viewport = " + viewport + ", location_type = " + location_type + ", location = " + location + "]";
    }
}
