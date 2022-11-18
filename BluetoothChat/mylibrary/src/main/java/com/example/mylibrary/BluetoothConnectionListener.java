package com.example.mylibrary;

import android.bluetooth.BluetoothDevice;

public interface BluetoothConnectionListener {
    void onMessageReceived(BluetoothDevice device, String message);
    void onMessageSent(BluetoothDevice device, String message);
    void onSocketConnected(BluetoothDevice device);
    void onConnectAccepted(BluetoothDevice device);
    void onSocketError(Exception e);
}
