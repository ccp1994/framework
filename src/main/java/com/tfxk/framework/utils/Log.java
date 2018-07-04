package com.tfxk.framework.utils;

/**
 * Created by Chenchunpeng on 14-12-9 下午4:06.
 */
public class Log {
    private static boolean open = true;

    public static boolean isOpen() {
        return open;
    }

    public static void setOpen(boolean openLog) {
        open = openLog;
    }

    public static void v(String tag, String message) {
        if (open)
            android.util.Log.v(tag, message);
    }

    public static void w(String tag, String message) {
        if (open)
            android.util.Log.w(tag, message);
    }

    public static void i(String tag, String message) {
        if (open)
            android.util.Log.i(tag, message);
    }

    public static void e(String tag, String message) {
        if (open)
            android.util.Log.e(tag, message);
    }

    public static void d(String tag, String message) {
        if (open)
            android.util.Log.d(tag, message);
    }

    public static void println(String tag, String str) {
        if (open) {
            if (str.length() > 4000) {
                int chunkCount = str.length() / 4000;     // integer division
                for (int i = 0; i <= chunkCount; i++) {
                    int max = 4000 * (i + 1);
                    if (max >= str.length()) {
                        System.out.println("===" + tag + "===" + str.substring(4000 * i));
                    } else {
                        System.out.println("===" + tag + "===" + str.substring(4000 * i, max));
                    }
                }
            } else {
                System.out.println("===" + tag + "===" + str);
            }
        }
    }
}
