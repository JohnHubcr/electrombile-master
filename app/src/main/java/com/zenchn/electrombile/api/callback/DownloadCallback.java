package com.zenchn.electrombile.api.callback;

import com.zenchn.electrombile.wrapper.callback.ProgressResponseCallback;

import java.io.File;

/**
 * Created by wangr on 2017/3/15 0015.
 */
public interface DownloadCallback extends ProgressResponseCallback {

    void onDownloadSuccess(File file);

    void onDownloadFailure();

    void onDownloadCompleted();
}
