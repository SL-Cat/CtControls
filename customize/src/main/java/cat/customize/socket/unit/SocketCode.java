package cat.customize.socket.unit;

/**
 * Created by HSL
 * on 2023/6/19.
 */

public class SocketCode {
    public static final String SERVICE = "SERVICE"; //客户端
    public static final String HOST = "HOST"; //服务端


    public static final int SERVICE_CONNECT_SUCCESS = 0; //客户端连接成功
    public static final int SERVICE_CONNECT_FILED = 1; //客户端连接失败
    public static final int SERVICE_CONNECT_SHUT = 2; //客户端连接关闭
    public static final int HOST_CONNECT_OPEN = 3; //服务端开启
    public static final int HOST_CONNECT_SUCCESS = 4; //服务端连接成功
    public static final int HOST_CONNECT_SHUT = 5; //服务端连接关闭
    public static final int SERVICE_SEND_DATA  = 6; //客户端发送数据
    public static final int SERVICE_RECEIVE_DATA  = 7; //客户端接收发送返回的数据
    public static final int HOST_RECEIVE_DATA  = 8; //服务端接收数据
    public static final int HOST_SEND_DATA  = 9; //服务端发送接收到的数据


}
