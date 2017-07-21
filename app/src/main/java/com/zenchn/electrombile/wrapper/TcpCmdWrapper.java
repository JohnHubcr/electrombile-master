package com.zenchn.electrombile.wrapper;

import com.zenchn.electrombile.entity.CommandModel;
import com.zenchn.electrombile.entity.model.TcpCmdEnum;
import com.zenchn.electrombile.eventBus.TcpCmdRequestEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 作    者：wangr on 2017/3/4 20:51
 * 描    述：
 * 修订记录：
 */

public class TcpCmdWrapper {

    public static void sendTcpCmd(TcpCmdEnum tcpCmdEnum) {
        EventBus.getDefault().post(new TcpCmdRequestEvent(tcpCmdEnum));
    }

    public static void sendLimitTcpCmd(String speedPercent) {
        CommandModel commandModel = TcpCmdEnum.车辆限速.getTcpCmd();
        commandModel.setParameter(speedPercent);
        EventBus.getDefault().post(new TcpCmdRequestEvent(commandModel, TcpCmdEnum.车辆限速.name()));
    }

}
