package com.zenchn.electrombile.engine.bean;


public class ST_BMSTrouble {

    boolean TempDL;// 放电低温
    boolean TempDH;// 放电高温
    boolean TempCL;// 充电低温
    boolean TempCH;// 充电高温
    boolean CurrD;// 放电过流
    boolean CurrC;// 充电过流
    boolean VoltL;// 过放保护
    boolean VoltH;// 过压保护

    public ST_BMSTrouble(boolean tempDL, boolean tempDH, boolean tempCL, boolean tempCH, boolean currD, boolean currC, boolean voltL, boolean voltH) {
        super();
        TempDL = tempDL;
        TempDH = tempDH;
        TempCL = tempCL;
        TempCH = tempCH;
        CurrD = currD;
        CurrC = currC;
        VoltL = voltL;
        VoltH = voltH;
    }

    public boolean isTempDL() {
        return TempDL;
    }

    public void setTempDL(boolean tempDL) {
        TempDL = tempDL;
    }

    public boolean isTempDH() {
        return TempDH;
    }

    public void setTempDH(boolean tempDH) {
        TempDH = tempDH;
    }

    public boolean isTempCL() {
        return TempCL;
    }

    public void setTempCL(boolean tempCL) {
        TempCL = tempCL;
    }

    public boolean isTempCH() {
        return TempCH;
    }

    public void setTempCH(boolean tempCH) {
        TempCH = tempCH;
    }

    public boolean isCurrD() {
        return CurrD;
    }

    public void setCurrD(boolean currD) {
        CurrD = currD;
    }

    public boolean isCurrC() {
        return CurrC;
    }

    public void setCurrC(boolean currC) {
        CurrC = currC;
    }

    public boolean isVoltL() {
        return VoltL;
    }

    public void setVoltL(boolean voltL) {
        VoltL = voltL;
    }

    public boolean isVoltH() {
        return VoltH;
    }

    public void setVoltH(boolean voltH) {
        VoltH = voltH;
    }

    @Override
    public String toString() {
        return (VoltH ? "" : "过压保护" + "\n" + "") +
                (VoltL ? "" : "过放保护" + "\n" + "") +
                (CurrC ? "充电过流" + "\n" : "") +
                (CurrD ? "放电过流" + "\n" : "") +
                (TempCH ? "充电高温" + "\n" : "") +
                (TempCL ? "充电低温" + "\n" : "") +
                (TempDH ? "放电高温" + "\n" : "") +
                (TempDL ? "放电低温" : "");
    }


}
