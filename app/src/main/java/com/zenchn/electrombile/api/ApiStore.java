package com.zenchn.electrombile.api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 作    者：wangr on 2017/2/21 14:33
 * 描    述：
 * 修订记录：
 */
interface ApiStore {

    /**
     * 授权接口
     * --------------------------------------------------------------------
     */

    // 获取令牌
    @FormUrlEncoded
    @POST("/ddc_api/rest/api/token/login")
    Call<String> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/ddc_api/rest/api/token/login")
    Call<String> getToken(@Field("client_id") String client_id,
                          @Field("client_secret") String client_secret,
                          @Field("grant_type") String grant_type,
                          @Field("username") String username,
                          @Field("password") String password,
                          @Field("device_id") String device_id,
                          @Field("device_name") String device_name,
                          @Field("device_type") String device_type);

    // 刷新令牌
    @FormUrlEncoded
    @POST("/ddc_api/rest/api/token/login")
    Call<String> refreshToken(@Field("client_id") String client_id,
                              @Field("client_secret") String client_secret,
                              @Field("grant_type") String grant_type,
                              @Field("refresh_token") String refresh_token);

    // 注销令牌
    @GET("/ddc_api/rest/api/token/logout/{accessToken}")
    Call<String> logout(@Path("accessToken") String accessToken);

    // 更新接口
    @GET("/ddc_api/rest/api/getApkVersion")
    Call<String> getApkVersion(@Query("systemType") String systemType);

    /**
     * 通用接口
     * --------------------------------------------------------------------
     */

    // 下载文件
    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    // 上传单个文件
    @Multipart
    @POST("/ddc_api/rest/api/file/upload/{accessToken}")
    Call<String> uploadSingleFile(@Path("accessToken") String accessToken,
                                  @Part("fileSort") RequestBody fileSort,
                                  @Part MultipartBody.Part file);

    // 上传多个文件
    @Multipart
    @POST("/ddc_api/rest/api/file/upload/{accessToken}")
    Call<String> uploadMultipleFiles(@Path("accessToken") String accessToken,
                                     @Part("fileSort") RequestBody fileSort,
                                     @Part MultipartBody.Part... files);

    // 删除单个文件
    @FormUrlEncoded
    @DELETE("/ddc_api/rest/api/del/{fileId}/{accessToken}")
    Call<String> deleteSingleFile(@Path("accessToken") String accessToken,
                                  @Field("fileId") String fileId);

    /**
     * 用户接口
     * --------------------------------------------------------------------
     */

    //手机号注册
    @FormUrlEncoded
    @POST("/ddc_api/rest/api/user/register")
    Call<String> register(@Field("mobilePhone") String mobilePhone,
                          @Field("password") String password,
                          @Field("authCode") String authCode);

    // 获取验证码
    @GET("/ddc_api/rest/api/user/getAuthCode")
    Call<String> getAuthCode(@Query("mobilePhone") String mobilePhone,
                             @Query("codeType") String codeType);

    // 修改密码（根据原密码）
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("/ddc_api/rest/api/user/revPwd/{accessToken}")
    Call<String> revPwd(@Path("accessToken") String accessToken,
                        @Body RequestBody body);

    // 重置密码（根据验证码）
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("/ddc_api/rest/api/user/revPwdByAuthCode")
    Call<String> resetPwd(@Body RequestBody body);

    // 更新紧急联系人
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("/ddc_api/rest/api/user/updateOtherPhone/{accessToken}")
    Call<String> updateUrgentContact(@Path("accessToken") String accessToken,
                                     @Body RequestBody body);

    // 更换手机号
    @FormUrlEncoded
    @PUT("/ddc_api/rest/api/user/accountReplace/{accessToken}")
    Call<String> modifyAccount(@Path("accessToken") String accessToken,
                               @Field("newMobilePhone") String newMobilePhone,
                               @Field("oldPassword") String oldPassword,
                               @Field("authCode") String authCode);

