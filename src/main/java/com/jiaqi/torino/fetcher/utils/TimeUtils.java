package com.jiaqi.torino.fetcher.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static ZonedDateTime parse(String dateTimeStr) {
        return parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
    }

    public static ZonedDateTime parse(String dateTimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return parse(dateTimeStr, formatter);
    }

    public static ZonedDateTime parse(String dateTimeStr, DateTimeFormatter formatter) {
        return ZonedDateTime.parse(dateTimeStr, formatter);
    }

    public static ZonedDateTime convertToUTC(ZonedDateTime dt) {
        return dt.withZoneSameInstant(ZoneId.of("UTC"));
    }

    public static String formatUTC(ZonedDateTime dt) {
        return format(convertToUTC(dt));
    }
    
    public static String formatUTC(ZonedDateTime dt, String pattern) {
        return format(convertToUTC(dt), pattern);
    }

    public static String format(ZonedDateTime dt) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return dt.format(formatter);
    }

    public static String format(ZonedDateTime dt, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dt.format(formatter);
    }

    public static String format(ZonedDateTime dt, DateTimeFormatter formatter) {
        return dt.format(formatter);
    }

    public static void main(String[] args) {
        System.out.println(ZonedDateTime.now());
    }
}