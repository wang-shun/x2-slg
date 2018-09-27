
package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xgame.gameconst.BuffType;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.utils.EnumUtils;

/**
 * 玩家身上buff
 * @author jacky.jiang
 *
 */
public class PlayerBuff implements JBaseTransform {
	
	@Tag(1)
	private String buffId;
	
	@Tag(2)
	private BuffType buffType;
	
	@Tag(3)
	private String buffKey;
	
	@Tag(4)
	private long startTime;
	
	@Tag(5)
	private long endTime;
	
	@Tag(6)
	private long taskId;
	
	@Tag(7)
	private Map<String, Object> buffAddition = new HashMap<String, Object>();
	
	public BuffType getBuffType() {
		return buffType;
	}

	public void setBuffType(BuffType buffType) {
		this.buffType = buffType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Map<String, Object> getBuffAddition() {
		return buffAddition;
	}

	public void setBuffAddition(Map<String, Object> buffAddition) {
		this.buffAddition = buffAddition;
	}

	public String getBuffId() {
		return buffId;
	}

	public void setBuffId(String buffId) {
		this.buffId = buffId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public String getBuffKey() {
		return buffKey;
	}

	public void setBuffKey(String buffKey) {
		this.buffKey = buffKey;
	}

	@SuppressWarnings("unchecked")
	public static String factoryBuffId(BuffType buffType, String buffKey) {
		return StringUtils.join(buffType, ":", buffKey);
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("buffId", buffId);
		jbaseData.put("buffKey", buffKey);
		jbaseData.put("startTime", startTime);
		jbaseData.put("endTime", endTime);
		jbaseData.put("taskId", taskId);
		if(buffType != null) {
			jbaseData.put("buffType", buffType.ordinal());	
		} else {
			jbaseData.put("buffType", BuffType.DEFAULT.ordinal());	
		}
		jbaseData.put("buffAddition", JsonUtil.toJSON(buffAddition));
		return jbaseData;
	}
	
	
	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.buffId = jBaseData.getString("buffId","");
		this.buffKey = jBaseData.getString("buffKey","");
		this.startTime = jBaseData.getLong("startTime",0);
		this.endTime = jBaseData.getLong("endTime",0);
		this.taskId = jBaseData.getLong("taskId",0);
		
		int buffType = jBaseData.getInt("buffType", 0);
		this.buffType = EnumUtils.getEnum(BuffType.class, buffType);
		
		String buffAdditionString = jBaseData.getString("buffAddition", "");
		if(!StringUtils.isEmpty(buffAdditionString)) {
			this.buffAddition = JsonUtil.fromJSON(buffAdditionString, Map.class);
		}
	}
}
