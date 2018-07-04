package com.tfxk.framework;

import android.os.Environment;

import java.io.Serializable;

/**
 * Created by diannao on 2017/4/24.
 */

public class Config {
    public static final String BASE_URL = "http://www.huawa.com/";
    public static final String API_BASE = BASE_URL + "";
    public static final String DEV_URL = "";
    public static final String REAL_URL = "";
    public static final Serializable UPLOAD_IMAGE_RULE = "http://www.huawa.com/article-451.html";
    public static final int IMAGE_QUALITY = 50;
    public static final String HELP_URL = "http://www.huawa.com/wap/help.html";
    public static final String LOGIN_PROTOCOL = "http://www.huawa.com/article-115.html";
    public static final String APK_NAME = "huawa_express.apk";
    public static final String TEMPLATE_SIGN_PAPER = "http://www.huawa.com/data/upload/down/sign.png";
    public static final String TEMPLATE_FLOWER = "http://www.huawa.com/data/upload/down/flower.png";

    public static final class File {
        public static final String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String baseFolder = sdcard + "/.huawa_express";
        private static String locLogPath = baseFolder + "/log/";
        public static String screenShotsPath = baseFolder + "/ScreenShots/";
        public static String savePath = baseFolder + "/save/";
        public static final String shareDir = baseFolder + "/share/";
        public static final String fontsDir = baseFolder + "/fonts/";
        public static final String cacheDir = baseFolder + "/cache/";
        public static String imageCachePath = baseFolder + "/cache_image/";
        public static final String APK_PATH = baseFolder + "/apk/";
        public static String APK_FILE_PATH = APK_PATH + APK_NAME;
    }
}
