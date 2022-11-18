package com.example.mylibrary;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.example.mylibrary.scan.BluetoothScanListener;
import com.example.mylibrary.utils.ThreadUtils;
import com.example.mylibrary.utils.proxy.ProxyUtils;

/**
 * 如果用户开关蓝牙，那server怎么办？
 * 底层读写出错的时候要通知上层重新建立socket连接，且stream要关闭
 * 来的消息非常长怎么办，是否会接收不全?
 *
 * 其它可参考的：
 * 通信层进一步抽象可支持蓝牙WIFI自动切换
 * 收发消息用低功耗，传文件用经典蓝牙
 */
public class BluetoothSDK {

    private static BluetoothAPI INSTANCE;

    public static void initialize(Context context, BluetoothConnectionListener listener) {
        ThreadUtils.assertMainThread();
        if (INSTANCE != null) {
            throw new IllegalStateException("sdk already initialized");
        }
        INSTANCE = new BluetoothAPIImpl(context, ProxyUtils.getUIProxy(listener));
    }

    public static void startScan(BluetoothScanListener scanListener) throws BluetoothException {
        ThreadUtils.assertMainThread();
        INSTANCE.startScan(ProxyUtils.getUIProxy(scanListener));
    }

    public static void stopScan() {
        ThreadUtils.assertMainThread();
        INSTANCE.stopScan();
    }

    public static void sendMessage(BluetoothDevice device, String message) throws BluetoothException {
        ThreadUtils.assertMainThread();
        INSTANCE.sendMessage(device, message);
    }

    public static void sendBytes(BluetoothDevice device, byte[] bytes) throws BluetoothException {
        ThreadUtils.assertMainThread();
        INSTANCE.sendBytes(device, bytes);
    }

    public static void disconnect(BluetoothDevice device) {
        ThreadUtils.assertMainThread();
        INSTANCE.disconnect(device);
    }

    public static void connect(BluetoothDevice device) {
        ThreadUtils.assertMainThread();
        INSTANCE.connect(device);
    }

    public static void bind(BluetoothDevice device) {
        ThreadUtils.assertMainThread();
        INSTANCE.bind(device);
    }

    public static void unbind(BluetoothDevice device) {
        ThreadUtils.assertMainThread();
        INSTANCE.unbind(device);
    }
}
