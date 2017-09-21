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

package net.impacthub.app.model.features.groups.chatter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/24/2017.
 */

public class MySubscription {

    private String id;
    private String url;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "MySubscription [id = " + id + ", url = " + url + "]";
    }
}
