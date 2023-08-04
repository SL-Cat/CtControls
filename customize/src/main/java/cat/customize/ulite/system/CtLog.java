package cat.customize.ulite.system;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import cat.customize.ulite.DateUtils;

/**
 * Created by HSL on 2020/12/23.
 */

public class CtLog {

    static {
        HandlerThread handlerThread = new HandlerThread("LogToFile");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        mLogFileHandler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                LogFileInfo info = (LogFileInfo) msg.obj;
                String data = info.message;
                data = info.level + ":" + data;
                if (null != data && data.length() != 0) {
                    final String finalData = data;
                    FileOutputStream fout = null;
                    try {
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            File dir = new File(CtBasicConfig.getPath());
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            File filed = new File(CtBasicConfig.getPath()+CtBasicConfig.getFileName());
                            fout = new FileOutputStream(filed, true);
                            fout.write(finalData.toString().getBytes());
                            fout.write("\r\n".getBytes());
                            fout.close();
                        }
                    } catch (Exception e) {
                        CtLog.e("Exception", e.toString());
                    }
                }
            }
        };
    }

    private static final Handler mLogFileHandler;

    private static class LogFileInfo {
        public int level = Log.DEBUG;
        public String logFileName;
        public String tag;
        public String message;
        public Throwable throwable;
    }

    public final static String getLevelText(int level) {
        switch (level) {
            case Log.VERBOSE:
                return "VERBOSE";
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            default:
                return "UNKNOW";
        }
    }

    public static void i(String tag, String msg) {
        if (CtBasicConfig.isDebugMode()) {
            log(Log.INFO, tag, msg, 0);
            logToFile(Log.INFO, tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (CtBasicConfig.isDebugMode()) {
            log(Log.INFO, tag, msg, 0);
            logToFile(Log.INFO, tag, msg, tr);
        }
    }

    public static void d(String msg) {
        if (CtBasicConfig.isDebugMode()) {
            log(Log.DEBUG, "cat_d", msg, 0);
            logToFile(Log.DEBUG, "cat_d", DateUtils.getStringDate() + ":" + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (CtBasicConfig.isDebugMode()) {
            log(Log.DEBUG, tag, msg, 0);
            logToFile(Log.DEBUG, tag, DateUtils.getStringDate() + ":" + msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (CtBasicConfig.isDebugMode()) {
            log(Log.DEBUG, tag, msg, 0);
            logToFile(Log.DEBUG, tag, msg, tr);
        }
    }

    private static void log(int type, String tag, String msg, int index) {
        if (msg.length() > ((4000 * index) + 4000)) {
            String str = msg.substring(index * 4000, (index * 4000) + 4000);
            logInfo(type, tag, str, msg, index, true);
        } else {
            String str = msg.substring(index * 4000);
            logInfo(type, tag, str, msg, index, false);
        }
    }

    private static void logInfo(int type, String tag, String str, String msg, int index, boolean isLength) {
        switch (type) {
            case Log.VERBOSE:
                Log.v(tag, str == null ? "" : str);
                break;
            case Log.DEBUG:
                Log.d(tag, str == null ? "" : str);
                break;
            case Log.INFO:
                Log.i(tag, str == null ? "" : str);
                break;
            case Log.WARN:
                Log.w(tag, str == null ? "" : str);
                break;
            case Log.ERROR:
                Log.e(tag, str == null ? "" : str);
                break;
        }
        if (isLength) {
            index++;
            log(type, tag, msg, index);
        }
    }

    public static void e(String tag, String msg) {
        if (CtBasicConfig.isDebugMode()) {
            log(Log.ERROR, tag, msg, 0);
            logToFile(Log.ERROR, tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (CtBasicConfig.isDebugMode()) {
            log(Log.ERROR, tag, msg, 0);
            logToFile(Log.ERROR, tag, msg, tr);
        }
    }

    public static void v(String tag, String msg) {
        if (CtBasicConfig.isDebugMode()) {
            Log.v(tag, msg == null ? "" : msg);
            logToFile(Log.VERBOSE, tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (CtBasicConfig.isDebugMode()) {
            Log.v(tag, msg == null ? "" : msg, tr);
            logToFile(Log.VERBOSE, tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (CtBasicConfig.isDebugMode()) {
            Log.w(tag, msg == null ? "" : msg);
            logToFile(Log.WARN, tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (CtBasicConfig.isDebugMode()) {
            Log.w(tag, msg == null ? "" : msg, tr);
            logToFile(Log.WARN, tag, msg, tr);
        }
    }

    /**
     * 保存成文件
     *
     * @param logFileName 文件的位置
     */
    public static void logToFile(String logFileName, int level, String tag, String message, Throwable throwable) {
        if (CtBasicConfig.isLogToFile()) {
            LogFileInfo info = new LogFileInfo();
            info.logFileName = logFileName;
            info.level = level;
            info.tag = tag;
            info.message = message;
            info.throwable = throwable;
            Message msg = mLogFileHandler.obtainMessage(0, info);
            msg.sendToTarget();
        }
    }

    public static void logToFile(String logFileName, int level, String tag, String message) {
        logToFile(logFileName, level, tag, message, null);
    }

    public static void logToFile(String logFileName, String tag, String message) {
        logToFile(logFileName, Log.DEBUG, tag, message, null);
    }

    public static void logToFile(int level, String tag, String message, Throwable throwable) {
        logToFile(CtBasicConfig.getFileName(), level, tag, message, throwable);
    }

    public static void logToFile(int level, String tag, String message) {
        logToFile(CtBasicConfig.getFileName(), level, tag, message);
    }

    public static void logToFile(String tag, String message) {
        logToFile(CtBasicConfig.getFileName(), tag, message);
    }
}
