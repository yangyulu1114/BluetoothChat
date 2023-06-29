package com.yulu.bluetoothchat.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.BluetoothConnectionListener;
import com.example.mylibrary.BluetoothException;
import com.example.mylibrary.BluetoothSDK;
import com.example.mylibrary.scan.BluetoothScanListener;
import com.example.mylibrary.scan.ScanResult;
import com.example.mylibrary.utils.BluetoothUtils;
import com.yulu.bluetoothchat.R;
import com.yulu.bluetoothchat.activity.adapter.DeviceListAdapter;
import com.yulu.bluetoothchat.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("MissingPermission")
public class MainActivity extends AppCompatActivity implements BluetoothConnectionListener, BluetoothScanListener {
    private static final int REQUEST_PERMISSION = 1;

    private ListView mListView;
    private DeviceListAdapter mDeviceListAdapter;
    private List<ScanResult> mDevices;

    private TextView mTvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothSDK.initialize(this, this);
        PermissionUtils.requestPermissions(this, BluetoothUtils.getRequiredPermissions(), REQUEST_PERMISSION);
        Log.v("bush", String.format("version: %s", Build.VERSION.SDK_INT));
        mListView = findViewById(R.id.list);
        mDeviceListAdapter = new DeviceListAdapter(this);
        mListView.setAdapter(mDeviceListAdapter);
        mDevices = new ArrayList<>();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startChatDetail(mDevices.get(position).getDevice());
            }
        });

        mTvStatus = findViewById(R.id.status);
    }

    private void startChatDetail(BluetoothDevice device) {
        Intent intent = new Intent(this, ChatDetailActivity.class);
        intent.putExtra("device", device);
        startActivity(intent);
    }

    private void onAllPermissionGranted() {
//        mBluetoothChatHelper.startServer();
        startScanBluetoothDevices();
    }

    private void startScanBluetoothDevices() {
        try {
            BluetoothSDK.startScan(this);
        } catch (BluetoothException e) {
            Log.e("bush", "startScan failed", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.v("bush", String.format("grantResult for %s: %s", permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED ? "granted" : "denied"));
                    String text = getString(R.string.error_scanning_requires_permission, permissions[i]);
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            onAllPermissionGranted();
        }
    }

    @Override
    public void onScanStarted() {
        mDevices.clear();
        Log.v("bush", String.format("onScanStarted %s", Thread.currentThread().getName()));
    }

    @Override
    public void onDeviceFound(BluetoothDevice device, int rssi) {
        Log.v("bush", String.format("onDeviceFound %s, name=(%s), rssi=%s, %s", device.getAddress(), device.getName(), rssi, Thread.currentThread().getName()));
        mDevices.add(new ScanResult(device, rssi));
        mDeviceListAdapter.update(mDevices);
    }

    @Override
    public void onScanStopped() {
        Log.v("bush", "onScanStopped");
    }

    @Override
    public void onScanFailed(Exception e) {
        Log.v("bush", "onScanFailed", e);
    }

    @Override
    public void onMessageReceived(BluetoothDevice device, String message) {

    }

    @Override
    public void onMessageSent(BluetoothDevice device, String message) {

    }

    @Override
    public void onSocketConnected(BluetoothDevice device) {

    }

    @Override
    public void onConnectAccepted(BluetoothDevice device) {

    }

    @Override
    public void onSocketError(Exception e) {

    }

//    @Override
//    public void onScanStarted() {
//        mDevices.clear();
//    }
//
//    @Override
//    public void onDeviceFound(BluetoothDevice device, int rssi) {
//        if (TextUtils.isEmpty(device.getName())) {
//            return;
//        }
//        mDevices.add(new ScanResult(device, rssi));
//        mDeviceListAdapter.update(mDevices);
//    }
//
//    @Override
//    public void onScanStopped() {
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mBluetoothChatHelper.stopScan();
//    }
//
//    @Override
//    protected void onDestroy() {
//        mBluetoothChatHelper.unregisterListener(this);
//        super.onDestroy();
//    }
//
//    @Override
//    public void onDataReceived(BluetoothDevice device, byte[] data, int size) {
//
//    }
}
