package me.code.yiguitest;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期utils
 * @author JinYiDing
 * @version 2015年3月29日 下午9:39:30
 * @description
 */
public final class DateTimeUtil {

    /** 日期格式 yyyy-MM-dd */
    public final static String  DATE_FORMAT                = "yyyy-MM-dd";

    /** 日期格式 MM/dd/yyyy */
    public final static String  DATE_FORMAT_EN             = "MM/dd/yyyy";

    /** 日期格式 yyyy年MM月dd日 */
    private final static String DATE_FORMAT_CN             = "yyyy年MM月dd日";

    /** 日期格式 yyyy-MM-dd HH:mm:ss */
    public final static String  TIME_FORMAT                = "yyyy-MM-dd HH:mm:ss";

    /** 日期格式 yyyy-MM-dd HH:mm:ss */
    public final static String  TIME_FORMAT_STR            = "yyyy/MM/dd HH:mm:ss";

    /** 日期格式 yyyyMMddHHmmssSSS */
    public final static String  FULL_TIME_FORMAT           = "yyyyMMddHHmmssSSS";

    /** 日期格式 yyyy年MM月dd日 HH:mm:ss */
    public final static String  TIME_FORMAT_CN             = "yyyy年MM月dd日 HH:mm:ss";

    /** 日期格式 yyyy-MM */
    public final static String  MONTH_FORMAT               = "yyyy-MM";

    /** 日期格式 yy-MM */
    public final static String  MONTH_FORMATH              = "yy-MM";

    /** 日期格式 yyyy */
    public final static String  YEAR_FORMAT                = "yyyy";

    /** 日期格式 yyyyMMdd */
    public final static String  DAY_FORMAT                 = "yyyyMMdd";

    /** 日期格式 dd日 */
    public final static String  DAY_CN_ONLY_FORMAT         = "dd日";

    /** 日期格式 MM月 */
    public final static String  MONTH_CN_ONLY_FORMAT       = "MM月";

    /** 日期格式 yyMMdd */
    public final static String  DAY_FORMAT_YYMMDD          = "yyMMdd";

    /**
     * 日期格式 精确到秒 ：yyyyMMddHHmmss
     */
    public static final String  DATE_FORMDATE_TO_SECOND    = "yyyyMMddHHmmss";

    /** 日期格式 精确到毫秒 */
    public static final String  DATE_FORMATE_TO_MILLISEC   = "yyyyMMddHHmmssSSS";

    /** 日期格式 精确到毫秒 */
    public static final String  SIMPLE_FORMATE_TO_MILLISEC = "yyMMddHHmmssSSS";

    /** 日期格式 12/07 */
    public static final String  DATE_FORMATE_MMDD          = "MM/dd";

    /** 日期格式 12-07 */
    public static final String  DATE_FORMATE_MM_DD         = "MM-dd";

    /** 日期格式 12.07 */
    public static final String  DATE_FORMATE_MD            = "MM.dd";

    private static final int    TH_1000                    = 1000;

    /** 每天的时分秒最后一刻 */
    public static final String  EACH_DAY_HMS_LAST          = " 23:59:59";

    /** 每天的时分秒最初一刻 */
    public static final String  EACH_DAY_HMS_BEGIN         = " 00:00:00";

    /**
     * 取得当前系统时间，返回java.util.Date类型
     *
     * @see Date
     * @return java.util.Date 返回服务器当前系统时间
     */
    public static Date getCurrDate() {
        return new Date();
    }

    /**
     * 取得当前系统毫秒时间，返回long类型
     *
     * @see Date
     * @return java.util.Date 返回服务器当前系统时间
     */
    public static long getCurrMilliseconds() {
        return DateTimeUtil.getCurrDate().getTime();
    }

