package cat.hucustomize.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cat.customize.xlist.BaseListAdapter;
import cat.hucustomize.R;

/**
 * Created by HSL
 * on 2023/4/20.
 */

public class CtBluetoothAdapter extends BaseListAdapter<BluetoothDevice> {

    public CtBluetoothAdapter(Context context, List<BluetoothDevice> list) {
        super(context, list);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.bluetooth_item,null);
        }
        TextView deviceNameTv = (TextView) convertView.findViewById(R.id.bluetooth_device_item_name);
        BluetoothDevice bluetoothDevice = list.get(position);
        deviceNameTv.setText(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
        return convertView;
    }
}
