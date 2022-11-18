package com.yulu.bluetoothchat.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mylibrary.scan.ScanResult;
import com.yulu.bluetoothchat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressLint("MissingPermission")
public class DeviceListAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<ScanResult> mDataList = new ArrayList<>();

    public DeviceListAdapter(Context context) {
        mContext = context;
    }

    public void update(List<ScanResult> results) {
        mDataList.clear();
        mDataList.addAll(results);
        Collections.sort(mDataList, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult o1, ScanResult o2) {
                return o2.getRssi() - o1.getRssi();
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public ScanResult getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView name;
        TextView mac;
        TextView rssi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.device_list_item, null);
            holder= new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.mac = convertView.findViewById(R.id.mac);
            holder.rssi = convertView.findViewById(R.id.rssi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ScanResult result = getItem(position);
        holder.name.setText(result.getDevice().getName());
        holder.mac.setText(result.getDevice().getAddress());
        holder.rssi.setText(String.format("rssi:%s", result.getRssi()));
        return convertView;
    }
}
