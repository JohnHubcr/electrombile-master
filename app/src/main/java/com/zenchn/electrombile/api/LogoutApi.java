package com.zenchn.electrombile.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.callback.LogoutCallback;
import com.zenchn.electrombile.kit.LogKit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/2/26 16:22
 * 描    述：
 * 修订记录：
 */
public class LogoutApi extends BaseApi {

    private LogoutApi() {
    }

    public static LogoutApi getInstance() {
        return new LogoutApi();
    }


    /**
     * 注销
     *
     * @param loginName
     * @param accessToken
     * @param callback
     */
    public void Logout(final String loginName, String accessToken, final LogoutCallback callback) {

        mApiStore
                .logout(accessToken)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            if (response.errorBody() == null) {

                                JSONObject jsonResult = JSONObject.parseObject(response.body());
                                int statusCode = jsonResult.getIntValue("statusCode");
                                if (BuildConf.ApiStatusCode.SUCCESS == statusCode) {

                                    LogKit.success(loginName + "  注销成功", "注销成功！");
                                    callback.onLogoutSuccess();

                                } else if (BuildConf.ApiStatusCode.MESSAGE == statusCode) {

                                    callback.onLogoutFailure();
                                    LogKit.success(loginName + "  注销失败", "失败原因：" + jsonResult.getString("message"));
                                }

                            } else {

                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success(loginName + "  注销失败", "失败原因：\n" + errorResult.getString("error"));
                                callback.onLogoutFailure();

                            }

                        } catch (Exception e) {
                            callback.onLogoutFailure();
                            LogKit.exception(loginName + "  注销失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onLogoutFailure();
                        LogKit.exception(loginName + "  注销失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

}
