package com.example.mylibrary;

public class BluetoothException extends Exception {

    public BluetoothException(String message) {
        super(message);
    }

    public BluetoothException(String message, Throwable cause) {
        super(message, cause);
    }

    public BluetoothException(Throwable cause) {
        super(cause);
    }
}
