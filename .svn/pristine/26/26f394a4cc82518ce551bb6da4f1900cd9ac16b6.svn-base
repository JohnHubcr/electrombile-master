package com.zenchn.electrombile.ui.fragment;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseHomePageFragment;
import com.zenchn.electrombile.entity.AlarmMessageInfo;
import com.zenchn.electrombile.ui.activity.AlarmMessageActivity;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.mlibrary.utils.DateUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageFragment extends BaseHomePageFragment {

    @BindView(R.id.iv_alarm_point)
    ImageView ivAlarmPoint;
    @BindView(R.id.tv_alarm_time)
    TextView tvAlarmTime;
    @BindView(R.id.tv_alarm_desc)
    TextView tvAlarmDesc;

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.fragment_title_message);
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData() {
        initAlarmCache();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isHideFunction != null)
            initAlarmCache();
    }

    private void initAlarmCache() {
        AlarmMessageInfo alarmMessage = ACacheWrapper.getAlarmMessage();
        if (alarmMessage != null) {
            ivAlarmPoint.setVisibility(View.VISIBLE);
            tvAlarmTime.setVisibility(View.VISIBLE);
            tvAlarmDesc.setVisibility(View.VISIBLE);

            String msgContent = alarmMessage.getMsgContent();
            if (CommonUtils.isNonNull(msgContent))
                tvAlarmDesc.setText(msgContent);

            String msgDate = DateUtils.convertDateToString(alarmMessage.getMsgDate(), "yyyy-MM-dd HH:mm:ss");
            if (CommonUtils.isNonNull(msgDate))
                tvAlarmTime.setText(msgDate);

        } else {
            ivAlarmPoint.setVisibility(View.INVISIBLE);
            tvAlarmTime.setVisibility(View.GONE);
            tvAlarmDesc.setVisibility(View.GONE);
        }
    }

    @Override
    protected void handler(Message msg) {
    }

    @OnClick({R.id.ll_alarm, R.id.ll_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_alarm:
                if (isHideFunction == null) {
                    CommonDialogFactory.createBindDialog(getActivity()).show();
                } else {
                    startActivity(new Intent(getActivity(), AlarmMessageActivity.class));
                }
                break;
            case R.id.ll_message:
                if (isHideFunction == null) {
                    CommonDialogFactory.createBindDialog(getActivity()).show();
                } else {
                    CommonDialogFactory.createSimpleDialog(getActivity(), getString(R.string.coming_soon), Constants.SHOW_DIALOG_SHORT_TIME);
                }
//                startActivity(new Intent(getActivity(), SystemMessageActivity.class));
                break;
        }
    }

}
