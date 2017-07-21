package com.zenchn.electrombile.api.callback;

/**
 * 作    者：wangr on 2017/3/4 15:08
 * 描    述： 波导接口
 * 修订记录：
 */
public interface GpsLink {

    String birdUrl = "http://api.gpslink.cn/";// 波导主机地址
    String birdToken = "/Token";// token接口
    String birdTcpCmds = "/api/Tcpcmds";// 远程指令接口

    interface ErrorCallback {

        void onTokenRefuse();

    }

    interface GetTokenCallback{

        void onGetTokenFailure();

        void onGetTokenSuccess(String authorization);

    }

    interface SendTcpCmdCallback extends ErrorCallback {


        void onSendTcpCmdFailure();

        void onSendTcpCmdSuccess(String id);

    }

    interface QueryTcpCmdCallback extends ErrorCallback {

        void onQueryTcpCmdFailure();

        void onQueryTcpCmdLimited();

        void onQueryTcpCmdSuccess(String res_msg);


    }

}
