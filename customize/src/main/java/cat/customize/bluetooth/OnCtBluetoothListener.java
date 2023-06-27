package cat.customize.bluetooth;

/**
 * Created by HSL
 * on 2023/4/21.
 */

public interface OnCtBluetoothListener {

    void connectStatus(int status);
    void connectSuccess(String string);
    void connectError(String message);
    void sendMessage(String data);

    void receiveMessage(String data);
}
