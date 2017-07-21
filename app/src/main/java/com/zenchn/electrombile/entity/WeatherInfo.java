package com.zenchn.electrombile.entity;

import com.zenchn.electrombile.entity.model.WeatherEnum;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/2/25 0:50
 * 描    述：
 * 修订记录：
 */
public class WeatherInfo implements Serializable{

    private String province;//省
    private String city;//市
    private String date;// 日期
    private WeatherEnum weather;// 天气
    private String wind;// 风向
    private String temperature;// 温度
    private String nightTemperature;// 夜间温度
    private String pm25;// 空气质量pm2.5
    private String week;// 星期

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(String nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WeatherEnum getWeather() {
        return weather;
    }

    public void setWeather(WeatherEnum weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

}
