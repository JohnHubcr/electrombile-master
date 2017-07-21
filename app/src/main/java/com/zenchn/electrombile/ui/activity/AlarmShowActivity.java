package com.zenchn.electrombile.ui.activity;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.AlarmEventBus;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.model.AlarmEnum;
import com.zenchn.electrombile.eventBus.AlarmEvent;
import com.zenchn.electrombile.widget.AnimationFactory;
import com.zenchn.mlibrary.utils.DateUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 9:39
 * 描    述：报警消息的提示类
 * 修订记录：
 */

public class AlarmShowActivity extends BaseActivity {

    @BindView(R.id.tv_alarm_desc)
    TextView tvAlarmDesc;
    @BindView(R.id.tv_alarm_time)
    TextView tvAlarmTime;
    @BindView(R.id.ll_alarm)
    LinearLayout llAlarm;
    @BindView(R.id.ll_alarm_show)
    LinearLayout llAlarmShow;

    private ArgbEvaluator argbEvaluator;
    int[] pageColors = new int[]{0x86222222, Color.TRANSPARENT};
    private int count = 0;
    private long offsetTime = 40;
    private long duration = 800;
    private boolean isFirstTouch = true;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        argbEvaluator = new ArgbEvaluator();
        String alarmType = getIntent().getStringExtra("alarmType");
        AlarmEnum alarmEnum = AlarmEnum.classifyAlarmByMsgType(alarmType);
        tvAlarmDesc.setText(alarmEnum.getAlarmDesc());
        tvAlarmTime.setText(DateUtils.getStandardCurrentTime());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alarm_show;
    }

    @OnClick({R.id.ll_ignore, R.id.ll_follow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_ignore:
                onBackPressed();
                break;
            case R.id.ll_follow:
                startActivity(new Intent(this, AlarmMessageActivity.class));
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlarmEventBus.getSpecialTrain().post(new AlarmEvent(AlarmEnum.解除警报));
        if (isFirstTouch) {
            startMissAnimation();
        }
    }

    @Override
    public boolean useUiHandler() {
        return true;
    }

    private void startMissAnimation() {
        isFirstTouch = false;// 限定只允许执行一次

        AnimationSet animationSet = AnimationFactory.createAlertDialogMissAnimation(false, duration);
        llAlarm.setAnimation(animationSet);
        animationSet.start();
        animationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        count++;
                        float positionOffset = offsetTime * count * 1.0f / duration;
                        int bgColor = (int) argbEvaluator.evaluate(positionOffset, pageColors[0], pageColors[1]);
                        llAlarmShow.setBackgroundColor(bgColor);
                        mHandler.postDelayed(this, offsetTime);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.removeCallbacksAndMessages(null);
                finish();
            }
        });
    }

}
