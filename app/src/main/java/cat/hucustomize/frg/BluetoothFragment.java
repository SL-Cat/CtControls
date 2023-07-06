package cat.hucustomize.frg;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.bluetooth.CtBluetoothUlite;
import cat.customize.bluetooth.OnCtBluetoothListener;
import cat.customize.bluetooth.OnCtBluetoothSearchListener;
import cat.customize.ulite.system.LogCt;
import cat.customize.xlist.XListView;
import cat.hucustomize.R;
import cat.hucustomize.ToastUlit;
import cat.hucustomize.adapter.CtBluetoothAdapter;

/**
 * Created by HSL
 * on 2023/4/20.
 */

public class BluetoothFragment extends Fragment {

    private CtBluetoothAdapter connectAdapter;
    private CtBluetoothAdapter offlineAdapter;

    private List<BluetoothDevice> connectList = new ArrayList<>();
    private List<BluetoothDevice> offlineList = new ArrayList<>();
    private XListView connectLsView;
    private XListView offlineLsView;

    private CtBluetoothUlite ctBluetoothUlite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bluetooth_layout, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        connectLsView = ((XListView) view.findViewById(R.id.bluetooth_connection_ls));
        offlineLsView = ((XListView) view.findViewById(R.id.bluetooth_offline_ls));
        view.findViewById(R.id.bluetooth_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctBluetoothUlite.searchMatchDevices();
            }
        });

        connectLsView.setPullRefreshEnable(false);
        connectLsView.setPullLoadEnable(false);

        offlineLsView.setPullRefreshEnable(false);
        offlineLsView.setPullLoadEnable(false);

        ctBluetoothUlite = new CtBluetoothUlite(getActivity());
        ctBluetoothUlite.init("00001101-0000-1000-8000-00805F9B34FB");
    }

    private void initData() {
        connectAdapter = new CtBluetoothAdapter(getActivity(), connectList);
        offlineAdapter = new CtBluetoothAdapter(getActivity(), offlineList);
        connectLsView.setAdapter(connectAdapter);
        offlineLsView.setAdapter(offlineAdapter);
        connectAdapter.notifyDataSetChanged();
        offlineAdapter.notifyDataSetChanged();
        connectLsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice bluetoothDevice = connectList.get(position - 1);
                ctBluetoothUlite.connectDevice(bluetoothDevice);
            }
        });
        offlineLsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice bluetoothDevice = offlineList.get(position - 1);
                ctBluetoothUlite.connectDevice(bluetoothDevice);
            }
        });
        initBluetooth();

    }

    private void initBluetooth() {
        ctBluetoothUlite.setOnCtBluetoothListener(new OnCtBluetoothListener() {
            @Override
            public void connectStatus(int status) {
                switch (status) {
                    case 0:
                        LogCt.d("连接断开");
                        break;
                    case 1:
                        LogCt.d("连接状态变化");
                        break;
                    case 2:
                        LogCt.d("连接中");
                        break;
                    case 3:
                        LogCt.d("连接成功");
                        ctBluetoothUlite.sendMessage("string");
                        break;
                }

            }

            @Override
            public void connectSuccess(String string) {
                LogCt.d(string);
            }

            @Override
            public void connectError(String message) {

            }

            @Override
            public void sendMessage(String data) {
            }

            @Override
            public void receiveMessage(String data) {
                ToastUlit.Toast(getActivity(), data + "");

            }
        });
        ctBluetoothUlite.setDeviceStatusListener(new OnCtBluetoothSearchListener() {
            @Override
            public void unMatchedEquipment(BluetoothDevice device) {
                offlineList.add(device);
                offlineAdapter.notifyDataSetChanged();
            }

            @Override
            public void matchedEquipment(BluetoothDevice device) {
                boolean isFlag = false;
                for (BluetoothDevice bluetoothDevice : connectList) {
                    if (bluetoothDevice.getAddress().equals(device.getAddress())) {
                        isFlag = true;
                        break;
                    }
                }
                if (!isFlag) {
                    connectList.add(device);
                    connectAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void searchEnd() {
                LogCt.d("searchEnd");
            }

            @Override
            public void matchBonded() {
                LogCt.d("matchBonded");
                ctBluetoothUlite.searchMatchDevices();
            }

            @Override
            public void matchBonding() {
                LogCt.d("matchBonding");
                ctBluetoothUlite.searchMatchDevices();

            }

            @Override
            public void matchNone() {
                LogCt.d("matchNone");

            }
        });
    }

}
