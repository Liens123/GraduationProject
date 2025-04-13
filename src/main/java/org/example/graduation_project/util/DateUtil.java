package org.example.graduation_project.util;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    private DateUtil() {

    }

    private static String[] DAY_NAMES = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * Date Time Formatter
     */
    public static class Formatter {
        public static final DateTimeFormatter YYYY_MM = DateTimeFormatter.ofPattern("yyyy-MM");
        public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        public static final DateTimeFormatter YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        public static final DateTimeFormatter YYYY_MM_DD_00_00_00 = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        public static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
        public static final DateTimeFormatter YYYYMMDDHHMMSSSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        public static final DateTimeFormatter HH_MM_SS_SSS = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        public static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");
        public static final DateTimeFormatter RFC_3339 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+08:00");
    }

    /**
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return toLocalDate(date).format(Formatter.YYYY_MM_DD).toString();
    }

    /**
     * @param date
     * @param formatter
     * @return
     */
    public static String formatDate(Date date, DateTimeFormatter formatter) {
        return toLocalDate(date).format(formatter);
    }

    /**
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return toLocalDateTime(date).format(Formatter.YYYY_MM_DD_HH_MM_SS).toString();
    }

    /**
     * @param date
     * @param formatter
     * @return
     */
    public static String formatDateTime(Date date, DateTimeFormatter formatter) {
        return toLocalDateTime(date).format(formatter);
    }

    /**
     * @param date
     * @return
     */
    public static String formatUTCDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    /**
     * @param dateString
     * @param formatter
     * @return
     */
    public static Date parseDate(String dateString, DateTimeFormatter formatter) {
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * @param dateString
     * @param formatter
     * @return
     */
    public static Date parseDateTime(String dateString, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date parseDateTime(Date date, int hour, int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    /**
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, int hours) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR, hours);
        return ca.getTime();
    }

    /**
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinutes(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    /**
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param date
     * @return
     */
    public static int getDayNum(Date date) {
        int day = getDayOfWeek(date) - 1;
        return day < 0 ? 0 : day;
    }

    /**
     * @param date
     * @return
     */
    public static String getDayName(Date date) {
        return DAY_NAMES[getDayNum(date)];
    }

    /**
     * @return
     */
    public static Date getNextDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @return
     */
    public static Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @return
     */
    public static Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @return
     */
    public static Date getWeekDate(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @param date1
     * @param date2
     * @return
     */
    public static int getDaysDiff(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return (int) TimeUnit.MILLISECONDS.toDays(calendar2.getTimeInMillis() - calendar1.getTimeInMillis());
    }

    /**
     * @param date1
     * @param date2
     * @return
     */
    public static int getSecondsDiff(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return (int) TimeUnit.MILLISECONDS.toSeconds(calendar2.getTimeInMillis() - calendar1.getTimeInMillis());
    }

    /**
     * @param date
     * @return
     */
    public static int getAge(Date date) {
        LocalDate birthdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthdate, currentDate).getYears();
    }

    public static void main(String[] args) {
        System.out.println(getWeekDate(Calendar.MONDAY));
    }
}
