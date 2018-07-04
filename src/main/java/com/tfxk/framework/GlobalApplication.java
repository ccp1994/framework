package com.tfxk.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.tfxk.framework.utils.Log;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by diannao on 2017/4/24.
 */

public abstract class GlobalApplication extends MultiDexApplication {
    private static final String SP_NAME = "data";
    private static Context context;
    private static GlobalApplication mInstance;


    public SharedPreferences trackConf;
    public AtomicInteger mSequenceGenerator = new AtomicInteger();
    private Glide mGlide;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initGlobal();
        initRetrofit();
        Thread.setDefaultUncaughtExceptionHandler(new HuawaExceptionHandler());
    }

    protected void initGlobal() {

        mInstance = this;
        clearAppCacheFile();
        initPush();
        Log.setOpen(getDevelopeEnvironment());
    }

    protected abstract boolean getDevelopeEnvironment();

    public Glide getPicasso() {
        if (null == mGlide)
            initGlide();
        return mGlide;
    }

    protected void initGlide() {
        if (mGlide == null) {
            mGlide = Glide.get(this);
        }
    }

    private void clearAppCacheFile() {
        File file = new File(Config.File.imageCachePath);
        if (file.exists()) {
            file.delete();
        }
    }

    private void initPush() {

    }

    /**
     * 初始化网络模块
     */
    private void initRetrofit() {
        //因为需要统一加Header，故加上网络拦截器统一处理header
    }

    public abstract String getApiBaseUrl();


    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences share = context.getSharedPreferences(SP_NAME, 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences share = context.getSharedPreferences(SP_NAME, 0);
        return share.getBoolean(key, false);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences share = context.getSharedPreferences(SP_NAME, 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences share = context.getSharedPreferences(SP_NAME, 0);
        return share.getString(key, null);
    }


    public static GlobalApplication getInstance() {
        return mInstance;
    }
}
