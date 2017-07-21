package com.zenchn.electrombile.utils.imageUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 图片处理工具类
 *
 * @author xuxu
 */
public class ImageUtil {

    /**
     * 图片保存到本地
     *
     * @param bitmap
     * @param path
     */
    public static void saveToSamllImg(Bitmap bitmap, String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        File file = new File(path);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩图片
     * @param srcPath
     * @param windowWidth 手机
     * @param windowHigh
     * @return
     */
//	public static Bitmap getImage(String srcPath, int rate) {  
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
//        newOpts.inJustDecodeBounds = true;  
//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回be为空  
//        newOpts.inJustDecodeBounds = false;  
//        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
//        int be = rate;//be=1表示不缩放  
//        newOpts.inSampleSize = be;//设置缩放比例  
//        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
//        bitmap = BitmapFactory.decodeFile(srcPath, newOpts); 
//    	return bitmap;
//    }  

    /**
     * 获取图片路径
     *
     * @param uri
     * @param activity
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
//		int column_index = cursor
//				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * 保存图片到本地
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, String filePath) {
        File file = new File(filePath);
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), null, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    /**
     * 压缩图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path) {
        Bitmap bitmap = null;
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                    new File(path)));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            int i = 0;
            while (true) {
                if ((options.outWidth >> i <= 1000)
                        && (options.outHeight >> i <= 1000)) {
                    in = new BufferedInputStream(
                            new FileInputStream(new File(path)));
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                    break;
                }
                i += 1;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap getImage(String path) {
        Uri uri = Uri.parse(path);
        Bitmap bmp = BitmapFactory.decodeFile(uri.toString());
        return bmp;
    }

    /**
     * 图片转灰度
     *
     * @param bmSrc
     * @return
     */
    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        int width, height;
        height = bmSrc.getHeight();
        width = bmSrc.getWidth();
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

}
