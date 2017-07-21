package com.zenchn.electrombile.engine.bean;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 作    者：wangr on 2017/3/14 10:24
 * 描    述：仪表故障状态描述类
 * 修订记录：
 */
public class JK_MeterTrouble {

    boolean meterLinearBrake;// 线性刹把 b6
    boolean meterTurn;// 转把 b7
    boolean meterSwitch;// 开关 b8

    public JK_MeterTrouble() {
    }

    public JK_MeterTrouble(boolean meterLinearBrake, boolean meterTurn, boolean meterSwitch) {
        this.meterLinearBrake = meterLinearBrake;
        this.meterTurn = meterTurn;
        this.meterSwitch = meterSwitch;
    }

    public boolean isMeterLinearBrake() {
        return meterLinearBrake;
    }

    public void setMeterLinearBrake(boolean meterLinearBrake) {
        this.meterLinearBrake = meterLinearBrake;
    }

    public boolean isMeterTurn() {
        return meterTurn;
    }

    public void setMeterTurn(boolean meterTurn) {
        this.meterTurn = meterTurn;
    }

    public boolean isMeterSwitch() {
        return meterSwitch;
    }

    public void setMeterSwitch(boolean meterSwitch) {
        this.meterSwitch = meterSwitch;
    }

    @Override
    public String toString() {
        return "线性刹把:" + CommonUtils.formatTrouble(meterLinearBrake) + "\n"
                + "转把:" + CommonUtils.formatTrouble(meterTurn) + "\n"
                + "开关:" + CommonUtils.formatTrouble(meterSwitch);
    }

    /**
     * 是否有异常
     *
     * @return
     */
    public boolean isHasTrouble() {
        return meterLinearBrake || meterTurn || meterSwitch;
    }
}
