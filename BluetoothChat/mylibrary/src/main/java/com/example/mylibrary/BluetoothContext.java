package com.example.mylibrary;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;

public class BluetoothContext {
    private final BluetoothConnectionListener mListener;
    private final List<BluetoothConnectionWorker> mConnectionWorkers;

    public BluetoothContext(BluetoothConnectionListener listener) {
        mListener = listener;
        mConnectionWorkers = new ArrayList<>();
    }

    public BluetoothConnectionListener getListener() {
        return mListener;
    }

    public void addWorker(BluetoothConnectionWorker worker) {
        mConnectionWorkers.add(worker);
    }

    public BluetoothConnectionWorker getWorker(BluetoothDevice device) {
        for (BluetoothConnectionWorker worker : mConnectionWorkers) {
            if (worker.getDevice().equals(device)) {
                return worker;
            }
        }
        return null;
    }
}
