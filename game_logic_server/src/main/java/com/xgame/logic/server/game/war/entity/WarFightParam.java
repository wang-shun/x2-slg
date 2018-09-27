package com.xgame.logic.server.game.war.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;

/**
 * 
 * @author jacky.jiang
 *
 */
public class WarFightParam {
	
	/**
	 * 进攻方
	 */
	private Player attackPlayer;
	/**
	 * 防守方
	 */
	private Player defendPlayer;
	/**
	 * 进攻方士兵信息
	 */
	private List<WorldMarchSoldier> attackSoldiers = new ArrayList<>();
	/**
	 * 防守方士兵
	 */
	private List<WorldMarchSoldier> defendSoldiers = new ArrayList<>();
	/**
	 * 携带石油数量
	 */
	private int oil;
	/**
	 * 战斗类型
	 */
	private WarType battleType;
	/**
	 * 进攻方行军队列
	 */
	private WorldMarch attackWorldMarch;
	/**
	 * 进攻方精灵信息
	 */
	private SpriteInfo attackSpriteInfo;
	/**
	 * 防守方行军队列
	 */
	private WorldMarch defendWorldMarch;
	/**
	 * 防守方精灵信息
	 */
	private SpriteInfo targetSpriteInfo;
	
	private String teamId;
	
	/**
	 * 防守方已经采集数量
	 */
	private int defendExplorerNum;
	
	/**
	 * 进攻方出征队列
	 */
	private List<WorldMarch> attackMarchList = new ArrayList<WorldMarch>();
	
	/**
	 * 防守方出征队列
	 */
	private List<WorldMarch> defendMarchList = new ArrayList<WorldMarch>();
	
	/**
	 * 副本ID
	 */
	private int copyId;
	
	/**
	 * 节点ID
	 */
	private int pointId;
	
	/**
	 * 资源类型
	 */
	private int resourceType;
	
	/**
	 * 资源等级
	 */
	private int resourceLevel;
	
	/**
	 * 迁城战斗
	 */
	private boolean moveCity;
	
	
	public Player getAttackPlayer() {
		return attackPlayer;
	}

	public void setAttackPlayer(Player attackPlayer) {
		this.attackPlayer = attackPlayer;
	}

	public Player getDefendPlayer() {
		return defendPlayer;
	}

	public void setDefendPlayer(Player defendPlayer) {
		this.defendPlayer = defendPlayer;
	}

	public List<WorldMarchSoldier> getAttackSoldiers() {
		return attackSoldiers;
	}

	public void setAttackSoldiers(List<WorldMarchSoldier> attackSoldiers) {
		this.attackSoldiers = attackSoldiers;
	}

	public List<WorldMarchSoldier> getDefendSoldiers() {
		return defendSoldiers;
	}

	public void setDefendSoldiers(List<WorldMarchSoldier> defendSoldiers) {
		this.defendSoldiers = defendSoldiers;
	}

	public int getOil() {
		return oil;
	}

	public void setOil(int oil) {
		this.oil = oil;
	}

	public WarType getBattleType() {
		return battleType;
	}

	public void setBattleType(WarType battleType) {
		this.battleType = battleType;
	}

	public WorldMarch getAttackWorldMarch() {
		return attackWorldMarch;
	}

	public void setAttackWorldMarch(WorldMarch attackWorldMarch) {
		this.attackWorldMarch = attackWorldMarch;
	}

	public SpriteInfo getAttackSpriteInfo() {
		return attackSpriteInfo;
	}

	public void setAttackSpriteInfo(SpriteInfo attackSpriteInfo) {
		this.attackSpriteInfo = attackSpriteInfo;
	}

	public WorldMarch getDefendWorldMarch() {
		return defendWorldMarch;
	}

	public void setDefendWorldMarch(WorldMarch defendWorldMarch) {
		this.defendWorldMarch = defendWorldMarch;
	}

	public SpriteInfo getTargetSpriteInfo() {
		return targetSpriteInfo;
	}

	public void setTargetSpriteInfo(SpriteInfo targetSpriteInfo) {
		this.targetSpriteInfo = targetSpriteInfo;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public List<WorldMarch> getAttackMarchList() {
		return attackMarchList;
	}

	public void setAttackMarchList(List<WorldMarch> attackMarchList) {
		this.attackMarchList = attackMarchList;
	}

	public List<WorldMarch> getDefendMarchList() {
		return defendMarchList;
	}

	public void setDefendMarchList(List<WorldMarch> defendMarchList) {
		this.defendMarchList = defendMarchList;
	}

	public int getDefendExplorerNum() {
		return defendExplorerNum;
	}

	public void setDefendExplorerNum(int defendExplorerNum) {
		this.defendExplorerNum = defendExplorerNum;
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public int getPointId() {
		return pointId;
	}

	public void setPointId(int pointId) {
		this.pointId = pointId;
	}

	public int getResourceType() {
		return resourceType;
	}

	public int getResourceLevel() {
		return resourceLevel;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public void setResourceLevel(int resourceLevel) {
		this.resourceLevel = resourceLevel;
	}
	
	public boolean isMoveCity() {
		return moveCity;
	}

	public void setMoveCity(boolean moveCity) {
		this.moveCity = moveCity;
	}

	public Set<Integer> soldierTypeList() {
		Set<Integer> soldierList = new ConcurrentHashSet<>();
		for(WorldMarchSoldier worldMarchSoldier : attackSoldiers) {
			soldierList.addAll(worldMarchSoldier.getSoldierTypeList());
		}
		return soldierList;
	}
	
}
