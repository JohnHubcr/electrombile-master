package com.zenchn.electrombile.utils.imageUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.lang.ref.WeakReference;

class ImagePickHelper {

    private static final int SELECT_PIC = 0x701;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 0x11;

    private Callback mCallback;
    private Context mContext;

    private WeakReference<?> mWeakReference;

    public ImagePickHelper(Context context) {
        mContext = context;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

//    public void selectImage(android.app.Fragment fragment) {
//        mWeakReference = new WeakReference<>(fragment);
//        doSelect(fragment);
//    }

    public void selectImage(Fragment fragment) {
        mWeakReference = new WeakReference<>(fragment);
        doSelect(fragment);
    }

    public void selectorImage(Activity activity) {
        mWeakReference = new WeakReference<>(activity);
        doSelect(activity);
    }

    private void doSelect(Activity activity) {
        Intent intent = createIntent();
        activity.startActivityForResult(intent, SELECT_PIC);
    }

    private void doSelect(android.support.v4.app.Fragment fragment) {
        Intent intent = createIntent();
        fragment.startActivityForResult(intent, SELECT_PIC);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void doSelect(android.app.Fragment fragment) {
        Intent intent = createIntent();
        fragment.startActivityForResult(intent, SELECT_PIC);
    }

    private Intent createIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    public void onActivityResult(int requestCode, int resultCode, String tag, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == SELECT_PIC) {
            Uri uri = data.getData();
            String path = Compatibility.getPath(mContext, uri);
            if (mCallback != null) {
                if (path != null) {
                    mCallback.onSuccess(path, tag);
                } else {
                    mCallback.onError(tag);
                }
            }
        }
    }

    public interface Callback {

        void onSuccess(String file, String tag);

        void onError(String tag);
    }

}
