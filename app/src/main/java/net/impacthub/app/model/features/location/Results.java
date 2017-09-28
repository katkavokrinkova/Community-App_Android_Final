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

public class Results {

    private String partial_match;
    private String place_id;
    private Address_components[] address_components;
    private String formatted_address;
    private String[] types;
    private Geometry geometry;

    public String getPartial_match() {
        return partial_match;
    }

    public String getPlace_id() {
        return place_id;
    }

    public Address_components[] getAddress_components() {
        return address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public String[] getTypes() {
        return types;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public String toString() {
        return "Results [partial_match = " + partial_match + ", place_id = " + place_id + ", address_components = " + address_components + ", formatted_address = " + formatted_address + ", types = " + types + ", geometry = " + geometry + "]";
    }
}
