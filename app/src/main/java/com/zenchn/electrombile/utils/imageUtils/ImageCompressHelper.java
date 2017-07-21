package com.zenchn.electrombile.utils.imageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.mlibrary.log.LogUtils;

import java.io.File;

/**
 * 图片压缩类
 *
 * @author wangr
 */
class ImageCompressHelper {

    private int mMaxWidth = 1000;
    private int mMaxHeight = 1000;
    private int mQuality = 80;
    private Bitmap.CompressFormat mCompressFormat = null;

    private CompressCallback mCallback;
    private File defaultCacheDir;

    public ImageCompressHelper(File defaultCacheDir) {
        this.defaultCacheDir = defaultCacheDir;
    }

    public void setCallback(CompressCallback callback) {
        mCallback = callback;
    }

    /**
     * 设置缓存文件位置
     *
     * @param defaultCacheDir 文件位置
     */
    public void setDefaultCacheDir(File defaultCacheDir) {
        this.defaultCacheDir = defaultCacheDir;
    }

    /**
     * 设置压缩后的文件大小
     *
     * @param maxWidth  压缩后文件宽度
     * @param maxHeight 压缩后文件高度
     */
    public void setOutPutImageSize(int maxWidth, int maxHeight) {
        mMaxWidth = maxWidth;
        mMaxHeight = maxHeight;
    }

