package com.xgame.logic.server.game.bag.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 添加道具
 * @author jacky.jiang
 *
 */
public class ItemChangeEventObject extends GameLogEventObject {
	
	public static final int ADD = 1;
	
	public static final int REMOVE = 2;
	
	public static final int ITEM_TYPE = 1;

	public static final int EQUIPMENT_TYPE = 2;
	
	/**
	 * 道具id
	 */
	private int itemTid;
	
	/**
	 * 玩家id
	 */
	private long playerId;
	
	/**
	 * 变化之前的数量
	 */
	private int beforeNum;
	
	/**
	 *  变化之后的数量
	 */
	private int afterNum;
	
	/**
	 * 变更类型（1增加2删除）
	 */
	private int changeType;
	
	/**
	 * 日志产生来源
	 */
	private GameLogSource logSource;
	
	/**
	 * 唯一id
	 */
	private long uid;
	
	/**
	 * 1-道具；2-装备
	 */
	private int goodsType;
	
	public ItemChangeEventObject(int itemTid, Player player, int beforeNum, int afterNum, int changeType, GameLogSource logSource, long uid,int goodsType) {
		super(player, EventTypeConst.ITEM_CHANGE);
		this.itemTid = itemTid;
		this.beforeNum = beforeNum;
		this.afterNum = afterNum;
		this.logSource = logSource;
		this.changeType = changeType;
		this.uid = uid;
		this.goodsType = goodsType;
	}

	public int getItemTid() {
		return itemTid;
	}

	public void setItemTid(int itemTid) {
		this.itemTid = itemTid;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getBeforeNum() {
		return beforeNum;
	}

	public void setBeforeNum(int beforeNum) {
		this.beforeNum = beforeNum;
	}

	public int getAfterNum() {
		return afterNum;
	}

	public void setAfterNum(int afterNum) {
		this.afterNum = afterNum;
	}

	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public GameLogSource getLogSource() {
		return logSource;
	}

	public void setLogSource(GameLogSource logSource) {
		this.logSource = logSource;
	}

	/**
	 * @return the goodsType
	 */
	public int getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType the goodsType to set
	 */
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	@Override
	public String toString() {
		return "ItemChangeEventObject [itemTid=" + itemTid + ", playerId="
				+ playerId + ", beforeNum=" + beforeNum + ", afterNum="
				+ afterNum + ", changeType=" + changeType + ", logSource="
				+ logSource + ", uid=" + uid + "]";
	}
	
	
}
