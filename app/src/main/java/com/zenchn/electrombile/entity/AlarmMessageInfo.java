package com.zenchn.electrombile.entity;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/3/2 17:51
 * 描    述：报警消息的描述类
 * 修订记录：
 */
public class AlarmMessageInfo implements Serializable {

    private String msgType;// 消息类型
    private String msgTitle;// 消息标题
    private String msgContent;// 消息内容
    private long msgDate;// 接收时间
    private LatLng vehicleLatLng;// 车辆定位坐标
    private String vehicleAddress;// 车辆定位信息

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public long getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(long msgDate) {
        this.msgDate = msgDate;
    }

    public LatLng getVehicleLatLng() {
        return vehicleLatLng;
    }

    public void setVehicleLatLng(LatLng vehicleLatLng) {
        this.vehicleLatLng = vehicleLatLng;
    }

    public String getVehicleAddress() {
        return vehicleAddress;
    }

    public void setVehicleAddress(String vehicleAddress) {
        this.vehicleAddress = vehicleAddress;
    }

    @Override
    public String toString() {
        return "AlarmMessageInfo{" +
                "msgType='" + msgType + '\'' +
                ", msgTitle='" + msgTitle + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", msgDate=" + msgDate +
                ", vehicleLatLng=" + vehicleLatLng +
                ", vehicleAddress='" + vehicleAddress + '\'' +
                '}';
    }
}
