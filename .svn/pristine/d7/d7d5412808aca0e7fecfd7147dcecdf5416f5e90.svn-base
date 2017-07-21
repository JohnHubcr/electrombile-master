package com.zenchn.electrombile.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.eventBus.TcpCmdRequestStatusEvent;
import com.zenchn.electrombile.eventBus.TcpCmdResultEvent;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;
import com.zenchn.electrombile.widget.PickerView.OptionsPickerView;
import com.zenchn.electrombile.widget.SlideSwitch;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.electrombile.wrapper.TcpCmdWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/24 15:15
 * 描    述：
 * 修订记录：
 */
public class VehicleSpeedLimitActivity extends BaseActivity implements SlideSwitch.SlideListener, OptionsPickerView.OnOptionsSelectListener {

    private final int LIMIT_SPEED_CLOSE_STATUS = -1;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sw_notifications)
    SlideSwitch swNotifications;
    @BindView(R.id.tv_max_speed_percent)
    TextView tvMaxSpeedPercent;
    @BindView(R.id.ll_control)
    LinearLayout llControl;

    private OptionsPickerView pvOptions;
    private int options;
    private int tempOptions;

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    private void addListener() {
        swNotifications.setSlideListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_vehicle_speed_limit));
        initCache();
    }

    private void initCache() {
        options = ACacheWrapper.getVehicleSpeedLimitOption();
        setLimitStatus(options);
    }

    private void setLimitStatus(int options) {
        if (options == LIMIT_SPEED_CLOSE_STATUS) {
            swNotifications.setState(false);
        } else {
            swNotifications.setState(true);
            String speedPercent = getOptionsItems().get(options);
            tvMaxSpeedPercent.setText(speedPercent);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_limit_speed;
    }

    @Override
    public void open() {
        llControl.setVisibility(View.VISIBLE);
    }

    @Override
    public void close() {
        llControl.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.ll_back, R.id.ll_notifications, R.id.ll_limit_speed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_limit_speed:
                //选项选择器
                if (pvOptions == null) {
                    pvOptions = new OptionsPickerView(this);
                    pvOptions.setTitle("请选择最高时速");//设置选择的标题
                    pvOptions.setCancelable(true);//设置是否可点击消息
                    pvOptions.setPicker(getOptionsItems());//三级联动效果(仅显示一级)
                    pvOptions.setOnOptionsSelectListener(this);//监听确定选择按钮
                }
                //点击弹出选项选择器
                pvOptions.show();
                break;

            case R.id.ll_notifications:
                if (options != LIMIT_SPEED_CLOSE_STATUS) {
                    tempOptions = options;
                    options = LIMIT_SPEED_CLOSE_STATUS;
                } else {
                    options = tempOptions;
                }
                setLimitStatus(options);
                break;
        }
    }

    @NonNull
    private ArrayList<String> getOptionsItems() {
        ArrayList<String> optionsItems = new ArrayList<>();
        optionsItems.add("无限速");
        optionsItems.add("90%");
        optionsItems.add("80%");
        optionsItems.add("70%");
        optionsItems.add("60%");
        optionsItems.add("50%");
        optionsItems.add("40%");
        optionsItems.add("30%");
        optionsItems.add("20%");
        optionsItems.add("10%");
        return optionsItems;
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        this.tempOptions = options1;
        String speedPercent = getOptionsItems().get(options1);
        tvMaxSpeedPercent.setText(speedPercent);
        showProgress();
        TcpCmdWrapper.sendLimitTcpCmd("无限速".equals(speedPercent) ? "0" : speedPercent.replace("%", ""));
    }

    @Subscribe
    public void onEventMainThread(TcpCmdRequestStatusEvent event) {
        hideProgress();
        String tcpCmdName = event.getTcpCmdName();
        boolean sendSuccess = event.isSendSuccess();
        if (sendSuccess)
            ACacheWrapper.saveVehicleSpeedLimitOption(tempOptions);

        CommonDialogFactory
                .createSendTcpCmdStatusDialog(this, String.format(getString(sendSuccess ? R.string.cmd_send_success : R.string.cmd_send_failure), tcpCmdName), sendSuccess, Constants
                        .SHOW_DIALOG_SHORT_TIME);
    }

    @Subscribe(sticky = true)
    public void onEventMainThread(TcpCmdResultEvent event) {
        String tcpCmdName = event.getTcpCmdName();
        boolean executeSuccess = event.isExecuteSuccess();
        if (executeSuccess) {
            this.options = tempOptions;
        } else {
            setLimitStatus(options);
            ACacheWrapper.saveVehicleSpeedLimitOption(options);
        }
        EventBus.getDefault().cancelEventDelivery(event);//取消事件继续发送
        EventBus.getDefault().removeStickyEvent(event);//删除该事件
        CommonDialogFactory.createQueryCmdDialog(this, String.format(getString(executeSuccess ? R.string.cmd_execute_success : R.string.cmd_execute_failure), tcpCmdName), executeSuccess, Constants.SHOW_DIALOG_LONG_TIME);
    }

}
