package cat.customize.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by HSL
 * on 2023/4/21.
 */
@SuppressLint("MissingPermission")
public class CtBluetoothUlite {

    private BluetoothAdapter mBluetoothAdapter;   //系统蓝牙实例
    private BluetoothReceiver receiver = new BluetoothReceiver();  //广播，返回蓝牙状态
    private BluetoothChatService chatService;   //蓝牙操作类
    private Context context;
    private OnCtBluetoothListener onCtBluetoothListener; //蓝牙业务操作回调;

    private CtBluetoothHandler handler;

    public CtBluetoothUlite(Context context) {
        this.context = context;
    }

    public void init() {
        initInfo(null);
    }

    public void init(String uid) {
        initInfo(uid);
    }

    private void initInfo(String uid) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        handler = new CtBluetoothHandler();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        context.registerReceiver(receiver, filter);
        if (uid != null) {
            chatService = new BluetoothChatService(context, handler, uid);
        } else {
            chatService = new BluetoothChatService(context, handler);
        }

        openBluetooth();
    }

    public void close() {
        //反注册广播取消蓝牙的配对
        if (mBluetoothAdapter != null)
            mBluetoothAdapter.cancelDiscovery();
        if (chatService != null) {
            chatService.stop();
        }
        context.unregisterReceiver(receiver);
    }


    public void searchMatchDevices() {
        if (!openBluetooth()) return;
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice bondedDevice : bondedDevices) {
            if (receiver.getBluetoothListener() != null) {
                receiver.getBluetoothListener().matchedEquipment(bondedDevice);
            }
        }
    }

    /**
     * 连接设备
     *
     * @param bluetoothDevice
     */
    public void connectDevice(BluetoothDevice bluetoothDevice) {
        if (!openBluetooth()) return;
        // 判断当前是否还是正在搜索周边设备，如果是则暂停搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
            try {
                createBond(BluetoothDevice.class, bluetoothDevice);
            } catch (Exception e) {
                return;
            }
        } else {
            chatService.connect(bluetoothDevice);
        }
    }

    public boolean createBond(Class btClass, BluetoothDevice btDevice)
            throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }


    private boolean openBluetooth() {
        //开启显示，让本机可以被搜索到
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            context.startActivity(discoverableIntent);
            return false;
        } else {
            mBluetoothAdapter.startDiscovery(); //开启搜索
        }
        return true;
    }

    /**
     * 数据操作连接监听
     *
     * @param onCtBluetoothListener
     */
    public void setOnCtBluetoothListener(OnCtBluetoothListener onCtBluetoothListener) {
        handler.setOnCtBluetoothListener(onCtBluetoothListener);
    }

    /**
     * 设备状态连接监听
     *
     * @param onCtBluetoothSearchListener
     */
    public void setDeviceStatusListener(OnCtBluetoothSearchListener onCtBluetoothSearchListener) {
        receiver.setBluetoothListener(onCtBluetoothSearchListener);
    }


    /**
     * 发送数据
     *
     * @param message
     */
    public void sendMessage(String message) {
        sendInfo(message, 0);
    }

    private void sendInfo(String data, int index) {
        if (data.length() > (index * 500)) {
            String substring = data.substring((500 * index), 500 * index);
            chatService.write(data.getBytes());
            sendInfo(substring, index+1);
        } else {
            chatService.write(data.getBytes());
        }
    }

}
