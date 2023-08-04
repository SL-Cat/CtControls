/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cat.customize.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import cat.customize.ulite.system.CtLog;

import static cat.customize.bluetooth.CtBluetoothHandler.DEVICE_NAME;
import static cat.customize.bluetooth.CtBluetoothHandler.MESSAGE_DEVICE_NAME;
import static cat.customize.bluetooth.CtBluetoothHandler.MESSAGE_READ;
import static cat.customize.bluetooth.CtBluetoothHandler.MESSAGE_STATE_CHANGE;
import static cat.customize.bluetooth.CtBluetoothHandler.MESSAGE_TOAST;
import static cat.customize.bluetooth.CtBluetoothHandler.MESSAGE_WRITE;
import static cat.customize.bluetooth.CtBluetoothHandler.TOAST;


public class BluetoothChatService {

    private static final String TAG = "BluetoothChatService";
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    private static UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    //    private static final UUID MY_UUID_INSECURE =
//            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    public final BluetoothAdapter mAdapter;
    public final Handler mHandler;
    public AcceptThread mSecureAcceptThread;
    public AcceptThread mInsecureAcceptThread;
    public ConnectThread mConnectThread;
    public ConnectedThread mConnectedThread;
    private int mState;

    public static final int STATE_NONE = 0;  //断开连接
    public static final int STATE_LISTEN = 1;  //状态变化
    public static final int STATE_CONNECTING = 2; //连接中
    public static final int STATE_CONNECTED = 3;  //已连接


    public BluetoothChatService(Context context, Handler handler, String uid) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
        MY_UUID_SECURE = UUID.fromString(uid);
    }


    public BluetoothChatService(Context context, Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }


    private synchronized void setState(int state) {
        CtLog.d("setState:" + mState + " -> " + state);
        mState = state;
        mHandler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }


    public synchronized int getState() {
        return mState;
    }


    public synchronized void start() {
        CtLog.d("start:");

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_LISTEN);

        if (mSecureAcceptThread == null) {
            mSecureAcceptThread = new AcceptThread();
            mSecureAcceptThread.start();
        }
    }

    public synchronized void connect(BluetoothDevice device) {
        CtLog.d("connect to:" + device);
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }


    @SuppressLint("MissingPermission")
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device) {

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }
        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }

        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        Message msg = mHandler.obtainMessage(MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }


    public synchronized void stop() {
        Log.d(TAG, "stop");

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }

        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }

        setState(STATE_NONE);

    }

    public void write(byte[] out) {
        ConnectedThread r;
        synchronized (this) {
            if (mState != STATE_CONNECTED)
                return;
            r = mConnectedThread;

        }
        r.write(out);
    }


    private void connectionFailed() {

        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "无法连接设备!");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        BluetoothChatService.this.start();
    }


    private void connectionLost() {

        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "设备连接已断开");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        BluetoothChatService.this.start();
    }

    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;
        private String mSocketType;

        @SuppressLint("MissingPermission")
        public AcceptThread() {
            BluetoothServerSocket tmp = null;

            try {
//                  tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);
            } catch (IOException e) {
                // Log.e(TAG, "Socket Type: " + mSocketType + "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {

            BluetoothSocket socket = null;

            while (mState != STATE_CONNECTED) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    //  Log.e(TAG, "Socket Type: " + mSocketType + "accept() failed", e);
                    break;
                }
                if (socket != null) {
                    synchronized (BluetoothChatService.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                // Situation normal. Start the connected thread.
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    //Log.e(TAG, "Could not close unwanted socket", e);
                                }
                                break;
                        }
                    }
                }
            }
        }

        public void cancel() {
            // Log.d(TAG, "Socket Type" + mSocketType + "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                // Log.e(TAG, "Socket Type" + mSocketType + "close() of server failed", e);
            }
        }
    }


    private class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;
        BluetoothSocket tmp = null;
        boolean isFlag = false;

        @SuppressLint("MissingPermission")
        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tmp = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
                        isFlag = true;
                    } catch (IOException e) {
                        CtLog.d(e.toString());
                        //  Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
                    }
                }
            }).start();

        }

        public void run() {
            mAdapter.cancelDiscovery();

            while (tmp != null && isFlag) {
                isFlag = false;
                try {
                    tmp.connect();
                } catch (IOException e) {
                    CtLog.d("e" + e.toString());
                    try {
                        tmp.close();
                    } catch (IOException e2) {
                        CtLog.d("e2" + e2.toString());
                        //   Log.e(TAG, "unable to close() " + mSocketType +
                        //         " socket during connection failure", e2);
                    }
                    connectionFailed();
                    return;
                }

                synchronized (BluetoothChatService.this) {
                    mConnectThread = null;
                }

                connected(tmp, mmDevice);
            }
        }

        public void cancel() {
            try {
                if (tmp != null) {
                    tmp.close();
                }
            } catch (IOException e) {
                //  Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            // Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                //  Log.e(TAG, "temp sockets not created", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {

            synchronized (BluetoothChatService.class) {

                byte[] buffer = new byte[1024 * 4];
                int bytes;

                while (mState == STATE_CONNECTED) {
                    try {
                        bytes = mmInStream.read(buffer);
                        String data = new String(buffer, 0, bytes, "UTF-8");
                        Message msg = Message.obtain();
                        msg.what = MESSAGE_READ;
                        msg.obj = data;
                        mHandler.sendMessage(msg);
                    } catch (IOException e) {
                        connectionLost();
                        BluetoothChatService.this.start();
                        break;
                    }
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);
                mmOutStream.flush();
                mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                //Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                //    Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}
