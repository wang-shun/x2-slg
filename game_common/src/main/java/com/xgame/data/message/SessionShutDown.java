package com.xgame.data.message;

/**
 * session 关闭
 * @author jacky.jiang
 *
 */
public class SessionShutDown {

    private long sessionID;

    public SessionShutDown(long sessionID) {
        this.sessionID = sessionID;
    }

    public long getSessionID() {
        return sessionID;
    }
}
