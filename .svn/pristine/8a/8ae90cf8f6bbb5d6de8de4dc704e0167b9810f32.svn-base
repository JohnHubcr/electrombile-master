package com.zenchn.electrombile.engine.bean;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 作    者：wangr on 2017/3/14 10:03
 * 描    述：BMS故障描述类
 * 修订记录：
 */
public class JK_BMSTrouble {

    boolean bmsMosDamage;// MOS损坏 b2
    boolean bmsChargingOver;// 充电过流保护 b3
    boolean bmsOverTemperature;// 过温保护 b4
    boolean bmsHighVoltage;// 高电压保护 b5
    boolean bmsLowVoltage;// 低电压保护 b6
    boolean bmsBatteryOverCurrent;// 电池过流保护 b7
    boolean bmsExternalCircuitShortCircuit;// 外部电路短路 b8

    public JK_BMSTrouble() {
    }

    public JK_BMSTrouble(boolean bmsMosDamage, boolean bmsChargingOver, boolean bmsOverTemperature, boolean bmsHighVoltage, boolean bmsLowVoltage, boolean bmsBatteryOverCurrent, boolean bmsExternalCircuitShortCircuit) {
        this.bmsMosDamage = bmsMosDamage;
        this.bmsChargingOver = bmsChargingOver;
        this.bmsOverTemperature = bmsOverTemperature;
        this.bmsHighVoltage = bmsHighVoltage;
        this.bmsLowVoltage = bmsLowVoltage;
        this.bmsBatteryOverCurrent = bmsBatteryOverCurrent;
        this.bmsExternalCircuitShortCircuit = bmsExternalCircuitShortCircuit;
    }

    public boolean isBmsMosDamage() {
        return bmsMosDamage;
    }

    public void setBmsMosDamage(boolean bmsMosDamage) {
        this.bmsMosDamage = bmsMosDamage;
    }

    public boolean isBmsChargingOver() {
        return bmsChargingOver;
    }

    public void setBmsChargingOver(boolean bmsChargingOver) {
        this.bmsChargingOver = bmsChargingOver;
    }

    public boolean isBmsOverTemperature() {
        return bmsOverTemperature;
    }

    public void setBmsOverTemperature(boolean bmsOverTemperature) {
        this.bmsOverTemperature = bmsOverTemperature;
    }

    public boolean isBmsHighVoltage() {
        return bmsHighVoltage;
    }

    public void setBmsHighVoltage(boolean bmsHighVoltage) {
        this.bmsHighVoltage = bmsHighVoltage;
    }

    public boolean isBmsLowVoltage() {
        return bmsLowVoltage;
    }

    public void setBmsLowVoltage(boolean bmsLowVoltage) {
        this.bmsLowVoltage = bmsLowVoltage;
    }

    public boolean isBmsBatteryOverCurrent() {
        return bmsBatteryOverCurrent;
    }

    public void setBmsBatteryOverCurrent(boolean bmsBatteryOverCurrent) {
        this.bmsBatteryOverCurrent = bmsBatteryOverCurrent;
    }

    public boolean isBmsExternalCircuitShortCircuit() {
        return bmsExternalCircuitShortCircuit;
    }

    public void setBmsExternalCircuitShortCircuit(boolean bmsExternalCircuitShortCircuit) {
        this.bmsExternalCircuitShortCircuit = bmsExternalCircuitShortCircuit;
    }

    @Override
    public String toString() {
        return "MOS损坏:" + CommonUtils.formatBoolean(bmsMosDamage) + "\n"
                + "充电过流保护:" + CommonUtils.formatBoolean(bmsChargingOver) + "\n"
                + "过温保护:" + CommonUtils.formatBoolean(bmsOverTemperature) + "\n"
                + "高电压保护:" + CommonUtils.formatBoolean(bmsHighVoltage) + "\n"
                + "低电压保护:" + CommonUtils.formatBoolean(bmsLowVoltage) + "\n"
                + "电池过流保护:" + CommonUtils.formatBoolean(bmsBatteryOverCurrent) + "\n"
                + "外部电路短路:" + CommonUtils.formatBoolean(bmsExternalCircuitShortCircuit);
    }

    /**
     * 是否有异常
     *
     * @return
     */
    public boolean isHasTrouble() {
        return bmsMosDamage || bmsChargingOver || bmsOverTemperature || bmsHighVoltage
                || bmsLowVoltage || bmsBatteryOverCurrent || bmsExternalCircuitShortCircuit;
    }

}
