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

import android.os.Handler;
import android.widget.TextView;

import net.impacthub.app.utilities.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class DateTimeAgoHelper implements Runnable {

    private TextView mPostDate;
    private String mGivenDate;

    public DateTimeAgoHelper(TextView postDate, String givenDate) {
        mPostDate = postDate;
        mGivenDate = givenDate;
        new Handler().post(this);
    }

    @Override
    public void run() {
        try {
            Date date = DateUtils.getDate(DateUtils.ISO_8601_FORMAT_1, mGivenDate);
            Date now = new Date();

            long dateTimeDifference = now.getTime() - date.getTime();

            long elapsedYears = dateTimeDifference / Format.YEAR.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.YEAR.getDateTime();
            if (elapsedYears > 0) {
//                mPostDate.setText(elapsedYears + formatDuration(" year", elapsedYears) +" ago");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DAY_MONTH_YEAR_FORMAT, Locale.UK);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                String formattedDate = simpleDateFormat.format(date);
                mPostDate.setText(formattedDate);
                return;
            }

            long elapsedMonths = dateTimeDifference / Format.MONTH.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.MONTH.getDateTime();
            if (elapsedMonths > 0) {
//                mPostDate.setText(elapsedMonths + formatDuration(" month", elapsedMonths) +" ago");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DAY_MONTH_YEAR_FORMAT, Locale.UK);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                String formattedDate = simpleDateFormat.format(date);
                mPostDate.setText(formattedDate);
                return;
            }

            long elapsedWeeks = dateTimeDifference / Format.WEEK.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.WEEK.getDateTime();
            if (elapsedWeeks > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DAY_MONTH_YEAR_FORMAT, Locale.UK);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                String formattedDate = simpleDateFormat.format(date);
                mPostDate.setText(formattedDate);
//                mPostDate.setText(elapsedWeeks + formatDuration(" week", elapsedWeeks) +" ago");
                return;
            }

            long elapsedDays = dateTimeDifference / Format.DAY.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.DAY.getDateTime();
            if (elapsedDays > 0) {
                mPostDate.setText(elapsedDays + formatDuration(" day", elapsedDays) + " ago");
                return;
            }

            long elapsedHours = dateTimeDifference / Format.HOUR.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.HOUR.getDateTime();
            if (elapsedHours > 0) {
                mPostDate.setText(elapsedHours + formatDuration(" hour", elapsedHours) + " ago");
                return;
            }

            long elapsedMinutes = dateTimeDifference / Format.MINUTE.getDateTime();
            dateTimeDifference = dateTimeDifference % Format.MINUTE.getDateTime();
            if (elapsedMinutes > 0) {
                mPostDate.setText(elapsedMinutes + formatDuration(" min", elapsedMinutes) + " ago");
                return;
            }

            long elapsedSeconds = dateTimeDifference / Format.SECOND.getDateTime();
//            mPostDate.setText(elapsedSeconds + formatDuration(" second", elapsedSeconds) +" ago");
            mPostDate.setText("just now");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String formatDuration(String duration, long value) {
        return value > 1 ? duration + "s" : duration;
    }

    public enum Format {
        DAY(Format.DAYS_MILLI),
        YEAR(Format.YEARS_MILLI),
        MONTH(Format.MONTHS_MILLI),
        WEEK(Format.WEEKS_MILLI),
        MINUTE(Format.MINUTES_MILLI),
        HOUR(Format.HOURS_MILLI),
        SECOND(Format.SECONDS_MILLI);

        private static final long SECONDS_MILLI = 1000;
        private static final long MINUTES_MILLI = SECONDS_MILLI * 60;
        private static final long HOURS_MILLI = MINUTES_MILLI * 60;
        private static final long DAYS_MILLI = HOURS_MILLI * 24;
        private static final long WEEKS_MILLI = DAYS_MILLI * 7;
        private static final long MONTHS_MILLI = WEEKS_MILLI * 4;
        private static final long YEARS_MILLI = MONTHS_MILLI * 12;

        private long dateTime;

        Format(long dt) {
            dateTime = dt;
        }

        public long getDateTime() {
            return dateTime;
        }
    }
}