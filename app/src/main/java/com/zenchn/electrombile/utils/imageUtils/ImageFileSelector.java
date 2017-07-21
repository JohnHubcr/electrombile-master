package com.zenchn.electrombile.utils.imageUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.mlibrary.log.LogUtils;

import java.io.File;

/**
 * 图片选择器
 *
 * @author wangr
 */
public class ImageFileSelector {

    private ImageFilesCallback mCallback;
    private ImagePickHelper mImagePickHelper;// 本地图片选择器
    private ImageCaptureHelper mImageTaker;// 相机操作类
    private ImageCompressHelper mImageCompressHelper;// 图片压缩器
    private ImageFileHelper mImageFileHelper;// 文件管理类
    private boolean isDelete;// 照片是否删除
    private File defaultCaptureDir;
    private File defaultCacheDir;

    public ImageFileSelector(final Context context) {

        mImageFileHelper = new ImageFileHelper(context);

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            defaultCacheDir = mImageFileHelper.getExternalImageCacheDir();// 缓存目录
            defaultCaptureDir = mImageFileHelper.getExternalImageFilesDir();// 长时间保存的目录
        }

        mImagePickHelper = new ImagePickHelper(context);
        mImagePickHelper.setCallback(new ImagePickHelper.Callback() {
            @Override
            public void onSuccess(String file, String tag) {
                LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "文件选择的图片路径：" + file);
                handleResult(file, tag, false);
            }

            @Override
            public void onError(String tag) {
                handleError(tag);
            }
        });

        mImageTaker = new ImageCaptureHelper(defaultCaptureDir);
        mImageTaker.setCallback(new ImageCaptureHelper.Callback() {

            @Override
            public void onSuccess(String file, String tag) {
                LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "相机拍下的图片路径：" + file);
                handleResult(file, tag, isDelete);
            }

            @Override
            public void onError(String tag) {
                handleError(tag);
            }

            @Override
            public void onError() {
                handleError();
            }
        });

        mImageCompressHelper = new ImageCompressHelper(defaultCacheDir);
        mImageCompressHelper.setCallback(new ImageCompressHelper.CompressCallback() {
            @Override
            public void onCallBack(String outFile, String tag) {
                LogUtils.printSimpleLog(BuildConf.LogTags.IMAGE_TAG, "压缩图像输出的图片路径：" + outFile);
                if (mCallback != null) {
                    mCallback.onCompressSuccess(outFile, tag);
                }
            }
        });
    }

    /**
     * 判断目录
     *
     * @return
     */
    public Boolean checkDir() {
        if (defaultCacheDir == null && defaultCaptureDir == null) {
            return null;
        } else if (defaultCacheDir != null && defaultCaptureDir != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置缓存文件位置
     *
     * @param defaultCacheDir 缓存文件位置
     */
    public void setDefaultCacheDir(File defaultCacheDir) {
        mImageCompressHelper.setDefaultCacheDir(defaultCacheDir);
    }

    /**
     * 设置照片文件位置
     *
     * @param defaultCaptureDir 照片文件位置
     */
    public void setDefaultCaptureDir(File defaultCaptureDir) {
        mImageTaker.setDefaultCaptureDir(defaultCaptureDir);
    }

    /**
     * 照片是否删除
     *
     * @return
     */
    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 设置压缩后的文件大小
     *
     * @param maxWidth  压缩后文件宽度
     * @param maxHeight 压缩后文件高度
     */
    public void setOutPutImageSize(int maxWidth, int maxHeight) {
        mImageCompressHelper.setOutPutImageSize(maxWidth, maxHeight);
    }

    /**
     * 设置压缩后保存图片的质量
     *
     * @param quality 图片质量 0 - 100
     */
    public void setQuality(int quality) {
        mImageCompressHelper.setQuality(quality);
    }

    /**
     * 设置压缩后保存图片的格式
     *
     * @param compressFormat 图片格式
     */
    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        mImageCompressHelper.setCompressFormat(compressFormat);
    }

    /**
     * 设置操作完成的回调
     *
     * @param callback
     */
    public void setCallback(ImageFilesCallback callback) {
        mCallback = callback;
    }

    /**
     * 从本地图库选择图片的回调
     *
     * @param requestCode
     * @param resultCode
     * @param tag
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, String tag, Intent data) {
        mImagePickHelper.onActivityResult(requestCode, resultCode, tag, data);
        mImageTaker.onActivityResult(requestCode, resultCode, tag, data);
    }

    public void onSaveInstanceState(Bundle outState) {
        mImageTaker.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mImageTaker.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 从本地图库选择图片
     *
     * @param activity
     */
    public void selectImage(Activity activity) {
        mImagePickHelper.selectorImage(activity);
    }

    public void selectImage(android.app.Fragment fragment) {
        mImagePickHelper.selectImage(fragment);
    }

    /**
     * 拍照
     *
     * @param activity
     */
    public void takePhoto(Activity activity) {
        mImageTaker.captureImage(activity);
    }

    public void takePhoto(android.app.Fragment fragment) {
        mImageTaker.captureImage(fragment);
    }

    /**
     * 执行图片压缩任务
     *
     * @param fileName
     * @param deleteSrc
     */
    private void handleResult(String fileName, String tag, boolean deleteSrc) {
        File file = new File(fileName);
        if (file.exists()) {
            mImageCompressHelper.compress(fileName, tag, deleteSrc);
        } else {
            if (mCallback != null) {
                mCallback.onImageError(tag);
            }
        }
    }

    private void handleError() {
        if (mCallback != null) {
            mCallback.onImageError();
        }
    }

    private void handleError(String tag) {
        if (mCallback != null) {
            mCallback.onImageError(tag);
        }
    }

    /**
     * 图片文件操作的回调接口
     *
     * @author wangr
     */
    public interface ImageFilesCallback {

        void onSelectSuccess(String file, String tag);

        void onCompressSuccess(String file, String tag);

        void onImageError();

        void onImageError(String tag);
    }

}
