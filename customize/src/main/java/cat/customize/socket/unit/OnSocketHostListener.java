package cat.customize.socket.unit;

/**
 * Created by HSL
 * on 2023/6/26.
 */

public interface OnSocketHostListener {

    void receiveOpen();
    void connectStatus(boolean status);
    void connectDevice(String ip);
    void receiveListener(String socketMsg);

}
