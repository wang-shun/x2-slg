package com.xgame.logic.server.core.net.gate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.data.message.SessionShutDown;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.io.MSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * session 管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class SessionManager {

	@Autowired
	private MultiGateManager muilGateManager;
	
    private ConcurrentHashMap<Long, PlayerSession> sessionMap = new ConcurrentHashMap<>();
    
    private ConcurrentHashMap<Long, Long> playerIdSessionMap = new ConcurrentHashMap<>();

    public PlayerSession getSession(long sessionID) {
        return sessionMap.get(sessionID);
    }

    public void putSession(long sessionID, PlayerSession session) {
        sessionMap.putIfAbsent(sessionID, session);
    }
    
    public void addPlayerSession(long playerId, long sessionId) {
    	playerIdSessionMap.put(playerId, sessionId);
    }
    
    public void removePlayerSession(long playerId) {
    	playerIdSessionMap.remove(playerId);
    }

    public PlayerSession removeSessiion(long sessionID) {
        return sessionMap.remove(sessionID);
    }

    /**
     * 发送广播消息(制定多个玩家)
     * @param player
     * @param sessionIDList
     * @param obj
     */
    public synchronized void writeMulti(Player player, Collection<Long> sessionIDList, Object obj) {
    	int msgId = ((Communicationable)obj).getId();
        try {
        	byte[] bytes = MSerializer.encode(obj);
			List<GateServer> gates = muilGateManager.getServerList();
        	if(gates != null) {
        		for(GateServer gateServer : gates) {
    				gateServer.sendMultiSessionMessage(0, msgId, sessionIDList, false, bytes);
        		}
        	}
        } catch (Exception e) {
            log.error(String.format("write message: {} crash", msgId), e);
        }
    }
    
    /**
     * 发送给多个玩家
     * @param playerIds
     * @param obj
     */
    public synchronized void writePlayers(Collection<Long> playerIds, Object obj) {
    	List<Long> sessionIds = getSessionIds(playerIds);
    	int msgId = ((Communicationable)obj).getId();
        try {
        	byte[] bytes = MSerializer.encode(obj);
			List<GateServer> gates = muilGateManager.getServerList();
        	if(gates != null) {
				for (GateServer gateServer : gates) {
    				gateServer.sendMultiSessionMessage(0, msgId, sessionIds, false, bytes);
        		}
        	}
        } catch (Exception e) {
            log.error(String.format("write message: {} crash", msgId), e);
        }
    }
    
    /**
     * 广播消息（发送给在线玩家发送）
     * @param sendPlayer 发送方玩家
     * @param obj
     */
    public synchronized void broadcast(Object obj) {
    	int msgId = ((Communicationable)obj).getId();
        try {
        	byte[] bytes = MSerializer.encode(obj);
			List<GateServer> gates = muilGateManager.getServerList();
        	if(gates != null) {
        		for(GateServer gateServer : gates) {
    				gateServer.sendOnline(msgId, bytes);
        		}
        	}
        } catch (Exception e) {
            log.error(String.format("write message: {} errror", msgId), e);
        }
    }

    /**
     * 发送单条消息
     * @param player
     * @param msg
     */
    public synchronized void write(Player player, Object msg) {
    	int msgId = ((Communicationable)msg).getId();
    	try {
    		byte[] bytes = MSerializer.encode(msg);
    		PlayerSession playerSession = this.getSession(player.getRoleId());
    		if (playerSession != null) {
    			GateServer gateServer = muilGateManager.gateServer(playerSession.getGateId());
    			if(gateServer != null) {
    				gateServer.sendNetMessage(msgId, playerSession.getPlayerId(), false, bytes);
    			}
    		}
        } catch (Exception e) {
            log.error(String.format("write message: %s crash", msgId), e);
        }
    }
    
    /**
     * 发送单条消息
     * @param playerSession
     * @param msg
     */
    public synchronized void write(PlayerSession playerSession, Object msg) {
    	int msgId = ((Communicationable)msg).getId();
    	try {
    		byte[] bytes = MSerializer.encode(msg);
			GateServer gateServer = muilGateManager.gateServer(playerSession.getGateId());
			gateServer.sendNetMessage(msgId, playerSession.getSessionID(), false, bytes);
        } catch (Exception e) {
            log.error(String.format("write message: %s crash", msgId), e);
        }
    }
    
    /**
     * 发送连接关闭
     * @param sessionId
     */
    public synchronized void sendCloseSession(long sessionId, int gateId) {
    	PlayerSession playerSession = sessionMap.get(sessionId);
    	if(playerSession != null) {
    		SessionShutDown sessionShutDown = new SessionShutDown(sessionId);
    		GateServer gateServer = muilGateManager.gateServer(playerSession.getGateId());
        	if(gateServer != null) {
        		gateServer.sendKickSession(sessionShutDown);
        	}
    	}
    }
    
    /**
     * 获取在线玩家列表
     * @return
     */
    public synchronized List<Long> getOnlinePlayerIds() {
    	List<Long> list = new ArrayList<Long>();
    	if(sessionMap != null && !sessionMap.isEmpty()) {
    		Collection<PlayerSession> collection =  sessionMap.values();
        	for(PlayerSession playerSession : collection) {
        		list.add(playerSession.getPlayerId());
        	}	
    	}
    	return list;
    }
    
    /**
     * 获取玩家sessionId列表
     * @param playerIds
     * @return
     */
    private List<Long> getSessionIds(Collection<Long> playerIds) {
    	List<Long> sessionIds = new ArrayList<>();
    	if(sessionMap != null && !sessionMap.isEmpty()) {
    		Collection<PlayerSession> collection =  sessionMap.values();
        	for(PlayerSession playerSession : collection) {
        		if(playerIds.contains(playerSession.getPlayerId())) {
        			sessionIds.add(playerSession.getSessionID());
        		}
        	}	
    	}
    	return sessionIds;
    }
    
    /**
     * 获取sessionId
     * @param playerId
     * @return
     */
    public PlayerSession getSessionByPlayerId(long playerId) {
	    Long sessionId = playerIdSessionMap.get(playerId);
	    if(sessionId != null && sessionId > 0) {
	    	return sessionMap.get(sessionId);
	    }
    	return null;
    }
    
    public boolean checkOnline(long playerId) {
    	PlayerSession playerSession = getSessionByPlayerId(playerId);
    	if(playerSession != null && playerSession.getPlayerId() > 0) {
    		return true;
    	}
    	return false;
    }
}
