package com.zenchn.electrombile.engine.bean;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 作    者：wangr on 2017/3/14 11:07
 * 描    述：ECU故障描述类
 * 修订记录：
 */
public class JK_ECUTrouble {

    boolean ecuBrakeFault;// 刹把故障 b5
    boolean ecuTurningFault;// 转把故障 b6
    boolean ecuConverterException;// 转换器异常 b7
    boolean ecuOverCurrent;// ECU过流 b8

    public JK_ECUTrouble(boolean ecuBrakeFault, boolean ecuTurningFault, boolean ecuConverterException, boolean ecuOverCurrent) {
        this.ecuBrakeFault = ecuBrakeFault;
        this.ecuTurningFault = ecuTurningFault;
        this.ecuConverterException = ecuConverterException;
        this.ecuOverCurrent = ecuOverCurrent;
    }

    public boolean isEcuBrakeFault() {
        return ecuBrakeFault;
    }

    public void setEcuBrakeFault(boolean ecuBrakeFault) {
        this.ecuBrakeFault = ecuBrakeFault;
    }

    public boolean isEcuConverterException() {
        return ecuConverterException;
    }

    public void setEcuConverterException(boolean ecuConverterException) {
        this.ecuConverterException = ecuConverterException;
    }

    public boolean isEcuTurningFault() {
        return ecuTurningFault;
    }

    public void setEcuTurningFault(boolean ecuTurningFault) {
        this.ecuTurningFault = ecuTurningFault;
    }

    public boolean isEcuOverCurrent() {
        return ecuOverCurrent;
    }

    public void setEcuOverCurrent(boolean ecuOverCurrent) {
        this.ecuOverCurrent = ecuOverCurrent;
    }

    @Override
    public String toString() {
        return "刹把故障:" + CommonUtils.formatBoolean(ecuBrakeFault) + "\n"
                + "转把故障:" + CommonUtils.formatBoolean(ecuConverterException) + "\n"
                + "转换器异常:" + CommonUtils.formatBoolean(ecuTurningFault) + "\n"
                + "ECU过流:" + CommonUtils.formatBoolean(ecuOverCurrent);
    }

    /**
     * 是否有异常
     *
     * @return
     */
    public boolean isHasTrouble() {
        return ecuBrakeFault || ecuConverterException || ecuTurningFault || ecuOverCurrent;
    }
}
