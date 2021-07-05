package com.gwang.pricing.rule.resolver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Logger log = LoggerFactory.getLogger(DateUtil.class);
    public static final String DefaultShortFormat = "yyyy-MM-dd";
    public static final String DefaultLongFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String DefaultMinuteFormat = "yyyy-MM-dd HH:mm";

    public static String Date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static Date fromUnixTime(Integer seconds) {
        return new Date((long)seconds * 1000L);
    }

    public static Date fromUnixTime(Long seconds) {
        return new Date(seconds * 1000L);
    }

    public static int date2Unixtime(Date date) {
        return (int)(date.getTime() / 1000L);
    }

    public static Date today() {
        return toDay(new Date());
    }

    public static Date toDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static long parseTimeStr2Seconds(String timeStr) {
        String[] formatStrs = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd"};
        timeStr = StringUtil.null2Trim(timeStr);
        String[] var5 = formatStrs;
        int var4 = formatStrs.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String formatStr = var5[var3];
            Date date = parseTimeStrToDate(timeStr, formatStr);
            if (date != null) {
                return date.getTime() / 1000L;
            }
        }

        return 0L;
    }

    private static Date parseTimeStrToDate(String timeStr, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        try {
            return formatter.parse(timeStr);
        } catch (Exception var4) {
            log.warn("DateUtil.parseTimeStrToDate error.", var4);
            return null;
        }
    }
}
