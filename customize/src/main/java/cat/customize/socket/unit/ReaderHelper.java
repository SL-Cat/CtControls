package cat.customize.socket.unit;

import com.xuhao.didi.core.protocol.IReaderProtocol;

import java.nio.ByteOrder;

/**
 * Created by HSL
 * on 2023/6/26.
 */

public class ReaderHelper implements IReaderProtocol {
    @Override
    public int getHeaderLength() {
        return 13;
    }

    @Override
    public int getBodyLength(byte[] header, ByteOrder byteOrder) {
        Integer iOutcome = addHead(header);
        if (iOutcome != null) return iOutcome;
        return 0;
    }

    public static Integer addHead(byte[] header) {
        for (int i = 0; i < header.length; i++) {
            if ((char) header[i] == '!') {
                byte[] bBodyLen = new byte[4];
                System.arraycopy(header, 9, bBodyLen, 0, 4);
                int iOutcome = 0;
                byte bLoop;

                for (int j = 0; j < 4; j++) {
                    bLoop = bBodyLen[j];
                    iOutcome += (bLoop & 0xFF) << (8 * j);
                }
                return iOutcome;
            }
        }
        return null;
    }
}