    // 检验验证码
    @GET("/ddc_api/rest/api/user/verifyAuthCode")
    Call<String> verifyAuthCode(@Query("mobilePhone") String mobilePhone,
                                @Query("authCode") String authCode);

    @POST("/ddc_api/rest/api/user/accountFeedBack/{accessToken}")
    Call<String> accountFeedBack(@Path("accessToken") String accessToken,
                                 @Body RequestBody body);

    /**
     * 数据接口
     * --------------------------------------------------------------------
     */

    // 获取用户车辆列表信息
    @GET("/ddc_api/rest/api/user/getVehicleList/{accessToken}")
    Call<String> getVehicleList(@Path("accessToken") String accessToken);


    // 获取用户信息（仅首次登录不许要传serialNumber）
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/ddc_api/rest/api/user/getUser/{accessToken}")
    Call<String> getUser(@Path("accessToken") String accessToken,
                         @Body RequestBody body);

    //查询设备绑定
    @GET("/ddc_api/rest/api/vehicle/getEquipmentWhetherBound/{accessToken}")
    Call<String> getBindStatus(@Path("accessToken") String accessToken,
                               @Query("encryptSerialNumber") String encryptSerialNumber);

    // 设备绑定（绑定二合一）
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/ddc_api/rest/api/vehicle/equipmentBound/{accessToken}")
    Call<String> bindEquipment(@Path("accessToken") String accessToken,
                               @Body RequestBody body);

    // 车辆绑定
    @FormUrlEncoded
    @POST("/ddc_api/rest/api/vehicle/vehicleBound/{accessToken}")
    Call<String> bindEquipment(@Path("accessToken") String accessToken,
                               @Field("number") String number,
                               @Field("motorNo") String motorNo,
                               @Field("serialNumber") String serialNumber);

    // 车辆解绑（目前只支持从账号解绑）
    @FormUrlEncoded
    @POST("/ddc_api/rest/api/vehicle/equipmentUnbundling/{accessToken}")
    Call<String> unBindVehicle(@Path("accessToken") String accessToken,
                               @Field("serialNumber") String serialNumber,
                               @Field("id") String id,
                               @Field("userId") String userId,
                               @Field("equipmentId") String equipmentId);

    // 获取最近一次定位信息
    @GET("/ddc_api/rest/api/vehicle/getLastLocation/{accessToken}")
    Call<String> getLastLocation(@Path("accessToken") String accessToken,
                                 @Query("serialNumber") String serialNumber);

    // 获取最近一次轨迹信息
    @GET("/ddc_api/rest/api/vehicle/getLocationList/{accessToken}")
    Call<String> getLocationList(@Path("accessToken") String accessToken,
                                 @Query("serialNumber") String serialNumber,
                                 @Query("equmodel") String equmodel,
                                 @Query("beginTime") String beginTime,
                                 @Query("endTime") String endTime);

    // 获取车辆报警信息
    @GET("/ddc_api/rest/api/vehicle/getAlertList/{accessToken}")
    Call<String> getAlarmMessageList(@Path("accessToken") String accessToken,
                                     @Query("serialNumber") String serialNumber,
                                     @Query("pageNo") String pageNo,
                                     @Query("pageSize") String pageSize,
                                     @Query("type") String msgType);


    //设置常用车辆
    @PUT("/ddc_api/rest/api/vehicle/setCommonVehicle/{accessToken}")
    Call<String> setCommonVehicle(@Path("accessToken") String accessToken,
                                  @Body RequestBody body);

    // 获取车辆档案信息
    @GET("/ddc_api/rest/api/vehicle/getVehicleRecord/{accessToken}")
    Call<String> getVehicleRecord(@Path("accessToken") String accessToken,
                                  @Query("serialNumber") String serialNumber);

    // 获取车辆自检信息
    @GET("/ddc_api/rest/api/vehicle/getVehicleStatus/{accessToken}")
    Call<String> getVehicleStatus(@Path("accessToken") String accessToken,
                                  @Query("serialNumber") String serialNumber,
                                  @Query("equmodel") String equModel);

