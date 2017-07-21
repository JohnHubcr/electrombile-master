package com.zenchn.electrombile.wrapper.callback;

/**
 * 作    者：wangr on 2017/3/15 17:01
 * 描    述： 请求体进度回调接口，用于文件上传进度回调
 * 修订记录：
 */
public interface ProgressRequestCallback {

    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
