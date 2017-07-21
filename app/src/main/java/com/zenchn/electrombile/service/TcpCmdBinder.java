package com.zenchn.electrombile.service;

import com.zenchn.electrombile.eventBus.TcpCmdRequestEvent;

import java.util.Set;

/**
 * 作    者：wangr on 2017/3/6 18:54
 * 描    述：
 * 修订记录：
 */
public interface TcpCmdBinder {


    void sendTcpCmd(TcpCmdRequestEvent event);

    Set<String> getTcpCmdStack();

}
