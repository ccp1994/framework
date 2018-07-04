package com.tfxk.framework.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 
 * @author Yong 图片相关工具类
 */
public class BitmapUtils {
	public static  String bitmaptoString(Bitmap bitmap, int bitmapQuality) {

		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, bitmapQuality, bStream);
		byte[] bytes = bStream.toByteArray();
	     return Base64.encodeToString(bytes, Base64.NO_WRAP);
	}
	private static Bitmap compressImage(Bitmap image) {
		Bitmap bitmap=null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			
		
			
			
		image.compress(CompressFormat.JPEG, 90, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			if (options>10) {
				options -= 10;
			}else{
				break;
			}
			// 每次都减少10
		}
		} catch (Exception e) {
			// TODO: handle exception
			compressImage(bitmap);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
	
		try {
			bitmap = BitmapFactory.decodeStream(isBm, null, null);
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			while (bitmap==null) {
				  System.gc();  
		            System.runFinalization();  
		            bitmap =  BitmapFactory.decodeStream(isBm, null, null);  
				
			}
		}
		// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	public static Bitmap getimage(String srcPath) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
			newOpts.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
			float hh = 800f;// 这里设置高度为800f
			float ww = 480f;// 这里设置宽度为480f
			// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;// be=1表示不缩放
			if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;// 设置缩放比例
			// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (OutOfMemoryError e) {

		}
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}
}
