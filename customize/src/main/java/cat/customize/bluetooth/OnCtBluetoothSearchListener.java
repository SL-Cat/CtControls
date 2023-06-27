package cat.customize.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by HSL
 * on 2023/4/21.
 */

public interface OnCtBluetoothSearchListener {

    void unMatchedEquipment(BluetoothDevice device);  //搜索未配对设备
    void matchedEquipment(BluetoothDevice device);  //返回已配对设备
    void searchEnd(); //搜索结束
    void matchBonded();  //配对成功
    void matchBonding();  //配对中
    void matchNone();  //配对拒绝
}
