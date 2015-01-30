package com.example.dapurmasak08.githubsample.utils;

import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class DateUtils {
    private DateUtils() {
    }

    public static String format(String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            return "";
        }

        DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();
        DateTime dt = parser.parseDateTime(dateString);
        DateTimeFormatter formatter = DateTimeFormat.mediumDate();
        return formatter.print(dt);
    }
}
