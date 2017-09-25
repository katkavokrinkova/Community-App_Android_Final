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

package net.impacthub.app.utilities;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/25/2017.
 */

public final class TextUtils {

    private TextUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    public static boolean contains(String query, String... values) {
        boolean atLeastOne = false;
        for (String value : values) {
            if (value != null && value.toLowerCase().contains(query)) {
                atLeastOne = true;
                break;
            }
        }
        return atLeastOne;
    }
}