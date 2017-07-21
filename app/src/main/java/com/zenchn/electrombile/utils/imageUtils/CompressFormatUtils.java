package com.zenchn.electrombile.utils.imageUtils;

import android.graphics.Bitmap;
import android.os.Build;

/**
 * 图片压缩格式工具类
 * 
 * @author wangr
 *
 */
public class CompressFormatUtils {

	/**
	 * 获取图片的格式(枚举类型)
	 * 
	 * @param fileName
	 * @return 
	 */
	public static Bitmap.CompressFormat parseFormat(String fileName) {

		int dotPos = fileName.lastIndexOf(".");
		if (dotPos <= 0) {
			return Bitmap.CompressFormat.JPEG;
		}
		String ext = fileName.substring(dotPos + 1);
		if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
			return Bitmap.CompressFormat.JPEG;
		}
		if (ext.equalsIgnoreCase("png")) {
			return Bitmap.CompressFormat.PNG;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			if (ext.equalsIgnoreCase("webp")) {
				return Bitmap.CompressFormat.WEBP;
			}
		}
		return Bitmap.CompressFormat.JPEG;
	}

	/**
	 * 图片格式(枚举转字符串)
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExt(String fileName) {
		Bitmap.CompressFormat format = parseFormat(fileName);
		return getExt(format);
	}

	/**
	 * 获取图片格式(字符串拿到类型)
	 * 
	 * @param format
	 * @return
	 */
	public static String getExt(Bitmap.CompressFormat format) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			if (format == Bitmap.CompressFormat.WEBP) {
				return ".webp";
			}
		}

		if (format == Bitmap.CompressFormat.PNG) {
			return ".png";
		}
		return ".jpg";
	}
}
