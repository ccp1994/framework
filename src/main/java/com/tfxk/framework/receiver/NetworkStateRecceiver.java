package com.tfxk.framework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tfxk.framework.R;
import com.tfxk.framework.utils.CommonUtils;
import com.tfxk.framework.utils.DialogUtils;

/**
 * Created by chenchunpeng on 2017/5/13.
 */

public class NetworkStateRecceiver extends BroadcastReceiver {
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //System.out.println("网络状态已经改变");
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                String name = info.getTypeName();
//                if (name.equals("WIFI")) {
//                    //System.out.println("WIFI");
//
////                    editor.putString("netType", "wifi");
////                    editor.commit();
//
//                } else {
//
//                    editor.putString("netType", "3g");
//                    editor.commit();
//
//                }
//                EventBus.getDefault().post(new MessageEventNet(true));
            } else {
                if (CommonUtils.isAppRunningForeground(context))
                    DialogUtils.showNoNetWorkTipsDialog(context, R.mipmap.ic_launcher);
//                EventBus.getDefault().post(new MessageEventNet(false));

            }
        }
    }
}
