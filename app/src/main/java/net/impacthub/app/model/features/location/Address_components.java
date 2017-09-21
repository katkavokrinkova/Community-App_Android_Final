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

public class Address_components {

    private String long_name;
    private String[] types;
    private String short_name;

    public String getLong_name() {
        return long_name;
    }

    public String[] getTypes() {
        return types;
    }

    public String getShort_name() {
        return short_name;
    }

    @Override
    public String toString() {
        return "Address_components [long_name = " + long_name + ", types = " + types + ", short_name = " + short_name + "]";
    }
}
