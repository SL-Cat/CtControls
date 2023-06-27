package cat.customize.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by HSL
 * on 2023/4/24.
 */

public interface OnCtBluetConnectListener {

    void notMatchDevice();
    void connectDevice();
    void connectDeviceSuccess(BluetoothDevice device);
    void connectDeviceError(BluetoothDevice device);
}
