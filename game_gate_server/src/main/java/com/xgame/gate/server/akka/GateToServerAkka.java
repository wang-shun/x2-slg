package com.xgame.gate.server.akka;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import akka.actor.UntypedActor;

import com.xgame.data.message.BroadcastMsg;
import com.xgame.data.message.ResponseMessage;
import com.xgame.data.message.ServerMessage;
import com.xgame.data.message.SessionShutDown;
import com.xgame.data.statistic.MessageStat;
import com.xgame.gate.server.cache.GateChannelCache;

/**
 * akka gate-server业务处理
 * @author jacky.jiang
 */
@Slf4j
public class GateToServerAkka extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
    	try {
    		// 服务器推送消息
            if (msg instanceof ServerMessage) {
                ServerMessage serverMessage = (ServerMessage) msg;
                onServerMessage(serverMessage);
            }
            
            // 全服广播消息
            if(msg instanceof BroadcastMsg) {
            	BroadcastMsg broadcastMsg = (BroadcastMsg) msg;
            	broadcast(broadcastMsg);
            }

            if (msg instanceof SessionShutDown) {
                SessionShutDown sessionShutDown = (SessionShutDown) msg;
                onSessionShutDown(sessionShutDown);
            }
    	} catch(Exception e) {
    		log.error("gate-server akka", e);
    	}
    }

    /**
     * 服务端消息处理
     * @param serverMessage
     */
    private void onServerMessage(ServerMessage serverMessage) {
        List<Long> sessionIDList = serverMessage.getSessionIDList();
        for (long sessionID : sessionIDList) {
            Channel c = GateChannelCache.getInstance().getChannelBySessionID(sessionID);
            if (c == null) {
                continue;
            }

            MessageStat.getInstance().increase(1);
            c.writeAndFlush(new ResponseMessage(serverMessage.getMsgID(), serverMessage.getCallbackId(), serverMessage.getErrorCode(), serverMessage.getBytes()));
        }
    }

    /**
     * session关闭消息
     * @param sessionShutDown
     */
    private void onSessionShutDown(SessionShutDown sessionShutDown) {
        Channel c = GateChannelCache.getInstance().removeChannelBySessionID(sessionShutDown.getSessionID());
        if (c != null) c.close();
    }
    
    /**
     * 广播消息
     * @param broadcastMsg
     */
    private void broadcast(BroadcastMsg broadcastMsg) {
    	ResponseMessage responseMessage = new ResponseMessage(broadcastMsg.getMsgId(), 0, 0, broadcastMsg.getContents());
    	Collection<Channel> channelList = GateChannelCache.getInstance().getChannels(broadcastMsg.getIgnoreSesison());
    	if(channelList != null && !channelList.isEmpty()) {
    		for(Channel channel : channelList) {
    			if(channel != null) {
    				channel.writeAndFlush(responseMessage);
    			}
    		}
    	}
    }
}
