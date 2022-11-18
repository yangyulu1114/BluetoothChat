package com.example.mylibrary;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

public class BluetoothConnectionWorker {

    private final BluetoothDevice mRemoteDevice;
    private final BluetoothConnectionListener mConnectionListener;

    private final BluetoothConnectionReader mReader;
    private final BluetoothConnectionWriter mWriter;

    public BluetoothConnectionWorker(BluetoothContext bluetoothContext, BluetoothSocket socket) throws IOException {
        mConnectionListener = bluetoothContext.getListener();
        mRemoteDevice = socket.getRemoteDevice();

        mReader = new BluetoothConnectionReader(socket, bluetoothContext);
        mReader.start();

        mWriter = new BluetoothConnectionWriter(socket, bluetoothContext);
        mWriter.start();
    }

    public BluetoothDevice getDevice() {
        return mRemoteDevice;
    }

    public void sendBytes(byte[] bytes) {
        mWriter.write(bytes);
    }
}
