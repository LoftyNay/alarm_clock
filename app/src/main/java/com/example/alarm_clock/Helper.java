package com.example.alarm_clock;

import android.content.Context;
import android.text.format.DateUtils;

/**
 * Created by artem on 11.10.16.
 */
public class Helper {

    public static String toFormatDateString(Context context, String t) {
        return DateUtils.formatDateTime(context, Long.valueOf(t), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }

    public static String toFormatTimeString(Context context, String t) {
        return DateUtils.formatDateTime(context, Long.valueOf(t), DateUtils.FORMAT_SHOW_TIME);
    }

    public static Boolean stringToBoolean(String str) {
        return str.equals("1");
    }
}
