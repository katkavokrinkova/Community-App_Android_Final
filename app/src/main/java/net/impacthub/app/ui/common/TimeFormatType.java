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

package net.impacthub.app.ui.common;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/31/2017.
 */

public enum TimeFormatType {

    DAY(TimeFormatType.DAYS_MILLI),
    YEAR(TimeFormatType.YEARS_MILLI),
    MONTH(TimeFormatType.MONTHS_MILLI),
    WEEK(TimeFormatType.WEEKS_MILLI),
    MINUTE(TimeFormatType.MINUTES_MILLI),
    HOUR(TimeFormatType.HOURS_MILLI),
    SECOND(TimeFormatType.SECONDS_MILLI);

    private static final long SECONDS_MILLI = 1000;
    private static final long MINUTES_MILLI = SECONDS_MILLI * 60;
    private static final long HOURS_MILLI = MINUTES_MILLI * 60;
    private static final long DAYS_MILLI = HOURS_MILLI * 24;
    private static final long WEEKS_MILLI = DAYS_MILLI * 7;
    private static final long MONTHS_MILLI = WEEKS_MILLI * 4;
    private static final long YEARS_MILLI = MONTHS_MILLI * 12;

    private long dateTime;

    TimeFormatType(long dt) {
        dateTime = dt;
    }

    public long getDateTime() {
        return dateTime;
    }
}
