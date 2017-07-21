package com.zenchn.electrombile.entity.motorHardware;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * BMS故障描述类
 *
 * @author WangRui
 */
public class BMSTrouble {

    boolean bms_mos_damage;// MOS损坏 b2
    boolean bms_charging_over;// 充电过流保护 b3
    boolean bms_over_temperature;// 过温保护 b4
    boolean bms_high_voltage;// 高电压保护 b5
    boolean bms_low_voltage;// 低电压保护 b6
    boolean bms_battery_over_current;// 电池过流保护 b7
    boolean bms_external_circuit_short_circuit;// 外部电路短路 b8

    public BMSTrouble(boolean bms_mos_damage, boolean bms_charging_over, boolean bms_over_temperature,
                      boolean bms_high_voltage, boolean bms_low_voltage, boolean bms_battery_over_current,
                      boolean bms_external_circuit_short_circuit) {
        super();
        this.bms_mos_damage = bms_mos_damage;
        this.bms_charging_over = bms_charging_over;
        this.bms_over_temperature = bms_over_temperature;
        this.bms_high_voltage = bms_high_voltage;
        this.bms_low_voltage = bms_low_voltage;
        this.bms_battery_over_current = bms_battery_over_current;
        this.bms_external_circuit_short_circuit = bms_external_circuit_short_circuit;
    }

    public boolean isBms_mos_damage() {
        return bms_mos_damage;
    }

    public void setBms_mos_damage(boolean bms_mos_damage) {
        this.bms_mos_damage = bms_mos_damage;
    }

    public boolean isBms_charging_over() {
        return bms_charging_over;
    }

    public void setBms_charging_over(boolean bms_charging_over) {
        this.bms_charging_over = bms_charging_over;
    }

    public boolean isBms_over_temperature() {
        return bms_over_temperature;
    }

    public void setBms_over_temperature(boolean bms_over_temperature) {
        this.bms_over_temperature = bms_over_temperature;
    }

    public boolean isBms_high_voltage() {
        return bms_high_voltage;
    }

    public void setBms_high_voltage(boolean bms_high_voltage) {
        this.bms_high_voltage = bms_high_voltage;
    }

    public boolean isBms_low_voltage() {
        return bms_low_voltage;
    }

    public void setBms_low_voltage(boolean bms_low_voltage) {
        this.bms_low_voltage = bms_low_voltage;
    }

    public boolean isBms_battery_over_current() {
        return bms_battery_over_current;
    }

    public void setBms_battery_over_current(boolean bms_battery_over_current) {
        this.bms_battery_over_current = bms_battery_over_current;
    }

    public boolean isBms_external_circuit_short_circuit() {
        return bms_external_circuit_short_circuit;
    }

    public void setBms_external_circuit_short_circuit(boolean bms_external_circuit_short_circuit) {
        this.bms_external_circuit_short_circuit = bms_external_circuit_short_circuit;
    }

    @Override
    public String toString() {
        return "MOS损坏:" + CommonUtils.formatBoolean(bms_mos_damage) + "\n" + "充电过流保护:"
                + CommonUtils.formatBoolean(bms_charging_over) + "\n" + "过温保护:"
                + CommonUtils.formatBoolean(bms_over_temperature) + "\n" + "高电压保护:"
                + CommonUtils.formatBoolean(bms_high_voltage) + "\n" + "低电压保护:"
                + CommonUtils.formatBoolean(bms_low_voltage) + "\n" + "电池过流保护:"
                + CommonUtils.formatBoolean(bms_battery_over_current) + "\n" + "外部电路短路:"
                + CommonUtils.formatBoolean(bms_external_circuit_short_circuit);
    }

}
