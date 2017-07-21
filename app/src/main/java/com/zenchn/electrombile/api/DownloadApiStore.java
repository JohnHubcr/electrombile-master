package com.zenchn.electrombile.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by wangr on 2017/3/15 0015.
 */
public interface DownloadApiStore {

    // 下载文件
    @Streaming
    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    // 下载文件
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync1(@Url String fileUrl);

}
