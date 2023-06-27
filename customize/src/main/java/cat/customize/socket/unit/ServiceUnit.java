package cat.customize.socket.unit;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by HSL on 2020/4/16.
 */

public class ServiceUnit {

    public static String getHostIp(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //检查Wifi状态
        if (!wm.isWifiEnabled())
            wm.setWifiEnabled(true);
        WifiInfo wi = wm.getConnectionInfo();
        //获取32位整型IP地址
        int ipAdd = wi.getIpAddress();
        //把整型地址转换成“*.*.*.*”地址
        String ip = intToIp(ipAdd);
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

}
