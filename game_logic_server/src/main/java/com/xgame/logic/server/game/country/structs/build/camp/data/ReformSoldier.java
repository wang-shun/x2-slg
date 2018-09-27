package com.xgame.logic.server.game.country.structs.build.camp.data;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 改装厂的士兵
 * @author jacky.jiang
 *
 */
public class ReformSoldier implements Serializable,JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -139704439493169671L;
	
	/**
	 * id序号
	 */
	@Tag(1)
	private long id;
	/**
	 * 士兵id
	 */
	@Tag(2)
	private long solderId;
	/**
	 * 数量
	 */
	@Tag(3)
	private int num;
	/**
	 * 状态
	 */
	@Tag(7)
	private int state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getSolderId() {
		return solderId;
	}

	public void setSolderId(long solderId) {
		this.solderId = solderId;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("id", id);
		jBaseData.put("solderId", solderId);
		jBaseData.put("num", num);
		jBaseData.put("state", state);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.solderId = jBaseData.getLong("solderId", 0);
		this.num = jBaseData.getInt("num", 0);
		this.state = jBaseData.getInt("state", 0);
	}
	
}
