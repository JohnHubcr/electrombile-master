package com.zenchn.electrombile.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.callback.WeatherCallback;
import com.zenchn.electrombile.entity.WeatherInfo;
import com.zenchn.electrombile.entity.model.WeatherEnum;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.mlibrary.log.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/2/24 22:08
 * 描    述：
 * 修订记录：
 */
public class WeatherApi extends BaseApi {

    private WeatherApi() {
    }

    public static WeatherApi getInstance() {
        return new WeatherApi();
    }

    public void getWeatherInfoByCity(final String province, final String city, final WeatherCallback callback) {

        mApiStore
                .getWeatherInfoByCity(
                        BuildConf.JuheWeather.juheWeatherSite,
                        city,
                        BuildConf.JuheWeather.juheWeatherKey,
                        BuildConf.JuheWeather.juheWeatherDataType)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {

                                 try {
                                     JSONObject jsonResult = JSON.parseObject(response.body());
                                     String reason = jsonResult.getString("reason");
                                     if (reason.equals("successed!")) {
                                         JSONObject results = jsonResult.getJSONObject("result");
                                         JSONObject data = results.getJSONObject("data");

                                         WeatherInfo weather = new WeatherInfo();

                                         JSONObject realtime = data.getJSONObject("realtime");
                                         JSONObject weatherInfo = realtime.getJSONObject("weather");

                                         weather.setProvince(province);
                                         weather.setCity(city);
                                         weather.setWeather(WeatherEnum.classifyWeather(weatherInfo.getString("info")));
                                         weather.setTemperature(weatherInfo.getString("temperature"));

                                         JSONObject pm25 = data.getJSONObject("pm25");
                                         if (pm25 != null) {
                                             JSONObject pm25Info = pm25.getJSONObject("pm25");
                                             if (pm25Info != null)
                                                 weather.setPm25(pm25Info.getString("quality"));
                                         } else {
                                             weather.setPm25("良");
                                         }

                                         LogKit.success("聚合天气获取成功", "聚合天气：" + weatherInfo.getString("info") + "\n" + "枚举天气：" + weather.getWeather().name());
                                         LogUtils.printCustomLog(BuildConf.LogTags.WEATHER_TAG, "聚合天气：" + weatherInfo.getString("info") + "\n" + "枚举天气：" + weather.getWeather().name());

                                         callback.onGetWeatherInfoSuccess(weather);
                                     } else {
                                         LogKit.exception("聚合天气获取失败", "失败原因：" + reason);
                                         callback.onGetWeatherInfoError();
                                     }
                                 } catch (Exception e) {
                                     LogKit.exception("聚合天气获取失败", "解析错误，异常信息：\n" + e.toString());
                                     callback.onGetWeatherInfoError();
                                 }
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 LogKit.exception("聚合天气获取失败", "访问失败, 异常信息：" + t.toString());
                                 callback.onGetWeatherInfoError();
                             }
                         }

                );

    }

}
