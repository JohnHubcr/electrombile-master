package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.BaseStationInfo;
import com.zenchn.electrombile.presenter.ServiceMapPresenter;
import com.zenchn.electrombile.presenter.impl.ServiceMapPresenterImpl;
import com.zenchn.electrombile.ui.view.ServiceMapView;
import com.zenchn.electrombile.utils.MapAppUtils;
import com.zenchn.electrombile.widget.Dialog.SelectDialog;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/3/4 12:25
 * 描    述：
 * 修订记录：
 */
public class ServiceMapActivity extends BaseActivity implements ServiceMapView, SelectDialog.MenuItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_location)
    TextView tvLocation;

    private ServiceMapPresenter presenter;
    private boolean isLocationOn;
    private List<String> apps;

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();// activity 恢复时同时恢复地图控件
        presenter.onLocationResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();// activity 暂停时同时暂停地图控件
        presenter.onLocationPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        mapView.onDestroy();
    }

    public ServiceMapActivity() {
        presenter = new ServiceMapPresenterImpl(this);
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initMap();
        initData();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        mapView.showZoomControls(false);// 去除放大缩小按钮
        presenter.initMap(mapView.getMap());// 缩放比率、默认中心点
        presenter.initBDLocation(getApplicationContext());
    }

    @Override
    protected void initData() {
        Intent data = getIntent();
        tvTitle.setText(data.getStringExtra("title"));
        presenter.initData(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_map;
    }

    @OnClick({R.id.ll_back, R.id.ll_call, R.id.ll_navigation, R.id.ll_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;

            case R.id.ll_call:
//                RepairStationInfo repairStationInfo = presenter.getRepairStationInfo();
//                CommonDialogFactory.createCallDialog(this, "确认拨打 " + repairStationInfo.getName() + " 电话？", repairStationInfo.getPhone()).show();
                break;
            case R.id.ll_navigation:
                List<String> apps = MapAppUtils.checkLocalMapApp(this);
                this.apps = showActionSheet(apps);//显示菜单
                break;

            case R.id.ll_location:
                showProgress();
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        switchLocationIcon();
                        hideProgress();
                    }
                }, Constants.DEFINED_DELAY_TIME + new Random().nextInt(Constants.RANDOM_TIME));
                break;
        }
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

    /**
     * 切换定位状态
     */
    private void switchLocationIcon() {
        isLocationOn = !isLocationOn;
        ivLocation.setImageResource(isLocationOn ? R.drawable.location_on : R.drawable.location);
        presenter.showMyLocation(isLocationOn);
    }

    @Override
    public void updateMainStationDetailsInfo(BaseStationInfo baseStationInfo, double distanceValue) {
        if (baseStationInfo != null) {
            tvName.setText(baseStationInfo.getName());
            tvLocation.setText(baseStationInfo.getAddress());
            if (distanceValue > 0)
                tvDistance.setText(distanceValue > 1000f ? String.format(getString(R.string.distance_k), distanceValue / 1000) : String.format(getString(R.string.distance), distanceValue));
        }
    }

    @Override
    public void showNotLocationMessage() {
        showResMessage(R.string.please_wait_for_location);
    }

    @Override
    public void onSelectDialogItemClick(int itemPosition) {
        if (apps != null) {
            String mapName = apps.get(itemPosition);
            presenter.toNavigation(mapName);//开启导航
        }
    }
}
