package com.zenchn.electrombile.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.kit.RetrofitKit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/2/22 17:23
 * 描    述：
 * 修订记录：
 */
public class ResetPwdApi extends BaseApi {

    private ResetPwdApi() {
    }

    public static ResetPwdApi getInstance() {
        return new ResetPwdApi();
    }

    /**
     * 修改密码
     *
     * @param mobilePhone
     * @param encryptPassword
     * @param authCode
     * @param callback
     */
    public void resetPwd(String mobilePhone, String encryptPassword, String authCode, ApiCallback<String> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobilePhone", mobilePhone);
        jsonObject.put("newPassword", encryptPassword);
        jsonObject.put("authCode", authCode);
        resetPwd(RetrofitKit.createRequestBody(jsonObject), callback);
    }

    /**
     * 修改密码
     *
     * @param body
     * @param callback
     */
    private void resetPwd(RequestBody body, final ApiCallback<String> callback) {

        mApiStore
                .resetPwd(body)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {

                            if (response.code() == 200) {

                                JSONObject jsonResult = JSONObject.parseObject(response.body());
                                int statusCode = jsonResult.getIntValue("statusCode");
                                if (BuildConf.ApiStatusCode.SUCCESS == statusCode) {

                                    LogKit.success("  密码重置成功", "重置成功！");
                                    callback.onSuccess(null);

                                } else if (BuildConf.ApiStatusCode.MESSAGE == statusCode) {

                                    callback.onResponseError(jsonResult.getString("message"));
                                    LogKit.success("密码重置失败", "失败原因：" + jsonResult.getString("message"));
                                }

                            } else if (response.code() == 401) {

                                callback.onGrantRefuse();
                                LogKit.success("密码重置失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                            } else {

                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success("密码重置失败", "失败原因：\n" + errorResult.getString("error"));
                                callback.onFailure();

                            }

                        } catch (Exception e) {
                            callback.onFailure();
                            LogKit.exception("密码重置失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure();
                        LogKit.exception("密码重置失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }
}
