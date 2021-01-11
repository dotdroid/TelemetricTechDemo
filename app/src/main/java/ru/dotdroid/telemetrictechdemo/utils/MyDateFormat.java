package ru.dotdroid.telemetrictechdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.dotdroid.telemetrictechdemo.R;

public class MyDateFormat {

    public static String unixToDate(long unixDate) {
        long du = unixDate * 1000L;
        Date df = new Date(du);
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(df);
    }

    public static String unixToShortDate(long unixDate) {
        if (unixDate != 0) {
            long du = unixDate * 1000L;
            Date df = new Date(du);
            return new SimpleDateFormat("dd.MM.yy").format(df);
        } else {
            return "-";
        }
    }
}
