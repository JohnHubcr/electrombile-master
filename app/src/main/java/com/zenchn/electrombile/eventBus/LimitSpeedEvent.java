package com.zenchn.electrombile.eventBus;

/**
 * 作    者：wangr on 2017/3/4 21:23
 * 描    述： 
 * 修订记录：
 */
public class LimitSpeedEvent {

    private String authorization;
    private String authorizationPsw;
    private String speedPercent;

    public LimitSpeedEvent() {
    }

    public LimitSpeedEvent(String authorization, String authorizationPsw, String speedPercent) {
        this.authorization = authorization;
        this.authorizationPsw = authorizationPsw;
        this.speedPercent = speedPercent;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAuthorizationPsw() {
        return authorizationPsw;
    }

    public void setAuthorizationPsw(String authorizationPsw) {
        this.authorizationPsw = authorizationPsw;
    }

    public String getSpeedPercent() {
        return speedPercent;
    }

    public void setSpeedPercent(String speedPercent) {
        this.speedPercent = speedPercent;
    }
}
