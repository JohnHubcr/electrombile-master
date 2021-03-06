package com.zenchn.electrombile;

import android.os.Environment;

import java.io.File;

/**
 * 作    者：wangr on 2017/2/21 10:18
 * 描    述：常量订阅类（跟业务关系不紧密）
 * 修订记录：
 */
public class BuildConf {

    /**
     * host 主机地址
     * --------------------------------------------------------------------
     */
    public static final String BASE_REQUEST_HTTP_URL = "http://114.55.41.252:8888/ddc/";
    //        public static final String BASE_REQUEST_HTTP_URL = "http://192.168.1.113:8088/ddc/";
//    public static final String BASE_REQUEST_HTTP_URL = "http://192.168.1.114:8080/zdzc/";
    public static final String DEFAULT_ENCODING_CHARSET = "UTF-8";


    /**
     * app 文件路径
     * --------------------------------------------------------------------
     */
    // app根目录
    public static String appFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "zenchn";

    public static final String FILE_DIR_LOGS = "logs";
    public static final String FILE_DIR_CACHE = "cache";
    public static final String FILE_DIR_FILES = "files";
    public static final String FILE_DIR_DOWNLOAD = "download";
    public static final String FILE_DIR_APK = "apk";
    public static final String FILE_DIR_IMAGE = "images";

    /**
     * log 日志信息
     * --------------------------------------------------------------------
     */
    public static boolean isDebug = true;

    public interface LogTags {

        String TEST = "TEST_TAG";

        String TOKEN_TAG = "TOKEN_TAG";
        String ACTIVITY_COUNT = "ACTIVITY_COUNT";
        String SMS_TAG = "SMS_TAG";
        String IMAGE_TAG = "IMAGE_TAG";
        String UPDATE_TAG = "UPDATE_TAG";
        String WEATHER_TAG = "WEATHER_TAG";

        String ENGINE_TAG = "ENGINE_TAG";
        String LOCATION_TAG = "LOCATION_TAG";

        String SERVICE_TAG = "SERVICE_TAG";
        String DEBUG_TAG = "DEBUG_TAG";
    }

    /**
     * 聚合天气
     * --------------------------------------------------------------------
     */

    public interface JuheWeather {

        String juheWeatherSite = "http://op.juhe.cn/onebox/weather/query";// 聚合天气查询地址

        String juheWeatherKey = "c9264dfebf8ada75fd82150af43cc44f";// 聚合天气key

        String juheWeatherDataType = "json";// 聚合天气返回数据类型
    }

    /**
     * api 访问授权
     * --------------------------------------------------------------------
     */
    public interface API_GRANT {

        String CLIENT_TYPE = "Android";
        String CLIENT_ID = "00000000000000000000000000000001";
        String CLIENT_SECRET = "zdzc_ddc_android_app";
        String GRANT_TYPE = "password";
        String REFRESH_TOKEN = "refresh_token";

    }

    /**
     * APP更新接口状态
     * --------------------------------------------------------------------
     */
    public interface APP_UPDATE {

        //系统类型
        String SYSTEM_TYPE = "Android";

        /* 是否强制更新的状态 */
        int MUST_UPDATE = 1;

        /* 下载保存路径 */
        String DEST_FILE_DIR = appFolder + File.separator + FILE_DIR_DOWNLOAD + File.separator + FILE_DIR_APK;
        String DEST_FILE_NAME = "electrombile_update.apk";

    }

    /**
     * statusCode 接口返回状态码
     * --------------------------------------------------------------------
     */
    public interface ApiStatusCode {

        int MESSAGE = 0;
        int SUCCESS = 1;

        int OK = 200;
        int NO_GRANT = 401;

    }


    /**
     * AuthCode 发送验证码类型
     * --------------------------------------------------------------------
     */
    public interface AuthCodeType {

