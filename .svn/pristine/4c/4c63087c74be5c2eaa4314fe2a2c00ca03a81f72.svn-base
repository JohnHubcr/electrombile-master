package com.zenchn.electrombile.ui.fragment;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseFragment;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForSTInfo;
import com.zenchn.electrombile.eventBus.VehicleCheckResultForSTInfoEvent;
import com.zenchn.electrombile.widget.RadarScanView;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * 作    者：wangr on 2017/2/24 15:40
 * 描    述：
 * 修订记录：
 */
public class VehicleCheckSTFragment extends BaseFragment {

    @BindView(R.id.radar)
    RadarScanView radar;

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_vehicle_check_st;
    }

    @Override
    protected void initData() {
        radar.startScan();
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEventMainThread(VehicleCheckResultForSTInfoEvent event) {
        showVehicleCheckResult(event.getVehicleCheckResultForSTInfo());
    }

    private void showVehicleCheckResult(VehicleCheckResultForSTInfo vehicleCheckResultForSTInfo) {

    }

}
