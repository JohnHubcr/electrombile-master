package com.zenchn.electrombile.eventBus;

import com.zenchn.electrombile.entity.model.AlarmEnum;

/**
 * Created by wangr on 2016/10/31 0031.
 */
public class AlarmEvent {

    private AlarmEnum alarmEnum;

    public AlarmEvent(AlarmEnum alarmEnum) {
        this.alarmEnum = alarmEnum;
    }

    public AlarmEnum getAlarmEnum() {
        return alarmEnum;
    }
}
