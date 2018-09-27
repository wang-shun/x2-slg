package com.xgame.logic.server.game.email.data.investigate;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.country.bean.Vector2Bean;

/**
 *
 *2017-1-16  16:34:14
 *@author ye.yuan
 *
 */
public class InvestigateResourceMailData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2967124723547764770L;

	/**
	 * 资源类型
	 */
	@Tag(54)
	private int resourceType;
	
	/**
	 * 资源数量
	 */
	@Tag(55)
	private long value;
	
	/**
	 * 采集部队
	 */
	@Tag(56)
	private List<InvestigateSetOffSoldier> soldierList = new ArrayList<>();
	
	/**
	 * 资源等级
	 */
	@Tag(56)
	private int resourceLevel;
	
	/**
	 * 被侦查玩家id
	 */
	private long beScoutPlayerId;
	
	/**
	 * 被侦查玩家坐标
	 */
	private Vector2Bean location;

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public List<InvestigateSetOffSoldier> getSoldierList() {
		return soldierList;
	}

	public void setSoldierList(List<InvestigateSetOffSoldier> soldierList) {
		this.soldierList = soldierList;
	}

	public long getBeScoutPlayerId() {
		return beScoutPlayerId;
	}

	public void setBeScoutPlayerId(long beScoutPlayerId) {
		this.beScoutPlayerId = beScoutPlayerId;
	}

	public Vector2Bean getLocation() {
		return location;
	}

	public void setLocation(Vector2Bean location) {
		this.location = location;
	}

	public int getResourceLevel() {
		return resourceLevel;
	}

	public void setResourceLevel(int resourceLevel) {
		this.resourceLevel = resourceLevel;
	}
	
	
}
