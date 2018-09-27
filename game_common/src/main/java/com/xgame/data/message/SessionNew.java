package com.xgame.data.message;

/**
 * sessoin创建
 * @author jacky.jiang
 *
 */
public class SessionNew extends ClientMessage {

    private long ip;
    private int port;

    public SessionNew(long sessionID, long ip, int port) {
        setSessionID(sessionID);
        this.ip = ip;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

	public long getIp() {
		return ip;
	}
    
}
