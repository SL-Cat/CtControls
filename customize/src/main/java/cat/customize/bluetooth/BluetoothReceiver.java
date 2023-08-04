package cat.customize.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import cat.customize.ulite.system.CtLog;

/**
 * Created by 123 on 2018/6/29.
 */

public class BluetoothReceiver extends BroadcastReceiver {

    private OnCtBluetoothSearchListener bluetoothListener;
    private List<String> deviceId = new ArrayList<>();


    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (bluetoothListener == null) return;
        BluetoothDevice device = null;
        CtLog.d(action);
        switch (action) {
            case BluetoothDevice.ACTION_FOUND:
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    //添加未配对设备
                    if (!deviceId.contains(device.getAddress())) {
                        if (null != device.getName()) {
                            deviceId.add(device.getAddress());
                            bluetoothListener.unMatchedEquipment(device);
                        }
                    }
                }
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                //搜索完成
                bluetoothListener.searchEnd();
                break;
            case BluetoothDevice.ACTION_PAIRING_REQUEST:
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDED: //配对成功
//                    deviceId.clear();
                        bluetoothListener.matchBonded();
                        break;
                    case BluetoothDevice.BOND_BONDING://配对中
                        bluetoothListener.matchBonding();
                        break;
                    case BluetoothDevice.BOND_NONE://配对失败
                        bluetoothListener.matchNone();
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void setBluetoothListener(OnCtBluetoothSearchListener bluetoothListener) {
        this.bluetoothListener = bluetoothListener;
    }

    public OnCtBluetoothSearchListener getBluetoothListener() {
        if (bluetoothListener != null) {
            return bluetoothListener;
        } else return null;
    }
}
