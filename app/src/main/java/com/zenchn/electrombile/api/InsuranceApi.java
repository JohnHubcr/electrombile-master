package com.zenchn.electrombile.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.callback.AliPaySignCallback;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.api.callback.SubmitCallback;
import com.zenchn.electrombile.entity.InsuranceActivateInfo;
import com.zenchn.electrombile.entity.InsuranceClaimInfo;
import com.zenchn.electrombile.entity.InsurancePolicyInfo;
import com.zenchn.electrombile.entity.InsuranceProductInfo;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.ui.activity.InsuranceClaimActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/3/7 15:08
 * 描    述：保险模块的接口
 * 修订记录：
 */
public class InsuranceApi extends BaseApi {


    private InsuranceApi() {
    }

    public static InsuranceApi getInstance() {
        return new InsuranceApi();
    }

    /**
     * 获取产品列表
     *
     * @param accessToken
     * @param callback
     */
    public void getProductInfo(String accessToken, final ApiCallback<List<InsuranceProductInfo>> callback) {

        mApiStore
                .getProductInfo(accessToken)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 try {

                                     if (response.code() == 200) {

                                         JSONObject data = JSON.parseObject(response.body()).getJSONObject("data");
                                         JSONArray list = data.getJSONArray("list");

                                         List<InsuranceProductInfo> mData = null;

                                         if (list != null && !list.isEmpty()) {

                                             mData = new ArrayList<>();
                                             InsuranceProductInfo insuranceProductInfo = null;

                                             int size = list.size();
                                             for (int i = 0; i < size; i++) {
                                                 JSONObject obj = (JSONObject) list.get(i);
                                                 insuranceProductInfo = new InsuranceProductInfo();
                                                 insuranceProductInfo.setId(obj.getString("productCombinationId"));
                                                 insuranceProductInfo.setName(obj.getString("combinationName"));
                                                 insuranceProductInfo.setRemark(obj.getString("productRemark"));
                                                 insuranceProductInfo.setPrice(obj.getString("price"));
                                                 insuranceProductInfo.setOldPrice(obj.getString("oldPrice"));
                                                 insuranceProductInfo.setValidity(obj.getString("validity"));

                                                 mData.add(insuranceProductInfo);
                                             }
                                         }
                                         LogKit.success("保险产品列表信息获取成功", "列表信息：\n" + (mData != null ? mData.toString() : "列表为空！"));
                                         callback.onSuccess(mData);
                                     } else if (response.code() == 401) {

                                         callback.onGrantRefuse();
                                         LogKit.success("保险产品列表信息获取失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                                     } else {
                                         JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                         LogKit.success("保险产品列表信息获取失败", "失败原因：\n" + errorResult.getString("error"));
                                         callback.onFailure();
                                     }

                                 } catch (Exception e) {
                                     callback.onFailure();
                                     LogKit.exception("保险产品列表信息获取失败", "解析错误，异常信息：\n" + e.toString());
                                 }
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 callback.onFailure();
                                 LogKit.exception("保险产品列表信息获取失败", "访问失败, 异常信息：\n" + t.toString());
                             }
                         }

                );

    }


    /**
     * 创建支付宝支付订单
     *
     * @param accessToken
     * @param productCombinationId
     * @param serialNumber
     * @param callback
     */
    public void createIndent(String accessToken, String productCombinationId, final String serialNumber, final AliPaySignCallback callback) {

        mApiStore
                .createIndent(accessToken, productCombinationId, serialNumber)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {

                            if (response.code() == 200) {

                                JSONObject data = JSON.parseObject(response.body()).getJSONObject("data");
                                String sign = data.getString("par");
                                String outTradeNo = data.getString("outTradeNo");

                                LogKit.success(serialNumber + "  支付宝签名订单获取成功", "订单信息：\n" + sign + "\n订单唯一标识：\n" + outTradeNo);
                                callback.onSignSuccess(sign, outTradeNo);

                            } else if (response.code() == 401) {

                                callback.onGrantRefuse();
                                LogKit.success(serialNumber + "  支付宝签名订单获取失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                            } else {
                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success(serialNumber + "  支付宝签名订单获取失败", "失败原因：\n" + errorResult.getString("error"));
                                callback.onSignFailure();
                            }

                        } catch (Exception e) {
                            callback.onSignFailure();
                            LogKit.exception(serialNumber + "  支付宝签名订单获取失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onSignFailure();
                        LogKit.exception(serialNumber + "  支付宝签名订单获取失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

    /**
     * 查询支付结果
     *
     * @param accessToken
     * @param outTradeNo
     * @param callback
     */
    public void getPayStatus(String accessToken, String outTradeNo, final ApiCallback<Integer> callback) {

        mApiStore
                .getPayStatus(accessToken, outTradeNo)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {

                            if (response.code() == 200) {

                                JSONObject data = JSON.parseObject(response.body()).getJSONObject("data");
                                int payStatus = data.getIntValue("payStatus");

                                LogKit.success("获取支付宝订单支付状态成功", "状态信息：" + payStatus);
                                callback.onSuccess(payStatus);

                            } else if (response.code() == 401) {

                                callback.onGrantRefuse();
                                LogKit.success("获取支付宝订单支付状态失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                            } else {
                                JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                LogKit.success("获取支付宝订单支付状态失败", "失败原因：\n" + errorResult.getString("error"));
                                callback.onFailure();
                            }

                        } catch (Exception e) {
                            callback.onFailure();
                            LogKit.exception("获取支付宝订单支付状态失败", "解析错误，异常信息：\n" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure();
                        LogKit.exception("获取支付宝订单支付状态失败", "访问失败, 异常信息：\n" + t.toString());
                    }
                });
    }

    /**
     * 保险激活
     *
     * @param accessToken
     * @param serialNumber
     * @param insuranceActivateInfo
     * @param callback
     */
    public void submitInsuranceActivate(String accessToken, String serialNumber, InsuranceActivateInfo insuranceActivateInfo, SubmitCallback callback) {
        submitInsuranceActivate(accessToken,
                serialNumber,
                insuranceActivateInfo.getName(),
                insuranceActivateInfo.getIdCard(),
                insuranceActivateInfo.getMobilePhone(),
                insuranceActivateInfo.getFrameNumber(),
                insuranceActivateInfo.getMachineNumber(),
                callback);
    }


    /**
     * 保险激活
     *
     * @param accessToken
     * @param serialNumber
     * @param name
     * @param idCard
     * @param contactWay
     * @param number
     * @param motorNo
     * @param callback
     */
    private void submitInsuranceActivate(String accessToken, String serialNumber, String name, String idCard, String contactWay, String number, String motorNo, final SubmitCallback callback) {

        mApiStore
                .submitInsuranceActivate(accessToken, serialNumber, name, idCard, contactWay, number, motorNo)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 try {

                                     if (response.code() == 200) {

                                         JSONObject jsonResult = JSONObject.parseObject(response.body());
                                         int statusCode = jsonResult.getIntValue("statusCode");
                                         boolean data = BuildConf.ApiStatusCode.SUCCESS == statusCode;
                                         LogKit.success("保险激活成功", "是否激活：" + data);
                                         callback.onSuccess(data);

                                     } else if (response.code() == 401) {

                                         callback.onGrantRefuse();
                                         LogKit.success("保险激活失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                                     } else {
                                         JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                         LogKit.success("保险激活失败", "失败原因：\n" + errorResult.getString("error"));
                                         callback.onResponseError(errorResult.getString("error"));
                                     }

                                 } catch (Exception e) {
                                     callback.onFailure();
                                     LogKit.exception("保险激活失败", "解析错误，异常信息：\n" + e.toString());
                                 }
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 callback.onFailure();
                                 LogKit.exception("保险激活失败", "访问失败, 异常信息：\n" + t.toString());
                             }
                         }

                );

    }


    /**
     * 提交理赔信息
     *
     * @param accessToken
     * @param serialNumber
     * @param insuranceClaimInfo
     * @param fileParams
     * @param callback
     */
    public void submitInsuranceClaim(String accessToken, String serialNumber, InsuranceClaimInfo insuranceClaimInfo, Map<String, File> fileParams, SubmitCallback callback) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("serialNumber", serialNumber)
                .addFormDataPart("payTime", insuranceClaimInfo.getPayTime())
                .addFormDataPart("address", insuranceClaimInfo.getArea() + "," + insuranceClaimInfo.getAddress())
                .addFormDataPart("costPrice", String.valueOf(insuranceClaimInfo.getCostPrice()));

        if (fileParams != null) {
            if (fileParams.containsKey(InsuranceClaimActivity.MapKey.idCardFront))
                builder.addFormDataPart("idcardFrontFile", fileParams.get(InsuranceClaimActivity.MapKey.idCardFront).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileParams.get(InsuranceClaimActivity.MapKey.idCardFront)));
            if (fileParams.containsKey(InsuranceClaimActivity.MapKey.idCardBack))
                builder.addFormDataPart("idcardBackFile", fileParams.get(InsuranceClaimActivity.MapKey.idCardBack).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileParams.get(InsuranceClaimActivity.MapKey.idCardBack)));
            if (fileParams.containsKey(InsuranceClaimActivity.MapKey.insuranceSign))
                builder.addFormDataPart("qualificationFile", fileParams.get(InsuranceClaimActivity.MapKey.insuranceSign).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileParams.get(InsuranceClaimActivity.MapKey.insuranceSign)));
            if (fileParams.containsKey(InsuranceClaimActivity.MapKey.vehicleCertificate))
                builder.addFormDataPart("invoiceFile", fileParams.get(InsuranceClaimActivity.MapKey.vehicleCertificate).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileParams.get(InsuranceClaimActivity.MapKey.vehicleCertificate)));
            if (fileParams.containsKey(InsuranceClaimActivity.MapKey.vehicleInvoice))
                builder.addFormDataPart("insuranceSignFile", fileParams.get(InsuranceClaimActivity.MapKey.vehicleInvoice).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileParams.get(InsuranceClaimActivity.MapKey.vehicleInvoice)));
            if (fileParams.containsKey(InsuranceClaimActivity.MapKey.vehiclePhoto))
                builder.addFormDataPart("carPhotoFile", fileParams.get(InsuranceClaimActivity.MapKey.vehiclePhoto).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileParams.get(InsuranceClaimActivity.MapKey.vehiclePhoto)));

        }
        submitInsuranceClaim(accessToken, builder.build(), callback);
    }

    /**
     * 理赔信息提交
     *
     * @param accessToken
     * @param body
     * @param callback
     */
    private void submitInsuranceClaim(String accessToken, RequestBody body, final SubmitCallback callback) {

        getLongTimeoutRetrofit(60000L)
                .submitInsuranceClaim(accessToken, body)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 try {

                                     if (response.code() == 200) {

                                         JSONObject jsonResult = JSONObject.parseObject(response.body());
                                         int statusCode = jsonResult.getIntValue("statusCode");
                                         boolean data = BuildConf.ApiStatusCode.SUCCESS == statusCode;
                                         LogKit.success("理赔信息提交成功", "是否激活：" + data);
                                         callback.onSuccess(data);

                                     } else if (response.code() == 401) {

                                         callback.onGrantRefuse();
                                         LogKit.success("理赔信息提交失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                                     } else {
                                         JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                         LogKit.success("理赔信息提交失败", "失败原因：\n" + errorResult.getString("error"));
                                         callback.onResponseError(errorResult.getString("error"));
                                     }

                                 } catch (Exception e) {
                                     callback.onFailure();
                                     LogKit.exception("理赔信息提交失败", "解析错误，异常信息：\n" + e.toString());
                                 }
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 callback.onFailure();
                                 LogKit.exception("理赔信息提交失败", "访问失败, 异常信息：\n" + t.toString());
                             }
                         }

                );

    }


    /**
     * 查询用户车辆理赔信息
     *
     * @param accessToken
     * @param serialNumber
     * @param callback
     */
    public void getUserClaims(String accessToken, String serialNumber, final ApiCallback<InsuranceClaimInfo> callback) {

        mApiStore
                .getUserClaims(accessToken, serialNumber)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 try {

                                     if (response.code() == 200) {

                                         JSONObject data = JSONObject.parseObject(response.body()).getJSONObject("data");

                                         InsuranceClaimInfo insuranceClaimInfo = new InsuranceClaimInfo();

                                         insuranceClaimInfo.setPayTime(data.getString("payTime"));
                                         insuranceClaimInfo.setCostPrice(data.getFloatValue("costPrice"));

                                         String address = data.getString("address");
                                         String[] split = address.split(",");
                                         if (split.length >= 3
                                                 && (split[0].contains("省") || split[0].contains("自治区"))
                                                 && (split[1].contains("市") || split[1].contains("自治州"))
                                                 && (split[2].contains("区") || split[2].contains("市") || split[2].contains("县") || split[2].contains("旗") || split[2].contains("其他"))) {

                                             StringBuffer sb = new StringBuffer();
                                             for (int i = 0; i < 3; i++) {
                                                 sb.append(split[i]).append(i == 2 ? "" : ",");
                                             }
                                             insuranceClaimInfo.setArea(sb.toString());

                                             sb = new StringBuffer();
                                             for (int i = 3; i < split.length; i++) {
                                                 sb.append(split[i]).append(i == split.length - 1 ? "" : ",");
                                             }
                                             insuranceClaimInfo.setAddress(sb.toString());
                                         } else {
                                             insuranceClaimInfo.setAddress(address);
                                         }

                                         insuranceClaimInfo.setIdCardFront(data.getString("idcardFront"));
                                         insuranceClaimInfo.setIdCardBack(data.getString("idcardBack"));
                                         insuranceClaimInfo.setQualification(data.getString("qualification"));
                                         insuranceClaimInfo.setVehicleInvoice(data.getString("invoice"));
                                         insuranceClaimInfo.setVehiclePhoto(data.getString("carPhoto"));
                                         insuranceClaimInfo.setInsuranceSign(data.getString("insuranceSign"));

                                         LogKit.success("车辆理赔信息获取成功", "理赔信息：" + insuranceClaimInfo.toString());
                                         callback.onSuccess(insuranceClaimInfo);

                                     } else if (response.code() == 401) {

                                         callback.onGrantRefuse();
                                         LogKit.success("车辆理赔信息获取失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                                     } else {
                                         JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                         LogKit.success("车辆理赔信息获取失败", "失败原因：\n" + errorResult.getString("error"));
                                         callback.onResponseError(errorResult.getString("error"));
                                     }

                                 } catch (
                                         Exception e
                                         )

                                 {
                                     callback.onFailure();
                                     LogKit.exception("车辆理赔信息获取失败", "解析错误，异常信息：\n" + e.toString());
                                 }
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 callback.onFailure();
                                 LogKit.exception("车辆理赔信息获取失败", "访问失败, 异常信息：\n" + t.toString());
                             }
                         }

                );

    }


    /**
     * 查询用户车辆保单信息
     *
     * @param accessToken
     * @param serialNumber
     * @param callback
     */
    public void getUserVehiclePolicy(String accessToken, String serialNumber, final ApiCallback<InsurancePolicyInfo> callback) {

        mApiStore
                .getUserVehiclePolicy(accessToken, serialNumber)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 try {

                                     if (response.code() == 200) {

                                         JSONObject data = JSONObject.parseObject(response.body()).getJSONObject("data");

                                         InsurancePolicyInfo insurancePolicyInfo = new InsurancePolicyInfo();

                                         insurancePolicyInfo.setContent(data.getString("name"));
                                         insurancePolicyInfo.setCategory(data.getString("category"));
                                         insurancePolicyInfo.setRemark(data.getString("remark"));
                                         insurancePolicyInfo.setPhone(data.getString("phone"));
                                         insurancePolicyInfo.setProtectionStartTime(data.getString("protectionStartTime"));
                                         insurancePolicyInfo.setProtectionEndTime(data.getString("protectionEndTime"));

                                         LogKit.success("车辆保单信息获取成功", "理赔信息：" + insurancePolicyInfo.toString());
                                         callback.onSuccess(insurancePolicyInfo);

                                     } else if (response.code() == 401) {

                                         callback.onGrantRefuse();
                                         LogKit.success("车辆保单信息获取失败", "失败原因：\n" + JSON.parseObject(response.errorBody().string()).getString("error"));

                                     } else {
                                         JSONObject errorResult = JSON.parseObject(response.errorBody().string());
                                         LogKit.success("车辆保单信息获取失败", "失败原因：\n" + errorResult.getString("error"));
                                         callback.onResponseError(errorResult.getString("error"));
                                     }

                                 } catch (Exception e) {
                                     callback.onFailure();
                                     LogKit.exception("车辆保单信息获取失败", "解析错误，异常信息：\n" + e.toString());
                                 }
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 callback.onFailure();
                                 LogKit.exception("车辆保单信息获取失败", "访问失败, 异常信息：\n" + t.toString());
                             }
                         }

                );

    }
}
