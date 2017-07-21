package com.zenchn.electrombile.engine.constant;

/**
 * 作    者：wangr on 2017/3/7 13:34
 * 描    述：金开协议内容
 * 修订记录：
 */
public interface ProtocolForJK {

    interface MeterTroubleStatus {
        int h = 1 << 7;// 开关
        int g = 1 << 6;// 转把
        int f = 1 << 5;// 线性刹把
    }// 仪表故障状态

    interface ChargerTrouble {
        int h = 1 << 7;// 过流保护
        int g = 1 << 6;// 欠压保护
        int f = 1 << 5;// 过压保护
        int e = 1 << 4;// 温度保护
    }// 充电器故障

    interface BmsTrouble {
        int h = 1 << 7;// 外部电路短路
        int g = 1 << 6;// 电池过流保护
        int f = 1 << 5;// 低电压保护
        int e = 1 << 4;// 高电压保护
        int d = 1 << 3;// 过温保护
        int c = 1 << 2;// 充电过流保护
        int b = 1 << 1;// MOS损坏
    }// 电池故障

    interface ControlTroubleStatus1 {
        int h = 1 << 7;// 控制器故障
        int g = 1 << 6;// 电机缺相
        int f = 1 << 5;// 电机霍尔故障
        int e = 1 << 4;// 转把故障
        int d = 1 << 3;// 欠压保护
        int c = 1 << 2;// 防飞车保护
        int b = 1 << 1;// 过流保护
        int a = 1 << 0;// 赌转保护
    }// 控制器故障

    interface ControlTroubleStatus2 {
        int h = 1 << 7;// 夜行灯故障
        int g = 1 << 6;// 后转向灯故障
        int f = 1 << 5;// 刹车灯故障
        int e = 1 << 4;// 控制器过热保护
    }// 控制器故障

    interface EcuTrouble {

        int h = 1 << 7;// ECU过流
        int g = 1 << 6;// 转换器异常
        int f = 1 << 5;// 转把故障
        int e = 1 << 4;// 刹把故障

    }// ecu故障

    interface EcuOnlineCheck {
        int h = 1 << 7;// 控制器
        int g = 1 << 6;// GPS
        int f = 1 << 5;// 仪表
        int e = 1 << 4;// 蓝牙
        int d = 1 << 3;// 充电器
        int c = 1 << 2;// BMS
    }// ecu在线状态

    interface GyroscopeStatus {
        int a = 1;// 静态侧翻
        int b = 2;// 动态侧翻
        int c = 3;// 颠簸路段
        int d = 4;// 转弯前减速
        int e = 5;// 下坡加速度
        int f = 6;// 下坡减速度
        int g = 7;// 紧急制动
        int h = 8;// 下坡转弯
        int i = 9;// S型路线骑行
        int j = 10;// 猛拐
        int k = 11;// 夜行不开灯
    }// 陀螺仪状态
}
