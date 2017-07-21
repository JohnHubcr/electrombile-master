package com.zenchn.electrombile.ui.fragment;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.adapter.CheckSubjectAdapter;
import com.zenchn.electrombile.adapter.bean.CheckSubjectInfo;
import com.zenchn.electrombile.base.BaseFragment;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForJKInfo;
import com.zenchn.electrombile.eventBus.VehicleCheckResultForJKInfoEvent;
import com.zenchn.electrombile.ui.view.VehicleCheckView;
import com.zenchn.electrombile.widget.RecyclerView.decoration.GridDecoration;
import com.zenchn.electrombile.widget.RecyclerView.wrapper.ScanWrapperAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作    者：wangr on 2017/2/24 15:40
 * 描    述：
 * 修订记录：
 */
public class VehicleCheckJKFragment extends BaseFragment implements ScanWrapperAdapter.OnItemClickListener, ScanWrapperAdapter.OnScanListener {

    private VehicleCheckView vehicleCheckView;
    private boolean[] RESULTS;
    private int troubleCount;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.vehicleCheckView = (VehicleCheckView) defaultView;
    }

    @BindView(R.id.rv)
    RecyclerView rv;

    private final static String[] TITLES = new String[]{
            "ECU",
            "仪表盘",
            "充电器",
            "电池",
            "控制器",
            "电子控制单元"};

    private final static int[] ICONS = new int[]{
            R.drawable.gyro,
            R.drawable.dash,
            R.drawable.charger,
            R.drawable.battery,
            R.drawable.controller,
            R.drawable.ecu};

    private ScanWrapperAdapter wrapperAdapter;
    private List<CheckSubjectInfo> mData;
    private VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo;

    public VehicleCheckJKFragment() {
        mData = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            mData.add(new CheckSubjectInfo(TITLES[i], ICONS[i], null));
        }
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_vehicle_check_jk;
    }

    @Override
    protected void initData() {
        initRecycleView();
    }

    private void initRecycleView() {

        // 设置布局管理器
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        // 设置adapter
        wrapperAdapter = new ScanWrapperAdapter(new CheckSubjectAdapter(getActivity(), mData));
        rv.setAdapter(wrapperAdapter);

        // 设置扫描监听
        wrapperAdapter.setScanListener(this);

        // 设置条目监听
        wrapperAdapter.setItemClickListener(this);

        // 设置Item增加、移除动画
        rv.setItemAnimator(new DefaultItemAnimator());

        // 添加分割线
        rv.addItemDecoration(new GridDecoration(getActivity()));

    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEventMainThread(VehicleCheckResultForJKInfoEvent event) {
        showVehicleCheckResult(event.getVehicleCheckResultForJKInfo());
    }

    /**
     * 展示车辆检测结果
     *
     * @param vehicleCheckResultForJKInfo
     */
    private void showVehicleCheckResult(VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo) {
        this.vehicleCheckResultForJKInfo = vehicleCheckResultForJKInfo;
        RESULTS = new boolean[]{
                vehicleCheckResultForJKInfo != null && vehicleCheckResultForJKInfo.getJKEcuTrouble() != null && vehicleCheckResultForJKInfo.getJKEcuTrouble().isHasTrouble(),
                vehicleCheckResultForJKInfo != null && vehicleCheckResultForJKInfo.getJKMeterTrouble() != null && vehicleCheckResultForJKInfo.getJKMeterTrouble().isHasTrouble(),
                vehicleCheckResultForJKInfo != null && vehicleCheckResultForJKInfo.getJKChargerTrouble() != null && vehicleCheckResultForJKInfo.getJKChargerTrouble().isHasTrouble(),
                vehicleCheckResultForJKInfo != null && vehicleCheckResultForJKInfo.getJKBmsTrouble() != null && vehicleCheckResultForJKInfo.getJKBmsTrouble().isHasTrouble(),
                vehicleCheckResultForJKInfo != null && vehicleCheckResultForJKInfo.getJKControlTrouble() != null && vehicleCheckResultForJKInfo.getJKControlTrouble().isHasTrouble(),
                vehicleCheckResultForJKInfo != null && vehicleCheckResultForJKInfo.getJKEcuOnlineStatus() != null && vehicleCheckResultForJKInfo.getJKEcuOnlineStatus().isHasTrouble()};

        int size = mData.size();
        for (int i = 0; i < size; i++) {
            mData.get(i).setHasTrouble(RESULTS[i]);
        }
        wrapperAdapter.showResult();
    }

    @Override
    public void onItemClick(int position) {
        if (vehicleCheckResultForJKInfo != null) {

        }
    }

    @Override
    public void onScanStart() {
        if (vehicleCheckView != null) {
            troubleCount = 0;
            vehicleCheckView.setMotorGrade(troubleCount);
        }
    }

    @Override
    public void onCurrentScanItem(int position) {
        if (vehicleCheckView != null) {
            vehicleCheckView.setCurrentScanTitle(TITLES[position]);
            if (RESULTS[position])
                vehicleCheckView.setMotorGrade(++troubleCount);
        }
    }

    @Override
    public void onScanEnd() {
        if (vehicleCheckView != null) {
            vehicleCheckView.setCheckCompleted();
        }
    }
}
