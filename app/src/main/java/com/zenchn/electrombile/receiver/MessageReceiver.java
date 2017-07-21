package com.zenchn.electrombile.receiver;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.AlarmEventBus;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.entity.AlarmMessageInfo;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.model.AlarmEnum;
import com.zenchn.electrombile.eventBus.AccStatusEvent;
import com.zenchn.electrombile.eventBus.AlarmEvent;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.ui.activity.NotifyActivity;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.mlibrary.log.LogUtils;
import com.zenchn.mlibrary.utils.NotifyUtils;
import com.zenchn.mlibrary.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;


public class MessageReceiver extends XGPushBaseReceiver {

    private static final String LogTag = "TPushReceiver";
    private static final boolean isDebug = false;
    private static String serialNumber = null;

    private void show(Context context, String text) {
        // Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        if (isDebug) {
            ToastUtils.showDefaultMessage(context, text);
        }
    }

    // // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        if (context == null || xgPushShowedResult == null) {
            return;
        }
        show(context, "您有1条新消息, " + "通知被展示 ， " + xgPushShowedResult.toString());

        String customContent = xgPushShowedResult.getCustomContent();
        String content = xgPushShowedResult.getContent();
        String title = xgPushShowedResult.getTitle();

        handleXGPushTextMessage(context, title, content, customContent);

    }

    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        Log.d(LogTag, text);
    }

    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);

    }

    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);

    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
    }

    @Override
    public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            // 在这里拿token
            String token = message.getToken();
            text = "注册成功，token:" + token;
        } else {
            text = "注册失败，错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        LogUtils.printSimpleLog(LogTag, text, 4);
        show(context, text);
    }

    // 消息透传
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        String text = "收到消息:" + message.toString();

        String title = message.getTitle();
        String content = message.getContent();

        // 获取自定义key-value
        String customContent = message.getCustomContent();

        handleXGPushTextMessage(context, title, content, customContent);
        // APP自主处理消息的过程...
        Log.d(LogTag, text);
        LogUtils.printSimpleLog(LogTag, text, 4);
    }

    /**
     * 处理消息
     *
     * @param context
     * @param customContent
     */
    private void handleXGPushTextMessage(Context context, String title, String content, String customContent) {

        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);

                LogUtils.printSimpleLog(LogTag, "get custom value:" + customContent, 4);

                LoginInfo loginInfo = ACacheWrapper.getLoginInfo();

                if (loginInfo == null) {
                    return;
                }

                // key1为前台配置的key

                if (!obj.isNull("serialNumber")) {
                    String serialNumber = obj.getString("serialNumber");

                    LogUtils.printSimpleLog(LogTag, "get custom value:" + serialNumber, 4);

                    if (!serialNumber.equals(loginInfo.getSerialNumber())) {
                        return;
                    }
                }

                if (!obj.isNull("type")) {
                    String type = obj.getString("type");
                    LogUtils.printSimpleLog(LogTag, "get custom value:" + type, 4);

                    if (Constants.MSG_TYPE_EXIT.equals(type)) {

                        //如果推送过来的踢号时间小于本次登录时间，则视为无效
                        if (!obj.isNull("createTime")) {
                            long createTime = obj.getLong("createTime");
                            long loginTime = loginInfo.getLoginTime();
                            if (createTime < loginTime)
                                return;
                        }

                        ACacheWrapper.removeUserInfo();

                        if (title != null && content != null) {
                            notifyMsg(context, title, content, Constants.INTENT_TO_LOGIN);// 发送通知
                        }

                        ApplicationKit.getInstance().restartApp();
                        return;
                    }

                    if (Constants.MSG_TYPE_SYSTEM.equals(type)) {// 设防状态改变

                        if (!obj.isNull("accto")) {
                            int accto = obj.getInt("accto");

                            EventBus.getDefault().post(new AccStatusEvent(accto));

                            Log.d(LogTag, "accto=" + accto);
                            LogUtils.printSimpleLog(LogTag, "get custom value:" + accto, 4);

                        }

                    } else {// 报警信息推送
//                        updateCount(context, aCache);// 更新报警消息

                        AlarmEnum alarmEnum = AlarmEnum.classifyAlarmByMsgType(type);
                        AlarmEventBus.getSpecialTrain().post(new AlarmEvent(alarmEnum));
                        title = alarmEnum.name();
                        content = alarmEnum.getAlarmDesc();

                        if (title != null && content != null) {
                            notifyMsg(context, title, content, Constants.INTENT_TO_MESSAGE);// 发送通知
                            AlarmMessageInfo alarmMessageInfo = new AlarmMessageInfo();
                            alarmMessageInfo.setMsgContent(content);
                            alarmMessageInfo.setMsgTitle(title);
                            alarmMessageInfo.setMsgDate(obj.getLong("utcTime"));
                            alarmMessageInfo.setVehicleAddress(obj.getString(""));
//                            aCache.putEverything(loginInfo.getLoginName() + CacheKey.RECENTLY_ALARM_MESSAGE, alarmMessageInfo);
                        }
                    }
//                    context.sendBroadcast(intent);
                }

            } catch (JSONException e) {
                LogUtils.printSimpleLog(LogTag, e.toString(), 4);
                e.printStackTrace();
            }
        } else {
            if (title != null && content != null) {
                notifyMsg(context, title, content, Constants.INTENT_TO_MESSAGE);// 发送通知
            }
        }
    }

    /**
     * 消息通知
     *
     * @param context
     * @param title
     * @param content
     * @param type
     */
    public void notifyMsg(Context context, String title, String content, String type) {

        NotifyUtils notifyUtil = new NotifyUtils(context, Constants.NOTIFY_ID_LOGIN_OUT);


        // 设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setClass(context, NotifyActivity.class);
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        hangIntent.putExtra(Constants.INTENT_KEY, type);

        // 如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifyUtil.notify_normail_moreline(pendingIntent, R.drawable.logo_about, content, title, content, true, true, true);
    }

}
