package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zbar.lib.CaptureActivity;
import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.adapter.VehicleListAdapter;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.VehicleRecordInfo;
import com.zenchn.electrombile.presenter.VehicleListPresenter;
import com.zenchn.electrombile.presenter.impl.VehicleListPresenterImpl;
import com.zenchn.electrombile.ui.view.VehicleListView;
import com.zenchn.electrombile.widget.RecyclerView.listener.OnGetFlagListener;
import com.zenchn.electrombile.widget.RecyclerView.listener.OnItemGroupClickListener;
import com.zenchn.electrombile.widget.RecyclerView.decoration.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/28 11:58
 * 描    述：车辆列表显示界面
 * 修订记录：
 */
public class VehicleListActivity extends BaseActivity implements VehicleListView, OnItemGroupClickListener<VehicleRecordInfo>, OnGetFlagListener<Integer> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    private VehicleListAdapter adapter;

    private List<VehicleRecordInfo> mData;
    private VehicleListPresenter presenter;

    private int rawSelectedPosition = -1;
    private int preSelectedPosition;//上一个
    private int selectedPosition;//选中操作的条目标记
    private boolean isCommonVehicleChanged;

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public VehicleListActivity() {
        presenter = new VehicleListPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initRecycleView();
        initData();
    }

    private void initRecycleView() {
        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        rv.setAdapter(adapter = new VehicleListAdapter(this, mData = new ArrayList<>(), this));
        adapter.addOnFlagListener(this);

        //设置Item增加、移除动画
        rv.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        rv.addItemDecoration(new SimpleDecoration(this, SimpleDecoration.VERTICAL_LIST));
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.title_vehicle_list);
        presenter.getVehicleList(loginInfo.getAccessToken());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vehicle_list;
    }

    @OnClick({R.id.ll_back, R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_add:
                startActivity(new Intent(this, CaptureActivity.class));
                break;
        }
    }


    @Override
    public void onItemClick(int position, VehicleRecordInfo vehicleRecordInfo) {
        Intent intent = new Intent(this, VehicleArchivesActivity.class);
        intent.putExtra("serialNumber", vehicleRecordInfo.getSerialNumber());
        intent.putExtra("whetherCommonVehicle", vehicleRecordInfo.isWhetherCommon());
        startActivity(intent);
    }

    @Override
    public void onLeftButtonClick(int position, VehicleRecordInfo vehicleRecordInfo) {
        if (preSelectedPosition != position) {
            this.selectedPosition = position;
            mData.get(preSelectedPosition).setWhetherCommon(false);
            mData.get(position).setWhetherCommon(true);
            adapter.notifyDataSetChanged();
            presenter.setCommonVehicle(loginInfo.getAccessToken(), mData.get(position).getId(), mData.get(preSelectedPosition).getId());
        }
    }

    @Override
    public void onRightButtonClick(int position, VehicleRecordInfo vehicleRecordInfo) {
        if (mData.size() == 1 || preSelectedPosition != position) {
            if (Constants.VEHICLE.USER_LEVEL_MAIN == vehicleRecordInfo.getUserLevel()) {
                showResMessage(R.string.unbind_equipment_error_by_main_account);
            } else {
                this.selectedPosition = position;
                presenter.unBindEquipment(loginInfo.getAccessToken(), vehicleRecordInfo);
            }
        } else {
            showResMessage(R.string.unbind_equipment_error_by_is_common);
        }
    }

    @Override
    public void showVehicleList(List<VehicleRecordInfo> data) {
        if (data != null && !data.isEmpty()) {
            mData.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSetCommonVehicleResult(boolean result) {
        if (result) {
            this.isCommonVehicleChanged = true;
            showResMessage(R.string.set_common_vehicle_success);
        } else {
            mData.get(selectedPosition).setWhetherCommon(false);
            mData.get(preSelectedPosition).setWhetherCommon(true);
            adapter.notifyDataSetChanged();
            showResMessage(R.string.set_common_vehicle_failure);
        }
    }

    @Override
    public void onUnbindResponse(boolean result) {
        if (result) {
            showResMessage(R.string.unbind_equipment_success);
            mData.remove(selectedPosition);
            adapter.notifyDataSetChanged();
        } else {
            showResMessage(R.string.unbind_equipment_failure);
        }
    }

    @Override
    public void onBackPressed() {
        if (isCommonVehicleChanged || mData.size() == 0) {
            SwitchActivity
                    .launch(this);
        } else {
            finish();
        }
    }

    @Override
    public void onGetFlag(Integer position) {
        if (rawSelectedPosition == -1) {
            this.rawSelectedPosition = position;
        }
        this.preSelectedPosition = position;
    }
}
