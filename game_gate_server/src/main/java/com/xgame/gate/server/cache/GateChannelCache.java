package com.xgame.gate.server.cache;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef;

import com.google.common.collect.Lists;
import com.xgame.gate.server.akka.AkkaFactory;
import com.xgame.gate.server.akka.AkkaSystemFactory;
import com.xgame.gate.server.akka.GateToClientAkka;


/**
 * 网关
 * @author jacky.jiang
 *
 */
public class GateChannelCache {
	
	private static final GateChannelCache gateChannelCache = new GateChannelCache();
	
	private int clientAkkaNum = 20;
	
	public static GateChannelCache getInstance() {
		return gateChannelCache;
	}

    // 客户端缓存channel
    private ConcurrentHashMap<Long, Channel> sessionIDToChannel = new ConcurrentHashMap<>();
    
    // client actors
    private ConcurrentHashMap<Integer, ActorRef> clientActors = new ConcurrentHashMap<>();
    
    // server actors;
    private ConcurrentHashMap<Channel, ActorRef> serverActors = new ConcurrentHashMap<>();
    
    public void initAkka() {
    	if(clientAkkaNum > 0) {
			for (int i = 0; i < clientAkkaNum; i++) {
    			ActorRef actorRef = AkkaFactory.createActorRef("GateToClientAkka_" + i, GateToClientAkka.class);
    			clientActors.put(i, actorRef);
    		}
    	}
    }
    
    public void putSessionIDToChannel(long sessionID, Channel channel) {
        sessionIDToChannel.putIfAbsent(sessionID, channel);
    }

    public Channel removeChannelBySessionID(long sessionID) {
        return sessionIDToChannel.remove(sessionID);
    }

    public Channel getChannelBySessionID(long sessionID) {
        return sessionIDToChannel.get(sessionID);
    }
    
    public void addServerActor(Channel channel, ActorRef actorRef) {
    	this.serverActors.put(channel, actorRef);
    }

    /**
     * 根据客户端sessionId获得服务器channel
     * @param sessionId
     * @return
     */
    public Channel getServerChannel(Long sessionId) {
        if (serverActors == null || serverActors.isEmpty()) return null;
        int index  = (int)(sessionId % serverActors.size());
        List<Channel> channelList = Lists.newArrayList(serverActors.keySet());
        Channel channel = channelList.get(index);
        return channel;
    }
    
    /**
     * 获得服务器channel
     * @param sessionId
     * @return
     */
    public ActorRef getServerChannel(Channel channel) {
        return serverActors.get(channel);
    }
    
    /**
     * 获取客户端akka
     * @param sessionId
     * @return
     */
    public ActorRef getClientAkka(long sessionId) {
    	 if (clientActors == null || clientActors.isEmpty()) return null;
    	 int index = (int)(sessionId % clientActors.size());
    	 return clientActors.get(index);
    }
    
    /**
     * 添加服务器连接
     * @param channel
     * @param actorRef
     */
    public void addServerChannel(Channel channel, ActorRef actorRef) {
    	serverActors.put(channel, actorRef);
    }
    
    /**
     * 移除服务器连接
     * @param channel
     */
    public void removeServerChannel(Channel channel) {
    	ActorRef actorRef = serverActors.remove(channel);
    	if(actorRef != null) {
    		AkkaSystemFactory.getInstance().getActorSystem().stop(actorRef);	
    	}
    }
    
    /**
     * 获取
     * @param ignors
     * @return
     */
    public List<Channel> getChannels(List<Long> ignors) {
    	List<Channel> list = new ArrayList<Channel>();
    	if(ignors != null) {
    		ConcurrentHashMap<Long, Channel> tmpConcurrentHashMap = new ConcurrentHashMap<>();
    		tmpConcurrentHashMap.putAll(sessionIDToChannel);
    		if(ignors != null) {
    			for(Long id : ignors) {
    				tmpConcurrentHashMap.remove(id);
    			}
    		}
    		list.addAll(tmpConcurrentHashMap.values());
    	} else {
    		list.addAll(sessionIDToChannel.values());
    	}
    	return list;
    }

	public int getClientAkkaNum() {
		return clientAkkaNum;
	}

	public void setClientAkkaNum(int clientAkkaNum) {
		this.clientAkkaNum = clientAkkaNum;
	}
}
