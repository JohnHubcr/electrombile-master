package com.zenchn.electrombile.kit;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.mlibrary.log.LogUtils;

/**
 * 作    者：wangr on 2017/2/24 16:20
 * 描    述：
 * 修订记录：
 */

public class LogKit {

    public static void success(String title, String desc) {
        LogUtils.printCustomLog(BuildConf.LogTags.DEBUG_TAG, title, desc, LogUtils.LOG_LEVEL_D);
    }

    public static void exception(String title, String desc) {
        LogUtils.printCustomLog(BuildConf.LogTags.DEBUG_TAG, title, desc, LogUtils.LOG_LEVEL_E);
    }

    public static void tag(String tag, String desc) {
        LogUtils.printCustomLog(tag, desc, LogUtils.LOG_LEVEL_W);
    }

    public static void logService(String title, String desc) {
        LogUtils.printCustomLog(BuildConf.LogTags.SERVICE_TAG, title, desc, LogUtils.LOG_LEVEL_W);
    }

    public static void logEngine(String desc) {
        LogUtils.printCustomLog(BuildConf.LogTags.ENGINE_TAG, desc, LogUtils.LOG_LEVEL_W);
    }

    public static void logLocation(String desc) {
        LogUtils.printCustomLog(BuildConf.LogTags.LOCATION_TAG, desc, LogUtils.LOG_LEVEL_W);
    }
}
