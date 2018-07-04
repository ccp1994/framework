package com.tfxk.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import com.tfxk.framework.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchunpeng on 2015/6/8.
 * email:fpa@shubaobao.com<br>
 * for Intent Action
 */
public class IntentUtil {

    public static final int REQUEST_CODE_PIC_IMAGE = 101;

    public static File uri2File(Activity context, Uri uri) {
        File file = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = context.managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
    }

    /**
     * @param context
     * @param requestCode
     * @see FileUtils#parseImageFromFile(Context, Intent)
     */
    public static void pickImage(Activity context, int requestCode) {
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            // innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            innerIntent.setAction(Intent.ACTION_PICK);
            innerIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, context.getString(R.string.choose_picture));
        context.startActivityForResult(wrapperIntent, requestCode);
    }

    /**
     * select a picture by Camera,
     *
     * @param context
     * @param imagePath   the path of the image  which take by the camera
     * @param imageName   the name of the image which take by the camera
     * @param requestCode
     */
    public static File selectPicFromCamera(Activity context, String imagePath, String imageName, int requestCode) {
        if (!FileUtils.isSdCardExists()) {
            String st = context.getResources().getString(R.string.sd_card_does_not_exist);
            Toast.makeText(context, st, Toast.LENGTH_SHORT).show();
            return null;
        }

        File cameraFile = new File(imagePath, imageName);
        cameraFile.getParentFile().mkdirs();
        context.startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                requestCode);
        return cameraFile;
    }

    public static File selectPicFromCamera(Activity context, String filePath, int requestCode) {
        if (!FileUtils.isSdCardExists()) {
            String st = context.getResources().getString(R.string.sd_card_does_not_exist);
            Toast.makeText(context, st, Toast.LENGTH_SHORT).show();
            return null;
        }

        File cameraFile = new File(filePath);
        cameraFile.getParentFile().mkdirs();
        context.startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                requestCode);
        return cameraFile;
    }

    public static void downloadApk(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 通知相册改变
     * @param context
     * @param path
     */
    public static void notifyDICMUpdate(Context context,String path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 判断SDK版本是不是4.4或者高于4.4
            String[] paths = new String[]{path};
            MediaScannerConnection.scanFile(context, paths, null, null);
        } else {
            File file=new File(path);
            final Intent intent;
            if (file.isDirectory()) {
                intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                intent.setClassName("com.android.providers.media", "com.android.providers.media.MediaScannerReceiver");
                intent.setData(Uri.fromFile(file));
            } else {
                intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(new File(path)));
            }
            context.sendBroadcast(intent);
        }
    }
}
