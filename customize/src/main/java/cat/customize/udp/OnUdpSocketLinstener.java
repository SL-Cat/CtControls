package cat.customize.udp;

/**
 * Created by HSL on 2021/11/1.
 */

public interface OnUdpSocketLinstener {
    void receiveStr(String str);

    void sendStatus(boolean statu);
}
