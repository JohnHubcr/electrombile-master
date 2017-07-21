package com.zenchn.electrombile.engine.bean;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 作    者：wangr on 2017/3/14 10:13
 * 描    述：控制器故障状态描述类
 * 修订记录：
 */
public class JK_ControlTrouble {

    boolean betToProtect;// 赌转保护 b1
    boolean overCurrentProtection;// 过流保护 b2
    boolean antiRunawayProtection;// 防飞车保护 b3
    boolean underVoltageProtection;// 欠压保护 b4
    boolean turningFault;// 转把故障 b5
    boolean motorHolzerFault;// 电机霍尔故障 b6
    boolean motorPhaseMissing;// 电机缺相 b7
    boolean controllerFailure;// 控制器故障 b8
    boolean controlOverHeat;// 控制器过热保护 b5
    boolean brakeLampFailure;// 刹车灯故障 b6
    boolean rearTurnLampFault;// 后转向灯故障 b7
    boolean nocturnalLightFault;// 夜行灯故障 b8

    public JK_ControlTrouble() {
    }

    public JK_ControlTrouble(boolean betToProtect, boolean overCurrentProtection, boolean antiRunawayProtection, boolean underVoltageProtection, boolean turningFault, boolean motorHolzerFault, boolean motorPhaseMissing, boolean controllerFailure, boolean controlOverHeat, boolean brakeLampFailure, boolean rearTurnLampFault, boolean nocturnalLightFault) {
        this.betToProtect = betToProtect;
        this.overCurrentProtection = overCurrentProtection;
        this.antiRunawayProtection = antiRunawayProtection;
        this.underVoltageProtection = underVoltageProtection;
        this.turningFault = turningFault;
        this.motorHolzerFault = motorHolzerFault;
        this.motorPhaseMissing = motorPhaseMissing;
        this.controllerFailure = controllerFailure;
        this.controlOverHeat = controlOverHeat;
        this.brakeLampFailure = brakeLampFailure;
        this.rearTurnLampFault = rearTurnLampFault;
        this.nocturnalLightFault = nocturnalLightFault;
    }

    public boolean isBetToProtect() {
        return betToProtect;
    }

    public void setBetToProtect(boolean betToProtect) {
        this.betToProtect = betToProtect;
    }

    public boolean isOverCurrentProtection() {
        return overCurrentProtection;
    }

    public void setOverCurrentProtection(boolean overCurrentProtection) {
        this.overCurrentProtection = overCurrentProtection;
    }

    public boolean isAntiRunawayProtection() {
        return antiRunawayProtection;
    }

    public void setAntiRunawayProtection(boolean antiRunawayProtection) {
        this.antiRunawayProtection = antiRunawayProtection;
    }

    public boolean isUnderVoltageProtection() {
        return underVoltageProtection;
    }

    public void setUnderVoltageProtection(boolean underVoltageProtection) {
        this.underVoltageProtection = underVoltageProtection;
    }

    public boolean isTurningFault() {
        return turningFault;
    }

    public void setTurningFault(boolean turningFault) {
        this.turningFault = turningFault;
    }

    public boolean isMotorHolzerFault() {
        return motorHolzerFault;
    }

    public void setMotorHolzerFault(boolean motorHolzerFault) {
        this.motorHolzerFault = motorHolzerFault;
    }

    public boolean isMotorPhaseMissing() {
        return motorPhaseMissing;
    }

    public void setMotorPhaseMissing(boolean motorPhaseMissing) {
        this.motorPhaseMissing = motorPhaseMissing;
    }

    public boolean isControllerFailure() {
        return controllerFailure;
    }

    public void setControllerFailure(boolean controllerFailure) {
        this.controllerFailure = controllerFailure;
    }

    public boolean isControlOverHeat() {
        return controlOverHeat;
    }

    public void setControlOverHeat(boolean controlOverHeat) {
        this.controlOverHeat = controlOverHeat;
    }

    public boolean isBrakeLampFailure() {
        return brakeLampFailure;
    }

    public void setBrakeLampFailure(boolean brakeLampFailure) {
        this.brakeLampFailure = brakeLampFailure;
    }

    public boolean isRearTurnLampFault() {
        return rearTurnLampFault;
    }

    public void setRearTurnLampFault(boolean rearTurnLampFault) {
        this.rearTurnLampFault = rearTurnLampFault;
    }

    public boolean isNocturnalLightFault() {
        return nocturnalLightFault;
    }

    public void setNocturnalLightFault(boolean nocturnalLightFault) {
        this.nocturnalLightFault = nocturnalLightFault;
    }

    @Override
    public String toString() {
        return "赌转保护:" + CommonUtils.formatTrouble(betToProtect) + "\n"
                + "过流保护:" + CommonUtils.formatTrouble(overCurrentProtection) + "\n"
                + "防飞车保护:" + CommonUtils.formatTrouble(antiRunawayProtection) + "\n"
                + "欠压保护:" + CommonUtils.formatTrouble(underVoltageProtection) + "\n"
                + "转把故障:" + CommonUtils.formatTrouble(turningFault) + "\n"
                + "电机霍尔故障:" + CommonUtils.formatTrouble(motorHolzerFault) + "\n"
                + "电机缺相:" + CommonUtils.formatTrouble(motorPhaseMissing) + "\n"
                + "控制器故障:" + CommonUtils.formatTrouble(controllerFailure) + "\n"
                + "控制器过热保护:" + CommonUtils.formatTrouble(controlOverHeat) + "\n"
                + "刹车灯故障:" + CommonUtils.formatTrouble(brakeLampFailure) + "\n"
                + "后转向灯故障:" + CommonUtils.formatTrouble(rearTurnLampFault) + "\n"
                + "夜行灯故障:" + CommonUtils.formatTrouble(nocturnalLightFault);
    }

    /**
     * 是否有异常
     *
     * @return
     */
    public boolean isHasTrouble() {
        return betToProtect || overCurrentProtection || antiRunawayProtection || underVoltageProtection
                || turningFault || motorHolzerFault || motorPhaseMissing || controllerFailure
                || controlOverHeat || brakeLampFailure || rearTurnLampFault || nocturnalLightFault;
    }

}
