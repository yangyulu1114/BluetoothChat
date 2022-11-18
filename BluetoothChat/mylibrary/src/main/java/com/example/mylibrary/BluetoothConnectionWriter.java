package com.example.mylibrary;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BluetoothConnectionWriter extends Thread {

    private final BluetoothDevice mRemoteDevice;
    private final BluetoothConnectionListener mConnectionListener;
    private final OutputStream mOutputStream;

    private Queue<byte[]> mMessageQueue;

    public BluetoothConnectionWriter(BluetoothSocket socket, BluetoothContext bluetoothContext) throws IOException {
        mRemoteDevice = socket.getRemoteDevice();
        mConnectionListener = bluetoothContext.getListener();
        mOutputStream = socket.getOutputStream();

        mMessageQueue = new LinkedList<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                sendNextMessage();
            } catch (BluetoothException e) {
                mConnectionListener.onSocketError(e);
                break;
            }
        }
    }

    private void sendNextMessage() throws BluetoothException {
        byte[] buffer = null;
        synchronized (mMessageQueue) {
            while (mMessageQueue.isEmpty()) {
                try {
                    mMessageQueue.wait();
                } catch (InterruptedException e) {
                }
            }
            buffer = mMessageQueue.poll();
        }
        try {
            mOutputStream.write(buffer);
            mConnectionListener.onMessageSent(mRemoteDevice, new String(buffer));
        } catch (IOException e) {
            throw new BluetoothException("send data failed", e);
        }
    }

    public void write(final byte[] bytes) {
        byte[] copy = Arrays.copyOf(bytes, bytes.length);
        synchronized (mMessageQueue) {
            mMessageQueue.offer(copy);
            mMessageQueue.notify();
        }
    }
}
