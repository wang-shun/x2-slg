package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;

/**
 * 帮助信息
 * @author jacky.jiang
 *
 */
public class HelpInfo extends AbstractEntity<Long> implements JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6083315249304548587L;

	/**
	 * 帮助id
	 */
	private Long helpId;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 发送方id
	 */
	private long senderId;
	
	/**
	 * 最大帮助次数
	 */
	private int maxcount;
	/**
	 * 当前次数
	 */
	private int nowcount;
	/**
	 * 开始时间
	 */
	private long startTime;
	
	/**
	 * 每次减少时间
	 */
	private int reduceSec;
	/**
	 * 等级
	 */
	private int level;
	/**
	 * 类型
	 */
	private int type;
	
	/**
	 * 帮助的目标id
	 */
	private int sid;
	
	/**
	 * 任务id
	 */
	private long taskId;
	
	/**
	 * 帮助过的玩家id列表
	 */
	private ConcurrentHashSet<Long> helperIds = new ConcurrentHashSet<>();

	public int getMaxcount() {
		return maxcount;
	}

	public void setMaxcount(int maxcount) {
		this.maxcount = maxcount;
	}

	public int getNowcount() {
		return nowcount;
	}

	public void setNowcount(int nowcount) {
		this.nowcount = nowcount;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getReduceSec() {
		return reduceSec;
	}

	public void setReduceSec(int reduceSec) {
		this.reduceSec = reduceSec;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public long getSenderd() {
		return senderId;
	}

	public void setSenderd(long senderd) {
		this.senderId = senderd;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public void help(long playerId) {
		this.helperIds.add(playerId);
	}

	public Long getHelpId() {
		return helpId;
	}

	public void setHelpId(Long helpId) {
		this.helpId = helpId;
	}

	@Override
	public Long getId() {
		return this.helpId;
	}

	@Override
	public void setId(Long k) {
		this.helpId = k;
	}
	
	/**
	 * 是否已经帮助
	 * @param playerId
	 * @return  true 未帮助 false 已帮助
	 */
	public boolean canHelp(long playerId) {
		return !this.helperIds.contains(playerId);
	}

	public ConcurrentHashSet<Long> getHelperIds() {
		return helperIds;
	}

	public void setHelperIds(ConcurrentHashSet<Long> helperIds) {
		this.helperIds = helperIds;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("helpId", helpId);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("senderId", senderId);
		jbaseData.put("maxcount", maxcount);
		jbaseData.put("nowcount", nowcount);
		jbaseData.put("startTime", startTime);
		jbaseData.put("reduceSec", reduceSec);
		jbaseData.put("level", level);
		jbaseData.put("type", type);
		jbaseData.put("sid", sid);
		jbaseData.put("taskId", taskId);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.helpId = jBaseData.getLong("helpId",0);
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.senderId = jBaseData.getLong("senderId", 0);
		this.maxcount = jBaseData.getInt("maxcount", 0);
		this.nowcount = jBaseData.getInt("nowcount", 0);
		this.startTime = jBaseData.getLong("startTime", 0);
		this.reduceSec = jBaseData.getInt("reduceSec", 0);
		this.level = jBaseData.getInt("level", 0);
		this.type = jBaseData.getInt("type", 0);
		this.sid = jBaseData.getInt("sid", 0);
		this.taskId = jBaseData.getLong("taskId", 0);
	}
	
}