        String LOGIN = "LOGIN";//登录
        String REGISTER = "REGISTER";//注册
        String MODIFY_PWD = "MODIFYPWD";//修改密码
        String ACCOUNT_REPLACE = "ACCOUNTREPLACE";//账号更换
        String BIND_EQUIPMENT = "LOGIN";//设备绑定

    }

    /**
     * RequestResultCode 请求码
     * --------------------------------------------------------------------
     */
    public interface RequestResultCode {

        int MOTOR_ARCHIVES_REQUEST = 14;// 车辆档案请求码
        int SEND_VALIDATE_REQUEST = 15;// 发送验证码的请求码
        int REGISTER_REQUEST = 16;// 注册请求码
        int RESET_PASSWORD_REQUEST = 17;// 重设密码请求码
        int BIND_EQUIPMENT_REQUEST = 18;// 绑定设备验证请求码
        int MODIFY_ACCOUNT_REQUEST = 19;// 修改账号的请求码

        int SETTING_REQUEST = 20;// 设置页面请求码
        int EXCLUSIVE_SERVICE_REQUEST = 21;//专属服务（即保险）通用的请求码
        int PAY_REQUEST = 22;// 支付相关请求码
        int ADD_INSURANCE_REQUEST = 23;// 激活保险请求码
        int ADD_INSURANCE_CLAIM_REQUEST = 24;// 理赔信息的请求码
        int UPDATE_CONTACTS_REQUEST = 25;// 设置（变更）紧急联系人请求码
        int CHOOSE_CONTACTS_REQUEST = 26;// 选择紧急联系人请求码
    }

    /**
     * gpsLink 波导相关
     * --------------------------------------------------------------------
     */
    public interface GpsLink {

        int POST_TCP_CMD = 0;
        int QUERY_TCP_CMD = 1;

        String ERROR_FLAG = "查询超时";
        String REFUSE_FLAG = "获取授权失败";
        String SUCCESS_FLAG = "成功";
        String FAIL_FLAG = "失败";
        String PENDING_FLAG = "ECU设备关闭缓存到终端";

    }

    /**
     * MSG 消息类型
     * --------------------------------------------------------------------
     */
    public interface MessageType {

        String alert = "alert";//报警
        String remind = "remind";//提醒
        String info = "info";//消息
        String beLoginOut = "beLoginOut";//用户被退出
        String saddleAlert = "saddleAlert";//鞍座
        String powerAlert = "powerAlert";//电池
        String shakeAlert = "shakeAlert";//震动
        String moveAlert = "moveAlert";//位移
        String sideAlert = "sideAlert";//动态
        String system = "system";//系统

    }

    /**
     * ImageLoader 显示图片地址类型
     * --------------------------------------------------------------------
     */
    public interface ImageType {

        int IMG_LOCAL = 0;// 本地图片
        int IMG_NETWORK = 1;// 网络图片

    }

    /**
     * intentionService intent类型
     * --------------------------------------------------------------------
     */
    public interface IntentType {

        String UPDATE_APP_TYPE = "UPDATE_APP_TYPE";

    }

    /**
     * WebView 类型
     * --------------------------------------------------------------------
     */
    public interface WebViewType {

        int AGREEMENT = 0;// 软件许可及服务协议
        int STEAL_INSURANCE = 1;// 盗窃险
        int RESPONSIBILITY_INSURANCE = 2;// 第三方责任险
        int INSURANCE_CLAIM_PROCESS = 3;// 理赔流程
        int INSURANCE_CONTENT = 4;// 保险条款
        int INSURANCE_DECLARE = 5;// 免责声明
        int USER_DEFINED_WEB = 8;// 用户自定义页面
        int USER_DEFINED_WEB_NO_TITLE = 9;// 用户自定义页面

    }

    /**
     * AppPackage 应用的包名
     * --------------------------------------------------------------------
     */

