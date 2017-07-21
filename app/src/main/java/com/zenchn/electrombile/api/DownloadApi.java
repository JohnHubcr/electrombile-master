package com.zenchn.electrombile.api;

import android.support.annotation.NonNull;

import com.zenchn.electrombile.api.callback.DownloadCallback;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.kit.RetrofitKit;
import com.zenchn.electrombile.wrapper.RetrofitDownloadWrapper;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangr on 2017/3/15 0015.
 */
public class DownloadApi {

//    public static void download(@NonNull final String url, @NonNull final File targetFile, final DownloadCallback callback) {
//
//        RetrofitDownloadWrapper
//                .createDownloadService(5 * 60000L, callback)
//                .create(DownloadApiStore.class)
//                .downloadFileWithDynamicUrlSync("http://s1.music.126.net/download/android/CloudMusic_official_4.0.0_179175.apk")
//                .map(new Func1<ResponseBody, File>() {
//                    @Override
//                    public File call(ResponseBody responseBody) {
//                        Response<ResponseBody> response = Response.success(responseBody);
//                        if (response.body() != null)
//                            RetrofitKit.writeResponseBodyToDisk(response.body(), targetFile);
//                        return targetFile;
//                    }
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<File>() {
//                    @Override
//                    public void call(File file) {
//                        callback.onDownloadSuccess(file);
//                        LogKit.success("app下载成功", "结果：" + (file != null));
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        callback.onDownloadFailure();
//                        LogKit.exception("app下载失败", "请求体新信息：" + throwable.toString());
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
//                        callback.onDownloadCompleted();
//                    }
//                });
//
//    }

    public static void download(@NonNull final String url, @NonNull final File targetFile, final DownloadCallback callback) {

        RetrofitDownloadWrapper
                .createDownloadService(5 * 60000L, callback)
                .create(DownloadApiStore.class)
                .downloadFileWithDynamicUrlSync1("http://s1.music.126.net/download/android/CloudMusic_official_4.0.0_179175.apk")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {
                            RetrofitKit.writeResponseBodyToDisk(response.body(), targetFile);
                        } else {
                            LogKit.exception("app下载失败", "请求体新信息：" + "response is a null object");
                        }
                            callback.onDownloadSuccess(targetFile);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        callback.onDownloadFailure();
                        LogKit.exception("app下载失败", "请求体新信息：" + t.toString());
                    }
                });
    }

}

