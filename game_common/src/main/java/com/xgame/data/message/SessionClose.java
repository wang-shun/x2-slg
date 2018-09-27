package com.xgame.data.message;

/**
 * 关闭session
 * @author jacky.jiang
 *
 */
public class SessionClose extends ClientMessage {

    public SessionClose(long sessionID) {
        setSessionID(sessionID);
    }


}
