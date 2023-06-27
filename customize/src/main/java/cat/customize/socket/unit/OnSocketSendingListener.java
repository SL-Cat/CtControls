package cat.customize.socket.unit;

/**
 * Created by HSL
 * on 2023/6/25.
 */

public interface OnSocketSendingListener {

    void connectStatus(boolean status);
    void sendingListener(String sendMsg);
    void onError(String msg);
    void closeConnect(String close);

}
