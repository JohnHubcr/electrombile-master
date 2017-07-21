package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.UrgentContactApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.presenter.UrgentContactPresenter;
import com.zenchn.electrombile.ui.view.UrgentContactView;

/**
 * 作    者：wangr on 2017/2/22 18:33
 * 描    述：
 * 修订记录：
 */
public class UrgentContactPresenterImpl implements UrgentContactPresenter, ApiCallback<String> {

    private UrgentContactView urgentContactView;

    public UrgentContactPresenterImpl(UrgentContactView urgentContactView) {
        this.urgentContactView = urgentContactView;
    }

    @Override
    public void onDestroy() {
        urgentContactView = null;
    }

    @Override
    public void updateUrgentContact(String accessToken, String urgentContact) {
        if (urgentContactView != null) {
            urgentContactView.showProgress();
            UrgentContactApi
                    .getInstance()
                    .updateUrgentContact(accessToken, urgentContact, this);
        }
    }

    @Override
    public void onSuccess(String urgentContact) {
        if (urgentContactView != null) {
            urgentContactView.hideProgress();
            urgentContactView.updateUrgentContact(urgentContact);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (urgentContactView != null) {
            urgentContactView.hideProgress();
            urgentContactView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (urgentContactView != null) {
            urgentContactView.hideProgress();
            urgentContactView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (urgentContactView != null) {
            urgentContactView.hideProgress();
            urgentContactView.onGrantRefuse();
        }
    }

}
