package com.xgame.gate.server.core;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import com.xgame.gate.server.cache.GateChannelCache;

/**
 * 消息派发
 * @author jacky.jiang
 *
 */
@Slf4j
public class MessageDispatch {
	
	private static final MessageDispatch  messageDispatch = new MessageDispatch();
	
	public static MessageDispatch getInstance() {
		return messageDispatch;
	}
	
    public void sendMsg(long sessionID, Object o) {
        Channel c = GateChannelCache.getInstance().getServerChannel(sessionID);
        if (c == null) {
            log.warn("server to gate channel is not exist");
            return;
        }
        c.writeAndFlush(o);
    }
}
