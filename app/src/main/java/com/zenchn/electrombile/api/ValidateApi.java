package com.zenchn.electrombile.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.callback.ValidateCallback;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.kit.LogKit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/2/22 16:43
 * 描    述：
 * 修订记录：
 */
public class ValidateApi extends BaseApi {

    private ValidateApi() {
    }

    public static ValidateApi getInstance() {
        return new ValidateApi();
    }

    /**
     * 获取验证码
     *
     * @param mobilePhone
     * @param type
     * @param callback
     */
    public void getAuthCode(final String mobilePhone, String type, final ValidateCallback callback) {

        mApiStore
                .getAuthCode(mobilePhone, type)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            JSONObject jsonResult = JSON.parseObject(response.body());

                            int statusCode = jsonResult.getIntValue("statusCode");
                            if (BuildConf.ApiStatusCode.SUCCESS == statusCode) {

                                callback.onGetAuthCodeSuccess();
                                LogKit.success("验证码获取成功", "发送手机号：" + mobilePhone);

                            } else if (BuildConf.ApiStatusCode.MESSAGE == statusCode) {

                                callback.onRegisterResponseError(jsonResult.getString("message"));
                                LogKit.success("验证码获取失败", "失败原因：" + jsonResult.getString("message"));
                            }

                        } catch (Exception e) {
                            callback.onGetAuthCodeError();
                            LogKit.exception("验证码获取失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onModifyAccountError();
                        LogKit.exception("验证码获取失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

    /**
     * 注册
     *
     * @param mobilePhone
     * @param encryptPassword
     * @param authCode
     * @param callback
     */
    public void register(final String mobilePhone, final String encryptPassword, String authCode, final ValidateCallback callback) {

        mApiStore
                .register(mobilePhone, encryptPassword, authCode)
                .enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {
                            JSONObject jsonResult = JSON.parseObject(response.body());

                            int statusCode = jsonResult.getIntValue("statusCode");
                            if (BuildConf.ApiStatusCode.SUCCESS == statusCode) {

                                UserInfo userInfo = new UserInfo();
                                userInfo.setUsername(mobilePhone);
                                userInfo.setEncryptPassword(encryptPassword);
                                callback.onRegisterSuccess(userInfo);

                                LogKit.success("注册账号成功", "用户信息：" + userInfo.toString());

                            } else if (BuildConf.ApiStatusCode.MESSAGE == statusCode) {

                                callback.onRegisterResponseError(jsonResult.getString("message"));
                                LogKit.success("注册账号失败", "失败原因：" + jsonResult.getString("message"));
                            }

                        } catch (Exception e) {
                            callback.onRegisterError();
                            LogKit.exception("注册账号失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onRegisterError();
                        LogKit.exception("注册账号失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

    /**
     * 账号变更
     *
     * @param accessToken
     * @param newMobilePhone
     * @param encryptPassword
     * @param authCode
     * @param callback
     */
    public void modifyAccount(String accessToken, final String newMobilePhone, final String encryptPassword, String authCode, final ValidateCallback callback) {

        mApiStore
                .modifyAccount(accessToken, newMobilePhone, encryptPassword, authCode)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {

                            if (response.code() == 200) {
                                JSONObject jsonResult = JSON.parseObject(response.body());

                                int statusCode = jsonResult.getIntValue("statusCode");
                                if (BuildConf.ApiStatusCode.SUCCESS == statusCode) {

                                    callback.onModifyAccountSuccess(newMobilePhone);
                                    LogKit.success("修改账号成功", "新用户信息：" + newMobilePhone);

                                } else if (BuildConf.ApiStatusCode.MESSAGE == statusCode) {

                                    callback.onModifyAccountResponseError(jsonResult.getString("message"));
                                    LogKit.success("修改账号失败", "失败原因：" + jsonResult.getString("message"));
                                }
                            } else if (response.code() == 401) {
                                callback.onGrantRefuse();
                                LogKit.success("修改账号失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));
                            } else {
                                callback.onModifyAccountError();
                                LogKit.success(" 修改账号失败", "失败原因：" + JSON.parseObject(response.errorBody().string()).getString("error"));
                            }

                        } catch (Exception e) {
                            callback.onModifyAccountError();
                            LogKit.exception("修改账号失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onModifyAccountError();
                        LogKit.exception("修改账号失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

    /**
     * 验证验证码
     *
     * @param mobilePhone
     * @param authCode
     * @param callback
     */
    public void verifyAuthCode(String mobilePhone, String authCode, ValidateCallback callback) {

        mApiStore
                .verifyAuthCode(mobilePhone, authCode)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

}
