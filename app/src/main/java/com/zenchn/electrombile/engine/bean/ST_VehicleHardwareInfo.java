package com.zenchn.electrombile.engine.bean;

public class ST_VehicleHardwareInfo {

    private boolean repairStatus;// 修复状态
    private boolean vehicleState;// 车辆状态
    private boolean speedLimitedMode;// 限速模式

    private boolean gear4;// 4档状态
    private boolean gear3;// 3档状态
    private boolean gear2;// 2档状态
    private boolean gear1;// 1档状态
    private boolean reverseChargeState;// 反充电状态
    private boolean assistState;// 助力状态/电动状态
    private boolean cruisingState;// 巡航状态
    private boolean realTimeStatus;// 实时状态

    private boolean antiTheftState;// 防盗状态
    private boolean overVoltageCondition;// 过压状态
    private boolean underVoltageProtection;// 欠压状态
    private boolean motorLockStatus;// 锁电机状态
    private boolean motorHolzerFault;// 电机霍尔故障
    private boolean brakeFault;// 刹车故障
    private boolean turningFault;// 转把故障
    private boolean controlFault;// 控制器故障

    public ST_VehicleHardwareInfo() {
    }

    public ST_VehicleHardwareInfo(boolean repairStatus, boolean vehicleState, boolean speedLimitedMode) {
        this.repairStatus = repairStatus;
        this.vehicleState = vehicleState;
        this.speedLimitedMode = speedLimitedMode;
    }

    public ST_VehicleHardwareInfo(boolean repairStatus, boolean vehicleState, boolean speedLimitedMode, boolean gear4, boolean gear3, boolean gear2, boolean gear1, boolean reverseChargeState, boolean assistState, boolean cruisingState, boolean realTimeStatus, boolean antiTheftState, boolean overVoltageCondition, boolean underVoltageProtection, boolean motorLockStatus, boolean motorHolzerFault, boolean brakeFault, boolean turningFault, boolean controlFault) {
        this.repairStatus = repairStatus;
        this.vehicleState = vehicleState;
        this.speedLimitedMode = speedLimitedMode;
        this.gear4 = gear4;
        this.gear3 = gear3;
        this.gear2 = gear2;
        this.gear1 = gear1;
        this.reverseChargeState = reverseChargeState;
        this.assistState = assistState;
        this.cruisingState = cruisingState;
        this.realTimeStatus = realTimeStatus;
        this.antiTheftState = antiTheftState;
        this.overVoltageCondition = overVoltageCondition;
        this.underVoltageProtection = underVoltageProtection;
        this.motorLockStatus = motorLockStatus;
        this.motorHolzerFault = motorHolzerFault;
        this.brakeFault = brakeFault;
        this.turningFault = turningFault;
        this.controlFault = controlFault;
    }

    public int getTroubleCount() {
        int troubleCount = 0;
        if (controlFault)
            troubleCount++;
        if (turningFault)
            troubleCount++;
        if (brakeFault)
            troubleCount++;
        if (motorHolzerFault)
            troubleCount++;
        if (motorLockStatus)
            troubleCount++;
        if (underVoltageProtection)
            troubleCount++;
        if (overVoltageCondition)
            troubleCount++;
        return troubleCount;
    }

    public boolean isRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(boolean repairStatus) {
        this.repairStatus = repairStatus;
    }

    public boolean isVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(boolean vehicleState) {
        this.vehicleState = vehicleState;
    }

    public boolean isSpeedLimitedMode() {
        return speedLimitedMode;
    }

    public void setSpeedLimitedMode(boolean speedLimitedMode) {
        this.speedLimitedMode = speedLimitedMode;
    }

    public boolean isGear4() {
        return gear4;
    }

    public void setGear4(boolean gear4) {
        this.gear4 = gear4;
    }

    public boolean isGear3() {
        return gear3;
    }

    public void setGear3(boolean gear3) {
        this.gear3 = gear3;
    }

