package com.tfxk.framework.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.tfxk.framework.Config;
import com.tfxk.framework.R;

import java.io.File;
import java.util.List;
import java.util.Random;


/**
 * Created by Chenchunpeng on 2016/2/22.
 */
public class CommonUtils {


    /**
     * @param activity
     * @param title
     * @param message       the dialog 's message
     * @param cancelable    false to set dialog can not cancel
     * @param cancelText    the cancel button text ,set 0 to not show
     * @param confirmText   the confirm button text,set 0 to not show
     * @param clickListener {@link DialogInterface#BUTTON_NEGATIVE} -->negativeButton   {@link DialogInterface#BUTTON_POSITIVE}--->positiveButton
     */
    public static void showMaterialDialog(AppCompatActivity activity, String title, String message, boolean cancelable, int cancelText, int confirmText, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppDialogTheme);
        builder.setTitle(title).setMessage(message).setCancelable(cancelable);
        if (confirmText != 0)
            builder = builder.setPositiveButton(confirmText, clickListener);
        if (cancelText != 0)
            builder = builder.setNegativeButton(cancelText, clickListener);
        builder.show();
    }

    /**
     * @param activity
     * @param title
     * @param message       the dialog 's message
     * @param cancelText    the cancel button text ,set 0 to not show
     * @param confirmText   the confirm button text,set 0 to not show
     * @param clickListener {@link DialogInterface#BUTTON_NEGATIVE} -->negativeButton   {@link DialogInterface#BUTTON_POSITIVE}--->positiveButton
     */
    public static void showMaterialDialog(AppCompatActivity activity, String title, String message, int cancelText, int confirmText, DialogInterface.OnClickListener clickListener) {
        showMaterialDialog(activity, title, message, true, cancelText, confirmText, clickListener);
    }

    public static void outAppAlert(final AppCompatActivity base) {
        showMaterialDialog(base, base.getString(R.string.quite_title), base.getString(R.string.quite_content), false, R.string.cancel, R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        base.finish();
//                        Process.killProcess(android.os.Process.myPid());
                        break;
                }
            }
        });
    }


    /**
     * get cache File 's path
     *
     * @param originPath the origin file path of the File which need to generate cache file
     * @return
     */
    public static String getCacheFilePath(String originPath) {
        File originFile = new File(originPath);
        return Config.File.cacheDir + "/cache_" + originFile.getName();
    }

    /**
     * @param context
     * @return true if current app is running foreground,otherwise return false
     */
    public static boolean isAppRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List runningTaskInfoList = activityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo taskInfo = (ActivityManager.RunningTaskInfo) runningTaskInfoList.get(0);
        return context.getPackageName().equalsIgnoreCase(taskInfo.baseActivity.getPackageName());
    }


    public static String phoneSecret(String mobile) {
        String newPhone = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
        return newPhone;
    }


}