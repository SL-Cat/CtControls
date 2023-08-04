package cat.customize.ulite;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

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
    static Calendar cal = Calendar.getInstance();

    /**
     * 获取当前设备时区
     * @return
     */
    public static String getTimeZone(){
        String displayName = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
        return displayName;
    }

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


    public static int getYear() {
        return cal.get(Calendar.YEAR);//获取年份
    }

    public static int getMonth() {
        return cal.get(Calendar.MONTH) + 1;//获取月份
    }

    public static int getDay() {
        return cal.get(Calendar.DATE);//获取日
    }

    public static int getHour() {
        //  return  cal.get(Calendar.HOUR);//获取小时,12小时制
        return cal.get(Calendar.HOUR_OF_DAY); // 24小时制
    }

    public static int getMinute() {
        return cal.get(Calendar.MINUTE);//获取分
    }

    public static int getSecond() {
        return cal.get(Calendar.SECOND);//获取秒
    }

    public static String getWeek() {

        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }


    public static String getWeek(long date) {

        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }


    /**
     * 实现给定某日期，判断是星期几
     *
     * @param date 必须yyyy-MM-dd
     * @return
     */
    public static String getWeekday(String date) {
        SimpleDateFormat sd = new SimpleDateFormat(FORMAT_DATE_DAY);
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdw.format(d);
    }

    /**
     * @param sdf
     * @return
     */
    public static String getTime(SimpleDateFormat sdf) {
        //"yyyy-MM-dd HH:mm:ss aa",最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        long time = System.currentTimeMillis();
        // return time / 1000;
        return time;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str   字符串日期 “2016-02-26 12:00:00”
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            //  return sdf.parse(date_str).getTime() / 1000;
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间戳字符串转换成日期格式
     *
     * @param seconds 精确到毫秒
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String timeStamp2Date(long seconds, String format) {

        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // return sdf.format(new Date(Long.valueOf(seconds+"000")));
        return sdf.format(new Date(seconds));
    }

    /**
     * @param dateStr 2008-08-08 12:10:12
     * @param format  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getFormatDate(String dateStr, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断是否为今天或者今天以后
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isAfterToday(int year, int month, int day) {
        if (year > DateUtils.getYear()) {
            return true;
        } else if (year == DateUtils.getYear()) {
            if (month > DateUtils.getMonth()) {
                return true;
            } else if (month == DateUtils.getMonth()) {
                if (day >= DateUtils.getDay()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是否为当月或者当月以后
     *
     * @param year
     * @param month
     * @return
     */
    public static boolean isAfterThisMonth(int year, int month) {
        if (year > DateUtils.getYear()) {
            return true;
        } else if (year == DateUtils.getYear()) {
            if (month >= DateUtils.getMonth()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String getConvertMonth(String numberStr) {
        String[] months = {"一月", "二月", "三月", "四月", "五月",
                "六月", "七月", "八月", "九月", "十月",
                "十一月", "十二月"};
        try {
            int month = Integer.valueOf(numberStr);
            return months[month-1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE_DAY);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     * @param time
     */
    public static String getStringDate(String time) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(time);
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE_DAY);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yy-MM-dd
     */
    public static String getStringDateS() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        String dateString = formatter.format(now);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShortS() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss  *   * @param dateDate  * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss  *   * @param dateDate  * @return
     */
    public static String dateToStrMonthAndDay(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE_DAY);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE_DAY);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 得到现在时间
     *
     * @return
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 提取一个月中的最后一天
     *
     * @param day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在时间的年月（4位）
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(2, 8);
        return hour;
    }

    /**
     * 得到现在时间的年（2位）
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToYear() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(2, 4);
        return hour;
    }

    /**
     * 得到现在时间的日（2位）
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToDay2() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(6, 8);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat(FORMAT_DATE_DAY);
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_DAY);
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE_DAY);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat
     * @return
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */

    public static String getNo(int k) {

        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 返回一个随机数
     *
     * @param i
     * @return
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }


    /**
     * 判断两个时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean getTimeCompare(String date1, String date2) {
        boolean flag = false;
        //格式化时间
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginTime = CurrentTime.parse(date1);
            Date endTime = CurrentTime.parse(date2);
            //判断是否大于两天
            if (((endTime.getTime() - beginTime.getTime()) / (24 * 3600 * 1000)) >= 1) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }
}
