package com.xgame.logic.server.core.net.gate.session;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.net.gate.GateServer;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.msglib.XMessage;
import com.xgame.msglib.io.MSerializer;


/**
 * 记录玩家session
 * @author jacky.jiang
 *
 */
@Slf4j
public class PlayerSession {
	
	public static final int SESSION_STATE_ACTIVE = 1;

	private long playerId;
	private String ip;
	private int port;
	private long sessionID;
	private int gateId;
	private String address;
	private int status;
	private String account;
	private boolean cross = false;
	
	/**
	 * session上的业务参数
	 */
	private Map<String, Object> sessionAttribute = new HashMap<String, Object>();
	

	public PlayerSession(long sessionID, int gateId, String ip, int port) {
        setSessionID(sessionID);
        setIp(ip);
        setPort(port);
		@SuppressWarnings("unchecked")
		String address = StringUtils.join(ip, ":", port);
        setAddress(address);
        setStatus(SESSION_STATE_ACTIVE);
        setGateId(gateId);
    }
	
	/**
	 * 发送消息
	 * @param msg
	 */
	public void send(XMessage msg) {
		int msgId = msg.getId();
    	try {
    		byte[] bytes = MSerializer.encode(msg);
			GateServer gateServer = InjectorUtil.getInjector().multiGateManager.gateServer(this.gateId);
        	if(gateServer != null) {
        		XMessage xMessage = (XMessage) msg;
    			gateServer.sendNetMessage(msgId, sessionID, false, xMessage.getCallbackId(), xMessage.getErrorCode(), bytes);
			}
		} catch (Exception e) {
			log.error(String.format("write message: [%s] crash", msgId), e);
		}
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean isConnected() {
		return (status & SESSION_STATE_ACTIVE) != 0;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long roleID) {
		this.playerId = roleID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getGateId() {
		return gateId;
	}

	public void setGateId(int gateId) {
		this.gateId = gateId;
	}

	public void addAttribute(String key, Object obj) {
		sessionAttribute.put(key, obj);
	}
	
	/**
	 * 移除key
	 * @param key
	 */
	public void removeAttribute(String key) {
		sessionAttribute.remove(key);
	}

	public Integer getIntAttr(String key) {
		if (sessionAttribute.get(key) == null) {
			return null;
		}
		return (int) sessionAttribute.get(key);
	}

	public Long getLongAttr(String key) {
		if (sessionAttribute.get(key) == null) {
			return null;
		}
		return (Long) sessionAttribute.get(key);
	}

	public String getStringAttr(String key) {
		if (sessionAttribute.get(key) == null) {
			return null;
		}
		return (String) sessionAttribute.get(key);
	}

	public Double getDoubleAttr(String key) {
		if (sessionAttribute.get(key) == null) {
			return null;
		}
		return (Double) sessionAttribute.get(key);
	}

	public boolean isCross() {
		return cross;
	}

	public void setCross(boolean cross) {
		this.cross = cross;
	}
}
