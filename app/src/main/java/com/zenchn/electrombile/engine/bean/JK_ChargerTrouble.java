package com.zenchn.electrombile.engine.bean;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 作    者：wangr on 2017/3/14 10:03
 * 描    述：充电器故障描述类
 * 修订记录：
 */
public class JK_ChargerTrouble {

    boolean chargerTemperature;// 温度保护 b5
    boolean chargerOverVoltage;// 过压保护 b6
    boolean chargerUnderVoltage;// 欠压保护 b7
    boolean chargerOverCurrent;// 过流保护 b8

    public JK_ChargerTrouble() {
    }

    public JK_ChargerTrouble(boolean chargerTemperature, boolean chargerOverVoltage, boolean chargerUnderVoltage, boolean chargerOverCurrent) {
        this.chargerTemperature = chargerTemperature;
        this.chargerOverVoltage = chargerOverVoltage;
        this.chargerUnderVoltage = chargerUnderVoltage;
        this.chargerOverCurrent = chargerOverCurrent;
    }

    public boolean isChargerTemperature() {
        return chargerTemperature;
    }

    public void setChargerTemperature(boolean chargerTemperature) {
        this.chargerTemperature = chargerTemperature;
    }

    public boolean isChargerOverVoltage() {
        return chargerOverVoltage;
    }

    public void setChargerOverVoltage(boolean chargerOverVoltage) {
        this.chargerOverVoltage = chargerOverVoltage;
    }

    public boolean isChargerUnderVoltage() {
        return chargerUnderVoltage;
    }

    public void setChargerUnderVoltage(boolean chargerUnderVoltage) {
        this.chargerUnderVoltage = chargerUnderVoltage;
    }

    public boolean isChargerOverCurrent() {
        return chargerOverCurrent;
    }

    public void setChargerOverCurrent(boolean chargerOverCurrent) {
        this.chargerOverCurrent = chargerOverCurrent;
    }

    @Override
    public String toString() {
        return "温度保护:" + CommonUtils.formatTrouble(chargerTemperature) + "\n"
                + "过压保护:" + CommonUtils.formatTrouble(chargerOverVoltage) + "\n"
                + "欠压保护:" + CommonUtils.formatTrouble(chargerUnderVoltage) + "\n"
                + "过流保护:" + CommonUtils.formatTrouble(chargerOverCurrent);
    }

    /**
     * 是否有异常
     *
     * @return
     */
    public boolean isHasTrouble() {
        return chargerTemperature || chargerOverVoltage || chargerUnderVoltage || chargerOverCurrent;
    }
}
