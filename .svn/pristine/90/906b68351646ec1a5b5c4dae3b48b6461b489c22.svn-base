package com.zenchn.electrombile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.eventBus.IdentifyingCodeEvent;
import com.zenchn.mlibrary.log.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取pdu(一条短信)
        Object[] objects = (Object[]) intent.getExtras().get("pdus");

        for (Object object : objects) {
            // 创建smsmessage
            SmsMessage message = SmsMessage.createFromPdu((byte[]) object);

            // 获取短信的内容
            String body = message.getMessageBody();
            // 获取发送信息的号码 sender
            String address = message.getOriginatingAddress();

            LogUtils.printCustomLog(BuildConf.LogTags.SMS_TAG, "新短信来了", "发送号码：" + address + "\n" + "短信内容:" + body);

            if (body.contains(context.getString(R.string.app_name))) {
                Pattern pattern = Pattern.compile("\\d{4}");
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()) {
                    EventBus.getDefault().post(new IdentifyingCodeEvent(matcher.group()));
                    LogUtils.printCustomLog(BuildConf.LogTags.SMS_TAG, "抓取到短信验证码：" + matcher.group());
                }
            }

        }
    }

}
