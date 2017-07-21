package com.zenchn.electrombile.eventBus;

import com.zenchn.electrombile.entity.CommandModel;
import com.zenchn.electrombile.entity.model.TcpCmdEnum;

/**
 * 作    者：wangr on 2017/3/4 20:24
 * 描    述：
 * 修订记录：
 */

public class TcpCmdRequestEvent {

    private CommandModel commandModel;
    private String tcpCmdName;

    public TcpCmdRequestEvent(TcpCmdEnum tcpCmdEnum) {
        this.tcpCmdName = tcpCmdEnum.name();
        this.commandModel = tcpCmdEnum.getTcpCmd();
    }

    public TcpCmdRequestEvent(CommandModel commandModel, String tcpCmdName) {
        this.commandModel = commandModel;
        this.tcpCmdName = tcpCmdName;
    }

    public CommandModel getCommandModel() {
        return commandModel;
    }

    public String getTcpCmdName() {
        return tcpCmdName;
    }
}
