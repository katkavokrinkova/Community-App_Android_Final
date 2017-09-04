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

package net.impacthub.members.model.features.contacts;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class Attributes {

    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Attributes [type = " + type + ", url = " + url + "]";
    }
}
