package com.xgame.logic.server.game.awardcenter.entity;

import java.io.Serializable;

import org.apache.commons.lang3.EnumUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;

import io.protostuff.Tag;

/**
 *
 *2016-12-17  16:05:34
 *@author ye.yuan
 *
 */
public class AwardDB implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(1)
	private int ordinal;
	
	@Tag(2)
	private int awardId;
	
	@Tag(3)
	private int num;
	
	@Tag(4)
	private int type;
	
	@Tag(5)
	private Object param;
	
	/**
	 * 排序 map key
	 */
	@Tag(6)
	private int index;
	
	private GameLogSource gameLogSource = GameLogSource.DEFAULT;
	

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getAwardId() {
		return awardId;
	}

	public void setAwardId(int awardId) {
		this.awardId = awardId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public GameLogSource getGameLogSource() {
		return gameLogSource;
	}

	public void setGameLogSource(GameLogSource gameLogSource) {
		this.gameLogSource = gameLogSource;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("ordinal", ordinal);
		jbaseData.put("awardId", awardId);
		jbaseData.put("num", num);
		jbaseData.put("type", type);
		jbaseData.put("index", index);
		jbaseData.put("gameLogSource", gameLogSource.name());
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.ordinal = jBaseData.getInt("ordinal", 0);
		this.awardId = jBaseData.getInt("awardId", 0);
		this.num = jBaseData.getInt("num", 0);
		this.type = jBaseData.getInt("type", 0);
		this.index = jBaseData.getInt("index", 0);
		this.gameLogSource = EnumUtils.getEnum(GameLogSource.class, jBaseData.getString("gameLogSource", ""));
	}

}
