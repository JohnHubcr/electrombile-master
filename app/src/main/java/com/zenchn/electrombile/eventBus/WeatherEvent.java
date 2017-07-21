package com.zenchn.electrombile.eventBus;

import com.zenchn.electrombile.entity.WeatherInfo;

/**
 * 作    者：wangr on 2017/3/1 17:12
 * 描    述：
 * 修订记录：
 */
public class WeatherEvent {

    private WeatherInfo weatherInfo;

    public WeatherEvent(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }
}
