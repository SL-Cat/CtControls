package cat.customize.socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.dispatcher.IRegister;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClient;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClientIOCallback;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClientPool;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IServerActionListener;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IServerManager;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IServerShutdown;
import com.xuhao.didi.socket.server.action.ServerActionAdapter;
import com.xuhao.didi.socket.server.impl.OkServerOptions;

import java.nio.charset.Charset;

import cat.customize.socket.unit.ReaderHelper;
import cat.customize.socket.unit.SocketCode;
import cat.customize.socket.unit.SocketSendData;

/**
 * Created by HSL
 * on 2023/6/19.
 */

public class SocketService implements IClientIOCallback {

    private static SocketService instance;
    private IRegister<IServerActionListener, IServerManager> server;
    private Handler mHandler;
    private MySocketService socketActionListener;
    private IServerManager iServerManager;
    private ReaderHelper protocol;

    public SocketService(int port) {
        server = OkSocket.server(port);
        protocol = new ReaderHelper();
    }


    public void initReceiver(Handler handler) {
        this.mHandler = handler;
        if (socketActionListener == null) {
            socketActionListener = new MySocketService(handler);
        }
        OkServerOptions.Builder builder = new OkServerOptions.Builder();
        builder.setReaderProtocol(protocol);
        iServerManager = server.registerReceiver(socketActionListener);
        iServerManager.listen(builder.build());
    }

    @Override
    public void onClientRead(OriginalData originalData, IClient client, IClientPool<IClient, String> clientPool) {
        String str = new String(originalData.getBodyBytes(), Charset.forName("utf-8"));
        Message msg = mHandler.obtainMessage();
        msg.what = SocketCode.HOST_RECEIVE_DATA;
        msg.obj = str;
        mHandler.sendMessage(msg);
        client.setReaderProtocol(protocol);
        clientPool.sendToAll(new SocketSendData(str));
    }



    @Override
    public void onClientWrite(ISendable sendable, IClient client, IClientPool<IClient, String> clientPool) {

    }

    public void disConnect() {
        server.unRegisterReceiver(socketActionListener);
        if (iServerManager.isLive()) {
            iServerManager.shutdown();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private class MySocketService extends ServerActionAdapter {

        private Handler handler;

        public MySocketService(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onServerListening(int serverPort) {
            super.onServerListening(serverPort);
            Log.d("result", "onServerListening: 开启服务");
            handler.sendEmptyMessage(SocketCode.HOST_CONNECT_OPEN);
        }

        @Override
        public void onClientConnected(IClient client, int serverPort, IClientPool clientPool) {
            super.onClientConnected(client, serverPort, clientPool);
            Message msg = mHandler.obtainMessage();
            msg.what = SocketCode.HOST_CONNECT_SUCCESS;
            msg.obj = client.getHostIp();
            mHandler.sendMessage(msg);
            client.addIOCallback(SocketService.this);
        }

        @Override
        public void onClientDisconnected(IClient client, int serverPort, IClientPool clientPool) {
            super.onClientDisconnected(client, serverPort, clientPool);
            Log.d("result", "onClientDisconnected: "+client.getHostIp());
        }

        @Override
        public void onServerWillBeShutdown(int serverPort, IServerShutdown shutdown, IClientPool clientPool, Throwable throwable) {
            super.onServerWillBeShutdown(serverPort, shutdown, clientPool, throwable);
            Log.d("result", "onServerWillBeShutdown: ");
        }

        @Override
        public void onServerAlreadyShutdown(int serverPort) {
            super.onServerAlreadyShutdown(serverPort);
            Log.d("result", "onServerAlreadyShutdown: ");
        }
    }
}
