package com.zenchn.electrombile.engine.callback;

import com.zenchn.electrombile.adapter.bean.ContactInfo;

/**
 * 作    者：wangr on 2017/2/27 16:25
 * 描    述：
 * 修订记录：
 */

public interface ContactCallback {

    void addContactInfo(ContactInfo contactInfo);

    void onQueryCompleted();
}
