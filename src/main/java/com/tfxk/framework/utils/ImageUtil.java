package com.tfxk.framework.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Random;

/**
 * class for image handle.<br/>
 *
 * @see #copyFileTo(File, File)
 * @see #calculateInSampleSize(BitmapFactory.Options, int, int)
 * @see #decodeSampledBitmapFromResource(String, int, int)
 * @see #gray(Drawable)
 * @see #resizeImage(Bitmap, float, float)
 * @see #getBitmapFromAssets(Context, String)
 * @see #toRoundBitmap(Bitmap)
 */
public final class ImageUtil {
    private static String tag = ImageUtil.class.getSimpleName();

    public static boolean isAPI19() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    public static int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    public static Bitmap blur(Bitmap bitmap, int radius) {
        //bitmap = compressImage(bitmap, 100);
        Bitmap overlay = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmap.recycle();
        return doFastBlur(overlay, radius, true);
    }

    public static Bitmap doFastBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {
        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }
        if (radius < 1) {
            return (null);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;
        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];
        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }
        yw = yi = 0;
        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;
        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;
            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;
                sir = stack[i + radius];
                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];
                rbs = r1 - Math.abs(i);
                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
// Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];
                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    @SuppressLint("NewApi")
    public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

        if (Build.VERSION.SDK_INT > 16) {
            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
            final RenderScript rs = RenderScript.create(context);
            final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(radius /* e.g. 3.f */);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(bitmap);
            return bitmap;
        }


        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    /**
     * @param src
     * @param to
     */
    public static void copyFileTo(File src, File to) {
        try {
            to.delete();
            to.createNewFile();
            Bitmap pic = BitmapFactory.decodeFile(src.getAbsolutePath());
            final Bitmap temp = pic.copy(Bitmap.Config.ARGB_8888, true);
            FileOutputStream fos = new FileOutputStream(to);
            temp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            pic.recycle();
            temp.recycle();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据targetWidth和targetHeight获取等比例缩放的bitmap
     *
     * @param bitmap
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap getScaledBitmap(Bitmap bitmap, int targetWidth, int targetHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int inSampleSize = 1;
        if (width > targetWidth || height > targetHeight) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) targetHeight);
            final int widthRatio = Math.round((float) width / (float) targetWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f / inSampleSize, 1.0f / inSampleSize);
        if (width == 0 || height == 0)
            return bitmap;
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
        return bitmap;
    }

    public static Bitmap decodeSampledBitmapFromResource(String filePath, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * get a drawable by bitmap get from url
     *
     * @param url the url of the bitmap
     * @return
     */
//    public static Drawable getDrawableFromUrl(String url) {
//        final String filePath = ImageUtil.downLoad(GlobalApp.getContext().getFilesDir().getAbsolutePath() + "/", url, ".png", null);
//        return Drawable.createFromPath(filePath);
//    }

    /**
     * make the drawable be gray
     *
     * @param mDrawable
     */
    public static void gray(Drawable mDrawable) {
        //Make this drawable mutable.
        //A mutable drawable is guaranteed to not share its state with any other drawable.
        mDrawable.mutate();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        mDrawable.setColorFilter(cf);
    }

    /**
     * resize the bitmap
     *
     * @param bitmap
     * @param w
     * @param h
     * @return a new Drawable with new size
     */
    public static Drawable resizeImage(final Bitmap bitmap, final float w, final float h) {
        // load the origial Bitmap
        if (null == bitmap) return null;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // calculate the scale
        float scaleWidth = w / width;
        float scaleHeight = h / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return new BitmapDrawable(resizedBitmap);
    }

    /**
     * compress image bitmap by "quality compress"
     *
     * @param image
     * @param targetSize the target size of bitmap need compress to
     * @return
     */
    public static Bitmap compressBitmap(Bitmap image, int targetSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//quality compress
        int options = 100;
        while (baos.toByteArray().length / 1024 > targetSize) {    //if the size of bitmap is still > targetSize kb,compress
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }


    /**
     * compress image bitmap by "size scale compress
     *
     * @param srcPath    the bitmap 's file path
     * @param reqWidth   the width which requested to compress
     * @param reqHeight  the height which requested to compress
     * @param targetSize the target size of bitmap need compress to
     * @return
     */
    public static Bitmap compressBitmap(String srcPath, int reqWidth, int reqHeight, int targetSize) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap;// = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = calculateInSampleSize(newOpts, reqWidth, reqHeight);//set the scale
        //read bitmap again
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressBitmap(bitmap, targetSize);
    }


    /**
     * compress image bitmap by "size scale compress
     *
     * @param srcPath   the bitmap 's file path
     * @param reqWidth  the width which requested to compress
     * @param reqHeight the height which requested to compress
     * @return
     */
    public static Bitmap compressBitmap(String srcPath, int reqWidth, int reqHeight) {
        return compressBitmap(srcPath, reqWidth, reqHeight, 100);
    }

    /**
     * compress a scaled bitmap & the size of bitmap is < 1M
     *
     * @param image
     * @param targetSize the target size of bitmap need compress to
     * @return
     */
    public static Bitmap compressScaledBitmap(Bitmap image, int targetSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//if the size of bitmap > 1M ,compress to avoid oom
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int reqHeight = ViewUtil.mdpi.height;
        int reqWidth = ViewUtil.mdpi.width;
        newOpts.inSampleSize = calculateInSampleSize(newOpts, reqWidth, reqHeight);
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressBitmap(bitmap, targetSize);
    }

    public static File bitmapToFile(Bitmap bitmap, String toFlePath) {
        try {
            File toFile = new File(toFlePath);
            if (toFile.exists())
                toFile.delete();
            toFile.createNewFile();
            final Bitmap temp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            FileOutputStream fos = new FileOutputStream(toFile);
            temp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            bitmap.recycle();
            temp.recycle();
            return toFile;
        } catch (IOException e) {
            Log.e(tag, e.toString());
        }
        return null;
    }

    /**
     * download a bitmap by url
     *
     * @param folder the folder to be the directory of the bitmap
     * @param url
     * @param suffix
     * @param name
     * @return
     */
//    public static String downLoad(String folder, String url, String suffix, String name) {
//        InputStream is = null;
//        BufferedInputStream bis = null;
//        OutputStream os = null;
//        BufferedOutputStream bos = null;
//        HttpClient client = null;
//        try {
//            String filename = new StringBuffer().append(folder).append(null == name ? StringUtils.md5(url) : name).append(suffix).toString();
//            File file = new File(filename);
//            if (file.length() > 2000) return file.getAbsolutePath();
//            final long begin = System.currentTimeMillis();
//            client = HttpUtils.getHttpClient();
//            HttpResponse response = request(client, url, null);
//            final long length = Long.parseLong(response.getFirstHeader("Content-Length").getValue());
//            /*System.out.println(filename);
//            System.out.println(length + "," + file.length() + "," + url);*/
//            if (length == file.length()) return file.getAbsolutePath();
//            file.delete();
//            is = response.getEntity().getContent();
//            System.out.println("down:" + url + " to " + filename);
//            bis = new BufferedInputStream(is);
//            os = new FileOutputStream(file);
//            bos = new BufferedOutputStream(os);
//            int d;
//            while (-1 != (d = bis.read())) {
//                bos.write(d);
//            }
//            bos.flush();
//            System.out.println("download take:" + (System.currentTimeMillis() - begin) + "ms");
//            return filename;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != is) is.close();
//                if (null != bis) bis.close();
//                if (null != os) os.close();
//                if (null != bos) bos.close();
//                if (null != client) client.getConnectionManager().shutdown();
//                if (null != bos) bos.close();
//            } catch (IOException e) {
//                //
//            }
//        }
//        return null;
//    }

    /**
     * delete the bitmap file if it exists<br/>
     *
     * @param folder
     * @param url
     * @param suffix
     * @param name
     * @return
     * @see #downLoad(String, String, String, String)
     */
//    public static String downLoadIfExist(String folder, String url, String suffix, String name) {
//        String filename = new StringBuffer().append(folder).append(null == name ? StringUtils.md5(url) : name).append(suffix).toString();
//        File file = new File(filename);
//        file.delete();
//        return downLoad(folder, url, suffix, name);
//    }


    /**
     * get the bitmap from the file holder asserts
     *
     * @param c    context
     * @param path the path depends on asserts
     * @return
     */
    public static Bitmap getBitmapFromAssets(Context c, String path) {
        try {
            InputStream is = c.getAssets().open(path);
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            Log.e("debug", e.toString());
            return null;
        }
    }

    /**
     * make the bitmap to be a rounded bitmap
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 根据图片比例自动适配ImageView适配
     *
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public static void autoFitImageView(Context context, final ImageView imageView, final String imageUrl) {
        final int scrW = ViewUtil.getScreenWidth(context);
        Glide.with(context).load(imageUrl)
                .asBitmap().signature(new StringSignature(String.valueOf(new Random(1000).nextInt())))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        float scale = resource.getHeight() * 1.0f / resource.getWidth();
                        int maxHeight = (int) (scrW * scale);
                        ViewUtil.resizeView(imageView, scrW, maxHeight);
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public static Bitmap handleUploadBitmap(String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        File file = new File(imgPath);
        options.inSampleSize = 1;//图片宽高都为原来的二分之一，即图片为
        if (file.exists()) {
            if (file.length() > 512 * 1024) {//0.5M的大小减半
                options.inSampleSize = 2;
            }
            if (file.length() > 1024 * 1024 * 2) {//大于2M就缩放图片为原来的1/3防止崩溃
                options.inSampleSize = 4;//图片宽高都为原来的二分之一，即图片为

            }
        }
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
        return bitmap;
    }
}
