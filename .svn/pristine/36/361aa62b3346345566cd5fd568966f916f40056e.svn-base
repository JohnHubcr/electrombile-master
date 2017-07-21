package com.zenchn.electrombile.utils.imageUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.zenchn.electrombile.app.Constants;
import com.zenchn.mlibrary.utils.FileUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件管理类
 *
 * @author wangr
 */
public class ImageFileHelper {

    private Activity mContext;

    public ImageFileHelper(Context context) {
        this.mContext = (Activity) context;
    }

    /**
     * 复制文件
     *
     * @param source 源文件路径
     * @param dest   目标路径
     * @return
     */
    public static boolean copy(File source, File dest) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        boolean result = true;
        try {
            bis = new BufferedInputStream(new FileInputStream(source));
            bos = new BufferedOutputStream(new FileOutputStream(dest, false));

            byte[] buf = new byte[1024];
            bis.read(buf);

            do {
                bos.write(buf);
            } while (bis.read(buf) != -1);
        } catch (IOException e) {
            result = false;
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 在图片缓存文件夹生成一个临时文件
     *
     * @param ext 文件后缀名 e.g ".jpg"
     * @return 生成的临时文件
     */
    public File generateExternalImageCacheFile(String ext) {
        String fileName = "img_" + System.currentTimeMillis();
        return generateExternalImageCacheFile(fileName, ext);
    }

    private File generateExternalImageCacheFile(String fileName, String ext) {
        File cacheDir = getExternalImageCacheDir();
        String path = cacheDir.getPath() + File.separator + fileName + ext;
        return new File(path);
    }

    /**
     * 在文件夹生成一个临时文件
     *
     * @param dir 图片缓存文件夹
     * @param ext 生成的临时文件
     * @return
     */
    public static File createImageCacheFile(File dir, String ext) {
        String fileName = "img_" + System.currentTimeMillis();
        return new File(dir.getPath() + File.separator + fileName + ext);
    }

    /**
     * 生成一个临时文件缓存文件夹
     *
     * @return
     */
    public File getExternalImageCacheDir() {
        File externalCacheDir = getExternalCacheDir();
        if (externalCacheDir != null) {
            String path = externalCacheDir.getPath() + File.separator + Constants.FILE_DIR_IMAGE + "/image_selector";
            File file = new File(path);
            if (file.isFile()) {
                FileUtils.deleteDirectory(path);
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        final String cacheDir = "/Android/data/" + mContext.getPackageName() + File.separator + Constants.FILE_DIR_CACHE + File.separator + Constants.FILE_DIR_IMAGE;
        return new File(cacheDir);
    }

    public File getExternalCacheDir() {
        File file = null;
        try {
            file = mContext.getExternalCacheDir();
            if (file == null) {
                String cacheDir = "/Android/data/" + mContext.getPackageName() + File.separator + Constants.FILE_DIR_CACHE;
                file = new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
            }
        } catch (Exception e) {
            file = new File(Constants.appFolder + File.separator + Constants.FILE_DIR_CACHE);
        } finally {
            if (file != null) {
                return file;
            }
        }
        return file;
    }

    /**
     * 生成一个长时间保存图片的文件夹
     *
     * @return
     */
    public File getExternalImageFilesDir() {
        File file = null;
        try {
            file = mContext.getExternalFilesDir(File.separator + Constants.FILE_DIR_IMAGE);
        } catch (Exception e) {
            file = new File(Constants.appFolder + File.separator + Constants.FILE_DIR_FILES + File.separator + Constants.FILE_DIR_IMAGE);
        } finally {
            if (file != null) {
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
        }
        // final String defaultFile = "/Android/data/" +
        // mContext.getPackageName() + "/files" + "/image";
        // return new File(Environment.getExternalStorageDirectory().getPath() +
        // defaultFile);
        return file;
    }
}
