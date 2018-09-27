package com.xgame.logic.server.game.soldier.entity.model;

import java.util.List;

import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;


/**
 * 士兵变化信息
 * @author jacky.jiang
 *
 */
public class SoldierChangeEventObject extends GameLogEventObject {
	
	/**
	 * 士兵id
	 */
	private long soldierId;

	/**
	 * 兵工厂类型
	 */
	private int soldierType;

	/**
	 * 变化前数量
	 */
	private int beforeNum;
	
	/**
	 * 当前数量
	 */
	private int currentNum;

	
	/**
	 * 出征类型
	 */
	private MarchType marchType;
	
	/**
	 * 行军状态
	 */
	private MarchState marchState;
	
	/**
	 * 来源信息
	 */
	private GameLogSource gameLogSource;
	

	/**
	 * 士兵级别  TODO
	 */
	private int level;
	
	/**
	 * 地盘ID列表 TODO 
	 */
	private List<Integer> sids;
	
	public SoldierChangeEventObject(Player player, int type, long soldierId, int soldierType, 
									int beforeNum, int currentNum, MarchType marchType, MarchState marchState,
									GameLogSource gameLogSource, int level, List<Integer> sids) {
	
			
		super(player, type);
		this.soldierId = soldierId;
		this.soldierType = soldierType;
		this.beforeNum = beforeNum;
		this.currentNum = currentNum;
		this.marchType = marchType;
		this.gameLogSource = gameLogSource;
		this.marchState = marchState;
		this.level = level;
		this.sids = sids;
	}
	
	public int getSoldierType() {
		return soldierType;
	}

	public void setSoldierType(int soldierType) {
		this.soldierType = soldierType;
	}

	public int getBeforeNum() {
		return beforeNum;
	}

	public void setBeforeNum(int beforeNum) {
		this.beforeNum = beforeNum;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public MarchType getMarchType() {
		return marchType;
	}

	public void setMarchType(MarchType marchType) {
		this.marchType = marchType;
	}

	public MarchState getMarchState() {
		return marchState;
	}

	public void setMarchState(MarchState marchState) {
		this.marchState = marchState;
	}

	public GameLogSource getGameLogSource() {
		return gameLogSource;
	}

	public void setGameLogSource(GameLogSource gameLogSource) {
		this.gameLogSource = gameLogSource;
	}
	
	public long getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(long soldierId) {
		this.soldierId = soldierId;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	
	
	/**
	 * @return the sids
	 */
	public List<Integer> getSids() {
		return sids;
	}

	/**
	 * @param sids the sids to set
	 */
	public void setSids(List<Integer> sids) {
		this.sids = sids;
	}

	@Override
	public String toString() {
		return "SoldierChangeEventObject [soldierId=" + soldierId
				+ ", campType=" + soldierType + ", beforeNum=" + beforeNum
				+ ", currentNum=" + currentNum + ", marchType=" + marchType
				+ ", marchState=" + marchState + ", gameLogSource="
				+ gameLogSource + "]";
	}
	
}
