package com.yulu.bluetoothchat.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {

    public static boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(ContextUtils.getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

//    public static String[] getAllUnGrantedPermissions(Context context) {
//        List<String> permissions = new ArrayList<>();
//        try {
//            String packageName = context.getPackageName();
//            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
//            for (String requestedPermission : packageInfo.requestedPermissions) {
//                if (ContextCompat.checkSelfPermission(context, requestedPermission)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    permissions.add(requestedPermission);
//                }
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//        }
//        return permissions.toArray(new String[0]);
//    }

    public static boolean requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (permissions.length > 0) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            return true;
        }
        return false;
    }
}
