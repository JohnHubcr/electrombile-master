package com.zenchn.electrombile.engine;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.TcpCmdApi;
import com.zenchn.electrombile.api.callback.GpsLink;
import com.zenchn.electrombile.engine.callback.TcpCmdCallback;
import com.zenchn.electrombile.entity.CommandModel;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.wrapper.ACacheWrapper;

import java.util.Random;

/**
 * 作    者：wangr on 2017/3/4 14:47
 * 描    述：远程指令发送的引擎
 * 修订记录：
 */
public class TcpCmdEngine implements GpsLink.GetTokenCallback, GpsLink.SendTcpCmdCallback, GpsLink.QueryTcpCmdCallback {

    private TcpCmdApi tcpCmdApi;
    private Handler mHandler;

    private String serialNumber;
    private String authorizationPsw;

    private String tcpCmdName;
    private CommandModel commandModel;
    private TcpCmdCallback cmdCallback;

    private String authorization;
    private Runnable queryTask;
    private String queryId;

    private int errorCount;
    private int status;


    private TcpCmdEngine() {
        tcpCmdApi = TcpCmdApi.getInstance();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static TcpCmdEngine getInstance() {
        return new TcpCmdEngine();
    }


    /**
     * 开启一个指令任务
     *
     * @param applicationContext
     * @param serialNumber
     * @param authorizationPsw
     * @param tcpCmdName
     * @param commandModel
     * @param callback
     */
    public void startTcpTask(Context applicationContext, String serialNumber, String authorizationPsw, String tcpCmdName, CommandModel commandModel, TcpCmdCallback callback) {
        this.serialNumber = serialNumber;
        this.authorizationPsw = authorizationPsw;
        this.tcpCmdName = tcpCmdName;
        this.commandModel = commandModel;
        this.cmdCallback = callback;
        start();
    }

    private void start() {
        authorization = ACacheWrapper.getGPSLinkToken();
        if (CommonUtils.isEmpty(authorization)) {
            getToken();
        } else {
            postTcpCmd();
        }
    }

    /**
     * 获取token
     */
    private void getToken() {
        tcpCmdApi.getGpsLinkToken(GpsLink.birdUrl + GpsLink.birdToken, serialNumber, authorizationPsw, "password", "single", this);
    }


    @Override
    public void onGetTokenFailure() {
        errorCount++;
        if (errorCount < 3) {
            getToken();
        } else {
            if (cmdCallback != null)
                cmdCallback.onTcpCmdRequestStatus(tcpCmdName, false);
        }
    }

    @Override
    public void onGetTokenSuccess(String authorization) {
        this.authorization = authorization;
        ACacheWrapper.tempGPSLinkToken(authorization);
        if (BuildConf.GpsLink.POST_TCP_CMD == status)
            postTcpCmd();
        else if (BuildConf.GpsLink.QUERY_TCP_CMD == status)
            queryTcpCmd();
    }

    /**
     * 发送指令
     */
    public void postTcpCmd() {
        status = BuildConf.GpsLink.POST_TCP_CMD;
        tcpCmdApi
                .postTcpCmd(GpsLink.birdUrl + GpsLink.birdTcpCmds, authorization, serialNumber, commandModel, this);
    }

    @Override
    public void onSendTcpCmdFailure() {
        errorCount++;
        if (errorCount < 3)
            postTcpCmd();
        else {
            if (cmdCallback != null)
                cmdCallback.onTcpCmdRequestStatus(tcpCmdName, false);
        }
    }

    @Override
    public void onSendTcpCmdSuccess(String id) {
        this.queryId = id;
        if (cmdCallback != null)
            cmdCallback.onTcpCmdRequestStatus(tcpCmdName, true);

        //创建查询任务
        queryTask = new Runnable() {
            @Override
            public void run() {
                tcpCmdApi
                        .queryTcpCmd(GpsLink.birdUrl + GpsLink.birdTcpCmds, authorization, queryId, TcpCmdEngine.this);
            }
        };

        //创建超时任务
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacksAndMessages(null);
                if (cmdCallback != null)
                    cmdCallback.onTcpCmdResult(tcpCmdName, false, BuildConf.GpsLink.ERROR_FLAG);
            }
        }, 1000 * 60);

        queryTcpCmd();
    }

    /**
     * 查询指令
     */
    public void queryTcpCmd() {
        status = BuildConf.GpsLink.QUERY_TCP_CMD;
        mHandler.post(queryTask);
    }

    @Override
    public void onQueryTcpCmdFailure() {
        mHandler.postDelayed(queryTask, 1000 + new Random().nextInt(100));
    }

    @Override
    public void onQueryTcpCmdLimited() {
        mHandler.removeCallbacks(queryTask);
        mHandler.postDelayed(queryTask, 2000 + new Random().nextInt(200));
    }

    @Override
    public void onQueryTcpCmdSuccess(String res_msg) {
        if (CommonUtils.isNonNull(res_msg) && (res_msg.contains(BuildConf.GpsLink.SUCCESS_FLAG) || res_msg.contains(BuildConf.GpsLink.FAIL_FLAG) || res_msg.contains(BuildConf.GpsLink.PENDING_FLAG))) {
            if (cmdCallback != null)
                cmdCallback.onTcpCmdResult(tcpCmdName, true, res_msg);
            mHandler.removeCallbacksAndMessages(null);
        } else {
            mHandler.postDelayed(queryTask, 1000 + new Random().nextInt(100));
        }
    }

    @Override
    public void onTokenRefuse() {
        if (BuildConf.GpsLink.POST_TCP_CMD == status || BuildConf.GpsLink.QUERY_TCP_CMD == status) {
            getToken();//发送或查询指令的时候权限拒绝，重新获取
        } else {
            if (cmdCallback != null)
                cmdCallback.onTcpCmdResult(tcpCmdName, false, BuildConf.GpsLink.ERROR_FLAG);
        }
    }

}
