package cat.customize.bluetooth;


import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import cat.customize.ulite.system.CtLog;

/**
 * Created by HSL
 * on 2023/4/24.
 */

public class CtBluetoothHandler extends Handler {
    private OnCtBluetoothListener onCtBluetoothListener;
    public static final int MESSAGE_STATE_CHANGE = 1;  //状态改变
    public static final int MESSAGE_READ = 2; //接收
    public static final int MESSAGE_WRITE = 3;   //发送
    public static final int MESSAGE_DEVICE_NAME = 4;  //设备 名称
    public static final int MESSAGE_TOAST = 5;  //连接失败

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    public void setOnCtBluetoothListener(OnCtBluetoothListener onCtBluetoothListener) {
        this.onCtBluetoothListener = onCtBluetoothListener;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if (onCtBluetoothListener == null) return;
        switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                int arg1 = msg.arg1;
                onCtBluetoothListener.connectStatus(arg1);
                break;
            case MESSAGE_TOAST:
                String message = msg.getData().getString(TOAST);
                CtLog.d(message);
                onCtBluetoothListener.connectError(message);
                break;
            case MESSAGE_DEVICE_NAME:
                String data = msg.getData().getString(DEVICE_NAME);
                onCtBluetoothListener.connectSuccess(data);
                break;
            case MESSAGE_READ: //读取接收数据
                String readMessage = (String) msg.obj;
                onCtBluetoothListener.receiveMessage(readMessage);
                break;
            case MESSAGE_WRITE: //发送端，正在发送的数据
                byte[] writeBuf = (byte[]) msg.obj;
                String writeMessage = new String(writeBuf);
                onCtBluetoothListener.sendMessage(writeMessage);
                break;

        }
    }
}
