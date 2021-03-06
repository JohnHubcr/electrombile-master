package com.zenchn.electrombile;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * 作    者：wangr on 2017/2/21 14:17
 * 描    述：常量描述类（跟业务紧密相关）
 * 修订记录：
 */
public class Constants {

    public static final String YEAR_MONTH_DAY_HOUR_MIN = "yyyy-MM-dd HH:mm";

    public static final int BACK_EXIT_DELAY_TIME = 3000;

    // 模拟加载时间(提高用户体验)
    public static final int DEFINED_DELAY_TIME = 50;
    public static final int RANDOM_TIME = 100;

    /**
     * MAP 地图类型
     * --------------------------------------------------------------------
     */
    public interface MapType {

        String bMap = "bMap";//百度地图（bd09ll）
        String aMap = "aMap";//高德地图（gcj02）
        String gMap = "gMap";//大地坐标（gps原始）
    }

    /**
     * 车辆状态标量
     * --------------------------------------------------------------------
     */
    public interface VEHICLE {

        int COMMON_VEHICLE = 1;//常用车辆
        int USER_LEVEL_MAIN = 1;//主账号
        int USER_LEVEL_ASSIST = 2;//从账号

    }

    /**
     * 设备状态标量
     * --------------------------------------------------------------------
     */
    public interface DEVICE {

        // 设防状态
        int P_STATUS_OFF = 0;//撤防
        int P_STATUS_ON = 1;// 设防

        // acc状态
        int ACC_STATUS_COLSE = 0;// ACC关
        int ACC_STATUS_OPEN = 1;// ACC开

    }

    /**
     * 保险状态标量
     * --------------------------------------------------------------------
     */

    public interface INSURANCE {

        // 激活保险类型
        int INSURANCE_ACTIVATE_ADD = 0;// 添加
        int INSURANCE_ACTIVATE_EDIT = 1;// 编辑
        int INSURANCE_ACTIVATE_OBSERVE = 2;// 查看

        int INSURANCE_CLAIM_COMPLETED = 1;//理赔信息已完善
        int INSURANCE_CLAIM_ADD = 1;//添加
        int INSURANCE_CLAIM_SUPERADDITION = 2;//追加
    }

    /**
     * Service 售后服务类型
     * --------------------------------------------------------------------
     */
    public interface ServiceType {

        int repair = 0;//维修站点
        int charge = 1;//充电站点

    }


    /**
     * 轨迹渐变颜色
     * --------------------------------------------------------------------
     */

    public static List<Integer> colorList = new ArrayList<>();

    static {
        colorList.add(Color.rgb(255, 0, 0));// R255 红色
        for (int i = 1; i < 255; i++) {
            if (i % 2 == 0)
                colorList.add(Color.rgb(255, i, 0));
        }
        colorList.add(Color.rgb(255, 255, 0));// RG255 黄色
        for (int i = 254; i > 0; i--) {
            if (i % 2 == 0)
                colorList.add(Color.rgb(i, 255, 0));
        }
        colorList.add(Color.rgb(0, 255, 0));// G255 绿色
    }

}
