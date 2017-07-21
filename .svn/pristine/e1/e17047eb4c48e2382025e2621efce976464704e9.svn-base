package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;
import com.zenchn.electrombile.entity.InsuranceClaimInfo;

import java.io.File;
import java.util.Map;

/**
 * 作    者：wangr on 2017/3/8 14:53
 * 描    述：
 * 修订记录：
 */
public interface InsuranceClaimPresenter extends BasePresenter {

    /**
     * 提交车辆理赔信息
     *
     * @param accessToken
     * @param serialNumber
     * @param insuranceClaimInfo
     * @param fileParams
     */
    void submitInsuranceClaimInfo(String accessToken, String serialNumber,InsuranceClaimInfo insuranceClaimInfo, Map<String, File> fileParams);

    /**
     * 查询车辆理赔信息
     *
     * @param accessToken
     * @param serialNumber
     */
    void getUserClaims(String accessToken, String serialNumber);

}
