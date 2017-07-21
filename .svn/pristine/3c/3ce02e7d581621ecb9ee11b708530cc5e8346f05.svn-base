package com.zenchn.electrombile.kit;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.BuildConf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作    者：wangr on 2017/2/21 15:56
 * 描    述：Retrofit封装类
 * 修订记录：
 */

public class RetrofitKit {

    private Retrofit retrofit;

    private RetrofitKit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConf.BASE_REQUEST_HTTP_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getDefaultOkHttpClient())
                .build();
    }

    private static class SingletonInstance {
        private static final RetrofitKit INSTANCE = new RetrofitKit();
    }

    public static RetrofitKit getInstance() {
        return SingletonInstance.INSTANCE;
    }

    @NonNull
    private static OkHttpClient getDefaultOkHttpClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new LoggerInterceptor("Retrofit"))
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .writeTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(15000L, TimeUnit.MILLISECONDS)
                .build();
    }


    /**
     * 指定一个最大超时时间的Retrofit
     *
     * @param timeout
     * @return
     */
    public static Retrofit getLongTimeoutRetrofit(long timeout) {
        timeout = timeout > 0 ? timeout : 30000L;
        return new Retrofit.Builder()
                .baseUrl(BuildConf.BASE_REQUEST_HTTP_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkHttpClient
                        .Builder()
                        .addInterceptor(new LoggerInterceptor("Retrofit"))
                        .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                        .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                        .readTimeout(timeout, TimeUnit.MILLISECONDS)
                        .build())
                .build();
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * 创建一个请求体
     *
     * @param jsonString
     * @return
     */
    public static RequestBody createRequestBody(String jsonString) {
        return RequestBody.create(MediaType.parse("application/json"), jsonString);
    }

    /**
     * 创建一个请求体
     *
     * @param jsonObject
     * @return
     */
    public static RequestBody createRequestBody(JSONObject jsonObject) {
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toJSONString());
    }

    /**
     * 创建一个请求体
     *
     * @param params
     * @return
     */
    public static RequestBody createRequestBody(Map<String, Object> params) {
        return RequestBody.create(MediaType.parse("application/json"), new JSONObject(params).toJSONString());
    }

    /**
     * 写入到本地文件
     *
     * @param body
     * @param targetFile
     * @return
     */
    public static boolean writeResponseBodyToDisk(ResponseBody body, File targetFile) {

        try {

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {

                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(targetFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            return false;
        }
    }

}
