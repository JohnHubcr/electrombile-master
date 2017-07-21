package com.zenchn.electrombile.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.model.TcpCmdEnum;
import com.zenchn.electrombile.eventBus.TcpCmdRequestStatusEvent;
import com.zenchn.electrombile.eventBus.TcpCmdResultEvent;
import com.zenchn.electrombile.service.CoreService;
import com.zenchn.electrombile.service.TcpCmdBinder;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;
import com.zenchn.electrombile.wrapper.TcpCmdWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/24 15:15
 * 描    述：
 * 修订记录：
 */
public class VehicleControlActivity extends BaseActivity implements ServiceConnection {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_lock)
    ImageView ivLock;
    @BindView(R.id.iv_unlock)
    ImageView ivUnlock;
    @BindView(R.id.ll_lock_button)
    LinearLayout llLockButton;
    @BindView(R.id.iv_pStatus_on)
    ImageView ivPStatusOn;
    @BindView(R.id.iv_pStatus_off)
    ImageView ivPStatusOff;
    @BindView(R.id.ll_pStatus_button)
    LinearLayout llPStatusButton;
    @BindView(R.id.ll_light_button)
    LinearLayout llLightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bindService(new Intent(this, CoreService.class), this, Context.BIND_AUTO_CREATE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_vehicle_control));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_control;
    }


    @OnClick({R.id.ll_back, R.id.ll_lock, R.id.ll_unlock, R.id.ll_pStatus_on, R.id.ll_pStatus_off, R.id.ll_light_on, R.id.ll_light_off})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_lock:
                TcpCmdWrapper.sendTcpCmd(TcpCmdEnum.远程锁车);
                break;
            case R.id.ll_unlock:
                TcpCmdWrapper.sendTcpCmd(TcpCmdEnum.远程解锁);
                break;
            case R.id.ll_pStatus_on:
                TcpCmdWrapper.sendTcpCmd(TcpCmdEnum.远程设防);
                break;
            case R.id.ll_pStatus_off:
                TcpCmdWrapper.sendTcpCmd(TcpCmdEnum.远程撤防);
                break;
            case R.id.ll_light_on:

                break;
            case R.id.ll_light_off:

                break;
        }
        showProgress();
        setViewEnabled(view, false);
    }

    @Subscribe(sticky = true, priority = 1)
    public void onEventMainThread(TcpCmdResultEvent event) {
        String tcpCmdName = event.getTcpCmdName();
        boolean executeSuccess = event.isExecuteSuccess();
        syncViewStatus(event.getTcpCmdStack());//同步控件状态
        EventBus.getDefault().cancelEventDelivery(event);//取消事件继续发送
        EventBus.getDefault().removeStickyEvent(event);//删除该事件
        setViewStatus(tcpCmdName, true);
        CommonDialogFactory
                .createQueryCmdDialog(this, String.format(getString(executeSuccess ? R.string.cmd_execute_success : R.string.cmd_execute_failure), tcpCmdName), executeSuccess, Constants
                        .SHOW_DIALOG_LONG_TIME);
    }

    /**
     * 同步控件状态
     *
     * @param tcpCmdStack
     */
    private void syncViewStatus(Set<String> tcpCmdStack) {
        if (tcpCmdStack != null && !tcpCmdStack.isEmpty())
            for (String tcpCmdName : tcpCmdStack) {
                setViewStatus(tcpCmdName, false);
            }
    }

    @Subscribe
    public void onEventMainThread(TcpCmdRequestStatusEvent event) {
        hideProgress();
        String tcpCmdName = event.getTcpCmdName();
        boolean sendSuccess = event.isSendSuccess();
        setViewStatus(tcpCmdName, !sendSuccess);//发送失败设置指令任务已完成
        CommonDialogFactory
                .createSendTcpCmdStatusDialog(this, String.format(getString(sendSuccess ? R.string.cmd_send_success : R.string.cmd_send_failure), tcpCmdName), sendSuccess, Constants
                        .SHOW_DIALOG_SHORT_TIME);
    }

    /**
     * 设置指令状态在界面上面的显示
     *
     * @param tcpCmdName
     * @param isCompleted 是否执行/执行完成
     */
    public void setViewStatus(String tcpCmdName, boolean isCompleted) {
        if (TcpCmdEnum.远程撤防.name().equals(tcpCmdName) || TcpCmdEnum.远程设防.name().equals(tcpCmdName)) {
            setViewEnabled(llPStatusButton, isCompleted);
        } else if (TcpCmdEnum.远程锁车.name().equals(tcpCmdName) || TcpCmdEnum.远程解锁.name().equals(tcpCmdName)) {
            setViewEnabled(llLockButton, isCompleted);
        } else if (TcpCmdEnum.远程开灯.name().equals(tcpCmdName) || TcpCmdEnum.远程关灯.name().equals(tcpCmdName)) {
            setViewEnabled(llLightButton, isCompleted);
        }
    }

    /**
     * 设置控件是否可以点击或者取消点击
     *
     * @param view
     * @param isEnabled
     */
    private void setViewEnabled(View view, boolean isEnabled) {
        CommonUtils.setViewGroupEnabled(view, isEnabled);
        if (view == llLockButton) {
            ivLock.setImageResource(isEnabled ? R.drawable.lock : R.drawable.lock_gray);
            ivUnlock.setImageResource(isEnabled ? R.drawable.unlock : R.drawable.unlock_gray);
        } else if (view == llPStatusButton) {
            ivPStatusOn.setImageResource(isEnabled ? R.drawable.bug_on : R.drawable.bug_on_gray);
            ivPStatusOff.setImageResource(isEnabled ? R.drawable.bug_off : R.drawable.bug_off_gray);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        TcpCmdBinder tcpCmdBinder = (TcpCmdBinder) service;
        Set<String> tcpCmdStack = tcpCmdBinder.getTcpCmdStack();
        syncViewStatus(tcpCmdStack);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

}
