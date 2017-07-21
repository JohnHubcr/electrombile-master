package com.zenchn.electrombile.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.api.callback.LoginCallback;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.ClientInfo;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.kit.RetrofitKit;
import com.zenchn.mlibrary.utils.DateUtils;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/2/21 16:23
 * 描    述：
 * 修订记录：
 */
public class LoginApi extends BaseApi {

    private LoginApi() {
    }

    public static LoginApi getInstance() {
        return new LoginApi();
    }

    /**
     * 获取token
     *
     * @param userInfo
     * @param callback
     */
    public void getToken(final UserInfo userInfo, final LoginCallback callback) {

        mApiStore
                .getToken(userInfo.getClientId(),
                        userInfo.getClientSecret(),
                        userInfo.getGrantType(),
                        userInfo.getUsername(),
                        userInfo.getEncryptPassword(),
                        userInfo.getDeviceId(),
                        userInfo.getDeviceName(),
                        userInfo.getDeviceType())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            if (response.errorBody() == null) {

                                JSONObject jsonResult = JSON.parseObject(response.body());
                                LoginInfo loginInfo = new LoginInfo();

                                loginInfo.setLoginName(userInfo.getUsername());
                                loginInfo.setLoginTime(DateUtils.getCurrentTime());
                                loginInfo.setAccessToken(jsonResult.getString("access_token"));
                                loginInfo.setRefreshToken(jsonResult.getString("refresh_token"));
                                loginInfo.setExpiresIn(jsonResult.getLongValue("expires_in"));

                                LogKit.success(userInfo.getUsername() + "  令牌获取成功", "令牌信息：\n" + loginInfo.toString());
                                callback.onLoginSuccess(userInfo, loginInfo);

                            } else {

                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success(userInfo.getUsername() + "  令牌获取失败", "失败原因：\n" + errorResult.getString("error"));
                                callback.onLoginResponseError(errorResult.getString("error_description"));

                            }

                        } catch (Exception e) {
                            callback.onLoginFailure();
                            LogKit.exception(userInfo.getUsername() + "  令牌获取失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onLoginFailure();
                        LogKit.exception(userInfo.getUsername() + "  令牌获取失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

    /**
     * 刷新token
     *
     * @param userInfo
     * @param loginInfo
     * @param callback
     */
    public void refreshToken(final UserInfo userInfo, final LoginInfo loginInfo, final ApiCallback<LoginInfo> callback) {

        mApiStore
                .refreshToken(userInfo.getClientId(),
                        userInfo.getClientSecret(),
                        userInfo.getGrantType(),
                        loginInfo.getRefreshToken())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            if (response.errorBody() == null) {

                                JSONObject jsonResult = JSON.parseObject(response.body());

                                loginInfo.setRefreshToken(jsonResult.getString("refresh_token"));
                                loginInfo.setExpiresIn(jsonResult.getLongValue("expires_in"));

                                LogKit.success(userInfo.getUsername() + "  令牌刷新成功", "令牌信息：\n" + loginInfo.toString());
                                callback.onSuccess(loginInfo);

                            } else {

                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success(userInfo.getUsername() + "  令牌刷新失败", "失败原因：\n" + errorResult.getString("error_description"));
                                callback.onResponseError(errorResult.getString("error_description"));

                            }

                        } catch (Exception e) {
                            callback.onFailure();
                            LogKit.exception(userInfo.getUsername() + "  令牌刷新失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure();
                        LogKit.exception(userInfo.getUsername() + "  令牌刷新失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

    /**
     * 获取登录信息
     *
     * @param clientInfo
     * @param loginInfo
     * @param callback
     */
    public void getUser(ClientInfo clientInfo, LoginInfo loginInfo, ApiCallback<LoginInfo> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serialNumber", clientInfo.getSerialNumber());
        jsonObject.put("systemType", clientInfo.getSystemType());
        jsonObject.put("appSpecification", clientInfo.getAppSpecification());
        jsonObject.put("appVersion", clientInfo.getAppVersion());
        jsonObject.put("vehicleIMEI", clientInfo.getVehicleIMEI());
        jsonObject.put("token", clientInfo.getToken());
        jsonObject.put("quitTime", clientInfo.getQuitTime());
        getUser(loginInfo, RetrofitKit.createRequestBody(jsonObject), callback);
    }

    /**
     * 获取登录信息
     *
     * @param loginInfo
     * @param body
     * @param callback
     */
    private void getUser(final LoginInfo loginInfo, final RequestBody body, final ApiCallback<LoginInfo> callback) {

        mApiStore
                .getUser(loginInfo.getAccessToken(), body)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {

                            if (response.code() == 200) {

                                JSONObject jsonResult = JSON.parseObject(response.body());
                                JSONObject data = jsonResult.getJSONObject("data");

                                loginInfo.setSerialNumber(data.getString("serialNumber"));
                                String authorizationPsw = data.getString("authorizationPsw");
                                loginInfo.setAuthorizationPsw(authorizationPsw == null ? "123456" : authorizationPsw);
                                loginInfo.setEquModel(data.getIntValue("equmodel"));//设备版本标识
                                loginInfo.setEquModel(4);//设备版本标识

                                loginInfo.setStatusDesc(data.getString("statusDesc"));
                                loginInfo.setPerfectStatus(data.getIntValue("perfectStatus"));//资料是否完善
                                loginInfo.setProtectionStatus(data.getIntValue("protectionStatus"));//保险状态

                                loginInfo.setRegisterDate(data.getLongValue("registerDate"));//用户注册日期
                                loginInfo.setOtherPhone(data.getString("otherPhone"));//紧急联系人
                                loginInfo.setIsMainAccount(data.getBooleanValue("whetherMain"));//是否主账号

                                LogKit.success(loginInfo.getLoginName() + "  登录成功", "登录信息：\n" + loginInfo.toString());
                                callback.onSuccess(loginInfo);

                            } else if (response.code() == 401) {

                                callback.onGrantRefuse();
                                LogKit.success(loginInfo.getLoginName() + "  登录失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                            } else {
                                callback.onFailure();
                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success(loginInfo.getLoginName() + "  登录失败", "失败原因：\n" + errorResult.getString("error"));
                            }
                        } catch (Exception e) {
                            callback.onFailure();
                            LogKit.exception(loginInfo.getLoginName() + "  登录失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure();
                        LogKit.exception(loginInfo.getLoginName() + "  登录失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

}
