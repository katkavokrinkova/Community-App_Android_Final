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
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public final class DateUtils {

    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String UTC_DATE_TIME_FORMAT_ISO_8601_1 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String UTC_DATE_TIME_FORMAT_ISO_8601_2 = "yyyy-MM-dd'T'HH:mm'Z'";
    public static final String UTC_DATE_TIME_FORMAT_ISO_8601_3 = "yyyy-MM-dd'T'HH:mmZ";
    public static final String UTC_DATE_TIME_FORMAT_ISO_8601_4 = "yyyy-MM-dd'T'HH:mm:ssX";
    public static final String UTC_DATE_TIME_FORMAT_ISO_8601_5 = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String UTC_DATE_TIME_FORMAT_ISO_8601_6 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final String SIMPLE_FORMAT = "yyyy-MM-dd";
    public static final String DAY_MONTH_FORMAT = "dd MMM";
    public static final String DAY_MONTH_YEAR_FORMAT = "dd MMM yyyy";
    public static final String TIME_FORMAT_12_HOUR = "ha";

    private DateUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    public static String getShortDate() {
        return new SimpleDateFormat(SIMPLE_FORMAT).format(new Date());
    }

    public static String getIso_8601Date() {
        return new SimpleDateFormat(UTC_DATE_TIME_FORMAT_ISO_8601_6).format(new Date());
    }

    public static String getStringFromDate(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.UK);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }
}
