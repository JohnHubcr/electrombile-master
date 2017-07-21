package com.zenchn.electrombile.wrapper.callback;

import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * 作    者：wangr on 2017/3/1 13:51
 * 描    述：
 * 修订记录：
 */

public interface BDReverseGeoCallback {

    void onGetBDGeoCodeSuccess(ReverseGeoCodeResult reverseGeoCodeResult);

    void onGetBDGeoCodeFailure();

}
