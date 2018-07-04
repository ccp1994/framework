package com.tfxk.framework.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 */
public final class TimeUtil {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年
    private static String tag = TimeUtil.class.getSimpleName();

//    public static String formatTime(Context context,long time) {
//        long diff = new Date().getTime() - time;
//        long r = 0;
//        if (diff > year) {
//            r = (diff / year);
//            return r + context.getString(R.string.years_ago);
//        }
//        if (diff > month) {
//            r = (diff / month);
//            return r + context.getString(R.string.months_ago);
//        }
//        if (diff > day) {
//            r = (diff / day);
//            return r + context.getString(R.string.days_ago);
//        }
//        if (diff > hour) {
//            r = (diff / hour);
//            return r + context.getString(R.string.hours_ago);
//        }
//        if (diff > minute) {
//            r = (diff / minute);
//            return r + context.getString(R.string.minutes_ago);
//        }
//        return context.getString(R.string.a_moment_ago);
//    }

    public static boolean isSameDay(Calendar day1, Calendar day2) {
        final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String today = yyyyMMdd.format(day1.getTime());
        return today.equals(yyyyMMdd.format(day2.getTime()));
    }

    public static boolean isToday(Calendar date) {
        final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String today = yyyyMMdd.format(Calendar.getInstance().getTime());
        return today.equals(yyyyMMdd.format(date.getTime()));
    }

    public static boolean isToday(long recordTime) {
        final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String today = yyyyMMdd.format(Calendar.getInstance().getTime());
        final String record = yyyyMMdd.format(new Date(recordTime));
        System.out.println("isToDay   " + today + "  :  recordTime  " + record);
        return today.equals(record);
    }

    public static int daysBetween(Calendar start, Calendar end) {
        final int i = 0x5265c00;
        return Math.abs((int) (end.getTimeInMillis() - start.getTimeInMillis()) / i);
    }

    // accept time with 10 digits
    public static int getDayFromNow(long time) {
        long diff = time * 1000 - new Date().getTime();
        return (int) (diff / year);
    }

