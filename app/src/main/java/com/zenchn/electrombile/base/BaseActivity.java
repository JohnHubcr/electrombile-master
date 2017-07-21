package com.zenchn.electrombile.base;


import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.tencent.android.tpush.XGPushManager;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.mlibrary.base.DefaultAppCompatActivity;
import com.zenchn.mlibrary.base.IApplicationKit;

/**
 * 作    者：wangr on 2017/2/24 14:31
 * 描    述： activity 基类
 * 修订记录：
 */
public abstract class BaseActivity extends DefaultAppCompatActivity implements BaseView {

    protected LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(savedInstanceState);
    }

    @Override
    protected IApplicationKit getIApplicationKit() {
        return ApplicationKit.getInstance();
    }

    //界面布局的初始化操作
    protected abstract void initContentView(Bundle savedInstanceState);

    // 可能全屏或者没有ActionBar等
    @Override
    protected void setCustomBase() {
        loginInfo = ApplicationKit.getInstance().getLoginInfo();// 获取登陆信息
    }

    protected abstract void initData();

    //如果需要接受消息，则重写该方法
    protected void handler(Message msg) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        ApplicationKit.getInstance().setCurrentRunningForeground(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        XGPushManager.onActivityStarted(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ApplicationKit.getInstance().setCurrentRunningForeground(false);
    }

    // 横竖屏切换，键盘等
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(savedInstanceState);
        } catch (Exception e) {

        }
    }

    /**
     * 防止按钮重复提交
     *
     * @param view
     */
    protected void disabledView(final View view) {
        CommonUtils.setViewGroupEnabled(view, false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonUtils.setViewGroupEnabled(view, true);
            }
        }, 5000);
    }

    @Override
    public void onGrantRefuse() {
        ACacheWrapper.removeUserInfo();//移除保存的用户信息
        ApplicationKit
                .getInstance()
                .restartApp();
    }

}
