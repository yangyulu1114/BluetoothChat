package com.example.mylibrary.scan;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.mylibrary.BluetoothException;
import com.example.mylibrary.utils.BluetoothUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressLint("MissingPermission")
public class BluetoothScanner {
    private static final String TAG = "BluetoothScanner";

    private final Context mContext;
    private final BluetoothAdapter mBluetoothAdapter;
    private final BluetoothScanListener mScanListener;
    private final Set<BluetoothDevice> mDevices;

    // prevent onScanStopped called multiple times
    private boolean mIsScanning;

    public BluetoothScanner(Context context, BluetoothScanListener listener) {
        mContext = context;
        mScanListener = listener;
        mBluetoothAdapter = BluetoothUtils.getBluetoothAdapter();
        mDevices = new HashSet<>();
    }

    public void start() throws BluetoothException {
        if (!mBluetoothAdapter.isEnabled()) {
            throw new BluetoothException("Bluetooth is not on");
        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        registerReceiver();
        mBluetoothAdapter.startDiscovery();
        onScanStarted();

        List<BluetoothDevice> devices = BluetoothUtils.getBondedDevices();
        for (BluetoothDevice device : devices) {
            onDeviceFound(device, 0);
        }
    }

    private void onScanStarted() {
        if (!mIsScanning) {
            mDevices.clear();
            mIsScanning = true;
            mScanListener.onScanStarted();
        }
    }

    private void onScanStopped() {
        if (mIsScanning) {
            mDevices.clear();
            mIsScanning = false;
            mScanListener.onScanStopped();
        }
    }

    private void onDeviceFound(BluetoothDevice device, int rssi) {
        if (!mIsScanning) {
            return;
        }
        if (mDevices.add(device)) {
            mScanListener.onDeviceFound(device, rssi);
        }
    }

    public void stop() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        onScanStopped();
    }

    private void registerReceiver() {
        BroadcastReceiver receiver = new BluetoothScanReceiver();
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(receiver, intentFilter);
    }

    private class BluetoothScanReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,
                        Short.MIN_VALUE);
                onDeviceFound(device, rssi);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                onScanStopped();
            }
        }
    }
}
