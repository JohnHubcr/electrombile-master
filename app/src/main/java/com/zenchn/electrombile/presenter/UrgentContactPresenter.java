package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/2/22 18:32
 * 描    述：
 * 修订记录：
 */
public interface UrgentContactPresenter extends BasePresenter {

    /**
     * 更换紧急联系人
     *
     * @param accessToken
     * @param urgentContact
     */
    void updateUrgentContact(String accessToken, String urgentContact);

}
