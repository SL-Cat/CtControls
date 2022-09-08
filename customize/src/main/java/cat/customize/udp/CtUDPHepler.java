package cat.customize.udp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by HSL on 2021/10/29.
 */

public class CtUDPHepler {

    private String TAG = CtUDPHepler.class.getName();

    public OnUdpSocketLinstener onUDPSocketServiceLinstener;
    public OnUdpSocketLinstener onUDPSocketClinentLinstener;

    private static CtUDPHepler ctUdpHepler;

    private Context context;

    private Thread mServiceThread;
    private Thread mClientThread;

    private static final int RECEIVE_STR = 0;
    private static final int SEND_STATUS = 1;

    private ServiceRunnable serviceRunnable;

    private ClientRunnble mClientRunnble;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVE_STR:
                    String str = (String) msg.obj;
                    if (str != null) {
                        if (onUDPSocketServiceLinstener != null) {
                            onUDPSocketServiceLinstener.receiveStr(str);
                        }
                    }
                    break;
                case SEND_STATUS:
                    if (onUDPSocketClinentLinstener != null) {
                        onUDPSocketClinentLinstener.sendStatus(true);
                    }
                    break;
            }
        }
    };
    private DatagramSocket serviceSocket;
    private DatagramSocket clinentSocket;


    public static CtUDPHepler getInstance(Context context) {
        if (ctUdpHepler == null) {
            synchronized (CtUDPHepler.class) {
                ctUdpHepler = new CtUDPHepler(context);
                return ctUdpHepler;
            }
        }
        return ctUdpHepler;
    }

    /**
     * 初始化
     *
     * @param context
     */
    private CtUDPHepler(Context context) {
        this.context = context;
    }

    /**
     * 开启服务端,接收信息
     */
    public void startService(int prot, OnUdpSocketLinstener onUdpSocketLinstener) {
        this.onUDPSocketServiceLinstener = onUdpSocketLinstener;
        if (serviceRunnable == null) {
            serviceRunnable = new ServiceRunnable(prot);
            mServiceThread = new Thread(serviceRunnable);
            mServiceThread.start();
        }
    }


    /**
     * 开启服务端发送信息
     *
     * @param ip
     */
    public void startClient(final String ip, int port, OnUdpSocketLinstener onUDPSocketClinentLinstener) {
        this.onUDPSocketClinentLinstener = onUDPSocketClinentLinstener;
        if (mClientRunnble == null) {
            mClientRunnble = new ClientRunnble(ip, port);
            mClientThread = new Thread(mClientRunnble);
            mClientThread.start();
        }
    }


    /**
     * 报文起始位标识
     */
    private String headStr = "";

    /**
     * 报文头部长度
     */
    private int headLenth = 0;

    /**
     * 报文长度记录长度
     */
    private int headIndex = 0;

    /**
     * 添加请求头
     *
     * @param head         报文头判断字段
     * @param heandAllSize 报文前缀头总长度
     */
    public void addHead(String head, int heandAllSize) {
        this.headStr = head;
        this.headLenth = heandAllSize;
        this.headIndex = heandAllSize - head.length();
    }

    private class ServiceRunnable implements Runnable {

        private int prot;

        public ServiceRunnable(int prot) {
            this.prot = prot;
        }

        @Override
        public void run() {

            int maxLength = 2048;
            byte[] result = new byte[maxLength];
            try {
                if (serviceSocket == null) {
                    serviceSocket = new DatagramSocket(null);
                    serviceSocket.setReuseAddress(true);
                    //设置本地端口
                    serviceSocket.bind(new InetSocketAddress(prot));
                }
                while (true) {
                    DatagramPacket reveive = new DatagramPacket(result, result.length);
                    serviceSocket.receive(reveive);
                    byte[] data = reveive.getData();
                    byte[] head = headStr.getBytes();
                    String strs = new String(data, 0, data.length);

                    for (int i = 0; i < data.length - headLenth; i++) {
                        String ss = new String(data, i, head.length);
                        if (ss.equals(headStr)) {
                            byte[] bBodyLen = new byte[headIndex];
                            System.arraycopy(data, i + headStr.length(), bBodyLen, 0, headIndex);
                            String lenth = new String(bBodyLen, 0, bBodyLen.length);
                            Integer length = Integer.valueOf(lenth);

                            if (data.length - headLenth - i >= length) {
                                byte[] dataStr = new byte[length];
                                System.arraycopy(data, i + headLenth, dataStr, 0, length);

                                String str = new String(dataStr, 0, dataStr.length);

                                Message msg = mHandler.obtainMessage();
                                msg.obj = str;
                                msg.what = RECEIVE_STR;
                                mHandler.sendMessage(msg);
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stopService();
            }
        }
    }

    private boolean sendFalg = false;

    private String sendData = null;

    private class ClientRunnble implements Runnable {

        private String serviceIp;
        private int port;


        public ClientRunnble(String serviceIp, int port) {
            this.serviceIp = serviceIp;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                if (clinentSocket == null) {
                    clinentSocket = new DatagramSocket(null);
                }
                clinentSocket.setReuseAddress(true);
                //设置本地端口
                clinentSocket.bind(new InetSocketAddress(port));
                InetAddress inetAddress = InetAddress.getByName(serviceIp);
                while (true) {
                    if (sendFalg && sendData != null) {
                        byte[] data = sendData.getBytes();
                        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, port);
                        clinentSocket.send(datagramPacket);
                        mHandler.sendEmptyMessage(SEND_STATUS);
                        sendFalg = false;
                    }
                    SystemClock.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送数据
     *
     * @param str
     */
    public void sendStr(String str) {
        if (sendHead != null) {
            String length = String.valueOf(str.length());
            while (length.length() < dataLenth) {
                length = "0" + length;
            }
            this.sendData = sendHead + length + str;
        } else {
            this.sendData = str;
        }
        sendFalg = true;
    }

    private String sendHead = null;
    private int dataLenth = 0;


    /**
     * 发送带有数据头的数据
     *
     * @param headStr   数据头标识
     * @param dataLenth 数据内容长度描述长度位
     */
    public void addSendHead(String headStr, int dataLenth) {
        this.sendHead = headStr;
        this.dataLenth = dataLenth;

    }

    /**
     * 关闭服务
     */
    public void stopService() {
        // 关闭socket
        if (serviceSocket != null) {
            serviceSocket.close();
            serviceSocket = null;
        }
        if (mServiceThread != null) {
            mServiceThread.interrupt();
            mServiceThread = null;
            serviceRunnable = null;
            mHandler.removeMessages(RECEIVE_STR);
        }
    }

    /**
     * 关闭发送服务
     */
    public void stopClient() {
        // 关闭socket
        if (clinentSocket != null) {
            clinentSocket.close();
            clinentSocket = null;
        }
        if (mClientThread != null) {
            mClientThread.interrupt();
            mClientThread = null;
            mClientRunnble = null;
            mHandler.removeMessages(SEND_STATUS);
        }
    }


}
