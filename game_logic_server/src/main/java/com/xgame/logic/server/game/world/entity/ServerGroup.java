package com.xgame.logic.server.game.world.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 
 * @author jacky.jiang
 *
 */
public class ServerGroup implements JBaseTransform {
	
	/**
	 * 服务器id
	 */
	private int serverId;
	
	/**
	 * 服务器ip
	 */
	private String ip;
	
	/**
	 * 端口
	 */
	private int port;
	
	/**
	 * 服务器开服时间
	 */
	private long serverOpenTime;
	
	/**
	 * 网关信息
	 */
	private String gateInfo;
	
	/**
	 * 执政盟图片
	 */
	private String thronePic;
	
	/**
	 * 政府简称
	 */
	private String throneAbbr;
	
	/**
	 * 政府名称
	 */
	private String throneName;
	
	/**
	 * 在线人数
	 */
	private int onlineNum;
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getServerOpenTime() {
		return serverOpenTime;
	}

	public void setServerOpenTime(long serverOpenTime) {
		this.serverOpenTime = serverOpenTime;
	}

	public String getThronePic() {
		return thronePic;
	}

	public void setThronePic(String thronePic) {
		this.thronePic = thronePic;
	}

	public String getGateInfo() {
		return gateInfo;
	}

	public void setGateInfo(String gateInfo) {
		this.gateInfo = gateInfo;
	}

	public int getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}

	public String getThroneAbbr() {
		return throneAbbr;
	}

	public void setThroneAbbr(String throneAbbr) {
		this.throneAbbr = throneAbbr;
	}

	public String getThroneName() {
		return throneName;
	}

	public void setThroneName(String throneName) {
		this.throneName = throneName;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("serverId", serverId);
		jbaseData.put("ip", ip);
		jbaseData.put("port", port);
		jbaseData.put("serverOpenTime", serverOpenTime);
		jbaseData.put("gateInfo", gateInfo);
		jbaseData.put("thronePic", thronePic);
		jbaseData.put("throneAbbr", throneAbbr);
		jbaseData.put("throneName", throneName);
		jbaseData.put("onlineNum", onlineNum);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.serverId = jBaseData.getInt("serverId", 0);
		this.ip = jBaseData.getString("ip", "");
		this.port = jBaseData.getInt("port", 0);
		this.serverOpenTime = jBaseData.getLong("serverOpenTime", 0);
		this.gateInfo = jBaseData.getString("gateInfo", "");
		this.thronePic = jBaseData.getString("thronePic", "");
		this.throneAbbr = jBaseData.getString("throneAbbr", "");
		this.throneName = jBaseData.getString("throneName", "");
		this.onlineNum = jBaseData.getInt("onlineNum", 0);
	}

}
