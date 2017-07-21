package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/3/2 17:32
 * 描    述：
 * 修订记录：
 */
public interface AlarmMessagePresenter extends BasePresenter {

    /**
     * 获取报警消息
     *
     * @param accessToken
     * @param serialNumber
     * @param pageNo
     * @param pageSize
     * @param type
     * @param isRefresh
     */
    void getAlarmMessageList(String accessToken, String serialNumber, int pageNo, int pageSize, String type, boolean isRefresh);

}
