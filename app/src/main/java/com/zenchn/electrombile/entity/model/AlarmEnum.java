package com.zenchn.electrombile.entity.model;

/**
 * 作    者：wangr on 2017/3/2 21:07
 * 描    述：
 * 修订记录：
 */
public enum AlarmEnum {

    解除警报("clearAlarm") {
        @Override
        public String getAlarmDesc() {
            return "警报解除,恢复正常！";
        }
    },
    鞍座报警("saddleAlert") {
        @Override
        public String getAlarmDesc() {
            return "警报！鞍座被打开";
        }
    },
    电池报警("powerAlert") {
        @Override
        public String getAlarmDesc() {
            return "警报！电池已被盗";
        }
    },
    震动报警("shakeAlert") {
        @Override
        public String getAlarmDesc() {
            return "警报！震动报警";
        }
    },
    位移报警("moveAlert") {
        @Override
        public String getAlarmDesc() {
            return "警报！电动车被盗";
        }
    },
    动态侧翻报警("sideAlert") {
        @Override
        public String getAlarmDesc() {
            return "警报！电动车动态侧翻";
        }
    },
    静态侧翻报警("staticSideAlert") {
        @Override
        public String getAlarmDesc() {
            return "警报！电动车静态侧翻";
        }
    },
    新报警("new") {
        @Override
        public String getAlarmDesc() {
            return "警报！您的电动车即将炸毁";
        }
    };

    public String alarmType;

    AlarmEnum(String alarmType) {
        this.alarmType = alarmType;
    }

    public abstract String getAlarmDesc();

    public static AlarmEnum classifyAlarmByMsgType(String msgType) {
        for (AlarmEnum alarmEnum : values()) {
            if (alarmEnum.alarmType.equals(msgType))
                return alarmEnum;
        }
        return AlarmEnum.新报警;
    }
}
