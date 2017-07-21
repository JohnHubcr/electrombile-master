package com.zenchn.electrombile.wrapper.okHttp;

import com.zenchn.electrombile.wrapper.callback.ProgressRequestCallback;
import com.zenchn.electrombile.wrapper.callback.ProgressResponseCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作    者：wangr on 2017/3/15 17:06
 * 描    述：OkHttpClient进行代理
 * 修订记录：
 */
public class OkHttpClientAgent {

    private OkHttpClient.Builder builder;

    private OkHttpClientAgent() {
        builder = new OkHttpClient.Builder();
    }

    public static OkHttpClientAgent getInstance() {
        return new OkHttpClientAgent();
    }

    public OkHttpClientAgent setConnectTimeout(long timeout) {
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        return this;
    }

    public OkHttpClientAgent setWriteTimeout(long timeout) {
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        return this;
    }

    public OkHttpClientAgent setReadTimeout(long timeout) {
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        return this;
    }

    public OkHttpClientAgent setAllTimeout(long timeout) {
        builder
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS);
        return this;
    }

    public OkHttpClient build() {
        return builder.build();
    }

    /**
     * 代理OkHttpClient，用于下载文件的回调
     *
     * @param callback 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public OkHttpClient addProgressResponseCallback(final ProgressResponseCallback callback) {

        //增加拦截器
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());

                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), callback))
                        .build();
            }
        });
        return builder.build();
    }


    /**
     * 代理OkHttpClient，用于上传文件的回调
     *
     * @param callback 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public OkHttpClient addProgressRequestCallback(final ProgressRequestCallback callback) {

        //增加拦截器
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .method(original.method(), new ProgressRequestBody(original.body(), callback))
                        .build();
                return chain.proceed(request);
            }
        });
        return builder.build();
    }

    /**
     * 创建一个标准的上传Client
     *
     * @param callback
     * @return
     */
    public static OkHttpClient createScalarsRequestClient(ProgressRequestCallback callback) {
        return getInstance()
                .setAllTimeout(60000L)
                .addProgressRequestCallback(callback);
    }

    /**
     * 创建一个标准的下载Client
     *
     * @param callback
     * @return
     */
    public static OkHttpClient createScalarsResponseClient(ProgressResponseCallback callback) {
        return getInstance()
                .setAllTimeout(60000L)
                .addProgressResponseCallback(callback);
    }

}