    public static String timeMsToString(int timeMs) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("0%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("00:%02d:%02d", minutes, seconds).toString();
        }
    }

    public static long stringToTimeMs(String timeString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(timeString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long stringToTimeMs(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = sdf.parse(timeString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long stringToTimeMsg(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = sdf.parse(timeString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static String getMinuteAndSecond() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", calendar.get(Calendar.MINUTE))).append(":").append(String.format("%02d", calendar.get(Calendar.SECOND)));
        return sb.toString();
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR)).append(calendar.get(Calendar.MONTH) + 1).append(calendar.get(Calendar.DAY_OF_MONTH));
        return sb.toString();
    }

    public static int getDayofWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static String getAirDate() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR)).append("/").append(calendar.get(Calendar.MONTH) + 1).append("/").append(calendar.get(Calendar.DAY_OF_MONTH));
        return sb.toString();
    }

    /**
     * @param timestamp 10 digits
     * @return formatted date like "1970-01-01"
     */
    public static String getDateByTimestamp(long timestamp) {
        return String.format("%tF", new Date(timestamp * 1000));
    }

    /**
     * @param time 10 digits
     * @return formatted date like "1970/1/1"
     */
    public static String getTime(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time) * 1000);
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR)).append("/")
                .append(calendar.get(Calendar.MONTH) + 1).append("/")
                .append(calendar.get(Calendar.DAY_OF_MONTH));
        return sb.toString();
    }

    //long time=1404959664213  string =2014-07-10 10:36:06
    public static String timeLongToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }

    public static String timeLongToStr(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date(time));
    }

    /**
     * for timestamp with 10bit
     *
     * @param time
     * @return
     */
    public static String unixtimeLongToString(long time) {
        return unixtimeLongToString(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * for timestamp with 10bit
     *
     * @param time
     * @return
     */
    public static String unixtimeLongToString(long time, String pattern) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(time * 1000L));
    }

    public static String getFormatDate(String pattern) { //pattern时间格式  yyyy-MM-dd HH:mm:ss||yyyy-MM-dd
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dFormate = new SimpleDateFormat(pattern);
        String fDate = dFormate.format(date);
        return fDate;
    }

    public static String getFormatDate(Calendar cal, String pattern) { //pattern时间格式  yyyy-MM-dd HH:mm:ss||yyyy-MM-dd
        Date date = new Date(cal.getTimeInMillis());
        SimpleDateFormat dFormate = new SimpleDateFormat(pattern);
        String fDate = dFormate.format(date);
        return fDate;
    }

    public static String getFormatDate(long timeInMs, String pattern) { //pattern时间格式  yyyy-MM-dd HH:mm:ss||yyyy-MM-dd
        Date date = new Date(timeInMs);
        SimpleDateFormat dFormate = new SimpleDateFormat(pattern);
        String fDate = dFormate.format(date);
        return fDate;
    }

    public static String getFormatDate(String timeInFormat, String pattern) { //pattern时间格式  yyyy-MM-dd HH:mm:ss||yyyy-MM-dd
        SimpleDateFormat dFormate = new SimpleDateFormat(pattern);
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(dFormate.parse(timeInFormat));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getFormatDate(c1, pattern);
    }

    /**
     * @param day
     * @param date the date of the compare date
     * @return the day between date and today
     * @see #getDayFromToday(int day, long time)
     */
    public static int getDayFromToday(int day, Date date) {
        return getDayFromToday(day, date.getTime());
    }

    /**
     * @param day  the initial day
     * @param time the ms-time of the compare day
     * @return the difference day number between the compare date and today
     */
    public static int getDayFromToday(int day, long time) {
        Date today = new Date();
        long ms = today.getTime() - time;
        int dd = Math.round((Math.abs(ms) / 86400000.0f));
        return ((ms > 0) ? day - dd : day + dd);
    }

    /**
     * get day befor today
     *
     * @param days the days you want to get befor today
     * @return
     */
    public static String getDayBefor(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);    //得到前days天的日历
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String beforDay = sdf.format(new Date(cal.getTimeInMillis()));
        return beforDay;
    }

    /**
     * get day after today
     *
     * @param days the days you want to get befor today
     * @return
     */
    public static String getDayAfter(int days) {
        return getDayAfter(days, "yyyy-MM-dd");
    }

    public static String getDayAfter(int days, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +days);    //得到前days天的日历
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String beforDay = sdf.format(new Date(cal.getTimeInMillis()));
        return beforDay;
    }

    public static Calendar getDayAfterCalendar(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +days);    //得到前days天的日历
        return cal;
    }

    public static Date getDayAfterDate(int days) {
        Calendar cal = getDayAfterCalendar(days);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        return date;
    }

    public static String getTimeHour() {
        Calendar cal = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(cal.get(Calendar.HOUR)).append(cal.get(Calendar.MINUTE));
        return sb.toString();
    }

    public static boolean isNight(int endH) {
        Calendar calendar = Calendar.getInstance();
        int curH = calendar.get(Calendar.HOUR_OF_DAY);
        return curH >= endH;
    }

    public static boolean isDay(int h, int sunset) {
        if (h >= sunset) return false;
        return true;
    }

    public static Calendar getCalendar(long time) {
        if (time == 0)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        return cal;
    }

    public static Calendar getCalendar(String formatTime, String fromatPattern) {
        if (TextUtils.isEmpty(formatTime))
            return Calendar.getInstance();
        SimpleDateFormat formator = new SimpleDateFormat(fromatPattern);
        Date date = new Date();
        try {
            date = formator.parse(formatTime);
        } catch (ParseException e) {
            Log.e(tag, e.toString());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 将long转换成对应的天数
     *
     * @param total
     * @return 2天12小时12分24秒
     */
    public static String longToDay(long total) {
        double dd = 1000 * 60 * 60 * 24.0f;
        int day = (int) (total / dd);
        if (day > 0) {
            total -= day * dd;
            return day + "天" + longToHour(total);
        } else {
            return longToHour(total);
        }

    }

    private static String longToHour(long total) {
        double hh = 1000 * 60 * 60.0f;
        int iHour = (int) (total / hh);
        if (iHour > 0) {
            total -= iHour * hh;
            return iHour + "小时" + longToMinute(total);
        } else return longToMinute(total);
    }

    private static String longToMinute(long total) {
        double mm = 1000 * 60.0f;
        int iMinute = (int) (total / mm);
        if (iMinute > 0) {
            total -= iMinute * mm;
            return iMinute + "分" + longToSeconds(total);
        } else
            return longToSeconds(total);
    }

    private static String longToSeconds(long total) {
        double ss = 1000.0f;
        int second = (int) (total / ss);
        if (second > 0)
            return second + "秒";
        else return "";
    }

    /**
     * @param compareCalendar
     * @return return true if compareCalendar is bigger than today Calendar
     */
    public static boolean compareIsTodayBefore(Calendar compareCalendar) {
        return compareDayBefore(Calendar.getInstance(), compareCalendar);
    }

    /**
     * @param initCalendar
     * @param compareCalendar
     * @return true if compareCalendar if before initCalendar
     */
    public static boolean compareDayBefore(Calendar initCalendar, Calendar compareCalendar) {
        int todayYear = initCalendar.get(Calendar.YEAR);
        int todayMonth = initCalendar.get(Calendar.MONTH);
        int todayDay = initCalendar.get(Calendar.DAY_OF_MONTH);
        int compareYear = compareCalendar.get(Calendar.YEAR);
        int compareMonth = compareCalendar.get(Calendar.MONTH);
        int compareDay = compareCalendar.get(Calendar.DAY_OF_MONTH);
        if (compareYear < todayYear)
            return true;
        else if (compareYear > todayYear) {//大本年
            return false;
        } else {//同一年
            if (compareMonth < todayMonth)
                return true;
            else if (compareMonth > todayMonth)//大本月
                return false;
            else {//同一月
                if (compareDay < todayDay)
                    return true;
                else if (compareDay > todayDay)//大今天
                    return false;
                else return false;//同一天
            }
        }
    }

    /**
     * @param from 起始日期
     * @param to   结束日期
     * @return age
     */
    public static int getAgeByFormatDate(String from, String to, String pattern) {
        {
            java.text.DateFormat df = new SimpleDateFormat(pattern);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            try {
                c1.setTime(df.parse(from));
                c2.setTime(df.parse(to));
            } catch (ParseException e) {

                System.err.println("日期格式不正确，" + pattern);
                e.printStackTrace();
            }
            int[] result = new int[3];
            result[0] = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) < 0 ? 0 : c2
                    .get(Calendar.YEAR) - c1.get(Calendar.YEAR);
            result[1] = (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH)) < 0 ? 0 : c2
                    .get(Calendar.MONTH)
                    - c1.get(Calendar.MONTH);
            result[2] = (c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH)) < 0 ? 0 : c2
                    .get(Calendar.DAY_OF_MONTH)
                    - c1.get(Calendar.DAY_OF_MONTH);

            int age = result[0];
            if (result[1] < 0)
                age -= 1;
            else if (result[1] == 0) {//同月
                if (result[2] < 0)
                    age -= 1;
            }
            return age;

        }
    }

    public static long getTimestampByDate(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTimeInMillis();
    }
}
