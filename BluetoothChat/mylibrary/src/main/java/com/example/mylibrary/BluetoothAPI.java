package com.example.mylibrary;

import android.bluetooth.BluetoothDevice;

import com.example.mylibrary.scan.BluetoothScanListener;

public interface BluetoothAPI {
    void startScan(BluetoothScanListener listener) throws BluetoothException;
    void stopScan();

    void bind(BluetoothDevice device);
    void unbind(BluetoothDevice device);

    void connect(BluetoothDevice device);
    void disconnect(BluetoothDevice device);

    void sendMessage(BluetoothDevice device, String message) throws BluetoothException;
    void sendBytes(BluetoothDevice device, byte[] bytes) throws BluetoothException;
}
