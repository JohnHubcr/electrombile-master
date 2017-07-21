package com.zenchn.electrombile.engine.bean;

/**
 * 作    者：wangr on 2017/3/14 10:23
 * 描    述：陀螺仪状态描述类
 * 修订记录：
 */
public class JK_GyroscopeStatus {

    boolean staticRollover;// 静态侧翻 1
    boolean dynamicRollover;// 动态侧翻 2
    boolean bumpyRoad;// 颠簸路段 3
    boolean decelerationBeforeTurning;// 转弯前减速 4
    boolean downhillAcceleration;// 下坡加速度 5
    boolean downhillDeceleration;// 下坡减速度 6
    boolean brakeHard;// 紧急制动 7
    boolean downhillTurn;// 下坡转弯 8
    boolean S_Type_Route;// S型路线骑行 9
    boolean cornerTurn;// 猛拐 10
    boolean noLightsAtNight;// 夜行不开灯 11

    public JK_GyroscopeStatus() {
    }

    public JK_GyroscopeStatus(boolean staticRollover, boolean dynamicRollover, boolean bumpyRoad, boolean decelerationBeforeTurning, boolean downhillAcceleration, boolean downhillDeceleration, boolean brakeHard, boolean downhillTurn, boolean s_Type_Route, boolean cornerTurn, boolean noLightsAtNight) {
        this.staticRollover = staticRollover;
        this.dynamicRollover = dynamicRollover;
        this.bumpyRoad = bumpyRoad;
        this.decelerationBeforeTurning = decelerationBeforeTurning;
        this.downhillAcceleration = downhillAcceleration;
        this.downhillDeceleration = downhillDeceleration;
        this.brakeHard = brakeHard;
        this.downhillTurn = downhillTurn;
        this.S_Type_Route = s_Type_Route;
        this.cornerTurn = cornerTurn;
        this.noLightsAtNight = noLightsAtNight;
    }

    @Override
    public String toString() {
        return (staticRollover ? "" : "静态侧翻" + "\n" + "") +
                (dynamicRollover ? "" : "动态侧翻" + "\n" + "") +
                (bumpyRoad ? "颠簸路段" + "\n" : "") +
                (decelerationBeforeTurning ? "转弯前减速" + "\n" : "") +
                (downhillAcceleration ? "下坡加速度" + "\n" : "") +
                (downhillDeceleration ? "下坡减速度" + "\n" : "") +
                (brakeHard ? "紧急制动" + "\n" : "") +
                (downhillTurn ? "下坡转弯" + "\n" : "") +
                (S_Type_Route ? "S型路线骑行" + "\n" : "") +
                (cornerTurn ? "猛拐" + "\n" : "") +
                (noLightsAtNight ? "夜行不开灯" : "");
    }

}
