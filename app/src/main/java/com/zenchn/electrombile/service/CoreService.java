package com.zenchn.electrombile.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.api.VehicleCoreApi;
import com.zenchn.electrombile.api.callback.VehicleContrailCallback;
import com.zenchn.electrombile.api.callback.VehicleLocationCallback;
import com.zenchn.electrombile.app.AlarmEventBus;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseService;
import com.zenchn.electrombile.engine.ContrailEngine;
import com.zenchn.electrombile.engine.TcpCmdEngine;
import com.zenchn.electrombile.engine.callback.ContrailCallback;
import com.zenchn.electrombile.engine.callback.TcpCmdCallback;
import com.zenchn.electrombile.entity.AlarmMessageInfo;
import com.zenchn.electrombile.entity.CommandModel;
import com.zenchn.electrombile.entity.VehicleContrailInfo;
import com.zenchn.electrombile.entity.VehicleLocationInfo;
import com.zenchn.electrombile.entity.model.AlarmEnum;
import com.zenchn.electrombile.eventBus.AccStatusEvent;
import com.zenchn.electrombile.eventBus.AlarmEvent;
import com.zenchn.electrombile.eventBus.GeoCodeEvent;
import com.zenchn.electrombile.eventBus.TcpCmdRequestEvent;
import com.zenchn.electrombile.eventBus.TcpCmdRequestStatusEvent;
import com.zenchn.electrombile.eventBus.TcpCmdResultEvent;
import com.zenchn.electrombile.eventBus.UpdateDataEvent;
import com.zenchn.electrombile.eventBus.VehicleContrailEvent;
import com.zenchn.electrombile.eventBus.VehicleContrailListEvent;
import com.zenchn.electrombile.eventBus.VehicleLocationEvent;
import com.zenchn.electrombile.eventBus.VehicleTrackingEvent;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.ui.activity.AlarmShowActivity;
import com.zenchn.electrombile.utils.EquModelUtils;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.electrombile.wrapper.BDReverseGeoWrapper;
import com.zenchn.electrombile.wrapper.callback.BDReverseGeoCallback;
import com.zenchn.mlibrary.utils.DateUtils;
import com.zenchn.mlibrary.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 作    者：wangr on 2017/2/21 18:54
 * 描    述：
 * 修订记录：
 */
public class CoreService extends BaseService implements VehicleLocationCallback, VehicleContrailCallback, BDReverseGeoCallback, ContrailCallback, TcpCmdCallback {

    //定位信息刷新时间间隔
    private final int defaultFrequency = 1000 * 12;

    //位置刷新距离
    private double defaultGetGeoCodeSpace = 300f;

    //位置解析任务刷新时间间隔
    private int defaultGetGeoCodeInterval = 1000 * 60 * 10;

    //轨迹任务刷新时间间隔
    private final int defaultTraceTimeInterval = 1000 * 60 * 30;

    //ACC关闭后刷新
    private final int defaultDelayedTraceTimeInterval = 1000 * 60 * 2;

    private String accessToken;
    private String serialNumber;
    private String authorizationPsw;
    private int equModel;

    private Runnable getVehicleLocationRunnable;
    private Runnable getVehicleLastTraceRunnable;
    private Runnable getGeoCodeRunnable;

    private SoundPool sp;
    private AudioManager am;
    private float volumeRatio;
    private HashMap<Integer, Integer> soundPoolMap;

    private LatLng preLatLng;
    private LatLng currentLatLng;
    private long recentlyTime;
    private int turnSignal;
    private boolean isPlayStatus;

