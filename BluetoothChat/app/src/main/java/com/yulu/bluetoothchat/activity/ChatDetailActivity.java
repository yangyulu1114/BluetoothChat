package com.yulu.bluetoothchat.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.yulu.bluetoothchat.R;
import com.yulu.bluetoothchat.activity.adapter.MessageListAdapter;

public class ChatDetailActivity extends Activity implements View.OnClickListener {

    private ListView mListView;
    private MessageListAdapter mAdapter;
    private Button mBtnSend;
    private BluetoothDevice mDevice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail_activity);
        mDevice = getIntent().getParcelableExtra("device");
        Log.v("bush", String.format("device: %s", mDevice.toString()));
        mBtnSend = findViewById(R.id.send);
        mBtnSend.setOnClickListener(this);
    }

    private void sendMessage(String message) {
//        try {
//            mBluetoothChatHelper.sendData(mDevice, message);
//        } catch (Exception e) {
//            Log.e("bush", "send data failed", e);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                sendMessage("hello world");
                break;
        }
    }

    @Override
    protected void onDestroy() {
//        mBluetoothChatHelper.unregisterListener(mBluetoothListener);
        super.onDestroy();
    }
}
