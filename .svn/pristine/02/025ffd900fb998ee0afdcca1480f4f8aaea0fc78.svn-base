package com.zenchn.electrombile.service;

import android.app.IntentService;
import android.content.Intent;

import com.zenchn.electrombile.BuildConf;


/**
 * 作    者：wangr on 2017/2/21 9:35
 * 描    述：
 * 修订记录：
 */
public class AsynTaskService extends IntentService {

    public AsynTaskService() {
        super("com.zenchn.electrombile.service.AsynTaskService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String type = intent.getStringExtra("type");

        switch (type) {

            case BuildConf.IntentType.UPDATE_APP_TYPE:
                updateApp();
                break;
        }

    }

    /**
     * 检测app版本
     */
    private void updateApp() {

    }


}
