package com.xgame.gate.server.core;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by youxl on 2016/4/18.
 */
public class AtomicRecord {

    /**
     * generate sessionid
     */
    public static AtomicLong sessionID = new AtomicLong(1);

    /**
     * generate actor seq number
     */
    public static AtomicLong actorSeq = new AtomicLong(1);
}