    public boolean isGear2() {
        return gear2;
    }

    public void setGear2(boolean gear2) {
        this.gear2 = gear2;
    }

    public boolean isGear1() {
        return gear1;
    }

    public void setGear1(boolean gear1) {
        this.gear1 = gear1;
    }

    public boolean isReverseChargeState() {
        return reverseChargeState;
    }

    public void setReverseChargeState(boolean reverseChargeState) {
        this.reverseChargeState = reverseChargeState;
    }

    public boolean isAssistState() {
        return assistState;
    }

    public void setAssistState(boolean assistState) {
        this.assistState = assistState;
    }

    public boolean isCruisingState() {
        return cruisingState;
    }

    public void setCruisingState(boolean cruisingState) {
        this.cruisingState = cruisingState;
    }

    public boolean isRealTimeStatus() {
        return realTimeStatus;
    }

    public void setRealTimeStatus(boolean realTimeStatus) {
        this.realTimeStatus = realTimeStatus;
    }

    public boolean isAntiTheftState() {
        return antiTheftState;
    }

    public void setAntiTheftState(boolean antiTheftState) {
        this.antiTheftState = antiTheftState;
    }

    public boolean isOverVoltageCondition() {
        return overVoltageCondition;
    }

    public void setOverVoltageCondition(boolean overVoltageCondition) {
        this.overVoltageCondition = overVoltageCondition;
    }

    public boolean isUnderVoltageProtection() {
        return underVoltageProtection;
    }

    public void setUnderVoltageProtection(boolean underVoltageProtection) {
        this.underVoltageProtection = underVoltageProtection;
    }

    public boolean isMotorLockStatus() {
        return motorLockStatus;
    }

    public void setMotorLockStatus(boolean motorLockStatus) {
        this.motorLockStatus = motorLockStatus;
    }

    public boolean isMotorHolzerFault() {
        return motorHolzerFault;
    }

    public void setMotorHolzerFault(boolean motorHolzerFault) {
        this.motorHolzerFault = motorHolzerFault;
    }

    public boolean isBrakeFault() {
        return brakeFault;
    }

    public void setBrakeFault(boolean brakeFault) {
        this.brakeFault = brakeFault;
    }

    public boolean isTurningFault() {
        return turningFault;
    }

    public void setTurningFault(boolean turningFault) {
        this.turningFault = turningFault;
    }

    public boolean isControlFault() {
        return controlFault;
    }

    public void setControlFault(boolean controlFault) {
        this.controlFault = controlFault;
    }

    @Override
    public String toString() {
        return (controlFault ? "控制器故障" + "\n" : "") +
                (turningFault ? "转把故障" + "\n" : "") +
                (brakeFault ? "刹车故障" + "\n" : "") +
                (motorHolzerFault ? "电机霍尔故障" + "\n" : "") +
                (motorLockStatus ? "电机锁死" : "电机未锁") + "\n" +
                (underVoltageProtection ? "欠压状态" : "非欠压状态") + "\n" +
                (overVoltageCondition ? "过压状态 " : "非过压状态") + "\n" +
                (antiTheftState ? "发生震动或移动" : "无震动或移动") + "\n" +

                (realTimeStatus ? "运行状态" : "静止状态") + "\n" +
                (cruisingState ? "巡航状态" : "非巡航状态") + "\n" +
                (assistState ? "电动状态" : "助力状态") + "\n" +
                (reverseChargeState ? "反充电状态" : "非反充电状态") + "\n" +
                (gear1 ? "档位 1 " + "\n" : "") +
                (gear2 ? "档位 2 " + "\n" : "") +
                (gear3 ? "档位 3 " + "\n" : "") +
                (gear4 ? "档位 4 " + "\n" : "") +

                (speedLimitedMode ? "限速状态" : "非限速状态") + "\n" +
                (repairStatus ? "故障修复状态" : "");
    }
}
