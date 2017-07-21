package com.zenchn.electrombile.api.callback;

/**
 * 作    者：wangr on 2017/2/26 15:15
 * 描    述：
 * 修订记录：
 */

public interface BindCallback extends GrantCallback{

    void onEquipmentHasBind(String serialNumber, String mobilePhone);

    void onEquipmentWithoutBind(String serialNumber);

    void onBindSuccess();

    void onBindFailure();

    void onBindFailure(String err_msg);
}
