package com.zenchn.electrombile.wrapper;

import com.zenchn.electrombile.wrapper.callback.ProgressRequestCallback;
import com.zenchn.electrombile.wrapper.okHttp.OkHttpClientAgent;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作    者：wangr on 2017/3/15 17:23
 * 描    述：
 * 修订记录：
 */
public class RetrofitUploadWrapper {


    /**
     * 创建带响应进度(上传进度)回调的service
     *
     * @param baseUrl
     * @param callback
     * @return
     */
    public static Retrofit createScalarsUploadService(String baseUrl, ProgressRequestCallback callback) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClientAgent.createScalarsRequestClient(callback))
                .build();
    }

    /**
     * 创建带响应进度(上传进度)回调的service
     *
     * @param baseUrl
     * @param callback
     * @return
     */
    public static Retrofit createUploadService(String baseUrl, ProgressRequestCallback callback) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClientAgent
                        .getInstance()
                        .setAllTimeout(30000L)
                        .addProgressRequestCallback(callback))
                .build();


    }

    /**
     * 创建带响应进度(上传进度)回调的service
     *
     * @param baseUrl
     * @param timeout
     * @param callback
     * @return
     */
    public static Retrofit createUploadService(String baseUrl, long timeout, ProgressRequestCallback callback) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClientAgent
                        .getInstance()
                        .setAllTimeout(timeout)
                        .addProgressRequestCallback(callback))
                .build();


    }
}
