package cat.customize.socket;

import cat.customize.socket.unit.OnSocketHostListener;
import cat.customize.socket.unit.OnSocketSendingListener;

/**
 * Created by HSL
 * on 2023/6/26.
 */

public class SocketUnit {

    private SocketHandler handler;
    private SocketService socketService;
    private SocketSend socketSend;

    public SocketUnit() {
        handler = new SocketHandler();
    }


    /**
     * 初始服务端配置
     * @param port
     * @param onSocketHostListener
     */
    public void initHost(int port, OnSocketHostListener onSocketHostListener){
        handler.setOnSocketHostListener(onSocketHostListener);
        socketService =new SocketService(port);
        socketService.initReceiver(handler);
    }

    /**
     * 初始客户端配置
     * @param ip
     * @param port
     */
    public void initSend(String ip, int port){
        socketSend =new SocketSend(ip,port);
        socketSend.initSocket(handler);
    }

    /**
     * 服务端回调监听
     */
    public void setSendListener(OnSocketSendingListener onSocketSendingListener){
        handler.setOnSocketSendingListener(onSocketSendingListener);
    }

    /**
     * 服务端发送数据
     * @param msg
     */
    public void sendMsg(String msg){
        if(socketSend!=null){
            socketSend.send(msg);
        }
    }

    /**
     * 关闭连接
     */
    public void closeConnect(){
        if (socketService != null) {
            socketService.disConnect();
            socketService = null;
        }
        if(socketSend!=null){
            socketSend.stop();
            socketSend = null;
        }
    }

    public void removeHandler(){
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
