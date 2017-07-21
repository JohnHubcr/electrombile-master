package com.zenchn.electrombile.wrapper;

import android.content.Context;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.mlibrary.log.LogUtils;

/**
 * 作    者：wangr on 2017/2/24 22:33
 * 描    述：对信鸽注册Token进行封装
 * 修订记录：
 */

public class XGRegisterWrapper implements XGIOperateCallback {

    private int errorCount;
    private Context mContext;

    private XGRegisterWrapper() {
    }

    public static XGRegisterWrapper getInstance() {
        return new XGRegisterWrapper();
    }

    /**
     * 注册信鸽
     *
     * @param context
     */
    public void registerXgPush(Context context) {
        this.mContext = context.getApplicationContext();
        XGPushManager.registerPush(mContext, this);
    }

    @Override
    public void onSuccess(Object data, int flag) {
        LogKit.success("信鸽token注册成功", "token=" + data.toString());
        LogUtils.printCustomLog(BuildConf.LogTags.TOKEN_TAG, "注册成功：" + data.toString());
        errorCount = 0;//重置错误计数
    }

    @Override
    public void onFail(Object data, int errCode, String msg) {
        errorCount++;
        LogKit.success("信鸽token注册失败" + errorCount + "次数", "msg=" + msg);
        LogUtils.printCustomLog(BuildConf.LogTags.TOKEN_TAG, "注册失败：" + errCode + "," + msg);
        if (errorCount < 5)
            XGPushManager.registerPush(mContext, this);
        else
            errorCount = 0;//重置错误计数
    }

}
