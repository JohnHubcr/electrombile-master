package com.zenchn.electrombile.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.adapter.RepairOutletsAdapter;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.RepairStationInfo;
import com.zenchn.electrombile.presenter.RepairOutletsPresenter;
import com.zenchn.electrombile.presenter.impl.RepairOutletsPresenterImpl;
import com.zenchn.electrombile.ui.view.RepairOutletsView;
import com.zenchn.electrombile.widget.RecyclerView.decoration.SimpleDecoration;
import com.zenchn.electrombile.widget.RecyclerView.wrapper.LoadMoreWrapperAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 作    者：wangr on 2017/2/24 15:17
 * 描    述：
 * 修订记录：
 */
public class RepairOutletsActivity extends BaseActivity implements RepairOutletsView, LoadMoreWrapperAdapter.OnLoadMoreListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private static final int PAGE_SIZE = 5;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.bl_refresh)
    BGARefreshLayout blRefresh;

    private RepairOutletsPresenter presenter;
    private LoadMoreWrapperAdapter wrapperAdapter;
    private int pageNumber;

    private List<RepairStationInfo> mData;
    private BDLocation bdLocation;
    private RepairOutletsAdapter adapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public RepairOutletsActivity() {
        presenter = new RepairOutletsPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initRecycleView();
        initRefreshLayout();
        initData();
    }

    private void initRecycleView() {

        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(this));

        //设置adapter
        adapter = new RepairOutletsAdapter(this, mData = new ArrayList<>());
        wrapperAdapter = new LoadMoreWrapperAdapter(adapter);
        wrapperAdapter
                .setOnLoadMoreListener(this)
                .setLoadMoreView(R.layout.recyclerview_default_loading);

        rv.setAdapter(wrapperAdapter);

        //设置Item增加、移除动画
        rv.setItemAnimator(new DefaultItemAnimator());

        //添加分割线
        rv.addItemDecoration(new SimpleDecoration(this, SimpleDecoration.VERTICAL_LIST));

    }

    private void initRefreshLayout() {
        // 为BGARefreshLayout设置代理
        blRefresh.setDelegate(this);

        // 设置下拉刷新和上拉加载更多的风格
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);

        // 设置下拉刷新和上拉加载更多的风格
        blRefresh.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_charging_outlets));
        presenter.getBDLocation(getApplicationContext());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repair_outlets;
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
    public void getBDLocationSuccess(BDLocation bdLocation) {
        if (bdLocation != null) {
            this.bdLocation = bdLocation;
            presenter.searchServiceStation(loginInfo.getAccessToken(), bdLocation, pageNumber = 1, PAGE_SIZE, Constants.ServiceType.repair, true);
        } else {
            showResMessage(R.string.no_location_info);
        }
    }

    @Override
    public void showLocationFailure() {
        showResMessage(R.string.no_location_info);
    }

    @Override
    public void onLoadError() {
        wrapperAdapter.setLoadError();
        wrapperAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSearchSuccess(List<RepairStationInfo> stationInfoList, int pageNumber, int totalPages) {
        if (stationInfoList != null && !stationInfoList.isEmpty()) {
            mData.addAll(stationInfoList);
            adapter.setLatLng(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
            wrapperAdapter.setLoadSuccess(pageNumber, totalPages);
            wrapperAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadMoreErrorReload() {
        presenter.searchServiceStation(loginInfo.getAccessToken(), bdLocation, pageNumber, PAGE_SIZE, Constants.ServiceType.repair, false);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.searchServiceStation(loginInfo.getAccessToken(), bdLocation, ++pageNumber, PAGE_SIZE, Constants.ServiceType.repair, false);
    }

    @Override
    public void onLoadMoreEnd() {
    }

    @Override
    public void onRefreshCompleted() {
        mData.clear();
        if (blRefresh != null)
            blRefresh.endRefreshing();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        presenter.searchServiceStation(loginInfo.getAccessToken(), bdLocation, pageNumber = 1, PAGE_SIZE, Constants.ServiceType.repair, true);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
