package com.zenchn.electrombile.eventBus;

/**
 * Created by wangr on 2016/10/31 0031.
 */
public class AccStatusEvent {

    private int accStatus;

    public AccStatusEvent(int accStatus) {
        this.accStatus = accStatus;
    }

    public int getAccStatus() {
        return accStatus;
    }

}
