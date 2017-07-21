package com.zenchn.electrombile.eventBus;

import java.util.Set;

/**
 * 作    者：wangr on 2017/3/4 20:24
 * 描    述： 
 * 修订记录：
 */

public class TcpCmdRequestStatusEvent {

    private Set<String> tcpCmdStack;
    private String tcpCmdName;
    private boolean isSendSuccess;

    public TcpCmdRequestStatusEvent(String tcpCmdName, boolean isSendSuccess) {
        this.tcpCmdName = tcpCmdName;
        this.isSendSuccess = isSendSuccess;
    }

    public TcpCmdRequestStatusEvent(Set<String> tcpCmdStack, String tcpCmdName, boolean isSendSuccess) {
        this.tcpCmdStack = tcpCmdStack;
        this.tcpCmdName = tcpCmdName;
        this.isSendSuccess = isSendSuccess;
    }

    public Set<String> getTcpCmdStack() {
        return tcpCmdStack;
    }

    public String getTcpCmdName() {
        return tcpCmdName;
    }

    public boolean isSendSuccess() {
        return isSendSuccess;
    }
}
