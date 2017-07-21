package com.zenchn.electrombile.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForJKInfo;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForSTInfo;
import com.zenchn.electrombile.eventBus.VehicleCheckResultForJKInfoEvent;
import com.zenchn.electrombile.eventBus.VehicleCheckResultForSTInfoEvent;
import com.zenchn.electrombile.presenter.VehicleCheckPresenter;
import com.zenchn.electrombile.presenter.impl.VehicleCheckPresenterImpl;
import com.zenchn.electrombile.ui.fragment.VehicleCheckJKFragment;
import com.zenchn.electrombile.ui.fragment.VehicleCheckSTFragment;
import com.zenchn.electrombile.ui.view.VehicleCheckView;
import com.zenchn.electrombile.utils.EquModelUtils;
import com.zenchn.electrombile.widget.CircleTextImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/24 15:17
 * 描    述：
 * 修订记录：
 */
public class VehicleCheckActivity extends BaseActivity implements VehicleCheckView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    CircleTextImageView ivHead;
    @BindView(R.id.tv_vehicle_grade)
    TextView tvVehicleGrade;
    @BindView(R.id.tv_vehicle_status)
    TextView tvVehicleStatus;
    @BindView(R.id.tv_vehicle_suggest)
    TextView tvVehicleSuggest;
    @BindView(R.id.tv_vehicle_check_title)
    TextView tvVehicleCheckTitle;

    private Boolean checkStatus;
    private boolean whetherEquModelForST;

    private VehicleCheckPresenter presenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public VehicleCheckActivity() {
        presenter = new VehicleCheckPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_vehicle_check));
        setCheckStatus(null);//设置状态为未体检
        whetherEquModelForST = EquModelUtils.isEquModelForST(loginInfo.getEquModel());
        initFragment(whetherEquModelForST);
    }

    /**
     * 根据协议设置显示
     *
     * @param whetherEquModelForST
     */
    private void initFragment(boolean whetherEquModelForST) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, whetherEquModelForST ? new VehicleCheckSTFragment() : new VehicleCheckJKFragment())
                .commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vehicle_check;
    }

    @Override
    public boolean useUiHandler() {
        return true;
    }

    @OnClick({R.id.ll_back, R.id.ll_vehicle_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_vehicle_check:
                if (checkStatus == null || !checkStatus) {
                    setCheckStatus(true);//设置状态为体检中
                    initVehicleCheck();//先从本地缓存中获取
                } else {
                    showResMessage(R.string.vehicle_checking);
                }
                break;
        }
    }

    private void initVehicleCheck() {
        presenter.getVehicleStatus(loginInfo.getAccessToken(), loginInfo.getSerialNumber(), loginInfo.getEquModel());
    }

    @Override
    public void setCheckCompleted() {
        setCheckStatus(false);//设置状态为体检完成
    }

    @Override
    public void onParserVehicleCheckSuccess(VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo) {
        EventBus.getDefault().post(new VehicleCheckResultForJKInfoEvent(vehicleCheckResultForJKInfo));
    }

    @Override
    public void onParserVehicleCheckSuccess(VehicleCheckResultForSTInfo vehicleCheckResultForSTInfo) {
        EventBus.getDefault().post(new VehicleCheckResultForSTInfoEvent(vehicleCheckResultForSTInfo));
    }

    /**
     * 设置体检状态
     *
     * @param checkStatus
     */
    private void setCheckStatus(Boolean checkStatus) {
        this.checkStatus = checkStatus;
        if (checkStatus != null && !checkStatus)
            tvVehicleStatus.setText(getString(R.string.vehicle_check_completed));
        tvVehicleCheckTitle.setText(checkStatus == null ? getString(R.string.vehicle_check_pre) : (getString(checkStatus ? R.string.vehicle_check_ing : R.string.vehicle_check_after)));

    }

    @Override
    public void setCurrentScanTitle(String title) {
        tvVehicleStatus.setText(String.format(getString(R.string.vehicle_check_current_item), title));
    }

    @Override
    public void setMotorGrade(int troubleCount) {
        if (troubleCount < 1) {
            tvVehicleGrade.setText("优");
            tvVehicleGrade.setTextColor(getResources().getColor(R.color.green_base));
            tvVehicleSuggest.setText(getString(R.string.vehicle_check_grade_nice));
        } else if (troubleCount < 2) {
            tvVehicleGrade.setText("良");
            tvVehicleGrade.setTextColor(getResources().getColor(R.color.yellow_base));
            tvVehicleSuggest.setText(getString(R.string.vehicle_check_grade_fine));
        } else {
            tvVehicleGrade.setText("危险");
            tvVehicleGrade.setTextColor(getResources().getColor(R.color.red_base));
            tvVehicleSuggest.setText(getString(R.string.vehicle_check_grade_bad));
        }
    }

}
