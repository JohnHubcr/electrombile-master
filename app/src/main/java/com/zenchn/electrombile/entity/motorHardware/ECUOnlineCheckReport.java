package com.zenchn.electrombile.entity.motorHardware;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * BMS在线检测描述类
 *
 * @author WangRui
 */
public class ECUOnlineCheckReport {

    boolean BMS;// BMS b3
    boolean charger;// 充电器 b4
    boolean Bluetooth;// 蓝牙 b5
    boolean appearance;// 仪表 b6
    boolean GPS;// GPS b7
    boolean controller;// 控制器 b8

    public ECUOnlineCheckReport(boolean BMS, boolean charger, boolean Bluetooth, boolean appearance, boolean GPS,
                                boolean controller) {
        super();
        this.BMS = BMS;
        this.charger = charger;
        this.Bluetooth = Bluetooth;
        this.appearance = appearance;
        this.GPS = GPS;
        this.controller = controller;
    }

    public boolean isBMS() {
        return BMS;
    }

    public void setBMS(boolean BMS) {
        this.BMS = BMS;
    }

    public boolean isCharger() {
        return charger;
    }

    public void setCharger(boolean charger) {
        this.charger = charger;
    }

    public boolean isBluetooth() {
        return Bluetooth;
    }

    public void setBluetooth(boolean Bluetooth) {
        this.Bluetooth = Bluetooth;
    }

    public boolean isAppearance() {
        return appearance;
    }

    public void setAppearance(boolean appearance) {
        this.appearance = appearance;
    }

    public boolean isGPS() {
        return GPS;
    }

    public void setGPS(boolean GPS) {
        this.GPS = GPS;
    }

    public boolean isController() {
        return controller;
    }

    public void setController(boolean controller) {
        this.controller = controller;
    }

    // @Override
    // public String toString() {
    // return "ECUOnlineCheck [BMS=" + BMS + ", charger=" + charger +
    // ", Bluetooth=" + Bluetooth + ", appearance="
    // + appearance + ", GPS=" + GPS + ", controller=" + controller + "]";
    // }

    @Override
    public String toString() {
        return "BMS:" + CommonUtils.formatTrouble(BMS) + "\n" + "充电器:" + CommonUtils.formatTrouble(charger) + "\n"
                + "蓝牙:" + CommonUtils.formatTrouble(Bluetooth) + "\n" + "仪表:" + CommonUtils.formatTrouble(appearance)
                + "\n" + "GPS:" + CommonUtils.formatTrouble(GPS) + "\n" + "控制器:" + CommonUtils.formatTrouble(controller);
    }

}
