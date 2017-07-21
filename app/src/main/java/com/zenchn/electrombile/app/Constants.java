package com.zenchn.electrombile.app;

import android.graphics.Color;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 作者：wangr on 2016/12/21 12:58
 * 描述：常量管理类
 */
public class Constants {

    // 是否收集报错日志
    public static boolean isDebug = true;

    // app根目录
    public static String appFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "zenchn";

    public static final String FILE_DIR_LOGS = "logs";
    public static final String FILE_DIR_CACHE = "cache";
    public static final String FILE_DIR_FILES = "files";
    public static final String FILE_DIR_DOWNLOAD = "download";
    public static final String FILE_DIR_APK = "apk";
    public static final String FILE_DIR_IMAGE = "images";

    // 聚合天气key
    public static final String WEATHER_KEY = "c9264dfebf8ada75fd82150af43cc44f";

    // 应用通知ID
    public static final int NOTIFY_ID_LOGIN_OUT = new Random().nextInt(10000);
    public static final int NOTIFY_ID_MESSAGE = 10000 + new Random().nextInt(10000);
    public static final int NOTIFY_ID_CONTROL = 20000 + new Random().nextInt(10000);

    // 跳转标示
    public static final String INTENT_KEY = "INTENT_KEY";
    public static final String INTENT_TO_MESSAGE = "INTENT_TO_MESSAGE";
    public static final String INTENT_TO_CONTROL = "INTENT_TO_CONTROL";
    public static final String INTENT_TO_LOGIN = "INTENT_TO_LOGIN";

    //缓存
    public static final int SAVE_GPS_LINK_TOKEN_TEMP_TIME = 90 * 60 * 1000;



    //自定义dialog的显示时间
    public static final long SHOW_DIALOG_LONG_TIME = 3000;
    public static final long SHOW_DIALOG_SHORT_TIME = 2000;

    //保险是否完善
    public static final int INSURANCE_PERFECT_STATUS_OK = 1;

    // 广播
    public static final String ACTION_APP_RUNNING_BACKGROUND = "com.zenchn.electrombile.ACTION_APP_RUNNING_BACKGROUND";
    public static final String ACTION_APP_RUNNING_FOREGROUND = "com.zenchn.electrombile.ACTION_APP_RUNNING_FOREGROUND";
    public static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";// 收到到短信的广播
    public static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";// 连接状态改变


    // acc状态
    public static final int ACC_STATUS_COLSE = 0;// ACC关闭
    public static final int ACC_STATUS_OPEN = 1;// ACC开启

    // 设防状态
    public static final int P_STATUS_OPEN = 1;// 设防
    public static final int P_STATUS_COLSE = 0;// 撤防

    // 在线状态
    public static final int OFFLINE_STATUS = 0;// 0 离线
    public static final int STATIC_STATUS = 10;// 10静止
    public static final int SPORTS_STATUS = 11;// 11运动

    // 激活保险类型
    public static final int INSURANCE_ADD = 0;// 添加
    public static final int INSURANCE_EDIT = 1;// 编辑

    // 消息类型
    public static final String MSG_TYPE_EXIT = "beLoginOut";// 退出登录
    public static final String MSG_TYPE_SYSTEM = "system";// 系统消息（设防状态）

    // 时间类型
    public static final String DATE_TYPE_NYR = "yyyy-mm-dd";// 年月日
    public static final String DATE_TYPE_SFM = "hh:mm:ss";// 年月日 时分秒


    public static class Result {
        public static final int ERROR = 0;// 失败
        public static final int SUCCESS = 1;// 成功
        public static final int NO_SESSION = 9;// 会话ID不存在
        public static final int NETWORK_ERROR = -1;// 网络不稳定
        public static final int SERVICE_ERROR = 500;// 服务器异常
        public static final int NOT_REQUEST = 204;// 未发起请求
    }

    // 轨迹渐变颜色
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
