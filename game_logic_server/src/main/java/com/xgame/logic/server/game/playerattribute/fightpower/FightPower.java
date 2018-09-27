package com.xgame.logic.server.game.playerattribute.fightpower;

import io.protostuff.Tag;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;

/**
 * 战力存储
 * @author zehong.he
 *
 */
public class FightPower extends AbstractEntity<Long> implements JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = -273683033582756581L;
	
	@Tag(1)
	private long playerId;

	/**
	 * 记录数据
	 */
	@Tag(2)
	private Map<Integer, Number> notes = new ConcurrentHashMap<>();
	
	public FightPower() {
		
	}
	
	public FightPower(long playerId) {
		this.playerId = playerId;
	}
	
	@Override
	public Long getId() {
		return playerId;
	}

	@Override
	public void setId(Long k) {
		this.playerId = k;
	}

	public Map<Integer, Number> getNotes() {
		return notes;
	}

	public void setNotes(Map<Integer, Number> notes) {
		this.notes = notes;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerId", playerId);
		jbaseData.put("notes", JsonUtil.toJSON(notes));
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId", 0);
		String noteStr = jBaseData.getString("notes", "");
		this.notes = JsonUtil.fromJSON(noteStr, Map.class);
	}

}
