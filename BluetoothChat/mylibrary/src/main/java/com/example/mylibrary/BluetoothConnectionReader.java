package com.example.mylibrary;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;

public class BluetoothConnectionReader extends Thread {

    private final BluetoothSocket mSocket;
    private final BluetoothDevice mRemoteDevice;
    private final BluetoothContext mBluetoothContext;
    private final BluetoothConnectionListener mConnectionListener;
    private final InputStream mInputStream;

    public BluetoothConnectionReader(BluetoothSocket socket, BluetoothContext bluetoothContext) throws IOException {
        mSocket = socket;
        mRemoteDevice = socket.getRemoteDevice();
        mBluetoothContext = bluetoothContext;
        mConnectionListener = bluetoothContext.getListener();
        mInputStream = socket.getInputStream();
    }

    @Override
    public void run() {
        while (true) {
            try {
                readNextBuffer();
            } catch (BluetoothException e) {
                mConnectionListener.onSocketError(e);
                break;
            }
        }
    }

    private void readNextBuffer() throws BluetoothException {
        byte[] buffer = new byte[1024];
        try {
            int bytes = mInputStream.read(buffer);
            if (bytes > 0) {
                mConnectionListener.onMessageReceived(mRemoteDevice, new String(buffer, 0, bytes));
            }
        } catch (IOException e) {
            throw new BluetoothException("read buffer failed", e);
        }
    }
}