    /**
     * 取得当前系统时间戳
     *
     * @see java.sql.Timestamp
     * @return java.sql.Timestamp 系统时间戳
     */
    public static java.sql.Timestamp getCurrTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate(Date, String)
     * @return String 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     */
    public static String getFormatDate(final Date currDate) {
        return DateTimeUtil.getFormatDate(currDate, DateTimeUtil.DATE_FORMAT);
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate(Date)
     * @return Date 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     */
    public static Date getFormatDateToDate(final Date currDate) {
        return DateTimeUtil.getFormatDate(DateTimeUtil.getFormatDate(currDate));
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate
     *            要格式化的日期
     * @param format 日期格式
     * @see #getFormatDate(Date)
     * @return Date 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     */
    public static Date getFormatDateToDate(final Date currDate, final String format) {
        return DateTimeUtil.getFormatDate(DateTimeUtil.getFormatDate(currDate, format), format);
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate(Date, String)
     * @return String 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     */
    public static String getFormatDate_CN(final Date currDate) {
        return DateTimeUtil.getFormatDate(currDate, DateTimeUtil.DATE_FORMAT_CN);
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate_CN(String)
     * @return Date 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     */
    public static Date getFormatDateToDate_CN(final Date currDate) {
        return DateTimeUtil.getFormatDate_CN(DateTimeUtil.getFormatDate_CN(currDate));
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate(String, String)
     * @return Date 返回格式化后的日期，默认格式为yyyy-MM-dd，如2006-02-15
     */
    public static Date getFormatDate(final String currDate) {
        return DateTimeUtil.getFormatDate(currDate, DateTimeUtil.DATE_FORMAT);
    }

    /**
     * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate(String, String)
     * @return 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
     */
    public static Date getFormatDate_CN(final String currDate) {
        return DateTimeUtil.getFormatDate(currDate, DateTimeUtil.DATE_FORMAT_CN);
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate
     *            要格式化的日期
     * @param format
     *            日期格式，如yyyy-MM-dd
     * @see SimpleDateFormat#format(Date)
     * @return String 返回格式化后的日期，格式由参数<code>format</code>
     *         定义，如yyyy-MM-dd，如2006-02-15
     */
    public static String getFormatDate(final Date currDate, final String format) {
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = DateFmtThreadLocalUtils.getSdf(format);
            return dtFormatdB.format(currDate);
        } catch (final Exception e) {
            dtFormatdB = DateFmtThreadLocalUtils.getSdf(DateTimeUtil.DATE_FORMAT);
            return dtFormatdB.format(currDate);
        }
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate
     *            要格式化的时间
     * @see #getFormatDateTime(Date, String)
     * @return String 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     */
    public static String getFormatDateTime(final Date currDate) {
        return DateTimeUtil.getFormatDateTime(currDate, DateTimeUtil.TIME_FORMAT);
    }

    /**
     * 得到格式化后的时间，格式为yyyy/MM/dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate
     *            要格式化的时间
     * @see #getFormatDateTime(Date, String)
     * @return String 返回格式化后的时间，默认格式为yyyy/MM/dd HH:mm:ss，如2006/02/15 15:23:45
     */
    public static String getFormatDateTimeStr(final Date currDate) {
        return DateTimeUtil.getFormatDateTime(currDate, DateTimeUtil.TIME_FORMAT_STR);
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate
     *            要格式环的时间
     * @see #getFormatDateTime(String)
     * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     */
    public static Date getFormatDateTimeToTime(final Date currDate) {
        return DateTimeUtil.getFormatDateTime(DateTimeUtil.getFormatDateTime(currDate));
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate
     *            要格式化的时间
     * @see #getFormatDateTime(String, String)
     * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     */
    public static Date getFormatDateTime(final String currDate) {
        return DateTimeUtil.getFormatDateTime(currDate, DateTimeUtil.TIME_FORMAT);
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate
     *            要格式化的时间
     * @see #getFormatDateTime(Date, String)
     * @return String 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     */
    public static String getFormatDateTime_CN(final Date currDate) {
        return DateTimeUtil.getFormatDateTime(currDate, DateTimeUtil.TIME_FORMAT_CN);
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate
     *            要格式化的时间
     * @see #getFormatDateTime_CN(String)
     * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     */
    public static Date getFormatDateTimeToTime_CN(final Date currDate) {
        return DateTimeUtil.getFormatDateTime_CN(DateTimeUtil.getFormatDateTime_CN(currDate));
    }

    /**
     * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @param currDate
     *            要格式化的时间
     * @see #getFormatDateTime(String, String)
     * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     */
    public static Date getFormatDateTime_CN(final String currDate) {
        return DateTimeUtil.getFormatDateTime(currDate, DateTimeUtil.TIME_FORMAT_CN);
    }

    /**
     * 根据格式得到格式化后的时间
     *
     * @param currDate
     *            要格式化的时间
     * @param format
     *            时间格式，如yyyy-MM-dd HH:mm:ss
     * @see SimpleDateFormat#format(Date)
     * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDateTime(final Date currDate, final String format) {
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = DateFmtThreadLocalUtils.getSdf(format);
            return dtFormatdB.format(currDate);
        } catch (final Exception e) {
            dtFormatdB = DateFmtThreadLocalUtils.getSdf(DateTimeUtil.TIME_FORMAT);
            return dtFormatdB.format(currDate);
        }
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate
     *            要格式化的日期
     * @param format
     *            日期格式，如yyyy-MM-dd
     * @see SimpleDateFormat#parse(String)
     * @return Date 返回格式化后的日期，格式由参数<code>format</code>定义，如yyyy-MM-dd，如2006-02-15
     */
    public static Date getFormatDate(final String currDate, final String format) {
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = DateFmtThreadLocalUtils.getSdf(format);
            return dtFormatdB.parse(currDate);
        } catch (final Exception e) {
            dtFormatdB = DateFmtThreadLocalUtils.getSdf(DateTimeUtil.DATE_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (final Exception ex) {
            }
        }
        return null;
    }

    /**
     * 根据格式得到格式化后的时间
     *
     * @param currDate
     *            要格式化的时间
     * @param format
     *            时间格式，如yyyy-MM-dd HH:mm:ss
     * @see SimpleDateFormat#parse(String)
     * @return Date 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
     */
    public static Date getFormatDateTime(final String currDate, final String format) {
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = DateFmtThreadLocalUtils.getSdf(format);
            return dtFormatdB.parse(currDate);
        } catch (final Exception e) {
            dtFormatdB = DateFmtThreadLocalUtils.getSdf(DateTimeUtil.TIME_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (final Exception ex) {
            }
        }
        return null;
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @see #getFormatDate(Date)
     * @return String 返回格式化后的当前服务器系统日期，格式为yyyy-MM-dd，如2006-02-15
     */
    public static String getCurrDateStr() {
        return DateTimeUtil.getFormatDate(DateTimeUtil.getCurrDate());
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @see #getFormatDateTime(Date)
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15
     *         15:23:45
     */
    public static String getCurrDateTimeStr() {
        return DateTimeUtil.getFormatDateTime(DateTimeUtil.getCurrDate());
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @see #getFormatDate(Date, String)
     * @return String 返回当前服务器系统日期，格式为yyyy年MM月dd日，如2006年02月15日
     */
    public static String getCurrDateStr_CN() {
        return DateTimeUtil.getFormatDate(DateTimeUtil.getCurrDate(), DateTimeUtil.DATE_FORMAT_CN);
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @see #getFormatDateTime(Date, String)
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     *         15:23:45
     */
    public static String getCurrDateTimeStr_CN() {
        return DateTimeUtil.getFormatDateTime(DateTimeUtil.getCurrDate(),
            DateTimeUtil.TIME_FORMAT_CN);
    }

    /**
     * 得到系统当前日期的前或者后几天
     *
     * @param iDate
     *            如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @see Calendar#add(int, int)
     * @return Date 返回系统当前日期的前或者后几天
     */
    public static Date getDateBeforeOrAfter(final int iDate) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几天
     * @param curDate
     *
     * @param iDate
     *            如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @see Calendar#add(int, int)
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几天
     */
    public static Date getDateBeforeOrAfter(final Date curDate, final int iDate) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }

    /**
     * 得到当前日期前或者后几年日期
     * @param year
     *          如果要获得前几年日期，该参数为负数； 如果要获得后几年日期，该参数为正数
     * @return Date
     */
    public static Date getDateBeforeOrAfterYear(final int year) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几年日期
     * @param curDate
     *
     * @param year
     *            如果要获得前几年日期，该参数为负数； 如果要获得后几年日期，该参数为正数
     * @see Calendar#add(int, int)
     * @return Date
     */
    public static Date getDateBeforeOrAfterYear(final Date curDate, final int year) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    /**
     * 得到当前日期前或者后几月日期
     * @param month
     *          如果要获得前几月日期，该参数为负数； 如果要获得后几月日期，该参数为正数
     * @return Date
     */
    public static Date getDateBeforeOrAfterMonth(final int month) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几月日期
     * @param curDate
     *
     * @param month
     *            如果要获得前几月日期，该参数为负数； 如果要获得后几月日期，该参数为正数
     * @see Calendar#add(int, int)
     * @return Date
     */
    public static Date getDateBeforeOrAfterMonth(final Date curDate, final int month) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * 得到格式化后的月份，格式为yyyy-MM，如2006-02
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate(Date, String)
     * @return String 返回格式化后的月份，格式为yyyy-MM，如2006-02
     */
    public static String getFormatMonth(final Date currDate) {
        return DateTimeUtil.getFormatDate(currDate, DateTimeUtil.MONTH_FORMAT);
    }

    /**
     * 得到格式化后的日，格式为yyyyMMdd，如20060210
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate(Date, String)
     * @return String 返回格式化后的日，格式为yyyyMMdd，如20060210
     */
    public static String getFormatDay(final Date currDate) {
        return DateTimeUtil.getFormatDate(currDate, DateTimeUtil.DAY_FORMAT);
    }

    /**
     * 得到格式化后的日，格式为yyMMdd，如060210
     *
     * @param currDate
     *            要格式化的日期
     * @see #getFormatDate(Date, String)
     * @return String 返回格式化后的日，格式为yyMMdd，如060210
     */
    public static String getNowFormatDay(final Date currDate) {
        return DateTimeUtil.getFormatDate(currDate, DateTimeUtil.DATE_FORMDATE_TO_SECOND);
    }

    /**
     * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     *
     * @param currDate
     *            要格式化的日期
     * @see Calendar#getMinimum(int)
     * @see #getFormatDate(Date, String)
     * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     */
    public static String getFirstDayOfMonth() {
        final Calendar cal = Calendar.getInstance();
        final int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return DateTimeUtil.getFormatDate(cal.getTime(), DateTimeUtil.DATE_FORMAT);
    }

    /**
     * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     *
     * @param currDate
     *            要格式化的日期
     * @see Calendar#getMinimum(int)
     * @see #getFormatDate(Date, String)
     * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
     */
    public static String getFirstDayOfMonth(final Date currDate) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        final int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return DateTimeUtil.getFormatDate(cal.getTime(), DateTimeUtil.DATE_FORMAT);
    }

    /**
     * 得到日期的前或者后几小时
     * @param curDate
     *
     * @param iHour
     *            如果要获得前几小时日期，该参数为负数； 如果要获得后几小时日期，该参数为正数
     * @see Calendar#add(int, int)
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几小时
     */
    public static Date getDateBeforeOrAfterHours(final Date curDate, final int iHour) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.HOUR_OF_DAY, iHour);
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几分钟
     * @param curDate
     * @param iMin
     *               如果要获得前几分钟日期，该参数为负数； 如果要获得后几分钟日期，该参数为正数
     * @return Date 返回参数<code>curDate</code> 得到日期的前或后几分钟
     */
    public static Date getDateBeforeOrAfterMins(final Date curDate, final int iMin) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.MINUTE, iMin);
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几秒钟
     * @param curDate
     * @param iSecond
     *               如果要获得前几秒钟日期，该参数为负数； 如果要获得后几秒钟日期，该参数为正数
     * @return Date 返回参数<code>curDate</code> 得到日期的前或后几秒钟
     */
    public final static Date getDateBeforeOrAfterSecond(final Date curDate, final int iSecond) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.SECOND, iSecond);
        return cal.getTime();
    }

    /**
     * 得到参数指定日期比当前时间的秒数
     * @param endDate
     * @return int
     */
    public static int getIntervalSeconds(final Date endDate) {
        return DateTimeUtil.getIntervalSeconds(endDate, new Date());
    }

    /**
     * 得到参数指定日期比当前时间的秒数
     * @param endDate
     * @param startDate
     * @return int
     */
    public static int getIntervalSeconds(final Date endDate, final Date startDate) {
        final Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        final Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        return (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / 1000);
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:35:43
     * @description 获取当前日期所在周的第一天(周日)
     * @param
     * @return Date
     */
    public static Date getFirstDayOfCurrentWeek() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:36:13
     * @param date
     * @description 获取传入日期所在周的第一天(周日)
     * @param
     * @return Date
     */
    public static Date getFirstDayOfWeek(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @description 获取当前日期所在周的最后一天(周六)
     * @param
     * @return Date
     */
    public static Date getLastDayOfCurrentWeek() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @param date
     * @description 获取传入日期所在周的最后一天(周六)
     * @param
     * @return Date
     */
    public static Date getLastDayOfWeek(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:35:43
     * @param calendar
     * @description 获取当前日期所在周的第一天(周日)
     * @return Date
     */
    public static Date getFirstDayOfCurrentWeek(final EnumCalendar calendar) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, calendar.getCalendar());
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:36:13
     * @param date
     * @description 获取传入日期所在周的第一天(周日)
     * @param calendar
     * @return Date
     */
    public static Date getFirstDayOfWeek(final Date date, final EnumCalendar calendar) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, calendar.getCalendar());
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @description 获取当前日期所在周的最后一天(周六)
     * @param calendar
     * @return Date
     */
    public static Date getLastDayOfCurrentWeek(final EnumCalendar calendar) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, calendar.getCalendar());
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @param date
     * @description 获取传入日期所在周的最后一天(周六)
     * @param calendar
     * @return Date
     */
    public static Date getLastDayOfWeek(final Date date, final EnumCalendar calendar) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, calendar.getCalendar());
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @description 获取当前日期所在月的第一天
     * @param
     * @return Date
     */
    public static Date getFirstDayDateOfMonth() {
        final Calendar cal = Calendar.getInstance();
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @description 获取当前日期所在月的第一天，例如今天为2016-02-32，则返回：2016-02-01 00:00:00.000
     * @param
     * @return Date
     */
    public static Date getFirstDateOfMonth() {
        final Calendar cal = Calendar.getInstance();
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @param date
     * @description 获取当前日期所在月的第一天，例如今天为2016-02-32，则返回：2016-02-01 00:00:00.000
     * @param
     * @return Date
     */
    public static Date getFirstDateOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @description 获取当前日期所在月的第一天，精确到毫秒
     * @param
     * @return Date
     */
    public static Long getFirstTimeOfMonth() {
        final Calendar cal = Calendar.getInstance();
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @param date
     * @description 获取当前日期所在月的第一天，精确到毫秒
     * @param
     * @return Date
     */
    public static Long getFirstTimeOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @param date
     * @description 获取传入日期所在月的第一天
     * @param
     * @return Date
     */
    public static Date getFirstDayDateOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        final Date startDate = DateTimeUtil.getFormatDate("20170327230000000",
            DateTimeUtil.FULL_TIME_FORMAT);
        final Date currentDate = DateTimeUtil.getCurrDate();
        final int day = DateTimeUtil.getDays(startDate, currentDate);
        System.out.println(day);
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @description 获取当前日期所在月的最后一天
     * @param
     * @return Date
     */
    public static Date getLastDayOfMonth() {
        final Calendar cal = Calendar.getInstance();
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     * 获取当月的最后一天，格式：YYYY-MM-DD 00:00:00.000
     *
     * @return Date
     */
    public static Date getLastDaytimeOfMonth() {
        final Calendar cal = Calendar.getInstance();
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @param date
     * @description 获取传入日期所在月的最后一天
     * @param
     * @return Date
     */
    public static Date getLastDayOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @param date
     * @param week
     * @description 获取传入日期所在月的最后一天
     * @return Date
     */
    public static Date getNextWeek(final Date date, final int week) {
        final Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_YEAR, week);
        return cal.getTime();
    }

    /**
     * @author JinYiDing
     * @version 2014年5月30日 下午2:39:57
     * @param date
     * @param monthCount
     * @description 获取传入日期所在月的最后一天
     *            date : 传入日期,如果为null则为今天
     *            month : 要偏移的月数,如果正数则向后几月,如果负数则向前几月,0为本月
     * @return Date
     */
    public static Date getNextMonth(final Date date, final int monthCount) {
        final Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, monthCount);
        return cal.getTime();
    }

    /**
     * 计算日期间隔月份
     * @param smallDate
     * @param bigDate
     * @return int
     */
    public static int getMonths(final Date smallDate, final Date bigDate) {
        int iMonth = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(smallDate);
            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(bigDate);
            if (objCalendarDate2.equals(objCalendarDate1)) {
                return 0;
            }
            if (objCalendarDate1.after(objCalendarDate2)) {
                final Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR)) {
                iMonth = (((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
                    .get(Calendar.YEAR)) * 12) + objCalendarDate2.get(Calendar.MONTH))
                         - objCalendarDate1.get(Calendar.MONTH);
            } else {
                iMonth = objCalendarDate2.get(Calendar.MONTH)
                         - objCalendarDate1.get(Calendar.MONTH);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /**
     * 返回相隔天数
     * @param startDate
     * @param endDate
     * @return int
     */
    public static int getDays(final Date startDate, final Date endDate) {
        final Calendar calst = Calendar.getInstance();
        final Calendar caled = Calendar.getInstance();
        calst.setTime(startDate);
        caled.setTime(endDate);
        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        final int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime()
            .getTime() / 1000)) / 3600 / 24;

        return days;
    }

    /**
     * 精确到秒
     * @return long
     */
    public static long getCurrSeconds() {
        return DateTimeUtil.getCurrDate().getTime() / DateTimeUtil.TH_1000;
    }

    /**
     * 将日期类型时间转换为14位数字类型yyyyMMddHHmmss
     * @param date
     * @return long
     */
    public final static long date2Long(final Date date) {
        if (date == null) {
            return 0l;
        }
        return Long.parseLong(new SimpleDateFormat(DateTimeUtil.DATE_FORMDATE_TO_SECOND)
            .format(date));
    }

    /**
     * 将秒转换为毫秒
     *
     * @param l
     * @return <a>Date</a>
     */
    public static Date tenLong2Date(final Long l) {
        if (l == null) {
            return null;
        }
        return new Date(DateTimeUtil.TH_1000 * l);
    }

    /**
     * 处理传入日期为一天的最后一刻, 返回日期字符串
     * <p>
     * 如: 2016-01-01 ---> 2016-01-01 23:59:59
     *
     * @param date
     * @return String
     */
    public final static String getDateLastStr(final Date date) {
        if (null == date) {
            return null;
        }
        final String dateFormat = DateTimeUtil.getFormatDate(date);
        return StringUtils.join(dateFormat, DateTimeUtil.EACH_DAY_HMS_LAST);
    }

    /**
     * 处理传入日期为一天的最后一刻, 返回日期型
     * <p>
     * 如: 2016-01-01 ---> 2016-01-01 23:59:59
     *
     * @param date
     * @return Date
     */
    public final static Date getDateLast(final Date date) {
        return DateTimeUtil.getFormatDateTime(DateTimeUtil.getDateLastStr(date));
    }

    /**
     * 处理传入日期为一天的最初一刻, 返回日期字符串
     * <p>
     * 如: 2016-01-01 ---> 2016-01-01 00:00:00
     *
     * @param date
     * @return String
     */
    public final static String getDayBeginStr(final Date date) {
        if (null == date) {
            return null;
        }
        final String dateFormat = DateTimeUtil.getFormatDate(date);
        return StringUtils.join(dateFormat, DateTimeUtil.EACH_DAY_HMS_BEGIN);
    }

    /**
     * 处理传入日期为一天的最初始一刻, 返回日期型
     * <p>
     * 如: 2016-01-01 ---> 2016-01-01 00:00:00
     *
     * @param date
     * @return Date
     */
    public final static Date getDayBegin(final Date date) {
        return DateTimeUtil.getFormatDateTime(DateTimeUtil.getDayBeginStr(date));
    }

    /**
     * 返回当前日期第几周
     * @param date
     * @param calendar
     * @return int 1:表示第一周，依次类推
     */
    public final static int getWeeks(final Date date, final EnumCalendar calendar) {
        final Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(calendar.calendar);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 格式化日期：yyyyWW
     * 说明： week表示第几周; 1:表示第一周，依次类推
     * @param date
     * @param calendar
     * @return String
     */
    public final static String getFormatDateToWeek(final Date date, final EnumCalendar calendar) {
        final String year = DateTimeUtil.getFormatDate(date, DateTimeUtil.YEAR_FORMAT);
        final Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(calendar.calendar);
        c.setTime(date);
        final int week = c.get(Calendar.WEEK_OF_YEAR);
        return DateTimeUtil.getFormatDate2Week(year, week);
    }

    /**
     * 格式化日期：yyyyWW
     * 说明： week表示第几周; 0:表示第一周，依次类推
     * @param date
     * @param calendar
     * @return String
     */
    public final static String getFormatDate2Week(final Date date, final EnumCalendar calendar) {
        final String year = DateTimeUtil.getFormatDate(date, DateTimeUtil.YEAR_FORMAT);
        final Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(calendar.calendar);
        c.setTime(date);
        final int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        return DateTimeUtil.getFormatDate2Week(year, week);
    }

    /**
     *
     * @param year
     * @param week
     * @return String
     */
    public final static String getFormatDate2Week(final String year, final int week) {
        return year + DateTimeUtil.fillLeftZero(week, 2);
    }

    private static String fillLeftZero(final int week, final int length) {
        return String.format("%0" + length + "d", week);
    }

    /**
     * 处理传入日期为一天的最后一刻, 返回日期字符串
     * <p>
     * 如: 2016-01-01 ---> 2016-01-01 23:59:59
     *
     * @param date
     * @return String
     */
    public final static String handleDateToDayLastStr(final Date date) {
        if (null == date) {
            return null;
        }
        final String dateFormat = DateTimeUtil.getFormatDate(date);
        return StringUtils.join(dateFormat, DateTimeUtil.EACH_DAY_HMS_LAST);
    }

    /**
     * 处理传入日期为一天的最后一刻, 返回日期型
     * <p>
     * 如: 2016-01-01 ---> 2016-01-01 23:59:59
     *
     * @param date
     * @return Date
     */
    public final static Date handleDateToDayLast(final Date date) {
        return DateTimeUtil.getFormatDateTime(DateTimeUtil.handleDateToDayLastStr(date));
    }

    /**
     *
     * @author LJJ
     * @version $Id: DateTimeUtil.java, v 0.1 2017年3月28日 下午1:25:56 LJJ Exp $
     */
    public enum EnumCalendar {

        /**  星期一*/
        MONDAY(Calendar.MONDAY),

        /**  星期天*/
        SUNDAY(Calendar.SUNDAY);

        EnumCalendar(final int calendar) {
            this.setCalendar(calendar);
        }

        private int calendar;

        /**
         * Getter method for property <tt>calendar</tt>.
         *
         * @return property value of calendar
         */
        public int getCalendar() {
            return this.calendar;
        }

        /**
         * Setter method for property <tt>calendar</tt>.
         *
         * @param calendar value to be assigned to property calendar
         */
        public void setCalendar(final int calendar) {
            this.calendar = calendar;
        }
    }

}