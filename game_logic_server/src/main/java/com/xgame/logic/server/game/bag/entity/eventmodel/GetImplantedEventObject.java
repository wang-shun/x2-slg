package com.xgame.logic.server.game.bag.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 获得植入体
 * @author zehong.he
 *
 */
public class GetImplantedEventObject extends GameLogEventObject {
	
	public GetImplantedEventObject(Player player,int equipmentId,int num,int totalNum) {
		super(player,EventTypeConst.GET_IMPLANTED);
		this.equipmentId = equipmentId;
		this.num = num;
		this.totalNum = totalNum;
	}
	
	/**
	 * 配置ID
	 */
	private int equipmentId;

	/**
	 * 数量
	 */
	private int num;
	
	/**
	 * 本次增加的植入体品质拥有数量
	 */
	private int totalNum;

	/**
	 * @return the equipmentId
	 */
	public int getEquipmentId() {
		return equipmentId;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	
	
	
}
