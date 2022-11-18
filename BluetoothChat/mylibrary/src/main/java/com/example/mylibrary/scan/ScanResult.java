package com.example.mylibrary.scan;

import android.bluetooth.BluetoothDevice;

public class ScanResult {

    private final BluetoothDevice device;
    private final int rssi;

    public ScanResult(BluetoothDevice device, int rssi) {
        this.device = device;
        this.rssi = rssi;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public int getRssi() {
        return rssi;
    }
}
