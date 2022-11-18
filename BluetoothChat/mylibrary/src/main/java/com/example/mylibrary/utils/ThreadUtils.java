package com.example.mylibrary.utils;

import android.os.Looper;

public class ThreadUtils {
    public static void assertMainThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("not in main thread");
        }
    }
}
