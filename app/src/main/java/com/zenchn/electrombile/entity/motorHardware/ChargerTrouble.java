package com.zenchn.electrombile.entity.motorHardware;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 充电器故障描述类
 *
 * @author WangRui
 */
public class ChargerTrouble {

    boolean charger_temperature;// 温度保护 b5
    boolean charger_overvoltage;// 过压保护 b6
    boolean charger_undervoltage;// 欠压保护 b7
    boolean charger_over_current;// 过流保护 b8

    public ChargerTrouble(boolean charger_temperature, boolean charger_overvoltage, boolean charger_undervoltage,
                          boolean charger_over_current) {
        super();
        this.charger_temperature = charger_temperature;
        this.charger_overvoltage = charger_overvoltage;
        this.charger_undervoltage = charger_undervoltage;
        this.charger_over_current = charger_over_current;
    }

    public boolean isCharger_temperature() {
        return charger_temperature;
    }

    public void setCharger_temperature(boolean charger_temperature) {
        this.charger_temperature = charger_temperature;
    }

    public boolean isCharger_overvoltage() {
        return charger_overvoltage;
    }

    public void setCharger_overvoltage(boolean charger_overvoltage) {
        this.charger_overvoltage = charger_overvoltage;
    }

    public boolean isCharger_undervoltage() {
        return charger_undervoltage;
    }

    public void setCharger_undervoltage(boolean charger_undervoltage) {
        this.charger_undervoltage = charger_undervoltage;
    }

    public boolean isCharger_over_current() {
        return charger_over_current;
    }

    public void setCharger_over_current(boolean charger_over_current) {
        this.charger_over_current = charger_over_current;
    }

//	@Override
//	public String toString() {
//		return "ChargerTrouble [charger_temperature=" + charger_temperature + ", charger_overvoltage="
//				+ charger_overvoltage + ", charger_undervoltage=" + charger_undervoltage + ", charger_over_current="
//				+ charger_over_current + "]";
//	}

    @Override
    public String toString() {
        return "温度保护:" + CommonUtils.formatTrouble(charger_temperature) + "\n" + "过压保护:"
                + CommonUtils.formatTrouble(charger_overvoltage) + "\n" + "欠压保护:"
                + CommonUtils.formatTrouble(charger_undervoltage) + "\n" + "过流保护:"
                + CommonUtils.formatTrouble(charger_over_current);
    }

}
