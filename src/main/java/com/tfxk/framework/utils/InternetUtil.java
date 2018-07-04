package com.tfxk.framework.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.tfxk.framework.Callback;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 */
public final class InternetUtil {

    private final static int corePoolSize = 60;
    private final static int maximumPoolSize = 80;
    private final static int keepAliveTime = 10;

    private final static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    private final static Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    // 检查是否有网络
    public static boolean hasInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {

            return true;
        } else {
            return false;
        }
    }

    public static boolean hasWIFIInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo.isConnected();
    }

    //判断      当前wifi是否连接
    public static boolean isCurrentWifiConnect(Context context,String Bssid) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        if (null != wifiInfo && Bssid.equals(wifiInfo.getBSSID())) return true;
        return false;
    }

    public static boolean isGPSProviderAvaliable(Activity activity) { // 判断Use
        // GPS
        // satellites.是否勾选
        LocationManager alm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return alm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isWIFIProviderAvaliable(Activity activity) { // 判断Use
        // wireless
        // networks
        // 是否勾选
        LocationManager alm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return alm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <P extends Object, T extends AsyncTask<P, ?, ?>> void startMyTask(T task, P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(threadPoolExecutor, params);
        } else {
            task.execute(params);
        }
    }


    private static final int CONNECT_TIMEOUT_NORMAL = 15000;
    private static final int CONNECT_TIMEOUT_SHORT = 3000;
    private static final String OFFICIAL_URL = "";
    private static final String TEST_URL = "http://www.baidu.com";

    public static void checkOfficialWeb(final Activity activity, final Callback<Boolean> callback) {
        checkUrl(OFFICIAL_URL, activity, callback, CONNECT_TIMEOUT_SHORT);
    }

    public static void checkPortal(Activity activity, Callback<Boolean> callback) {
        checkUrl(TEST_URL, activity, callback, CONNECT_TIMEOUT_NORMAL);
    }

    public static void checkUrl(final String urlString, final Activity activity, final Callback<Boolean> callback, final int waitTime) {
    }

    public static void error(Throwable throwable) {//todo test
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String message = sw.toString();
        Log.e("error", message);
    }

    private void sendEmail(Context context, String[] sendTo, String content) {
        // TODO Auto-generated method stub
        if (!InternetUtil.hasInternet(context)) {
            return;
        }
        Intent emailIt = new Intent(Intent.ACTION_SEND);
        emailIt.putExtra(Intent.EXTRA_EMAIL, sendTo);
        emailIt.putExtra(Intent.EXTRA_TEXT, content);
        emailIt.setType("text/plain");  //
        emailIt.putExtra(Intent.EXTRA_CC, "chenchunpeng1994@gmail.com");
        emailIt.putExtra(Intent.EXTRA_SUBJECT, "");
        context.startActivity(Intent.createChooser(emailIt, ""));
    }

    public static void downhtml(final String url, final Callback callback) {

    }
}
