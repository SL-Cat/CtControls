package cat.customize;

/**
 * 防止按钮多按工具类
 */

public class SecondClickUtils {
    private static long lastClickTime = 0;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFastClick(int endTime) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < endTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
