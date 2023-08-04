package cat.customize.socket;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import cat.customize.socket.unit.OnSocketHostListener;
import cat.customize.socket.unit.OnSocketSendingListener;
import cat.customize.socket.unit.SocketCode;


/**
 * Created by HSL
 * on 2023/6/26.
 */

public class SocketHandler extends Handler {

    private OnSocketHostListener onSocketHostListener;

    private OnSocketSendingListener onSocketSendingListener;

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case SocketCode.SERVICE_CONNECT_SUCCESS://客户端连接成功
                onSocketSendingListener.connectStatus(true);
                break;
            case SocketCode.SERVICE_CONNECT_FILED://客户端连接失败
                onSocketSendingListener.connectStatus(false);
                break;
            case SocketCode.SERVICE_CONNECT_SHUT://客户端连接关闭
                String close = (String) msg.obj;
                onSocketSendingListener.closeConnect(close);
                break;
            case SocketCode.SERVICE_CONNECT_ERROR://客户端多次连接失败返回异常
                String error = (String) msg.obj;
                onSocketSendingListener.onError(error);
                break;
            case SocketCode.HOST_CONNECT_OPEN://服务端开启
                onSocketHostListener.receiveOpen();
                break;
            case SocketCode.HOST_CONNECT_SUCCESS://服务端连接成功
                String ip = (String) msg.obj;
                onSocketHostListener.connectDevice(ip);
                break;
            case SocketCode.HOST_CONNECT_SHUT://服务端连接关闭
                onSocketHostListener.connectStatus(false);
                break;
            case SocketCode.SERVICE_SEND_DATA://客户端发送数据
                break;
            case SocketCode.SERVICE_RECEIVE_DATA://客户端接收发送返回的数据
                String sendRequest = (String) msg.obj;
                onSocketSendingListener.sendingListener(sendRequest);
                break;
            case SocketCode.HOST_RECEIVE_DATA://服务端接收数据
                String request = (String) msg.obj;
                onSocketHostListener.receiveListener(request);
                break;
            case SocketCode.HOST_SEND_DATA://服务端发送接收到的数据
                break;
        }
    }

    /**
     * 服务端回调
     *
     * @param onSocketHostListener
     */
    public void setOnSocketHostListener(OnSocketHostListener onSocketHostListener) {
        this.onSocketHostListener = onSocketHostListener;
    }

    /**
     * 客户端回调
     *
     * @param onSocketSendingListener
     */
    public void setOnSocketSendingListener(OnSocketSendingListener onSocketSendingListener) {
        this.onSocketSendingListener = onSocketSendingListener;
    }
}
