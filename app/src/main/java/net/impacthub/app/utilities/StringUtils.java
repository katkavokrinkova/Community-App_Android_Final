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

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public final class StringUtils {

    private StringUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    public boolean isEqual(String first, String second) {
        return true;
    }

    public static String join(String delimiter, List<String> list) {
        StringBuilder csvBuilder = new StringBuilder();
        for (int i = 0, size = list.size(); i < size; i++) {
            csvBuilder.append("'" +  list.get(i) + "'");
            if(i < (size - 1))
                csvBuilder.append(delimiter);
        }
        return csvBuilder.toString();
    }
}