    // 获取售后服务站点信息
    @GET("/ddc_api/rest/api/vehicle/getServiceStationList/{accessToken}")
    Call<String> getServiceStationList(@Path("accessToken") String accessToken,
                                       @Query("city") String city,
                                       @Query("district") String district,
                                       @Query("address") String address,
                                       @Query("pageNo") String pageNo,
                                       @Query("pageSize") String pageSize,
                                       @Query("serviceType") String serviceType);

    /**
     * 保险业务接口
     * --------------------------------------------------------------------
     */

    // 获取产品信息
    @GET("/ddc_api/rest/api/insurance/getProductList/{accessToken}")
    Call<String> getProductInfo(@Path("accessToken") String accessToken);

    // 创建支付订单
    @FormUrlEncoded
    @POST("/ddc_api/rest/api/insurance/createIndent/{accessToken}")
    Call<String> createIndent(@Path("accessToken") String accessToken,
                              @Field("productCombinationId") String productCombinationId,
                              @Field("serialNumber") String serialNumber);

    // 查询订单支付状态
    @GET("/ddc_api/rest/api/insurance/getPayStatus/{accessToken}")
    Call<String> getPayStatus(@Path("accessToken") String accessToken,
                              @Query("outTradeNo") String outTradeNo);

    // 激活车辆保险服务
    @FormUrlEncoded
    @POST("/ddc_api/rest/api/insurance/activationInsurance/{accessToken}")
    Call<String> submitInsuranceActivate(@Path("accessToken") String accessToken,
                                         @Field("serialNumber") String serialNumber,
                                         @Field("name") String name,
                                         @Field("idcard") String idCard,
                                         @Field("contactWay") String contactWay,
                                         @Field("number") String number,
                                         @Field("motorNo") String motorNo);

    // 完善车辆理赔信息
    @POST("/ddc_api/rest/api/insurance/perfectClaims/{accessToken}")
    Call<String> submitInsuranceClaim(@Path("accessToken") String accessToken,
                                      @Field("serialNumber") String serialNumber,
                                      @Field("payTime") String payTime,
                                      @Field("costPrice") String costPrice,
                                      @Field("address") String address,
                                      @Body RequestBody body);

    // 完善车辆理赔信息
    @POST("/ddc_api/rest/api/insurance/perfectClaims/{accessToken}")
    Call<String> submitInsuranceClaim(@Path("accessToken") String accessToken,
                                      @Body RequestBody body);

    // 查询用户车辆理赔信息
    @GET("/ddc_api/rest/api/insurance/getUserClaims/{accessToken}")
    Call<String> getUserClaims(@Path("accessToken") String accessToken,
                               @Query("serialNumber") String serialNumber);

    // 查询用户车辆保单信息
    @GET("/ddc_api/rest/api/insurance/getUserVehiclePolicy/{accessToken}")
    Call<String> getUserVehiclePolicy(@Path("accessToken") String accessToken,
                                      @Query("serialNumber") String serialNumber);

    /**
     * 外部接口
     * --------------------------------------------------------------------
     */

    // 查询天气信息
    @GET
    Call<String> getWeatherInfoByCity(@Url String url,
                                      @Query("cityname") String cityName,
                                      @Query("key") String key,
                                      @Query("dtype") String dType);


    // 获取波导接口的token
    @FormUrlEncoded
    @POST
    Call<String> getGpsLinkToken(@Url String url,
                                 @Field("username") String username,
                                 @Field("password") String password,
                                 @Field("grant_type") String grant_type,
                                 @Field("scope") String scope);

    //发送控车指令
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Call<String> postTcpCmd(@Url String url,
                            @Header("Authorization") String authorization,
                            @Query("cid") String cid,
                            @Body RequestBody body);

    //查询控车指令
    @GET
    Call<String> queryTcpCmd(@Url String url,
                             @Header("Authorization") String authorization,
                             @Query("id") String id);

}

