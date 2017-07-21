package com.zenchn.electrombile.engine.bean;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 作    者：wangr on 2017/3/14 10:15
 * 描    述：BMS在线检测描述类
 * 修订记录：
 */
public class JK_ECUOnlineStatus {

    boolean BMS;// BMS b3
    boolean Charger;// 充电器 b4
    boolean Bluetooth;// 蓝牙 b5
    boolean Appearance;// 仪表 b6
    boolean GPS;// GPS b7
    boolean Controller;// 控制器 b8

    public JK_ECUOnlineStatus() {
    }

    public JK_ECUOnlineStatus(boolean BMS, boolean charger, boolean bluetooth, boolean appearance, boolean GPS, boolean controller) {
        this.BMS = BMS;
        Charger = charger;
        Bluetooth = bluetooth;
        Appearance = appearance;
        this.GPS = GPS;
        Controller = controller;
    }

    public boolean isBMS() {
        return BMS;
    }

    public void setBMS(boolean BMS) {
        this.BMS = BMS;
    }

    public boolean isCharger() {
        return Charger;
    }

    public void setCharger(boolean charger) {
        Charger = charger;
    }

    public boolean isBluetooth() {
        return Bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        Bluetooth = bluetooth;
    }

    public boolean isAppearance() {
        return Appearance;
    }

    public void setAppearance(boolean appearance) {
        Appearance = appearance;
    }

    public boolean isGPS() {
        return GPS;
    }

    public void setGPS(boolean GPS) {
        this.GPS = GPS;
    }

    public boolean isController() {
        return Controller;
    }

    public void setController(boolean controller) {
        Controller = controller;
    }

    @Override
    public String toString() {
        return "BMS:" + CommonUtils.formatTrouble(BMS) + "\n"
                + "充电器:" + CommonUtils.formatTrouble(Charger) + "\n"
                + "蓝牙:" + CommonUtils.formatTrouble(Bluetooth) + "\n"
                + "仪表:" + CommonUtils.formatTrouble(Appearance) + "\n"
                + "GPS:" + CommonUtils.formatTrouble(GPS) + "\n"
                + "控制器:" + CommonUtils.formatTrouble(Controller);
    }

    /**
     * 是否有异常
     *
     * @return
     */
    public boolean isHasTrouble() {
        return BMS || Charger || Bluetooth || Appearance || GPS || Controller;
    }

}
