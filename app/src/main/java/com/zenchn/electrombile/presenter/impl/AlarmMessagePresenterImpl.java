package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.ListApi;
import com.zenchn.electrombile.api.callback.ApiListCallback;
import com.zenchn.electrombile.entity.AlarmMessageInfo;
import com.zenchn.electrombile.presenter.AlarmMessagePresenter;
import com.zenchn.electrombile.ui.view.AlarmMessageView;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/2 17:36
 * 描    述：
 * 修订记录：
 */
public class AlarmMessagePresenterImpl implements AlarmMessagePresenter, ApiListCallback<List<AlarmMessageInfo>> {

    private AlarmMessageView alarmMessageView;
    private boolean isRefresh;

    public AlarmMessagePresenterImpl(AlarmMessageView alarmMessageView) {
        this.alarmMessageView = alarmMessageView;
    }

    @Override
    public void getAlarmMessageList(String accessToken, String serialNumber, int pageNo, int pageSize, String type, boolean isRefresh) {
        if (alarmMessageView != null) {
            this.isRefresh = isRefresh;
            ListApi
                    .getInstance()
                    .getAlarmMessageList(accessToken, serialNumber, pageNo, pageSize, type, this);
        }
    }

    @Override
    public void onDestroy() {
        alarmMessageView = null;
    }

    @Override
    public void onSuccess(List<AlarmMessageInfo> data, int pageNumber, int totalPages) {
        if (alarmMessageView != null) {
            if (isRefresh)
                alarmMessageView.onRefreshCompleted();
            alarmMessageView.showMessageList(data, pageNumber, totalPages);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (alarmMessageView != null) {
            alarmMessageView.onResponseError(err_msg);
            alarmMessageView.onLoadError();
            if (isRefresh)
                alarmMessageView.onRefreshCompleted();
        }
    }

    @Override
    public void onFailure() {
        if (alarmMessageView != null) {
            alarmMessageView.onFailure();
            alarmMessageView.onLoadError();
            if (isRefresh)
                alarmMessageView.onRefreshCompleted();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (alarmMessageView != null) {
            alarmMessageView.hideProgress();
            alarmMessageView.onGrantRefuse();
        }
    }

}
