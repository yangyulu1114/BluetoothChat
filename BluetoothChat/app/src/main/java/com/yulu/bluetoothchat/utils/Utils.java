package com.yulu.bluetoothchat.utils;

import java.io.Closeable;
import java.io.IOException;

public class Utils {
    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
