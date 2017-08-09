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

package net.impacthub.members.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public final class DateUtils {

    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String SIMPLE_FORMAT = "yyyy-MM-dd";

    private DateUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    public static String getShortDate() {
        return new SimpleDateFormat(SIMPLE_FORMAT).format(new Date());
    }
}
