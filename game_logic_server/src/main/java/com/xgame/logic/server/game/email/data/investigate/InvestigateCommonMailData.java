package com.xgame.logic.server.game.email.data.investigate;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

/**
 * 侦查通用邮件数据
 * @author zehong.he
 *
 */
public class InvestigateCommonMailData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5698671347393413443L;
	
	/**
	 * 被侦查玩家id
	 */
	@Tag(1)
	private long beScoutPlayerId;
	
	/**
	 * 被侦查玩家坐标
	 */
	@Tag(2)
	private Vector2Bean location;

	/**
	 * 部队
	 */
	@Tag(3)
	private List<InvestigateSetOffSoldier> soldierList = new ArrayList<>();
	
	/**
	 * 预计占领完成时间（时间戳，侦查领地用）
	 */
	@Tag(4)
	private long finishedTime;
	
	/**
	 * 侦查类型
	 */
	@Tag(5)
	private SpriteType spriteType;
	

	public long getBeScoutPlayerId() {
		return beScoutPlayerId;
	}

	public Vector2Bean getLocation() {
		return location;
	}

	public List<InvestigateSetOffSoldier> getSoldierList() {
		return soldierList;
	}

	public void setBeScoutPlayerId(long beScoutPlayerId) {
		this.beScoutPlayerId = beScoutPlayerId;
	}

	public void setLocation(Vector2Bean location) {
		this.location = location;
	}

	public void setSoldierList(List<InvestigateSetOffSoldier> soldierList) {
		this.soldierList = soldierList;
	}

	public long getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(long finishedTime) {
		this.finishedTime = finishedTime;
	}

	public SpriteType getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(SpriteType spriteType) {
		this.spriteType = spriteType;
	}
}
