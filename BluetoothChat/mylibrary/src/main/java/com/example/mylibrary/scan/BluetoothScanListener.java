package com.example.mylibrary.scan;

import android.bluetooth.BluetoothDevice;

public interface BluetoothScanListener {
    void onScanStarted();

    void onDeviceFound(BluetoothDevice device, int rssi);

    void onScanStopped();

    void onScanFailed(Exception e);
}
