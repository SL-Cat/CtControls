package cat.customize.socket;

import android.os.Handler;
import android.os.Message;

import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import java.nio.charset.Charset;

import cat.customize.socket.unit.ReaderHelper;
import cat.customize.socket.unit.SocketCode;
import cat.customize.socket.unit.SocketSendData;


/**
 * Created by HSL
 * on 2023/6/26.
 */

public class SocketSend {

    private static SocketSend instance;
    private IConnectionManager manager;
    private static String ip;
    private static int port;
    private MyServerAction socketActionListener;

    public SocketSend(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public void initSocket(Handler handler) {
        ConnectionInfo info = new ConnectionInfo(ip, port);
        manager = OkSocket.open(info);
        OkSocketOptions options = manager.getOption();
        //调用OkSocket,开启这次连接的通道,调用通道的连接方法进行连接.
        OkSocketOptions.Builder builder = new OkSocketOptions.Builder(options);
        builder.setConnectTimeoutSecond(5);
        builder.setReaderProtocol(new ReaderHelper());
        //将新的修改后的参配设置给连接管理器
        manager.option(builder.build());
        socketActionListener = new MyServerAction(handler);
        manager.registerReceiver(socketActionListener);
        manager.connect();
    }


    private class MyServerAction extends SocketActionAdapter {

        private Handler handler;
        public MyServerAction(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
            super.onSocketDisconnection(info, action, e);
            Message msg = handler.obtainMessage();
            msg.what = SocketCode.SERVICE_CONNECT_SHUT;
            if(e!=null) {
                msg.obj = e.toString();
            }else {
                msg.obj = "";
            }
            handler.sendMessage(msg);
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
            super.onSocketConnectionFailed(info, action, e);
            //连接失败
            handler.sendEmptyMessage(SocketCode.SERVICE_CONNECT_FILED);

        }

        @Override
        public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
            super.onSocketWriteResponse(info, action, data);
            //发送的数据
        }

        @Override
        public void onPulseSend(ConnectionInfo info, IPulseSendable data) {
            super.onPulseSend(info, data);
            //发送心跳后的回调
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            super.onSocketReadResponse(info, action, data);
            //辅机返回的接收到的数据
            String str = new String(data.getBodyBytes(), Charset.forName("utf-8"));
            Message msg = handler.obtainMessage();
            msg.what = SocketCode.SERVICE_RECEIVE_DATA;
            msg.obj = str;
            handler.sendMessage(msg);
        }

        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
            super.onSocketConnectionSuccess(info, action);
            //连接成功");
            handler.sendEmptyMessage(SocketCode.SERVICE_CONNECT_SUCCESS);
        }
    }


    public void send(String jsonStr) {
        manager.send(new SocketSendData(jsonStr));
    }


    public void stop() {
        if (manager != null) {
            manager.disconnect();
            manager.unRegisterReceiver(socketActionListener);
        }
    }


}