    private Set<String> tcpCmdStack;//记录存入的指令

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CoreBinder();
    }

    /**
     * 创建services对象首先调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        initSound();
        registerReceiverData();
        AlarmEventBus.getSpecialTrain().register(this);
    }

    @Override
    protected void handler(Message msg) {

    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {

            flags = START_STICKY;
            accessToken = intent.getStringExtra("accessToken");
            serialNumber = intent.getStringExtra("serialNumber");
            authorizationPsw = intent.getStringExtra("authorizationPsw");
            equModel = intent.getIntExtra("equModel", 1);

            // 设置轮询定位任务
            getVehicleLocationRunnable = new Runnable() {
                @Override
                public void run() {
                    LogKit.logService(getClass().getSimpleName(), ":" + "isPolling……");
                    VehicleCoreApi
                            .getInstance()
                            .getLastLocation(accessToken, serialNumber, CoreService.this);
                    handler.postDelayed(this, defaultFrequency);
                }
            };

            //设置轨迹解析任务
            getVehicleLastTraceRunnable = new Runnable() {

                @Override
                public void run() {
                    LogKit.logService(getClass().getSimpleName(), ":" + "isGetLastTraceInfo……");
                    VehicleCoreApi
                            .getInstance()
                            .getLocationList(accessToken, serialNumber, equModel, CoreService.this);
                    handler.postDelayed(this, defaultTraceTimeInterval);
                }
            };

            //设置地理位置解析任务
            getGeoCodeRunnable = new Runnable() {

                @Override
                public void run() {
                    long currentTime = SystemClock.uptimeMillis();
                    if (preLatLng != null) {
                        if (DistanceUtil.getDistance(preLatLng, currentLatLng) >= defaultGetGeoCodeSpace || currentTime - recentlyTime >= defaultGetGeoCodeInterval) {
                            BDReverseGeoWrapper
                                    .getInstance()
                                    .getGeoCode(currentLatLng, CoreService.this);
                        } else {
                            LogKit.success(getClass().getSimpleName(), ":" + "车辆移动位置小于500米或时间小于" + defaultGetGeoCodeInterval / 1000 / 60 + "分钟,无需更新地址");
                        }
                    } else {
                        BDReverseGeoWrapper
                                .getInstance()
                                .getGeoCode(currentLatLng, CoreService.this);
                    }
                }
            };

            handler.post(getVehicleLocationRunnable);// 开始轮询
            handler.post(getVehicleLastTraceRunnable);//获取最近一次Acc关闭的轨迹段
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 注册接收数据广播
     */
    private void registerReceiverData() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Constants.ACTION_CONNECTIVITY_CHANGE);
        filter.addAction(Constants.ACTION_APP_RUNNING_BACKGROUND);
        filter.addAction(Constants.ACTION_APP_RUNNING_FOREGROUND);
        registerReceiver(receiver, filter);
    }

    /**
     * 接受广播
     */
    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Constants.ACTION_APP_RUNNING_FOREGROUND.equals(action)) {
                handler.removeCallbacks(getVehicleLocationRunnable);
                handler.post(getVehicleLocationRunnable);
            } else if (Constants.ACTION_APP_RUNNING_BACKGROUND.equals(action)) {
                handler.removeCallbacks(getVehicleLocationRunnable);
            } else if (Constants.ACTION_CONNECTIVITY_CHANGE.equals(action)) {
                boolean networkAvailable = NetworkUtils.isNetworkAvailable(CoreService.this);
                if (networkAvailable) {
                    handler.removeCallbacks(getVehicleLocationRunnable);
                    handler.post(getVehicleLocationRunnable);
                } else {
                    handler.removeCallbacks(getVehicleLocationRunnable);
                }
            } else if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(action)) {
                boolean isAirPlane = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
                if (isAirPlane) {
                    handler.removeCallbacks(getVehicleLocationRunnable);
                } else {
                    handler.removeCallbacks(getVehicleLocationRunnable);
                    handler.post(getVehicleLocationRunnable);
                }
            } else if (Intent.ACTION_USER_PRESENT.equals(action) || Intent.ACTION_SCREEN_ON.equals(action)) {
                handler.removeCallbacks(getVehicleLocationRunnable);
                handler.post(getVehicleLocationRunnable);
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                handler.removeCallbacks(getVehicleLocationRunnable);
            }
        }
    };

    /**
     * 初始化声音
     */
    private void initSound() {
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        int volumePercent = currentVolume / audioMaxVolume;
        volumeRatio = volumePercent < 0.8f ? 0.8f : volumePercent;
        soundPoolMap = new HashMap<>();
        soundPoolMap.put(1, sp.load(this, R.raw.alarm, 1));
        turnSignal = soundPoolMap.get(1);
    }

    /**
     * 播放报警声音
     */
    private void playSound() {
        if (!isPlayStatus) {
            turnSignal = sp.play(soundPoolMap.get(1), volumeRatio, volumeRatio, 1, -1, 1f);
            isPlayStatus = true;
        }
    }

    /**
     * 停止报警声音
     */
    private void stopSound() {
        sp.pause(turnSignal);
        isPlayStatus = false;
    }

    @Subscribe
    public void onEventMainThread(TcpCmdRequestEvent event) {
        postTcpCmd(event);
    }

    private void postTcpCmd(TcpCmdRequestEvent event) {
        CommandModel commandModel = event.getCommandModel();
        String tcpCmdName = event.getTcpCmdName();
        if (tcpCmdStack == null)
            tcpCmdStack = new LinkedHashSet<>();
        tcpCmdStack.add(tcpCmdName);//发送指令增加一条记录
        TcpCmdEngine
                .getInstance()
                .startTcpTask(getApplicationContext(), serialNumber, authorizationPsw, tcpCmdName, commandModel, this);
    }

    @Override
    public void onTcpCmdRequestStatus(String tcpCmdName, boolean result) {
        if (!result)
            tcpCmdStack.remove(tcpCmdName);//指令发送失败删除一条记录
        EventBus.getDefault().post(new TcpCmdRequestStatusEvent(tcpCmdStack, tcpCmdName, result));
    }

    @Override
    public void onTcpCmdResult(String tcpCmdName, boolean isSuccess, String result) {
        tcpCmdStack.remove(tcpCmdName);//指令执行完成，删除一条记录
        EventBus.getDefault().postSticky(new TcpCmdResultEvent(tcpCmdStack, tcpCmdName, isSuccess, result));
    }

    @Subscribe
    public void onEventMainThread(AlarmEvent event) {
        if (event.getAlarmEnum() == AlarmEnum.解除警报) {
            if (isPlayStatus)
                stopSound();
        } else {

            //缓存一条未读消息
            AlarmMessageInfo alarmMessageInfo = new AlarmMessageInfo();
            alarmMessageInfo.setMsgContent(event.getAlarmEnum().getAlarmDesc());
            alarmMessageInfo.setMsgTitle(event.getAlarmEnum().name());
            alarmMessageInfo.setMsgDate(DateUtils.getCurrentTime());

            ACacheWrapper.tempAlarmMessage(alarmMessageInfo);

            Activity topActivity = ApplicationKit.getInstance().getTopActivity();
            Intent intent = new Intent(topActivity, AlarmShowActivity.class);
            intent.putExtra("alarmType", event.getAlarmEnum().alarmType);
            topActivity.startActivity(intent);
            boolean alarmNotificationStatus = ACacheWrapper.getAlarmNotificationStatus();
            if (!alarmNotificationStatus) {
                if (!isPlayStatus) {
                    playSound();//开始报警
                    isPlayStatus = true;
                }
            }
        }
    }

    @Subscribe
    public void onEventMainThread(AccStatusEvent event) {
        if (event.getAccStatus() == Constants.ACC_STATUS_COLSE) {
            if (EquModelUtils.isHideFunction(equModel)) {//一期设备
                handler.postDelayed(getVehicleLastTraceRunnable, defaultDelayedTraceTimeInterval);
            } else {//二期设备
                handler.post(getVehicleLastTraceRunnable);
            }
        }
    }

    @Subscribe
    public void onEventMainThread(UpdateDataEvent event) {
        handler.removeCallbacks(getVehicleLocationRunnable);
        handler.post(getVehicleLocationRunnable);
        handler.removeCallbacks(getVehicleLastTraceRunnable);
        handler.post(getVehicleLastTraceRunnable);
    }

    @Subscribe
    public void onEventMainThread(VehicleTrackingEvent event) {
        boolean tracking = event.isTracking();
        if (tracking)
            getCurrentVehicleLocationInfo();//立即更新定位数据
        defaultGetGeoCodeInterval = tracking ? 5 * 1000 : 12 * 1000;//车辆追踪下5s刷新定位
        defaultGetGeoCodeSpace = tracking ? 10f : 300f;//车辆追踪下10米刷新位置解析
    }

    @Override
    public void onGetVehicleLocationSuccess(VehicleLocationInfo vehicleLocationInfo) {
        EventBus.getDefault().postSticky(new VehicleLocationEvent(vehicleLocationInfo));
        if (vehicleLocationInfo != null) {
            currentLatLng = new LatLng(vehicleLocationInfo.getBdLatitude(), vehicleLocationInfo.getBdLongitude());//更新定位坐标
            handler.post(getGeoCodeRunnable);//开启地址任务
        }
    }

    @Override
    public void onGetVehicleLocationFailure() {
        getCurrentVehicleLocationInfo();
    }

    /**
     * 延时3秒后查询电动车动态信息(避免请求过于频繁)
     */
    private void getCurrentVehicleLocationInfo() {
        handler.removeCallbacks(getVehicleLocationRunnable);
        handler.postDelayed(getVehicleLocationRunnable, 3000);
    }

    @Override
    public void onGetVehicleContrailSuccess(List<VehicleLocationInfo> vehicleLocationInfoList) {
        EventBus.getDefault().postSticky(new VehicleContrailListEvent(vehicleLocationInfoList));
        if (vehicleLocationInfoList != null && !vehicleLocationInfoList.isEmpty()) {
            ContrailEngine
                    .getInstance()
                    .analyzeContrail(vehicleLocationInfoList, CoreService.this);
        }
    }

    @Override
    public void onGetVehicleContrailFailure() {
    }

    @Override
    public void onGetVehicleContrailResponseError(String error_msg) {
    }

    @Override
    public void onGetBDGeoCodeSuccess(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult != null) {
            this.recentlyTime = System.currentTimeMillis();
            this.preLatLng = currentLatLng;
            EventBus.getDefault().postSticky(new GeoCodeEvent(reverseGeoCodeResult));
        }
    }

    @Override
    public void onGetBDGeoCodeFailure() {
    }

    @Override
    public void onAnalyzeCompleted(VehicleContrailInfo vehicleContrailInfo) {
        if (vehicleContrailInfo != null) {
            EventBus.getDefault().postSticky(new VehicleContrailEvent(vehicleContrailInfo));
        }
    }

    @Override
    public void onAnalyzeInterrupt() {
    }

    @Override
    public void onGrantRefuse() {
        handler.removeCallbacksAndMessages(null);
        ACacheWrapper.removeUserInfo();//移除保存的用户信息
        ApplicationKit
                .getInstance()
                .restartApp();
    }

    // 创建代理人对象
    class CoreBinder extends Binder implements TcpCmdBinder {

        @Override
        public void sendTcpCmd(TcpCmdRequestEvent event) {
            postTcpCmd(event);
        }

        @Override
        public Set<String> getTcpCmdStack() {
            return tcpCmdStack;
        }
    }


}
