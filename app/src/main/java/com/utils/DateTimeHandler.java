package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeHandler {
    public static long getTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String get_current_DDMMYYYY() {
        Calendar C = Calendar.getInstance();
        int n;
        return ((n = C.get(Calendar.DAY_OF_MONTH)) < 10 ? "0" + n : n) + "-" + ((n = (C.get(Calendar.MONTH) + 1)) < 10 ? "0" + n : n) + "-" + C.get(Calendar.YEAR);
    }

    public static Date get_current_Date() {
        Calendar C = Calendar.getInstance();
        Date date = new Date();
        date.setTime(C.getTimeInMillis());
        return date;
    }

    public static String get_DDMMYYYY(int day, int month, int year) {
        int n;
        return ((n = day) < 10 ? "0" + n : n) + "-" + ((n = month) < 10 ? "0" + n : n) + "-" + year;
    }

    public static Date stringToDate(String date, int addDay, int addMonth, int addYear) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date inDate = simpleDateFormat.parse(date);

        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.DATE, addDay);
        c.add(Calendar.MONTH, addMonth);
        c.add(Calendar.YEAR, addYear);

        return c.getTime();
    }

    public static String dateToString(Date date, int addDay, int addMonth, int addYear) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, addDay);
        c.add(Calendar.MONTH, addMonth);
        c.add(Calendar.YEAR, addYear);

        return simpleDateFormat.format(c.getTime());
    }

    public static String dateAdder(String date, int addDay, int addMonth, int addYear) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date inDate = simpleDateFormat.parse(date);

        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.DATE, addDay);
        c.add(Calendar.MONTH, addMonth);
        c.add(Calendar.YEAR, addYear);

        return simpleDateFormat.format(c.getTime());
    }

    public static Date dateAdder(Date date, int addDay, int addMonth, int addYear) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, addDay);
        c.add(Calendar.MONTH, addMonth);
        c.add(Calendar.YEAR, addYear);

        return c.getTime();
    }

    public static Date dateAdder(long milliseconds, int addDay, int addMonth, int addYear) {
        return dateAdder(new Date(milliseconds), addDay, addMonth, addYear);
    }

    public static String getDate(long timeInMillis, int addDat, int addMonth, int addYear) {
        return dateToString(new Date(timeInMillis), addDat, addMonth, addYear);
    }
}
