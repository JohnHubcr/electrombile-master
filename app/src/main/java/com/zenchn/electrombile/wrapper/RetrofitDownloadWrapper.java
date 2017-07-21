package com.zenchn.electrombile.wrapper;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.wrapper.callback.ProgressResponseCallback;
import com.zenchn.electrombile.wrapper.okHttp.OkHttpClientAgent;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作    者：wangr on 2017/3/15 17:23
 * 描    述：
 * 修订记录：
 */
public class RetrofitDownloadWrapper {

    /**
     * 创建带响应进度(下载进度)回调的service
     *
     * @param callback
     * @return
     */
    public static Retrofit createScalarsDownloadService(ProgressResponseCallback callback) {
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClientAgent.createScalarsResponseClient(callback))
                .build();
    }

    /**
     * 创建带响应进度(下载进度)回调的service
     *
     * @param callback
     * @return
     */
    public static Retrofit createDownloadService(ProgressResponseCallback callback) {
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClientAgent
                        .getInstance()
                        .setAllTimeout(60000L)
                        .addProgressResponseCallback(callback))
                .build();
    }

    /**
     * 创建带响应进度(下载进度)回调的service
     *
     * @param callback
     * @return
     */
    public static Retrofit createDownloadService(long timeout, ProgressResponseCallback callback) {
        return new Retrofit.Builder()
                .baseUrl(BuildConf.BASE_REQUEST_HTTP_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClientAgent
                        .getInstance()
                        .setAllTimeout(timeout)
                        .addProgressResponseCallback(callback))
                .build();
    }

}
