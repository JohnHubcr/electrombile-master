package com.zenchn.electrombile.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.entity.FeedbackInfo;
import com.zenchn.electrombile.kit.LogKit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/2/22 15:09
 * 描    述：
 * 修订记录：
 */
public class FeedbackApi extends BaseApi {

    private FeedbackApi() {
    }

    public static FeedbackApi getInstance() {
        return new FeedbackApi();
    }

    /**
     * 用户反馈信息
     *
     * @param accessToken
     * @param feedbackInfo
     */
    public void accountFeedBack(String accessToken, FeedbackInfo feedbackInfo, ApiCallback<Boolean> callback) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content", feedbackInfo.getFeedbackContent())
                .addFormDataPart("contactNumber", feedbackInfo.getContactNumber());

        if (feedbackInfo.getFeedbackImageFile() != null)
            builder.addFormDataPart("pictureFile", feedbackInfo.getFeedbackImageFile().getName(), RequestBody.create(MediaType.parse("multipart/form-data"), feedbackInfo.getFeedbackImageFile()));

        accountFeedBack(accessToken, builder.build(), callback);
    }

    /**
     * 用户反馈信息
     *
     * @param accessToken
     * @param body
     * @param callback
     */
    private void accountFeedBack(String accessToken, RequestBody body, final ApiCallback<Boolean> callback) {

        getLongTimeoutRetrofit(30000L)
                .accountFeedBack(accessToken, body)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 try {

                                     if (response.code() == 200) {

                                         JSONObject jsonResult = JSONObject.parseObject(response.body());
                                         int statusCode = jsonResult.getIntValue("statusCode");
                                         boolean data = BuildConf.ApiStatusCode.SUCCESS == statusCode;
                                         LogKit.success("用户反馈提交成功", "是否激活：" + data);
                                         callback.onSuccess(data);

                                     } else if (response.code() == 401) {

                                         callback.onGrantRefuse();
                                         LogKit.success("用户反馈提交失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                                     } else {
                                         JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                         LogKit.success("用户反馈提交失败", "失败原因：\n" + errorResult.getString("error"));
                                         callback.onResponseError(errorResult.getString("error"));
                                     }

                                 } catch (Exception e) {
                                     callback.onFailure();
                                     LogKit.exception("用户反馈提交失败", "解析错误，异常信息：\n" + e.toString());
                                 }
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 callback.onFailure();
                                 LogKit.exception("用户反馈提交失败", "访问失败, 异常信息：\n" + t.toString());
                             }
                         }

                );
    }
}