    public interface AppPackage {
        String TENCENT_WEIXIN = "com.tencent.mm";
        String BAIDU_MAP = "com.baidu.BaiduMap";
        String GAODE_MAP = "com.autonavi.minimap";
        String TENCENT_MAP = "com.tencent.map";
    }

    /**
     * activity 中用到的常量
     * --------------------------------------------------------------------
     */

    public interface ACTIVITY {

        int MAIN_BLUR_ANIMATION_DURATION = 300;
        int MAIN_BLUR_PROCESS_RADIUS = 20;
        float MAIN_BLUR_SCALE_VALUE = 1.1f;

        String FEEDBACK_KEY_FEEDBACK_PIC_FILE = "KEY_FEEDBACK_PIC_FILE";
        int FEEDBACK_CONTENT_MAX_LENGTH = 200;//最大数量
    }


    /**
     * alipay 支付宝
     * --------------------------------------------------------------------
     */
    public interface AliPay {

        String PARTNER = "2088421426397714";// 商户PID

        String SELLER = "2088421426397714";// 商户收款账号

        int PAY_STATUS_OK = 1;//已支付
    }

    /**
     * ACache 工具类缓存专用key
     * --------------------------------------------------------------------
     */
    public interface CacheKey {

        String SAVE_USER_LOGIN_NAME_HISTORY = "SAVE_USER_LOGIN_NAME_HISTORY";// 用户名历史信息
        String SAVE_USER_INFO = "SAVE_USER_INFO";//用户信息

        String SAVE_WEATHER_INFO = "SAVE_WEATHER_INFO";//天气信息
        String SAVE_IS_FIRST_LOGIN = "SAVE_IS_FIRST_LOGIN";// 是否首次登陆

        String SAVE_NOTIFICATION_SWITCH = "SAVE_NOTIFICATION_SWITCH";//保存通知开关

        String SAVE_LOGOUT_TIME = "SAVE_LOGOUT_TIME";//保存App退出登录的时间
        String SAVE_FEEDBACK_INFO = "SAVE_FEEDBACK_INFO";//保存用户反馈信息

        String SAVE_INSURANCE_ACTIVATE_INFO = "SAVE_INSURANCE_ACTIVATE_INFO";//保存未完成的保险激活信息
        String SAVE_INSURANCE_CLAIM_INFO = "SAVE_INSURANCE_CLAIM_INFO";//保存未完成的理赔信息

        //缓存数据部分（退出app则清除）
        String TEMP_LOGIN_INFO = "TEMP_LOGIN_INFO";// 登录信息
        String TEMP_IDENTIFYING_CODE_VALID = "TEMP_IDENTIFYING_CODE_VALID";// 验证码有效时间

        String TEMP_IDENTIFYING_CODE = "TEMP_IDENTIFYING_CODE";// 保存的验证码
        String TEMP_GPS_LINK_TOKEN = "TEMP_GPS_LINK_TOKEN";// 缓存波导token
        String TEMP_ALARM_MESSAGE = "TEMP_ALARM_MESSAGE";// 缓存未读或者忽略的报警消息

        String TEMP_INSURANCE_POLICY_INFO = "TEMP_INSURANCE_POLICY_INFO";//缓存保险保单信息
        String TEMP_INSURANCE_APPLY_CLAIM_INFO = "TEMP_INSURANCE_APPLY_CLAIM_INFO";//缓存理赔信息

        String TEMP_MOTOR_HARDWARE_INFO = "TEMP_MOTOR_HARDWARE_INFO";//缓存车辆硬件信息
        String TEMP_MOTOR_CHECK_INFO = "TEMP_MOTOR_CHECK_INFO";

        String TEMP_VEHICLE_RECORD_INFO = "TEMP_VEHICLE_RECORD_INFO";//缓存车辆档案信息
        String TEMP_VEHICLE_SPEED_LIMIT_OPTION = "TEMP_MOTOR_SPEED_LIMIT_OPTION";//缓存车辆限速信息
    }

}
