package com.yulu.bluetoothchat;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.mylibrary.BluetoothConnectionListener;
import com.example.mylibrary.BluetoothException;
import com.example.mylibrary.BluetoothSDK;
import com.example.mylibrary.scan.BluetoothScanListener;
import com.example.mylibrary.utils.BluetoothUtils;
import com.yulu.bluetoothchat.utils.PermissionUtils;

public class TestActivity extends Activity implements View.OnClickListener, BluetoothConnectionListener, BluetoothScanListener {

    private static final int REQUEST_PERMISSION = 1;

    private Button mBtnStart;
    private Button mBtnStop;
    private Button mBtnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        mBtnStart = findViewById(R.id.start);
        mBtnStop = findViewById(R.id.stop);
        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnSend = findViewById(R.id.send);
        mBtnSend.setOnClickListener(this);

        BluetoothSDK.initialize(this, this);

        PermissionUtils.requestPermissions(this, BluetoothUtils.getRequiredPermissions(), REQUEST_PERMISSION);
    }

    @Override
    public void onMessageReceived(BluetoothDevice device, String message) {
        Log.v("bush", String.format("onMessageReceived from %s, %s, %s", device.toString(), message, Thread.currentThread().getName()));
    }

    @Override
    public void onMessageSent(BluetoothDevice device, String message) {
        Log.v("bush", String.format("onMessageSent to %s, %s, %s", device.toString(), message, Thread.currentThread().getName()));
    }

    @Override
    public void onSocketConnected(BluetoothDevice device) {
        Log.v("bush", String.format("onSocketConnected to %s, %s", device.toString(), Thread.currentThread().getName()));
    }

    @Override
    public void onConnectAccepted(BluetoothDevice device) {
        Log.v("bush", String.format("onConnectAccepted from %s, %s", device.toString(), Thread.currentThread().getName()));
    }

    @Override
    public void onSocketError(Exception e) {
        Log.v("bush", String.format("onSocketError"), e);
    }

    @Override
    public void onScanStarted() {
        Log.v("bush", String.format("onScanStarted %s", Thread.currentThread().getName()));
    }

    @Override
    public void onDeviceFound(BluetoothDevice device, int rssi) {
        Log.v("bush", String.format("onDeviceFound %s, name=(%s), rssi=%s, %s", device.getAddress(), device.getName(), rssi, Thread.currentThread().getName()));
    }

    @Override
    public void onScanStopped() {
        Log.v("bush", "onScanStopped");
    }

    @Override
    public void onScanFailed(Exception e) {
        Log.v("bush", "onScanFailed", e);
    }

    private void sendMessage() {
        BluetoothDevice device = BluetoothUtils.getRemoteDevice("78:02:F8:EC:B7:AF");
        try {
            BluetoothSDK.sendMessage(device, "hello world");
        } catch (BluetoothException e) {
            Log.e("bush", "send message failed", e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                try {
                    BluetoothSDK.startScan(this);
                } catch (BluetoothException e) {
                    Log.e("bush", "scan exception", e);
                }
                break;
            case R.id.stop:
                BluetoothSDK.stopScan();
                break;
            case R.id.send:
                sendMessage();
                break;
        }
    }
}
