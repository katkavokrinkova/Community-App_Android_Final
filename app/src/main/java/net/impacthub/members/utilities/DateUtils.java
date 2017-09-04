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

import net.impacthub.members.ui.common.TimeFormatType;

import java.text.ParseException;
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

    public static final String ISO_8601_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String ISO_8601_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String ISO_8601_FORMAT_3 = "yyyy-MM-dd'T'HH:mm'Z'";
    public static final String ISO_8601_FORMAT_4 = "yyyy-MM-dd'T'HH:mmZ";
    public static final String ISO_8601_FORMAT_5 = "yyyy-MM-dd'T'HH:mm:ssX";
    public static final String ISO_8601_FORMAT_6 = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String ISO_8601_FORMAT_7 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String ISO_8601_FORMAT_8 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final String SIMPLE_FORMAT = "yyyy-MM-dd";
    public static final String DAY_MONTH_FORMAT = "dd MMM";
    public static final String DAY_MONTH_YEAR_FORMAT = "dd MMM yyyy";
    public static final String TIME_FORMAT_12_HOUR = "ha";
    public static final String TIME_FORMAT_24_HOUR = "HH:mm";

    private DateUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    public static String getShortDate() {
        return new SimpleDateFormat(SIMPLE_FORMAT, Locale.UK).format(new Date());
    }

    public static String getIso_8601Date() {
        return new SimpleDateFormat(ISO_8601_FORMAT_8, Locale.UK).format(new Date());
    }

    public static String getStringFromDate(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.UK);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }

    public static Date getDate(String format, String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.UK);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.parse(date);
    }

    public static String getElapsedDateTime(String givenDate) {
        try {
            Date date = DateUtils.getDate(DateUtils.ISO_8601_FORMAT_1, givenDate);
            Date now = new Date();
            long dateTimeDifference = now.getTime() - date.getTime();
            long yearTime = TimeFormatType.YEAR.getDateTime();
            long elapsedYears = dateTimeDifference / yearTime;
            dateTimeDifference = dateTimeDifference % yearTime;
            if (elapsedYears > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DAY_MONTH_YEAR_FORMAT, Locale.UK);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                return simpleDateFormat.format(date);
            }

            long monthTime = TimeFormatType.MONTH.getDateTime();
            long elapsedMonths = dateTimeDifference / monthTime;
            dateTimeDifference = dateTimeDifference % monthTime;
            if (elapsedMonths > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DAY_MONTH_YEAR_FORMAT, Locale.UK);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                return simpleDateFormat.format(date);
            }

            long weekTime = TimeFormatType.WEEK.getDateTime();
            long elapsedWeeks = dateTimeDifference / weekTime;
            dateTimeDifference = dateTimeDifference % weekTime;
            if (elapsedWeeks > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DAY_MONTH_YEAR_FORMAT, Locale.UK);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                return simpleDateFormat.format(date);
            }

            long dayTime = TimeFormatType.DAY.getDateTime();
            long elapsedDays = dateTimeDifference / dayTime;
            dateTimeDifference = dateTimeDifference % dayTime;
            if (elapsedDays > 0) {
                return elapsedDays + formatDuration(" day", elapsedDays) + " ago";
            }

            long hourTime = TimeFormatType.HOUR.getDateTime();
            long elapsedHours = dateTimeDifference / hourTime;
            dateTimeDifference = dateTimeDifference % hourTime;
            if (elapsedHours > 0) {
                return elapsedHours + formatDuration(" hour", elapsedHours) + " ago";
            }

            long minuteTime = TimeFormatType.MINUTE.getDateTime();
            long elapsedMinutes = dateTimeDifference / minuteTime;

            if (elapsedMinutes > 0) {
                return elapsedMinutes + formatDuration(" min", elapsedMinutes) + " ago";
            }
            return "just now";
        } catch (ParseException e) {
            return givenDate;
        }
    }

    private static String formatDuration(String duration, long value) {
        return value > 1 ? duration + "s" : duration;
    }
}
