package com.zenchn.electrombile.api;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.api.callback.BooleanCallback;
import com.zenchn.electrombile.entity.AppVersionInfo;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.kit.RetrofitKit;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作    者：wangr on 2017/3/15 10:59
 * 描    述：
 * 修订记录：
 */
public class AppUpdateApi extends BaseApi {

    private AppUpdateApi() {
    }

    public static AppUpdateApi getInstance() {
        return new AppUpdateApi();
    }

    /**
     * 获取app版本信息
     */
    public void getApkVersion(String systemType, final ApiCallback<AppVersionInfo> callback) {

        mApiStore
                .getApkVersion(systemType)
                .enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {

                            if (response.code() == 200) {

                                JSONObject data = JSONObject.parseObject(response.body()).getJSONObject("data");

                                AppVersionInfo appVersionInfo = new AppVersionInfo();
                                appVersionInfo.setVersionCode(data.getString("verCode"));
                                appVersionInfo.setDownloadUrl(data.getString("verFilePath"));
                                appVersionInfo.setUpdateDesc(data.getString("changeInfo"));
                                appVersionInfo.setIsMust(data.getIntValue("isMust"));
                                appVersionInfo.setFileMd5(data.getString("fileMd5"));
                                appVersionInfo.setFileSize(data.getLongValue("fileSize"));

                                LogKit.success("APP版本信息获取成功", "是否激活：" + data);
                                callback.onSuccess(appVersionInfo);

                            } else if (response.code() == 401) {

                                callback.onGrantRefuse();
                                LogKit.success("APP版本信息获取失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                            } else {
                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success("APP版本信息获取失败", "失败原因：\n" + errorResult.getString("error"));
                                callback.onResponseError(errorResult.getString("error"));
                            }

                        } catch (Exception e) {
                            callback.onFailure();
                            LogKit.exception("APP版本信息获取失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure();
                        LogKit.exception("APP版本信息获取失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

    /**
     * 下载app
     *
     * @param fileUrl
     */
    public void downloadApp(@NonNull final String fileUrl, @NonNull final File targetFile, final BooleanCallback callback) {

        mApiStore
                .downloadFileWithDynamicUrlSync(fileUrl)
                .map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody responseBody) {
                        Response<ResponseBody> response = Response.success(responseBody);
                        if (response.body() != null) {
                            return RetrofitKit.writeResponseBodyToDisk(response.body(), targetFile);
                        } else {
                            return false;
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onResponse(false);
                        LogKit.exception("app下载失败", "请求体新信息：" + e.toString());
                    }

                    @Override
                    public void onNext(Boolean result) {
                        callback.onResponse(result);
                        LogKit.success("app下载成功", "结果：" + result);
                    }
                });

    }


//        mApiStore
//                .downloadFileWithDynamicUrlSync(fileUrl)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                        if (response.body() != null) {
//                            boolean success = RetrofitKit.writeResponseBodyToDisk(response.body(), targetFile);
//                            callback.onResponse(success);
//                            LogKit.success("app下载成功", "结果：" + success);
//
//                        } else {
//                            callback.onResponse(false);
//                            LogKit.exception("app下载失败", "请求体新信息：" + response.toString());
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        callback.onResponse(false);
//                        LogKit.exception("app下载失败", "访问失败, 异常信息：\n" + t.toString());
//                    }
//                });

}
