package com.zenchn.electrombile.wrapper;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.ui.activity.NotifyActivity;
import com.zenchn.mlibrary.utils.DateUtils;
import com.zenchn.mlibrary.utils.NotifyUtils;

import java.util.Random;

/**
 * 作    者：wangr on 2017/3/1 14:06
 * 描    述：发送通知到通知栏的封装
 * 修订记录：
 */
public class NotifyWrapper {

    private NotifyWrapper() {
    }

    private static class SingletonInstance {
        private static final NotifyWrapper INSTANCE = new NotifyWrapper();
    }

    public static NotifyWrapper getInstance() {
        return SingletonInstance.INSTANCE;
    }


    /**
     * 发送通知
     *
     * @param context
     * @param title
     * @param content
     * @param type
     */
    public void notifyMsg(Context context, String title, String content, String type) {
        NotifyUtils notifyUtil = new NotifyUtils(context, Constants.NOTIFY_ID_LOGIN_OUT + new Random().nextInt(100));

        String currentTime = DateUtils.getCurrentTime("MM-dd HH:mm");

        Intent hangIntent = new Intent();
        hangIntent.setClass(context, NotifyActivity.class);
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        hangIntent.putExtra(Constants.INTENT_KEY, type);

        // 设置点击跳转
        // 如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifyUtil.notify_normail_moreline(pendingIntent, R.drawable.logo_about, content, title, currentTime + " " + content, true, true, true);
    }
}
