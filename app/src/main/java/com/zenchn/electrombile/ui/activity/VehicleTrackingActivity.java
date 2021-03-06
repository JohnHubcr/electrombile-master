package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.VehicleLocationInfo;
import com.zenchn.electrombile.entity.model.TcpCmdEnum;
import com.zenchn.electrombile.eventBus.GeoCodeEvent;
import com.zenchn.electrombile.eventBus.VehicleLocationEvent;
import com.zenchn.electrombile.presenter.VehicleTrackingPresenter;
import com.zenchn.electrombile.presenter.impl.VehicleTrackingPresenterImpl;
import com.zenchn.electrombile.ui.view.VehicleTrackingView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.utils.EquModelUtils;
import com.zenchn.electrombile.utils.MapAppUtils;
import com.zenchn.electrombile.widget.Dialog.SelectDialog;
import com.zenchn.electrombile.wrapper.TcpCmdWrapper;
import com.zenchn.mlibrary.utils.FormatUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * 作    者：wangr on 2017/2/24 15:13
 * 描    述：
 * 修订记录：
 */

public class VehicleTrackingActivity extends BaseActivity implements VehicleTrackingView, SelectDialog.MenuItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_speed)
    TextView tvSpeed;
    @BindView(R.id.tv_power)
    TextView tvPower;
    @BindView(R.id.tv_motor_p_status)
    TextView tvMotorPStatus;
    @BindView(R.id.iv_find)
    ImageView ivFind;

    private VehicleTrackingPresenter presenter;
    private List<String> apps;//本机安装的地图app
    private boolean isLocationOn;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onLocationResume();//恢复定位
        mapView.onResume();// activity
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onLocationPause();//暂停定位
        mapView.onPause();// activity 暂停时同时暂停地图控件
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        mapView.onDestroy();
    }


    public VehicleTrackingActivity() {
        this.presenter = new VehicleTrackingPresenterImpl(this);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public boolean useUiHandler() {
        return true;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initMap();
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vehicle_tracking;
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        mapView.showZoomControls(false);// 去除放大缩小按钮
        presenter.initMap(mapView.getMap());// 缩放比率、默认中心点
        presenter.initBDLocation(new LocationClient(getApplicationContext()));
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.title_vehicle_tracking));
        Boolean isHideFunction = EquModelUtils.isHideFunction(loginInfo.getEquModel());
        if (isHideFunction != null && isHideFunction)
            ivFind.setVisibility(View.GONE);
    }

    /**
     * 显示车辆定位信息
     *
     * @param vehicleLocationInfo
     */
    private void showVehicleLocation(VehicleLocationInfo vehicleLocationInfo) {
        if (vehicleLocationInfo != null) {
            tvPower.setText(String.format(getString(R.string.power), FormatUtils.formatToFloat(vehicleLocationInfo.getPower())));
            tvSpeed.setText(String.format(getString(R.string.speed), FormatUtils.formatToFloat(vehicleLocationInfo.getSpeed())));
            tvMotorPStatus.setText(getString(Constants.DEVICE.P_STATUS_ON == vehicleLocationInfo.getPStatus() ? R.string.p_status_on : R.string.p_status_off));
        }
    }

    /**
     * 显示车辆定位地址
     *
     * @param reverseGeoCodeAddress
     */
    private void showVehicleAddress(ReverseGeoCodeResult reverseGeoCodeAddress) {
        if (reverseGeoCodeAddress != null && CommonUtils.isNonNull(reverseGeoCodeAddress.getAddress())) {
            String location = getResources().getString(R.string.location);
            tvLocation.setText(String.format(location, reverseGeoCodeAddress.getAddress()));
        }
    }

    @OnTouch({R.id.ll_details})
    public boolean onTouchEvent(View view, MotionEvent event) {
        return true;
    }

    @OnClick({R.id.ll_back, R.id.iv_location, R.id.ll_navigation, R.id.iv_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:// 返回
                onBackPressed();
                break;
            case R.id.iv_location:// 定位
                showProgress();
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        switchLocationIcon();
                        hideProgress();
                    }
                }, Constants.DEFINED_DELAY_TIME + new Random().nextInt(Constants.RANDOM_TIME));
                break;
            case R.id.ll_navigation:
                List<String> apps = MapAppUtils.checkLocalMapApp(this);//扫描本机已安装的地图应用
                this.apps = showActionSheet(apps);//显示菜单
                break;
            case R.id.iv_history:
                startActivity(new Intent(this, VehicleContrailActivity.class));
                finish();
                break;

            case R.id.iv_find:
                disabledView(view);
                TcpCmdWrapper.sendTcpCmd(TcpCmdEnum.声光寻车);
                break;
        }
    }

    /**
     * 切换人/车 定位状态
     */
    private void switchLocationIcon() {
        isLocationOn = !isLocationOn;
        ivLocation.setImageResource(isLocationOn ? R.drawable.location_on : R.drawable.location);
        presenter.showMyLocation(isLocationOn);
    }

    /**
     * 设置显示菜单
     *
     * @param selectMap
     * @return
     */
    public List<String> showActionSheet(List<String> selectMap) {
        setTheme(R.style.ActionSheetStyle);
        SelectDialog selectDialog = new SelectDialog(this);
        selectDialog.setCancelButtonTitle("取消");
        selectDialog.setTitle(getString(R.string.select_map));
        if (selectMap.isEmpty())
            selectMap.add(MapAppUtils.BROWSER_DOWNLOAD);
        selectDialog.addItems(selectMap);
        selectDialog.setItemClickListener(this);
        selectDialog.setCancelableOnTouchMenuOutside(true);
        selectDialog.createItems();
        selectDialog.showMenu();

        return selectMap;
    }

    @Override
    public void showWaitForLocation() {
        showResMessage(R.string.please_wait_for_location);
    }

    @Override
    public void showNotLocationMessage() {
        showResMessage(R.string.no_location_info);
    }

    @Subscribe(sticky = true)
    public void onEventMainThread(VehicleLocationEvent event) {
        VehicleLocationInfo vehicleLocationInfo = event.getVehicleLocationInfo();
        showVehicleLocation(vehicleLocationInfo);
        presenter.updateVehicleLocation(vehicleLocationInfo);
    }

    @Subscribe(sticky = true)
    public void onEventMainThread(GeoCodeEvent event) {
        ReverseGeoCodeResult reverseGeoCodeResult = event.getReverseGeoCodeResult();
        showVehicleAddress(reverseGeoCodeResult);
    }

    @Override
    public void onSelectDialogItemClick(int itemPosition) {
        if (apps != null) {
            String mapName = apps.get(itemPosition);
            presenter.toNavigation(mapName);//开启导航
        }
    }
}
