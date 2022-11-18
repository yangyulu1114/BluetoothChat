package com.example.mylibrary;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.example.mylibrary.scan.BluetoothScanListener;
import com.example.mylibrary.scan.BluetoothScanner;
import com.example.mylibrary.utils.BluetoothUtils;

@SuppressLint("MissingPermission")
public class BluetoothAPIImpl implements BluetoothAPI {

    private final Context mContext;
    private final BluetoothContext mBluetoothContext;
    private final BluetoothConnectionListener mConnectionListener;
    private final BluetoothConnectionServer mBluetoothConnectionServer;

    private BluetoothScanner mCurScanner;

    BluetoothAPIImpl(Context context, BluetoothConnectionListener connectionListener) {
        mContext = context;
        mConnectionListener = connectionListener;
        mBluetoothContext = new BluetoothContext(connectionListener);

        mBluetoothConnectionServer = new BluetoothConnectionServer(mBluetoothContext);
        mBluetoothConnectionServer.start();
    }

    @Override
    public void startScan(BluetoothScanListener listener) throws BluetoothException {
        if (mCurScanner != null) {
            mCurScanner.stop();
        }
        mCurScanner = new BluetoothScanner(mContext, listener);
        mCurScanner.start();
    }

    @Override
    public void sendMessage(BluetoothDevice device, String message) throws BluetoothException {
        sendBytes(device, message.getBytes());
    }

    @Override
    public void sendBytes(BluetoothDevice device, byte[] bytes) throws BluetoothException {
        if (!BluetoothUtils.isBonded(device)) {
            throw new BluetoothException("Device not bonded");
        }
        BluetoothConnectionWorker worker = mBluetoothContext.getWorker(device);
        if (worker == null) {
            try {
                BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(BluetoothUtils.UUID_INSECURE);
                socket.connect();

                mConnectionListener.onSocketConnected(device);

                worker = new BluetoothConnectionWorker(mBluetoothContext, socket);
                mBluetoothContext.addWorker(worker);
            } catch (Exception e) {
                throw new BluetoothException("init bluetooth connection worker failed", e);
            }
        }
        worker.sendBytes(bytes);
    }

    @Override
    public void stopScan() {
        if (mCurScanner != null) {
            mCurScanner.stop();
        }
        mCurScanner = null;
    }

    @Override
    public void bind(BluetoothDevice device) {

    }

    @Override
    public void unbind(BluetoothDevice device) {

    }

    @Override
    public void connect(BluetoothDevice device) {

    }

    @Override
    public void disconnect(BluetoothDevice device) {

    }
}
