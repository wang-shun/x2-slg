package com.xgame.gate.server.core;

/**
 * Created by youxl
 */
public class Protocol {

    //gate => server
    public static final int SEND_TO_SERVER = 2;
    public static final int SESSION_NEW = 3;
    public static final int SESSION_CLOSED = 4;

    //server => gate
    public static final int SEND_TO_SESSION = 1;
    public static final int SESSION_SHUTDOWN = 5;
    public static final int SEND_TO_SESSION_IMMEDIATELY = 6;

    // broadcast to all session
    public static final int BROADCAST = 20;
    // broadcast to specific sessions
    public static final int BROADCAST_SESSION = 21;

}
