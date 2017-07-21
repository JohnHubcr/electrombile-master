package com.zenchn.electrombile.entity;

/**
 * 作    者：wangr on 2017/3/13 14:08
 * 描    述：远程指令(千万千万不要混淆！！！)
 * 修订记录：
 */
public class CommandModel {

    private String command;// 指令代码 ,
    private String parameter;// 指令参数  "" 表示不需要
    private int comtype;// 类型 0查询, 1设置 , 3 远程指令
    private String remarks;// 文字备注信息
    private String clientid;//发送设备 安卓 IOS 平台等

    public CommandModel(String command) {
        super();
        this.command = command;
        this.parameter = "";
        this.clientid = "Android";
    }

    public CommandModel(String command, String parameter, int comtype) {
        super();
        this.command = command;
        this.parameter = parameter;
        this.comtype = comtype;
        this.remarks = "";
        this.clientid = "Android";
    }

    public CommandModel(String command, String parameter, int comtype, String remarks) {
        super();
        this.command = command;
        this.parameter = parameter;
        this.comtype = comtype;
        this.remarks = remarks;
        this.clientid = "Android";
    }

    public CommandModel(String command, String parameter, int comtype, String remarks, String clientid) {
        this.command = command;
        this.parameter = parameter;
        this.comtype = comtype;
        this.remarks = remarks;
        this.clientid = clientid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getComtype() {
        return comtype;
    }

    public void setComtype(int comtype) {
        this.comtype = comtype;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "CommandModel{" +
                "command='" + command + '\'' +
                ", parameter='" + parameter + '\'' +
                ", comtype=" + comtype +
                ", remarks='" + remarks + '\'' +
                ", clientid='" + clientid + '\'' +
                '}';
    }
}
