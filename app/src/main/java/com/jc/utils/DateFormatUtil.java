package com.jc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DateFormatUtil {

    public DateFormatUtil() {
    }

    public static final String TIME_FORMAT_A = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_B = "yyyyMMddHHmmss";
    public static final String TIME_FORMAT_C = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String TIME_FORMAT_D = "yyyyMMdd";
    public static final String TIME_FORMAT_E = "yyyy年MM月dd日";
    public static final String TIME_FORMAT_F = "yyyyMMddHHmm";
    public static final String TIME_FORMAT_G = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String TIME_FORMAT_H = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT_I = "HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String YEAR_FORMAT = "yyyy";
    public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
    public static final String FORMAT_1 = ",##0.00";
    public static final String FORMAT_2 = "0.00";
    public static final String FORMAT_3 = ",###";
    
    private static final int HOURS_OF_DAY = 23;
    
    /**
     * 时间单位
     */
    public enum Unit {
        DAY, HOUR, MINUTE, WEEK, MONTH, QUARTER, YEAR
    }

    /**
     * start
     */
    public static final String START = "start";

    /**
     * end
     */
    public static final String END = "end";

    /**
     *
     */
    /**
     * 时间类型转化为格式化字符串
     *
     * @param date 时间
     * @param format 格式化样式
     * @return 格式化字符串，如果失败返回为null
     */
    public static String date2String(Date date, String format) {
        String dateStr = null;
        try {
            if (date != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                dateStr = simpleDateFormat.format(date);
            }
        } catch (Exception ex) {
        }
        return dateStr;
    }

    /**
     * 格式化字符串转化为时间类型
     *
     * @param dateStr 格式化字符串
     * @param format 格式化样式
     * @return 时间类型，如果失败返回为null
     */
    public static Date string2Date(String dateStr, String format) {

        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(format)) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException ex) {
        }
        return date;
    }

    /**
     * 获得date的本周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfTheWeek(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());

    }

    /**
     * 获得date的本周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfTheWeek(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        calendar.set(Calendar.HOUR_OF_DAY, HOURS_OF_DAY);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 获得本月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfTheMonth(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(date.getTime());
        // 日，设为一号
        calendar.set(Calendar.DATE, 1);
        // 月份加一，得到下个月的一号
        calendar.add(Calendar.MONTH, 1);
        // 下一个月减一为本月最后一天
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, HOURS_OF_DAY);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 获得本月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfTheMonth(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(date.getTime());
        // 日，设为一号
        calendar.set(Calendar.DATE, 1);
        // 月份加一，得到下个月的一号
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 获得本年的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfTheYear(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 获得本季度的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfTheQuarter(Date date) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);
        int month = c.get(Calendar.MONTH); // 注：一月份是 month==0
        int monthInterval = month % 3; // 获得本季度的第一月
        c.add(Calendar.MONTH, 0 - monthInterval);
        return getFirstDayOfTheMonth(c.getTime());
    }

    /**
     * 获取下一天时间
     *
     * @param date 设置时间
     * @param format 时间格式
     * @return 下一天时间 格式为TIME_FORMAT_A
     */
    public static String getNextDay(String date, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
            return null;
        }
        Date now = string2Date(date, format);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(now);
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        return date2String(tomorrow, TIME_FORMAT_A);
    }

    /**
     * 获得date的前month个月
     *
     * @param month
     * @param date
     * @return
     */
    public static Date getLastMonth(int month, Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -month);
            return calendar.getTime();

        }
        return null;

    }

    /**
     * 获得该date是一年当中的第几天,第几周,第几个月,第几季度,哪年
     *
     * @param date
     * @param unit
     * @return
     */
    public static Long getDWMQYByUnit(Date date, Unit unit) {
        if (date == null || unit == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        switch (unit) {
            case DAY:
                return Long.valueOf((calendar.get(Calendar.DAY_OF_YEAR)));
            case MONTH:
                return Long.valueOf(calendar.get(Calendar.MONTH) + 1);
            case WEEK:
                return Long.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
            case YEAR:
                return Long.valueOf(calendar.get(Calendar.YEAR));
            case QUARTER:
                return Long.valueOf(getQuarter(calendar.get(Calendar.MONTH) + 1));
            default:
                return null;
        }
    }

    private static int getQuarter(int month) {
        return (month - 1) / 3 + 1;
    }

    /**
     * 获得该时间点的临界时间
     *
     * @param date 时间
     * @param startOrEnd start 或者 end
     * @return
     */
    public static Date getCriticalPointOfDay(Date date, String startOrEnd) {

        Date returnDate = null;
        if (date == null) {
            return returnDate;
        }
        if (StringUtils.isEmpty(startOrEnd)) {
            return returnDate;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (START.equals(startOrEnd)) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            returnDate = c.getTime();

        }
        if (END.equals(startOrEnd)) {
            c.set(Calendar.HOUR_OF_DAY, HOURS_OF_DAY);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            returnDate = c.getTime();

        }
        return returnDate;

    }

    public static Date getYesterday(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    public static boolean isEnd(Date date, Unit unit) {
        Calendar cNow = Calendar.getInstance();
        cNow.setTime(date);
        Calendar cNext = Calendar.getInstance();
        cNext.setTime(date);
        cNext.add(Calendar.DATE, 1);

        switch (unit) {
            case WEEK:
                return (getDWMQYByUnit(cNext.getTime(), Unit.WEEK) - getDWMQYByUnit(cNow.getTime(), Unit.WEEK)) != 0;
            case MONTH:
                return (getDWMQYByUnit(cNext.getTime(), Unit.MONTH) - getDWMQYByUnit(cNow.getTime(), Unit.MONTH)) != 0;
            case QUARTER:
                return (getDWMQYByUnit(cNext.getTime(), Unit.QUARTER) - getDWMQYByUnit(cNow.getTime(), Unit.QUARTER)) != 0;
            case YEAR:
                return (getDWMQYByUnit(cNext.getTime(), Unit.YEAR) - getDWMQYByUnit(cNow.getTime(), Unit.YEAR)) != 0;
            default:
                return false;
        }
    }

    /**
     * 得到当天的第一微秒的时间。 <br>
     * 如：传入参数是2011-03-10 11:25:33， 返回的时间是 2011-03-10 00:00:00:000
     *
     * @param date
     * @return
     */
    public static Date getBeginningOfTheDay(Date date) {
        if (null == date) {
            return null;
        }
        return DateFormatUtil.string2Date(DateFormatUtil.date2String(date, DATE_FORMAT),
                DATE_FORMAT);
    }

    /**
     * 得到当天的最后一微秒的时间。 <br>
     * 如：传入参数是2011-03-10 11:25:33， 返回的时间是 2011-03-10 23:59:59:999
     *
     * @param date
     * @return
     */
    public static Date getEndOfTheDay(Date date) {
        if (null == date) {
            return null;
        }
        Date beginningOfTheDay = DateFormatUtil.getBeginningOfTheDay(date);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(beginningOfTheDay.getTime() + 24 * 60 * 60 * 1000 - 1);
        return calendar.getTime();
    }

    /**
     * 得到下一天的当前时刻的时间 <br>
     * 如：传入参数的时间是 2011-03-10 11:12:13 <br>
     * 返回的时间是 2011-03-11 11:12:13
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static boolean isFirstDayOfMonth(Date date) {
        if (null == date) {
            return false;
        }
        String firstDayOfMonth = DateFormatUtil.date2String(getFirstDayOfTheMonth(date),
                DateFormatUtil.DATE_FORMAT);
        String currentDay = DateFormatUtil.date2String(date, DateFormatUtil.DATE_FORMAT);
        return firstDayOfMonth.equals(currentDay);
    }

    public static boolean isFirstDayOfQuarter(Date date) {
        if (null == date) {
            return false;
        }
        String firstDayOfQuarter = DateFormatUtil.date2String(getFirstDayOfTheQuarter(date),
                DateFormatUtil.DATE_FORMAT);
        String currentDay = DateFormatUtil.date2String(date, DateFormatUtil.DATE_FORMAT);
        return firstDayOfQuarter.equals(currentDay);
    }

    public static boolean isFirstDayOfWeek(Date date) {
        if (null == date) {
            return false;
        }
        String firstDayOfWeek = DateFormatUtil.date2String(getFirstDayOfTheWeek(date),
                DateFormatUtil.DATE_FORMAT);
        String currentDay = DateFormatUtil.date2String(date, DateFormatUtil.DATE_FORMAT);
        return firstDayOfWeek.equals(currentDay);
    }

    public static boolean isFirstDayOfYear(Date date) {
        if (null == date) {
            return false;
        }
        String firstDayOfYear = DateFormatUtil.date2String(getFirstDayOfTheYear(date),
                DateFormatUtil.DATE_FORMAT);
        String currentDay = DateFormatUtil.date2String(date, DateFormatUtil.DATE_FORMAT);
        return firstDayOfYear.equals(currentDay);
    }

    /**
     * 获得前一天的日期。 <br>
     * 如传入 2011-03-09,返回2011-03-08
     *
     * @param date
     * @return
     */
    public static Date getLastDay(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回给定日期所在一周的七天。list.get(0)是第一天（周日）
     *
     * @param date
     * @return
     */
    public static List<Date> getWeekDays(Date date) {
        List<Date> retList = new ArrayList<Date>();
        if (date == null) {
            return retList;
        }
        Date temp = getFirstDayOfTheWeek(date);
        for (int i = 0; i < 7; i++) {
            retList.add(temp);
            temp = getNextDay(temp);
        }
        return retList;
    }

    /**
     * 返回给定日期所在一月的所有的天。list.get(0)是第一天（1号）
     *
     * @param date
     * @return
     */
    public static List<Date> getMonthDays(Date date) {
        List<Date> retList = new ArrayList<Date>();
        if (date == null) {
            return retList;
        }
        Date temp = getFirstDayOfTheMonth(date);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);
        int num = c.getActualMaximum(Calendar.DATE);
        for (int i = 0; i < num; i++) {
            retList.add(temp);
            temp = getNextDay(temp);
        }
        return retList;
    }

    /**
     * 判断两个日期是否同一天
     *
     * @param day1
     * @param day2
     * @return
     */
    public static boolean isTheSameDay(Date day1, Date day2) {
        if (day1 == null || day2 == null) {
            return false;
        }
        return DateFormatUtil.date2String(day1, DateFormatUtil.DATE_FORMAT).equals(
                DateFormatUtil.date2String(day2, DateFormatUtil.DATE_FORMAT));
    }

    /**
     * 判断两个日期是否同一月
     *
     * @param day1
     * @param day2
     * @return
     */
    public static boolean isInTheSameMonth(Date day1, Date day2) {
        if (day1 == null || day2 == null) {
            return false;
        }
        return DateFormatUtil.date2String(day1, DateFormatUtil.YEAR_MONTH_FORMAT).equals(
                DateFormatUtil.date2String(day2, DateFormatUtil.YEAR_MONTH_FORMAT));
    }

    /**
     * 返回指定日期所在年份的已过的月份的第一天。<br>
     * date：2011-03-14,则返回2011-01-01, 2011-02-01 <br>
     * date: 2011-01-09,则返回空的List <br>
     * date is null,则返回null
     *
     * @param date
     * @return
     */
    public static List<Date> getPastMonthsOfTheYear(Date date) {
        if (date == null) {
            return null;
        }

        Date firstDay = DateFormatUtil.getFirstDayOfTheYear(date);
        if (DateFormatUtil.isInTheSameMonth(date, firstDay)) {
            return Collections.emptyList();
        }

        List<Date> list = new ArrayList<Date>();
        Date lastDay = firstDay;
        list.add(lastDay); // 增加1月1日那天
        firstDay = DateFormatUtil.getNextMonth(firstDay);
        while (!DateFormatUtil.isInTheSameMonth(date, firstDay)) {
            list.add(firstDay);
            firstDay = DateFormatUtil.getNextMonth(firstDay);
        }
        return list;
    }

    /**
     * 返回指定日期下个月的第一天。<br>
     * 如 date: 2011-03-14,则返回2011-04-01
     *
     * @param date
     * @return
     */
    public static Date getNextMonth(Date date) {
        if (date == null) {
            return null;
        }
        Date firstDay = DateFormatUtil.getFirstDayOfTheMonth(date);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(firstDay);
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }

    public static Date getStartDate(Date date, Unit unit) {
        switch (unit) {
            case DAY:
                return getBeginningOfTheDay(date);
            case WEEK:
                return getBeginningOfTheDay(getFirstDayOfTheWeek(date));
            case MONTH:
                return getBeginningOfTheDay(getFirstDayOfTheMonth(date));
            case YEAR:
                return getBeginningOfTheDay(getFirstDayOfTheYear(date));
            case QUARTER:
                return getBeginningOfTheDay(getFirstDayOfTheQuarter(date));
            default:
                return null;
        }
    }

    public static Date getEndDate(Date date, Unit unit) {
        switch (unit) {
            case DAY:
                return getEndOfTheDay(date);
            case WEEK:
                return getEndOfTheDay(getLastDayOfTheWeek(date));
            case MONTH:
                return getEndOfTheDay(getLastDayOfTheMonth(date));
            case YEAR:
                return getEndOfTheDay(getLastDayOfTheYear(date));
            case QUARTER:
                return getEndOfTheDay(getLastDayOfTheQuarter(date));
            default:
                return null;
        }
    }

    /**
     * 参数如果是：2011-03-01 22:22:22，则返回2011-12-31 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfTheYear(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.YEAR, 1);
        Date nextYear = getFirstDayOfTheYear(calendar.getTime());
        return getEndOfTheDay(getLastDay(nextYear));
    }

    /**
     * 参数如果是：2011-02-01 22:22:22，则返回2011-03-31 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfTheQuarter(Date date) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(getFirstDayOfTheQuarter(date));
        c.add(Calendar.MONTH, 2);
        return (getEndOfTheDay(getLastDayOfTheMonth(c.getTime())));
    }

    public static Date getDateByRecordDay(Long recordDay, Long year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year.toString()));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, Integer.valueOf(String.valueOf((recordDay - 1))));
        return calendar.getTime();
    }

    /**
     * 判断date1和date2是否在同一天
     *
     * @param date1 时间
     * @param date2 时间
     * @return
     */
    public static boolean isEquals(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Date start = getCriticalPointOfDay(date1, START);
        Date end = getCriticalPointOfDay(date1, END);
        Long ls = start.getTime();
        Long le = end.getTime();
        Long ld = date2.getTime();
        return ld >= ls && ld <= le;
    }

    /**
     * 比较date2是否大于或者等于date1
     *
     * @param date1 时间
     * @param date2 时间
     * @return
     */
    public static boolean isBiger(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Long b = date1.getTime();
        Long e = date2.getTime();
        return e >= b;
    }

    /**
     * 计算两个时间相差的月份，从1开始，两个时间相同则相差月份为1
     * <p>
     * 在最大值内返回两个时间相差的月份，如果相差的时间大于这个最大值则返回最大值;
     * </p>
     * <li>相差的月份大于等于0</li> <li>相差的月份小于等于最大值differ</li>
     *
     * @param sdate 开始时间 （不能为空）
     * @param differ 最大相差值 （不能为空，大于0）
     * @param ldate 结束时间 （不能为空）
     * @return
     */
    public static Integer differMonth(Date sdate, Integer differ, Date ldate) {

        //开始时间
        Calendar sCal = Calendar.getInstance();
        sCal.setTime(sdate);
        sCal.set(Calendar.HOUR_OF_DAY, 0);
        sCal.set(Calendar.MINUTE, 0);
        sCal.set(Calendar.SECOND, 0);

        //结束时间
        Calendar lCal = Calendar.getInstance();
        lCal.setTime(ldate);
        lCal.set(Calendar.HOUR_OF_DAY, 0);
        lCal.set(Calendar.MINUTE, 0);
        lCal.set(Calendar.SECOND, 0);

        if (lCal.compareTo(sCal) < 0) {
            return 0;
        }
        //比较时间
        Calendar temp = sCal;
        for (int i = 1; i <= differ; i++) {
            temp.add(Calendar.MONTH, 1);
            temp.add(Calendar.DATE, -1);
            if (lCal.compareTo(temp) <= 0) {
                return i;
            }
            temp.add(Calendar.DATE, 1);
        }
        return differ;
    }

    /**
     * 计算date时间加上second秒后的时间
     * <p>
     * <li>如果second小于0或者为空 则返回date</li>
     * </p>
     *
     * @param date 时间点
     * @param second 秒数
     * @return
     */
    public static Date addTime(Date date, Integer second) {
        if (second == null || second < 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 计算date时间减去minute分后的时间
     * <p>
     * <li>如果minute大于0或者为空 则返回date</li>
     * </p>
     *
     * @param date 时间点
     * @param minute 分数
     * @return
     */
    public static Date deleteTime(Date date, Integer minute) {
        if (minute == null || minute > 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    /**
     * 给定指定时期，在修改其月份和天数
     * <p>
     * 如果参数有一个为空，则返回date
     * </p>
     *
     * @param date 指定时期
     * @param month 月份数
     * @param day 天数
     * @return
     */
    public static Date getDateBeforeNextMonth(Date date, Integer month, Integer day) {
        if (date == null || month == null || day == null) {
            return date;
        }
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 获得date所在月的天数
     *
     * @param date
     * @return
     */
    public static Integer getDaysAtTheMonth(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
