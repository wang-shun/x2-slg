package com.xgame.data.message;


/**
 * 网关-服务器通信协议
 * @author jacky.jiang
 *
 */
public class G2SProtocol {

    //gate => server
    public static final int SEND_TO_SERVER = 2;
    public static final int SESSION_NEW = 3;
    public static final int SESSION_CLOSED = 4;

    //server => gate
    public static final int SEND_TO_SESSION = 1;
    public static final int SESSION_SHUTDOWN = 5;
    public static final int SEND_TO_SESSION_IMMEDIATELY = 6;
    public static final int SERVER_SHUTDOWN = 7;
    
    // broadcast to all session
    public static final int BROADCAST = 20;
    // broadcast to specific sessions
    public static final int BROADCAST_SESSION = 21;
}
