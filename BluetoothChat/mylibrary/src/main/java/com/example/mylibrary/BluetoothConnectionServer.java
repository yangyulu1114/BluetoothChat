package com.example.mylibrary;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.mylibrary.utils.BluetoothUtils;

import java.io.IOException;

@SuppressLint("MissingPermission")
public class BluetoothConnectionServer extends Thread {

    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    private BluetoothServerSocket mServerSocket;
    private final BluetoothContext mBluetoothContext;
    private final BluetoothConnectionListener mConnectionListener;

    public BluetoothConnectionServer(BluetoothContext bluetoothContext) {
        mBluetoothContext = bluetoothContext;
        mConnectionListener = bluetoothContext.getListener();
    }

    @Override
    public void run() {
        BluetoothAdapter adapter = BluetoothUtils.getBluetoothAdapter();
        try {
            mServerSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, BluetoothUtils.UUID_INSECURE);
        } catch (IOException e) {
            mConnectionListener.onSocketError(new BluetoothException("Server start failed", e));
            return;
        }
        Log.v("bush", String.format("Start listening ..."));
        while (true) {
            try {
                BluetoothSocket socket = mServerSocket.accept();
                mConnectionListener.onConnectAccepted(socket.getRemoteDevice());
                startConnectionWorker(socket);
            } catch (IOException e) {
                mConnectionListener.onSocketError(new BluetoothException("Accept failed", e));
            }
        }
    }

    private void startConnectionWorker(BluetoothSocket socket) throws IOException {
        BluetoothConnectionWorker worker = new BluetoothConnectionWorker(mBluetoothContext, socket);
        mBluetoothContext.addWorker(worker);
    }
}
