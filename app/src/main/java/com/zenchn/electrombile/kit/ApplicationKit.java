package com.zenchn.electrombile.kit;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.baidu.location.BDLocation;
import com.tencent.android.tpush.service.XGPushService;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.callback.WeatherCallback;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.WeatherInfo;
import com.zenchn.electrombile.eventBus.BDLocationEvent;
import com.zenchn.electrombile.eventBus.GeoCodeEvent;
import com.zenchn.electrombile.eventBus.TcpCmdResultEvent;
import com.zenchn.electrombile.eventBus.VehicleContrailEvent;
import com.zenchn.electrombile.eventBus.VehicleContrailListEvent;
import com.zenchn.electrombile.eventBus.VehicleLocationEvent;
import com.zenchn.electrombile.eventBus.WeatherEvent;
import com.zenchn.electrombile.service.CoreService;
import com.zenchn.electrombile.ui.activity.LoginActivity;
import com.zenchn.electrombile.ui.activity.MainActivity;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.electrombile.wrapper.WeatherWrapper;
import com.zenchn.electrombile.wrapper.XGRegisterWrapper;
import com.zenchn.mlibrary.base.DefaultApplicationKit;
import com.zenchn.mlibrary.base.IApplicationKit;
import com.zenchn.mlibrary.cache.ACache;
import com.zenchn.mlibrary.log.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 作    者：wangr on 2017/3/9 11:43
 * 描    述：Application管理类
 * 修订记录：
 */
public class ApplicationKit extends DefaultApplicationKit implements IApplicationKit {

    private Intent coreService;
    private Intent xgPushService;

    private LoginInfo loginInfo;

    private int currentForegroundActivity;
    private int preForegroundActivity;

    private ApplicationKit() {
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    private static class SingletonInstance {
        private static final ApplicationKit INSTANCE = new ApplicationKit();
    }

    public static ApplicationKit getInstance() {
        return SingletonInstance.INSTANCE;
    }

    /**
     * 更新用户信息
     *
     * @param loginInfo
     */
    public void updateLoginInfo(@NonNull LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
        if (CommonUtils.isNonNull(loginInfo.getSerialNumber()))
            ACacheWrapper.addMarking(loginInfo.getSerialNumber());//对缓存增加标识
    }

    public Intent getCoreServiceIntent() {
        return coreService;
    }

    public Intent getXgPushServiceIntent() {
        return xgPushService;
    }

    public void initCustomSetting() {
        super.initCustomSetting();
        initACache();//初始化缓存
        initService();//初始化服务
        registerPush();//注册信鸽token
        updateWeather();//更新本地天气
    }

    /**
     * 初始化本地缓存
     */
    private void initACache() {
        ACache aCache = ACache.get(application);
        ACacheWrapper.init(aCache);
    }

    /**
     * 初始化服务
     */
    private void initService() {
        coreService = new Intent(application, CoreService.class);
        xgPushService = new Intent(application, XGPushService.class);
    }

    public void setCurrentRunningForeground(boolean isForeground) {
        currentForegroundActivity += isForeground ? 1 : -1;
        LogUtils.printCustomLog(BuildConf.LogTags.ACTIVITY_COUNT, "Foreground Activity Counts=" + currentForegroundActivity);
        if (preForegroundActivity > 0 && currentForegroundActivity <= 0) {
            application.sendBroadcast(new Intent(Constants.ACTION_APP_RUNNING_BACKGROUND));
            LogUtils.printCustomLog(BuildConf.LogTags.ACTIVITY_COUNT, "暂停轮询任务");
        } else if (preForegroundActivity <= 0 && currentForegroundActivity > 0) {
            application.sendBroadcast(new Intent(Constants.ACTION_APP_RUNNING_FOREGROUND));
            LogUtils.printCustomLog(BuildConf.LogTags.ACTIVITY_COUNT, "继续轮询任务");
        }
        preForegroundActivity = currentForegroundActivity;
    }

    /**
     * 重启程序
     */
    public void restartApp() {
        clearEvent();
        stopPollingService();
        restartActivity();
    }

    /**
     * 清除事件
     */
    private void clearEvent() {
        EventBus.getDefault().removeStickyEvent(GeoCodeEvent.class);//删除该事件
        EventBus.getDefault().removeStickyEvent(TcpCmdResultEvent.class);//删除该事件
        EventBus.getDefault().removeStickyEvent(VehicleLocationEvent.class);//删除该事件
        EventBus.getDefault().removeStickyEvent(VehicleContrailEvent.class);//删除该事件
        EventBus.getDefault().removeStickyEvent(VehicleContrailListEvent.class);//删除该事件
    }

    /**
     * 关闭轮询服务
     */
    private void stopPollingService() {
        application.stopService(coreService);// 关闭轮询服务
    }

    /**
     * 重启界面
     */
    private void restartActivity() {
        Activity topActivity = getTopActivity();
        if (topActivity != null) {
            Intent intent = new Intent(topActivity, LoginActivity.class);
            topActivity.startActivity(intent);
        } else {
            Intent intent = new Intent(application, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);
        }
    }

    /**
     * 退出程序
     */
    public void exitApp() {
        saveLogoutTime();
        clearNotify();
        stopAllService();
        super.exitApp();
    }

    /**
     * 记录用户退出时间
     */
    private void saveLogoutTime() {
        if (loginInfo != null)
            ACacheWrapper.saveLogoutTime(loginInfo.getLoginName(), System.currentTimeMillis());
    }

    /**
     * 清理通知栏
     */
    public void clearNotify() {
        NotificationManager nm = (NotificationManager) application.getSystemService(Activity.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    /**
     * 关闭所有后台服务
     */
    public void stopAllService() {
        application.stopService(xgPushService);//关闭信鸽服务
        application.stopService(coreService);// 关闭轮询服务
    }

    /**
     * 注册xgPush获取token
     */
    private void registerPush() {
        XGRegisterWrapper
                .getInstance()
                .registerXgPush(application.getApplicationContext());
    }

    /**
     * 更新本地天气信息
     */
    public void updateWeather() {

        WeatherWrapper
                .getInstance()
                .getWeather(application, new WeatherCallback() {
                    @Override
                    public void onGetBDLocationSuccess(BDLocation bdLocation) {
                        EventBus.getDefault().postSticky(new BDLocationEvent(bdLocation));
                    }

                    @Override
                    public void onGetWeatherInfoSuccess(WeatherInfo weatherInfo) {
                        ACacheWrapper.saveWeatherInfo(weatherInfo);
                        EventBus.getDefault().postSticky(new WeatherEvent(weatherInfo));
                    }

                    @Override
                    public void onGetWeatherInfoError() {

                    }
                });
    }
}
