package me.code.yiguitest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author CWJ
 * @version $Id: DateFmtThreadLocalUtils.java, v 0.1 2015年9月9日 下午10:01:45 CWJ Exp $
 */
public class DateFmtThreadLocalUtils {

    /** 锁对象 */
    private static final Object                               lockObj = new Object();

    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap  = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     * s
     * @param pattern
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = DateFmtThreadLocalUtils.sdfMap.get(pattern);
        if (tl == null) {
            synchronized (DateFmtThreadLocalUtils.lockObj) {
                tl = DateFmtThreadLocalUtils.sdfMap.get(pattern);
                if (tl == null) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    DateFmtThreadLocalUtils.sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * @param date
     * @param pattern
     * @return String
     */
    public static String format(final Date date, final String pattern) {
        return DateFmtThreadLocalUtils.getSdf(pattern).format(date);
    }

    /**
     * 
     * @param dateStr
     * @param pattern
     * @return Date
     * @throws ParseException
     */
    public static Date parse(final String dateStr, final String pattern) throws ParseException {
        return DateFmtThreadLocalUtils.getSdf(pattern).parse(dateStr);
    }
}
