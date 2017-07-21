package com.zenchn.electrombile.presenter.impl;

import android.app.Activity;

import com.zenchn.electrombile.engine.callback.ContactCallback;
import com.zenchn.electrombile.engine.ContactEngine;
import com.zenchn.electrombile.adapter.bean.ContactInfo;
import com.zenchn.electrombile.presenter.ChooseContactPresenter;
import com.zenchn.electrombile.ui.view.ChooseContactView;

/**
 * 作    者：wangr on 2017/2/27 16:19
 * 描    述：
 * 修订记录：
 */
public class ChooseContactPresenterImpl implements ChooseContactPresenter, ContactCallback {

    private ChooseContactView chooseContactView;

    public ChooseContactPresenterImpl(ChooseContactView chooseContactView) {
        this.chooseContactView = chooseContactView;
    }


    @Override
    public void onDestroy() {
        chooseContactView = null;
    }

    @Override
    public void queryContacts() {
        if (chooseContactView != null) {
            ContactEngine
                    .getInstance()
                    .queryContact((Activity) chooseContactView, this);
        }
    }


    @Override
    public void addContactInfo(ContactInfo contactInfo) {
        if (chooseContactView != null) {
            chooseContactView.addContacts(contactInfo);
        }
    }

    @Override
    public void onQueryCompleted() {
        if (chooseContactView != null) {
            chooseContactView.onQueryCompleted();
        }
    }

}
