package com.zenchn.electrombile.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.widget.SlideSwitch;
import com.zenchn.electrombile.wrapper.ACacheWrapper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/3/9 15:23
 * 描    述：消息免打扰的页面
 * 修订记录：
 */
public class NotificationModeActivity extends BaseActivity implements SlideSwitch.SlideListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sw_notifications)
    SlideSwitch swNotifications;

    private boolean alarmNotificationStatus;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    private void addListener() {
        swNotifications.setSlideListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_notification_switch;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_notification_switch));
        alarmNotificationStatus = ACacheWrapper.getAlarmNotificationStatus();
        swNotifications.setState(alarmNotificationStatus);
    }

    @Override
    public void open() {
        ACacheWrapper.saveAlarmNotificationStatus(true);
    }

    @Override
    public void close() {
        ACacheWrapper.saveAlarmNotificationStatus(false);
    }

    @OnClick({R.id.ll_back, R.id.ll_notifications})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_notifications:
                alarmNotificationStatus = !alarmNotificationStatus;
                swNotifications.setState(alarmNotificationStatus);
                break;
        }
    }

}
