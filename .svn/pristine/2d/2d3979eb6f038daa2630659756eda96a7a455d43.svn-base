package com.zenchn.electrombile.utils;

/**
 * 作    者：wangr on 2017/2/22 14:44
 * 描    述：
 * 修订记录：
 */
public class EquModelUtils {

    //硬件标示信息
    public static final int EQU_MODEL_ST = 4;//神腾协议

    /**
     * @param equModel
     * @return Boolean
     * null表示未激活设备
     * true表示需要屏蔽功能
     * false表示全功能
     */
    public static Boolean isHideFunction(int equModel) {
        return equModel == 0 ? null : equModel == 1 ? true : false;
    }

    public static boolean isEquModelForST(int equModel) {
        return EQU_MODEL_ST == equModel;
    }
}
