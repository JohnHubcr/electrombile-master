package com.zenchn.electrombile.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.callback.BindCallback;
import com.zenchn.electrombile.api.callback.UnbindCallback;
import com.zenchn.electrombile.entity.VehicleRecordInfo;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.kit.RetrofitKit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/2/22 14:27
 * 描    述：
 * 修订记录：
 */
public class BinderApi extends BaseApi {

    private BinderApi() {
    }

    public static BinderApi getInstance() {
        return new BinderApi();
    }

    /**
     * 获取设备绑定状态
     *
     * @param accessToken
     * @param serialNumber
     * @param callback
     */
    public void getBindStatus(String accessToken, final String serialNumber, final BindCallback callback) {

        mApiStore
                .getBindStatus(accessToken, serialNumber)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            if (response.code() == 200) {

                                JSONObject jsonResult = JSONObject.parseObject(response.body());
                                int statusCode = jsonResult.getIntValue("statusCode");
                                if (BuildConf.ApiStatusCode.SUCCESS == statusCode) {

                                    JSONObject data = jsonResult.getJSONObject("data");
                                    Boolean bindingStatus = data.getBooleanValue("bindingStatus");

                                    if (bindingStatus == null) {
                                        LogKit.success(serialNumber + " 设备绑定失败", "失败原因：未查询到绑定状态");
                                        callback.onBindFailure();
                                    } else if (bindingStatus) {
                                        String mobilePhone = data.getString("mobilePhone");
                                        callback.onEquipmentHasBind(serialNumber, mobilePhone);
                                        LogKit.success(serialNumber + "  设备绑定状态", serialNumber + "已被绑定，绑定手机号:" + mobilePhone);
                                    } else {
                                        callback.onEquipmentWithoutBind(serialNumber);
                                        LogKit.success(serialNumber + "  设备绑定状态", serialNumber + "未被绑定，现在去绑定！");
                                    }

                                } else if (BuildConf.ApiStatusCode.MESSAGE == statusCode) {

                                    callback.onBindFailure(jsonResult.getString("message"));
                                    LogKit.success(serialNumber + " 设备绑定失败", "失败原因：" + jsonResult.getString("message"));
                                }

                            } else if (response.code() == 401) {

                                callback.onGrantRefuse();
                                LogKit.success(serialNumber + "  设备绑定失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                            } else {
                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success(serialNumber + " 设备绑定失败", "失败原因：" + errorResult.getString("error"));
                                callback.onBindFailure();
                            }

                        } catch (Exception e) {
                            callback.onBindFailure();
                            LogKit.exception(serialNumber + "  设备绑定失败", "解析错误，异常信息：\n" + e.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        LogKit.exception(serialNumber + "  设备绑定失败", "访问失败, 异常信息：\n" + t.toString());
                        callback.onBindFailure();
                    }
                });
    }

    /**
     * 绑定设备
     *
     * @param accessToken
     * @param encryptSerialNumber
     * @param callback
     */
    public void bindEquipment(String accessToken, String encryptSerialNumber, BindCallback callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encryptSerialNumber", encryptSerialNumber);
        bindEquipment(accessToken, RetrofitKit.createRequestBody(jsonObject), callback);
    }


    /**
     * 绑定设备
     *
     * @param accessToken
     * @param encryptSerialNumber
     * @param mobilePhone
     * @param authCode
     * @param callback
     */
    public void bindEquipment(String accessToken, String encryptSerialNumber, String mobilePhone, String authCode, BindCallback callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encryptSerialNumber", encryptSerialNumber);
        jsonObject.put("mobilePhone", mobilePhone);
        jsonObject.put("authCode", authCode);
        bindEquipment(accessToken, RetrofitKit.createRequestBody(jsonObject), callback);
    }


    /**
     * 绑定设备
     *
     * @param accessToken
     * @param body
     * @param callback
     */
    public void bindEquipment(String accessToken, final RequestBody body, final BindCallback callback) {

        mApiStore
                .bindEquipment(accessToken, body)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            if (response.code() == 200) {

                                JSONObject jsonResult = JSONObject.parseObject(response.body());
                                int statusCode = jsonResult.getIntValue("statusCode");
                                if (BuildConf.ApiStatusCode.SUCCESS == statusCode) {

                                    LogKit.success("设备绑定(从)成功", "绑定成功！");
                                    callback.onBindSuccess();

                                } else if (BuildConf.ApiStatusCode.MESSAGE == statusCode) {

                                    callback.onBindFailure(jsonResult.getString("message"));
                                    LogKit.success("设备绑定(从)失败", "失败原因：" + jsonResult.getString("message"));
                                }

                            } else if (response.code() == 401) {

                                callback.onGrantRefuse();
                                LogKit.success("设备绑定(从)失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                            } else {
                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success("设备绑定(从)失败", "失败原因：" + errorResult.getString("error"));
                                callback.onBindFailure();
                            }

                        } catch (Exception e) {
                            callback.onBindFailure();
                            LogKit.exception("设备绑定(从)失败", "解析错误，异常信息：\n" + e.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        LogKit.exception("设备绑定(从)失败", "访问失败, 异常信息：\n" + t.toString());
                        callback.onBindFailure();
                    }
                });
    }

    /**
     * 解绑设置
     *
     * @param accessToken
     * @param vehicleRecordInfo
     * @param callback
     */
    public void unBindEquipment(String accessToken, VehicleRecordInfo vehicleRecordInfo, UnbindCallback callback) {
        unBindEquipment(accessToken, vehicleRecordInfo.getSerialNumber(), vehicleRecordInfo.getId(), vehicleRecordInfo.getUseId(), vehicleRecordInfo.getEquipmentId(), callback);
    }

    /**
     * 解绑设置（从账号）
     *
     * @param accessToken
     * @param serialNumber
     * @param id
     * @param userId
     * @param equipmentId
     * @param callback
     */
    private void unBindEquipment(String accessToken, String serialNumber, String id, String userId, String equipmentId, final UnbindCallback callback) {

        mApiStore
                .unBindVehicle(accessToken, serialNumber, id, userId, equipmentId)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            if (response.code() == 200) {

                                JSONObject jsonResult = JSONObject.parseObject(response.body());
                                int statusCode = jsonResult.getIntValue("statusCode");
                                if (BuildConf.ApiStatusCode.SUCCESS == statusCode) {

                                    LogKit.success("设备解绑(从)成功", "绑定成功！");
                                    callback.onUnbindResponse(true);

                                } else {

                                    callback.onUnbindResponse(false);
                                    LogKit.success("设备解绑(从)失败", "失败原因：" + jsonResult.getString("message"));

                                }

                            } else if (response.code() == 401) {

                                callback.onGrantRefuse();
                                LogKit.success("设备解绑(从)失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                            } else {
                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success("设备解绑(从)失败", "失败原因：" + errorResult.getString("error"));
                                callback.onUnbindResponse(null);
                            }

                        } catch (Exception e) {
                            callback.onUnbindResponse(null);
                            LogKit.exception("设备解绑(从)失败", "解析错误，异常信息：\n" + e.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        LogKit.exception("设备解绑(从)失败", "访问失败, 异常信息：\n" + t.toString());
                        callback.onUnbindResponse(null);
                    }
                });
    }

}
