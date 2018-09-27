package com.xgame.gate.server.akka;

import lombok.extern.slf4j.Slf4j;
import akka.actor.UntypedActor;

import com.xgame.data.message.SessionClose;
import com.xgame.data.message.SessionMessage;
import com.xgame.data.message.SessionNew;
import com.xgame.gate.server.core.MessageDispatch;

/**
 * Gate-client 消息
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateToClientAkka extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
    	try {
    		if (msg instanceof SessionMessage) {
                // get sessionid
                SessionMessage clientMsg = (SessionMessage) msg;
                MessageDispatch.getInstance().sendMsg(clientMsg.getSessionID(), new SessionMessage(clientMsg.getSessionID(), clientMsg.getMsgID(), clientMsg.getCalbackId(), clientMsg.getBytes()));
            } else if (msg instanceof SessionNew) {
                SessionNew sessionNew = (SessionNew) msg;
                MessageDispatch.getInstance().sendMsg(sessionNew.getSessionID(), sessionNew);
            } else if (msg instanceof SessionClose) {
                SessionClose sessionClose = (SessionClose) msg;
                MessageDispatch.getInstance().sendMsg(sessionClose.getSessionID(), sessionClose);
            }
    	} catch(Exception e) {
    		log.error("gate-client akka exception", e);
    	}
    }
}
