package com.zenchn.electrombile.utils.imageUtils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.zenchn.mlibrary.log.LogUtils;

import java.io.File;

/**
 * 相机操作类
 *
 * @author wangr
 */
class ImageCaptureHelper {

    private static final String KEY_OUT_PUT_FILE = "key_out_put_file";
    private static final int CHOOSE_PHOTO_FROM_CAMERA = 0x702;

    private Callback mCallback;
    private File defaultCaptureDir;
    private File defaultFiles;

    public ImageCaptureHelper(File defaultCaptureDir) {
        this.defaultCaptureDir = defaultCaptureDir;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void onSaveInstanceState(Bundle outState) {
        if (defaultFiles != null) {
            outState.putString(KEY_OUT_PUT_FILE, defaultFiles.getPath());
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String tempFilePath = savedInstanceState.getString(KEY_OUT_PUT_FILE);
            if (!TextUtils.isEmpty(tempFilePath)) {
                defaultFiles = new File(tempFilePath);
            }
        }
    }

    public void captureImage(Activity activity) {
        this.defaultFiles = ImageFileHelper.createImageCacheFile(defaultCaptureDir, ".jpg");// 临时文件
        try {
            activity.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            LogUtils.printErrorLog(e);
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    public void captureImage(Fragment fragment) {
        this.defaultFiles = ImageFileHelper.createImageCacheFile(defaultCaptureDir, ".jpg");// 临时文件
        try {
            fragment.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            LogUtils.printErrorLog(e);
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    public void captureImage(android.app.Fragment fragment) {
        this.defaultFiles = ImageFileHelper.createImageCacheFile(defaultCaptureDir, ".jpg");// 临时文件
        try {
            fragment.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            LogUtils.printErrorLog(e);
            if (mCallback != null) {
                mCallback.onError();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, String tag, Intent data) {
        if (requestCode == CHOOSE_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            if (defaultFiles != null && defaultFiles.exists()) {
                if (mCallback != null) {
                    mCallback.onSuccess(defaultFiles.getPath(), tag);
                }
            } else {
                if (mCallback != null) {
                    mCallback.onError(tag);
                }
            }
        }
    }

    private Intent createIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(defaultFiles));
        return intent;
    }

    public interface Callback {

        void onSuccess(String fileName, String tag);

        void onError(String key);

        void onError();
    }

    public void setDefaultCaptureDir(File defaultCaptureDir) {
        this.defaultCaptureDir = defaultCaptureDir;
    }

}