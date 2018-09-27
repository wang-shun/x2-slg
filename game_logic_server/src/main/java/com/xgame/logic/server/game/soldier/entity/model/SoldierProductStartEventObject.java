package com.xgame.logic.server.game.soldier.entity.model;

import java.util.List;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 开始生产士兵事件
 * @author zehong.he
 *
 */
public class SoldierProductStartEventObject extends GameLogEventObject {

	/**
	 * 兵工厂类型
	 */
	private int soldierType;

	/**
	 * 生产数量
	 */
	private int productNum;
	
	/**
	 * 地盘ID列表 TODO 
	 */
	private List<Integer> sids;
	
	public SoldierProductStartEventObject(Player player,int soldierType,int productNum,List<Integer> sids) {
		super(player, EventTypeConst.SOLDIER_PRODUCT_START);
		this.soldierType = soldierType;
		this.productNum = productNum;
		this.sids = sids;
	}


	public int getSoldierType() {
		return soldierType;
	}


	public int getProductNum() {
		return productNum;
	}


	public List<Integer> getSids() {
		return sids;
	}


	public void setSids(List<Integer> sids) {
		this.sids = sids;
	}
	
	
}
