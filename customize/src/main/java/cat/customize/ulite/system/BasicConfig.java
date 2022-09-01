package cat.customize.ulite.system;

import android.content.Context;

import cat.customize.DateUtils;

/**
 * Created by HSL on 2020/12/23.
 */

public class BasicConfig {

    public static Context ApplicationContext;
    private static boolean DebugMode = true; //日志是否打印
    private static boolean LogToFile = false; //是否打印成文件保存
    private static String Path = "/sdcard/Lead/Logs/";
    private static String FileName = DateUtils.getStringDateS() + "_OkHttpManager.text";

    public static void init(Context context) {
        ApplicationContext = context.getApplicationContext();
    }

    public static boolean isDebugMode() {
        return DebugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        DebugMode = debugMode;
    }

    public static boolean isLogToFile() {
        return LogToFile;
    }

    public static void setLogToFile(boolean logToFile,String path,String fileName) {
        LogToFile = logToFile;
        Path = path;
        FileName = fileName;
    }

    public static String getFileName(){
        return FileName;
    }

    public static String getPath(){
        return Path;
    }
}
