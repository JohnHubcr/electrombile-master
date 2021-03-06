package com.zenchn.electrombile.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.VehicleLocationInfo;
import com.zenchn.electrombile.eventBus.VehicleContrailQueryEvent;
import com.zenchn.electrombile.eventBus.VehicleContrailQueryListEvent;
import com.zenchn.electrombile.presenter.VehicleContrailPresenter;
import com.zenchn.electrombile.presenter.impl.VehicleContrailPresenterImpl;
import com.zenchn.electrombile.ui.fragment.VehicleContrailFragment;
import com.zenchn.electrombile.ui.view.VehicleContrailView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/24 15:13
 * 描    述：
 * 修订记录：
 */
public class VehicleContrailActivity extends BaseActivity implements VehicleContrailView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    private VehicleContrailPresenter presenter;
    private List<Fragment> fragments;

    public VehicleContrailActivity() {
        presenter = new VehicleContrailPresenterImpl(this);
    }


    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initFragments();
        initData();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new VehicleContrailFragment());
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_vehicle_contrail));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragments.get(0))
                .commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vehicle_contrail;
    }

    @OnClick(R.id.ll_back)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void showVehicleContrail(List<VehicleLocationInfo> vehicleLocationInfoList) {
        EventBus.getDefault().post(new VehicleContrailQueryListEvent(vehicleLocationInfoList));
    }

    @Subscribe
    public void onEventMainThread(VehicleContrailQueryEvent event) {
        long startTime = event.getStartTime();
        long endTime = event.getEndTime();
        presenter.getVehicleContrail(loginInfo.getAccessToken(), loginInfo.getSerialNumber(), loginInfo.getEquModel(), startTime, endTime);
    }

}