    /**
     * 设置压缩后的格式
     *
     * @param compressFormat 图片格式
     */
    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        mCompressFormat = compressFormat;
    }

    /**
     * 设置压缩后保存图片的质量
     *
     * @param quality 图片质量 0 - 100
     */
    public void setQuality(int quality) {
        mQuality = quality;
    }

    /**
     * 压缩图片
     *
     * @param fileName
     * @param tag
     * @param deleteSrc
     */
    public void compress(String fileName, String tag, boolean deleteSrc) {
        if (mMaxHeight <= 0 || mMaxWidth <= 0) {
            if (mCallback != null) {
                File outputFile = ImageFileHelper.createImageCacheFile(defaultCacheDir, ".jpg");
                ImageFileHelper.copy(new File(fileName), outputFile);
                mCallback.onCallBack(outputFile.getAbsolutePath(), tag);
            }
        } else {
            ImageFile srcImageFile = new ImageFile(fileName, deleteSrc);
            new CompressTask(tag).execute(srcImageFile);
        }
    }

    /**
     * 异步任务处理图片
     *
     * @author wangr
     */
    private class CompressTask extends AsyncTask<ImageFile, Integer, String> {

        protected String tag;

        protected CompressTask(String tag) {
            this.tag = tag;
        }

        @Override
        protected String doInBackground(ImageFile... params) {
            LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "------------------ 开始压缩图片 ------------------");
            ImageFile srcFileInfo = params[0];

            Bitmap.CompressFormat format = mCompressFormat;
            if (format == null) {
                LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "------------------ 默认压缩格式 ------------------");
                format = CompressFormatUtils.parseFormat(srcFileInfo.mSrcFilePath);
            }
            LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "----------------- 压缩格式: " + format.name() + " ------s-----------");

            File outputFile = ImageFileHelper.createImageCacheFile(defaultCacheDir, CompressFormatUtils.getExt(format));
            File srcFile = new File(srcFileInfo.mSrcFilePath);

            boolean isCompress = compressImageFile(srcFileInfo.mSrcFilePath, outputFile.getPath(), mMaxWidth, mMaxHeight, mQuality, format);
            if (!isCompress) {
                // 没有压缩，直接copy
                ImageFileHelper.copy(srcFile, outputFile);
            }
            if (srcFileInfo.mDeleteSrc) {
                srcFile.delete();
            }
            return outputFile.getPath();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (mCallback != null) {
                mCallback.onCallBack(result, tag);
            }
        }

    }

    /**
     * 压缩图片文件
     *
     * @param srcFile   源文件
     * @param dstFile   目标文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return true进行了压缩，false无需压缩
     */
    @SuppressWarnings("deprecation")
    public static boolean compressImageFile(String srcFile, String dstFile, int maxWidth, int maxHeight, int quality, Bitmap.CompressFormat compressFormat) {

        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "源路径:" + srcFile);
        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "压缩前文件大小:" + (int) (new File(srcFile).length() / 1024d) + "kb");
        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "限定的最大宽高:(" + maxWidth + " × " + maxHeight + ")", 4);

        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(srcFile, decodeOptions);
        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;

        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "图片实际宽高:(" + actualWidth + " × " + actualHeight + ")");

        if (actualWidth < maxWidth && actualHeight < maxHeight) {
            LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "无需压缩", 3);
            return rotateImage(srcFile, dstFile, quality, compressFormat);
        }

        int sampleSize;
        int w;
        int h;
        if (actualWidth * maxHeight > maxWidth * actualHeight) {
            w = maxWidth;
            h = (int) (w * actualHeight / (double) actualWidth);
            sampleSize = (int) (actualWidth / (double) maxWidth);
        } else {
            h = maxHeight;
            w = (int) (h * actualWidth / (double) actualHeight);
            sampleSize = (int) (actualHeight / (double) maxHeight);
        }
        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "------------------ 计算压缩参数 ------------------");
        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "压缩比率：" + sampleSize, 2);

        decodeOptions.inJustDecodeBounds = false;
        decodeOptions.inSampleSize = sampleSize;
        decodeOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        decodeOptions.inPurgeable = true;
        decodeOptions.inInputShareable = true;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(srcFile, decodeOptions);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
            LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "压缩错误：" + "\n" + "内存溢出：" + srcFile + ", size(" + actualWidth + " × " + actualHeight + ")", 4);
        }

        if (bitmap == null) {
            LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "压缩错误：" + "\n" + "文件解析错误，已停止", 4);
            return false;
        }
        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "压缩后宽高:(" + bitmap.getWidth() + " × " + bitmap.getHeight() + ")", 2);

        if (bitmap.getWidth() > maxWidth || bitmap.getHeight() > maxHeight) {
            Bitmap tempBitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            bitmap.recycle();
            bitmap = tempBitmap;
            LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "按比例缩小:(" + bitmap.getWidth() + " × " + bitmap.getHeight() + ")", 2);
        }

        int degree = ImageUtils.getExifOrientation(srcFile);
        if (degree != 0) {
            LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "图像旋转角度:" + degree, 2);
            Bitmap rotate = ImageUtils.rotateImage(degree, bitmap);
            bitmap.recycle();
            bitmap = rotate;
        }

        ImageUtils.saveBitmap(bitmap, dstFile, compressFormat, quality);
        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "压缩后文件大小:" + (int) (new File(dstFile).length() / 1024d) + "kb");
        LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "------------------ 压缩完成 ------------------");
        return true;
    }

    private static boolean rotateImage(String imageFile, String outputFile, int quality, Bitmap.CompressFormat compressFormat) {
        int degree = ImageUtils.getExifOrientation(imageFile);
        if (degree != 0) {
            LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "图像旋转角度:" + degree, 2);
            Bitmap origin = BitmapFactory.decodeFile(imageFile);
            Bitmap rotate = ImageUtils.rotateImage(degree, origin);
            if (rotate != null) {
                ImageUtils.saveBitmap(rotate, outputFile, compressFormat, quality);
                rotate.recycle();
                origin.recycle();
                return true;
            } else {
                LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "图片旋转错误：" + "\n" + "文件地址：" + imageFile, 4);
                LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "使用原始图片", 4);
            }
            origin.recycle();
        }

        return false;
    }

    public interface CompressCallback {
        void onCallBack(String outFile, String tag);
    }

    private class ImageFile {
        public final String mSrcFilePath;
        public final boolean mDeleteSrc;

        public ImageFile(String srcFilePath, boolean deleteSrc) {
            mSrcFilePath = srcFilePath;
            mDeleteSrc = deleteSrc;
        }
    }

}
