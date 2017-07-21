package com.zenchn.electrombile.eventBus;

/**
 * 作    者：wangr on 2017/3/1 21:18
 * 描    述：查询轨迹的事件
 * 修订记录：
 */
public class VehicleContrailQueryEvent {

    private long startTime;
    private long endTime;

    public VehicleContrailQueryEvent(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
