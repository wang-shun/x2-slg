package com.xgame.logic.server.game.email.data.collect;

import java.io.Serializable;

import com.xgame.logic.server.game.country.bean.Vector2Bean;

import io.protostuff.Tag;

/**
 * 采集邮件
 * @author jacky.jiang
 *
 */
public class CollectionEmailData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 玩家id
	@Tag(1)
	private long playerId;

	// 目标点
	@Tag(4)
	private int type;
	
	// 资源等级
	@Tag(5)
	private int level;

	// 采集的金币
	@Tag(6)
	private long value;

	// 目标点
	@Tag(10)
	private Vector2Bean target;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public Vector2Bean getTarget() {
		return target;
	}

	public void setTarget(Vector2Bean target) {
		this.target = target;
	}
	
}
