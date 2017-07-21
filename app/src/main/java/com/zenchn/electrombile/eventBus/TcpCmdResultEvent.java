package com.zenchn.electrombile.eventBus;

import java.util.Set;

/**
 * 作    者：wangr on 2017/3/4 20:39
 * 描    述：
 * 修订记录：
 */
public class TcpCmdResultEvent {

    private Set<String> tcpCmdStack;
    private String tcpCmdName;
    private boolean isExecuteSuccess;
    private String msg;

    public TcpCmdResultEvent(String tcpCmdName, boolean isExecuteSuccess, String msg) {
        this.tcpCmdName = tcpCmdName;
        this.isExecuteSuccess = isExecuteSuccess;
        this.msg = msg;
    }

    public TcpCmdResultEvent(Set<String> tcpCmdStack, String tcpCmdName, boolean isExecuteSuccess, String msg) {
        this.tcpCmdStack = tcpCmdStack;
        this.tcpCmdName = tcpCmdName;
        this.isExecuteSuccess = isExecuteSuccess;
        this.msg = msg;
    }

    public Set<String> getTcpCmdStack() {
        return tcpCmdStack;
    }

    public String getTcpCmdName() {
        return tcpCmdName;
    }

    public boolean isExecuteSuccess() {
        return isExecuteSuccess;
    }

    public String getMsg() {
        return msg;
    }
}
