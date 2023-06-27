package cat.customize.socket.unit;

import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.io.ByteArrayOutputStream;

/**
 * Created by HSL
 * on 2023/6/26.
 * 构建传输的byte[] 添加消息头
 */
public  class SocketSendData implements ISendable {

    private String str;

    public SocketSendData(String str) {
        this.str = str;
    }

    @Override
    public byte[] parse() {
        //根据服务器的解析规则,构建byte数组
        byte[] bb = addHead(str);
        return bb;
    }

    private byte[] addHead(String message) {

        byte[] headbyte = "!message:".getBytes();
        int headlen = headbyte.length;

        byte[] bodybyte = message.getBytes();
        int bodylen = bodybyte.length;
        byte[] lengthbytes = integerToBytes(bodylen, 4);

        byte[] kl = new byte[bodylen + headlen + 4];

        for (int i = 0; i < headlen; i++) {
            kl[i] = headbyte[i];
        }

        for (int i = 0; i < 4; i++) {
            kl[i + headlen] = lengthbytes[i];
        }

        for (int i = 0; i < bodylen; i++) {
            kl[i + headlen + 4] = bodybyte[i];
        }
        return kl;
    }

    public byte[] integerToBytes(int integer, int len) {
//   if (integer < 0) { throw new IllegalArgumentException("Can not cast negative to bytes : " + integer); }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        for (int i = 0; i < len; i++) {
            bo.write(integer);
            integer = integer >> 8;
        }
        return bo.toByteArray();
    }
}