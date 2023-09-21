package cat.customize.ulite.system;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cat.customize.ulite.DateUtils;

/**
 * Created by HSL on 2020/12/23.
 */

public class CtBasicConfig {

    public static Context ApplicationContext;
    private static boolean DebugMode = true; //日志是否打印
    private static boolean LogToFile = true; //是否打印成文件保存
    private static String Path = "/sdcard/Lead/Logs/";
    private static String FileName = DateUtils.getStringDateS() + "_log.text";
    private static List<Integer> logList = new ArrayList<>(); //禁用的日志级别

    public static void init(Context context) {
        ApplicationContext = context.getApplicationContext();
    }

    public static boolean isDebugMode() {
        return DebugMode;
    }


    public static void setDebugMode(boolean debugMode) {
        DebugMode = debugMode;
    }

    public static void setDebugMode(boolean debugMode, int... level) {
        DebugMode = debugMode;
        closeLog(level);
    }

    public static boolean isLogToFile() {
        return LogToFile;
    }

    public static void setLogToFile(boolean logToFile, String path, String fileName) {
        LogToFile = logToFile;
        Path = path;
        FileName = fileName;
    }

    public static String getFileName() {
        return FileName;
    }

    public static String getPath() {
        return Path;
    }


    /**
     * 设置日志输出级别
     * Log.VERBOSE:1
     * Log.DEBUG:2
     * Log.INFO:3
     * Log.WARN:4
     * Log.ERROR:5
     *
     * @param level
     * @return
     */
    public static List<Integer> closeLog(int... level) {
        logList.clear();
        for (int i : level) {
            logList.add(i);
        }
        return logList;
    }

    /**
     * 判断是否可以输出
     *
     * @param level
     * @return
     */
    public static boolean isLog(int level) {
        if (logList != null && logList.size() > 0) {
            if (logList.contains(level)) {
                return false;
            } else {
                return true;
            }
        } else return true;
    }
}
