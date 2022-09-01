package cat.customize.ulite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HSL
 * on 2022/7/26.
 */

public class DateUtils {

    public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_DAY = "yyyy-MM-dd";
    public static final String FORMAT_DATE_S = "yyyyMMddHHmmss";
    public static final String FORMAT_DATE_Day_S = "-HHmmss";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_TIME_S = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        return format.format(new Date());
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getCurrentDateDay_s() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_Day_S);
        return format.format(new Date());
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getCurrentDateDay() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_DAY);
        return format.format(new Date());
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getCurrentDate_S() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_S);
        return format.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat(FORMAT_TIME).format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @param format
     * @return
     */
    public static String getCurrentTimes(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 获取n day前的时间
     *
     * @param day
     * @return 时间戳
     */
    public static long getTimeMillisBeforeDay(int day) {
        long current = System.currentTimeMillis();
        long last = current - (1000L * 60 * 60 * 24 * day);
        return last;
    }

    /**
     * 获取n day前的时间
     *
     * @param day
     * @return
     */
    public static String getDateBeforeDay(int day) {
        long current = System.currentTimeMillis();
        long last = current - (1000L * 60 * 60 * 24 * day);
        String dateTime = timeMillisToDate(last);
        return dateTime;
    }

    /**
     * 时间戳转日期
     *
     * @param timeMillis
     * @return
     */
    public static String timeMillisToDate(Object timeMillis) {
        try {
            if (timeMillis instanceof Long) {
                long millis = (long) timeMillis;
                Date date = new Date(millis);
                return new SimpleDateFormat(FORMAT_DATE).format(date);
            } else if (timeMillis instanceof String) {
                long millis = Long.parseLong((String) timeMillis);
                Date date = new Date(millis);
                return new SimpleDateFormat(FORMAT_DATE).format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间转换成时间戳,参数和返回值都是字符串
     *
     * @param s
     * @return res
     * @throws ParseException
     */
    public static String dateToStamp(String s) {
        String res = null;
        //设置时间模版
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts);
            return res;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }
}
