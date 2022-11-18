package com.example.mylibrary.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressLint("MissingPermission")
public class BluetoothUtils {
    /*UUID*/
    public static final UUID UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private static BluetoothAdapter sBluetoothAdapter;

    public static BluetoothAdapter getBluetoothAdapter() {
        if (sBluetoothAdapter == null) {
            sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return sBluetoothAdapter;
    }

    public static BluetoothDevice getRemoteDevice(String address) {
        return getBluetoothAdapter().getRemoteDevice(address);
    }

    public static boolean isEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }

    public static boolean isBonded(BluetoothDevice device) {
        Set<BluetoothDevice> devices = sBluetoothAdapter.getBondedDevices();
        if (devices != null) {
            for (BluetoothDevice d : devices) {
                if (d.equals(device)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] getRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_FINE_LOCATION};
        } else {
            return new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.ACCESS_FINE_LOCATION};
        }
    }

    public static List<BluetoothDevice> getBondedDevices() {
        List<BluetoothDevice> devices = new ArrayList<>();
        BluetoothAdapter adapter = getBluetoothAdapter();
        if (adapter != null) {
            Set<BluetoothDevice> sets = adapter.getBondedDevices();
            if (sets != null) {
                devices.addAll(sets);
            }
        }
        return devices;
    }
}
