/*
 * cn.touch.kit.Dates.java
 * May 27, 2012 
 */
package cn.touch.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.nutz.lang.Strings;
import org.nutz.log.Logs;

/**
 * May 27, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class Dates {

    /**
     * 第二天
     * 
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return nextDay(calendar);
    }

    /**
     * 第二天
     * 
     * @return
     */
    public static Date getNextDay() {
        Calendar calendar = Calendar.getInstance();
        return nextDay(calendar);
    }

    /**
     * 第二天
     * 
     * @param calendar
     * @return
     */
    public static Date nextDay(Calendar calendar) {
        clean_H_M_S_MS_OfCalendar(calendar);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 当月第一天
     * 
     * @return
     */
    public static Date getFirstDayOnMonth() {
        return firstDayOnMonth(Calendar.getInstance());
    }

    /**
     * 当月第一天
     * 
     * @param calendar
     * @return
     */
    public static Date firstDayOnMonth(Calendar calendar) {
        clean_H_M_S_MS_OfCalendar(calendar);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 当月第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOnMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return firstDayOnMonth(calendar);
    }

    /**
     * 当月最后一天
     * 
     * @return
     */
    public static Date getLastDayOnMonth() {
        return lastDayOnMonth(Calendar.getInstance());
    }

    /**
     * 当月最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOnMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return lastDayOnMonth(calendar);
    }

    /**
     * 当月最后一天
     * 
     * @param calendar
     * @return
     */
    public static Date lastDayOnMonth(Calendar calendar) {
        clean_H_M_S_MS_OfCalendar(calendar);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 次月第一天
     * 
     * @return
     */
    public static Date getFirstDayOnNextMonth() {
        Calendar calendar = Calendar.getInstance();
        return firstDayOnNextMonth(calendar);
    }

    /**
     * 次月第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOnNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return firstDayOnNextMonth(calendar);
    }

    /**
     * 次月第一天
     * 
     * @param calendar
     * @return
     */
    public static Date firstDayOnNextMonth(Calendar calendar) {
        calendar.add(Calendar.MONTH, 1);
        return firstDayOnMonth(calendar);
    }

    /**
     * 下一秒
     * 
     * @param date
     * @return
     */
    public static Date getNextSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getNextSecond(calendar);
    }

    /**
     * 下一秒
     * 
     * @return
     */
    public static Date getNextSecond() {
        Calendar calendar = Calendar.getInstance();
        return getNextSecond(calendar);
    }

    /**
     * 下一秒
     * 
     * @param calendar
     * @return
     */
    public static Date getNextSecond(Calendar calendar) {
        calendar.add(Calendar.SECOND, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static void clean_H_M_S_MS_OfCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static Date parse(String dateStr, String patten) {
        if (!Strings.isBlank(dateStr) && !Strings.isBlank(patten)) {
            try {
                return new SimpleDateFormat(patten).parse(dateStr);
            } catch (ParseException e) {
                Logs.getLog(Dates.class).error(dateStr + " parse2: " + patten,
                        e);
            }
        }
        return null;
    }

    public static String format(Date date, String patten) {
        if (date != null && !Strings.isBlank(patten)) {
            return new SimpleDateFormat(patten).format(date);
        }
        return "";
    }

    /***************************************************/
    
    public static final String DATE_YYYY_MM = "yyyy-MM";
    public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATETIME_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";    

    public static Date parse4DATETIME_YYYY_MM_DD_HH_MM_SS(String dateStr) {
        return Dates.parse(dateStr, DATETIME_YYYY_MM_DD_HH_MM_SS);
    }

    public static Date parse4DATE_YYYY_MM_DD(String dateStr) {
        return parse(dateStr, DATE_YYYY_MM_DD);
    }

    public static Date parse4DATE_YYYY_MM(String dateStr) {
        return parse(dateStr, DATE_YYYY_MM);
    }

    public static String format2DATETIME_YYYY_MM_DD_HH_MM_SS(Date date) {
        return format(date, DATETIME_YYYY_MM_DD_HH_MM_SS);
    }

    public static String format2DATE_YYYY_MM_DD(Date date) {
        return format(date, DATE_YYYY_MM_DD);
    }

    public static String format2DATE_YYYY_MM(Date date) {
        return format(date, DATE_YYYY_MM);
    }
}
