package com.zenchn.electrombile.engine.constant;

/**
 * 作    者：wangr on 2017/3/7 13:32
 * 描    述：神腾协议内容
 * 修订记录：
 */
public interface ProtocolForST {

    interface VehicleConditionStatus {

        int s = 1 << 5;// 修复状态
        int r = 1 << 6;// 车辆状态
        int q = 1 << 7;// 限速模式

        int p = 1 << 0;// 4档状态
        int o = 1 << 1;// 3档状态
        int n = 1 << 2;// 2档状态
        int m = 1 << 3;// 1档状态
        int l = 1 << 4;// 反充电状态
        int k = 1 << 5;// 助力状态/电动状态
        int j = 1 << 6;// 巡航状态
        int i = 1 << 7;// 实时状态

        int h = 1 << 0;// 防盗状态
        int g = 1 << 1;// 过压状态
        int f = 1 << 2;// 欠压状态
        int e = 1 << 3;// 锁电机状态
        int d = 1 << 4;// 电机霍尔故障
        int c = 1 << 5;// 刹车故障
        int b = 1 << 6;// 转把故障
        int a = 1 << 7;// 控制器故障
    }// 车况故障状态（1表示有故障）

    interface BmsTrouble {
        int h = 1 << 7;// 放电低温
        int g = 1 << 6;// 放电高温
        int f = 1 << 5;// 充电低温
        int e = 1 << 4;// 充电高温
        int d = 1 << 3;// 放电过流
        int c = 1 << 2;// 充电过流
        int b = 1 << 1;// 过放保护
        int a = 1 << 0;// 过压保护
    }// 电池故障

}
