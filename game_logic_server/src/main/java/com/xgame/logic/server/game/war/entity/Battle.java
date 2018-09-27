package com.xgame.logic.server.game.war.entity;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.handler.IWarHandler;
import com.xgame.logic.server.game.war.message.ResWarDataMessage;


/**
 * 战场信息
 * @author jacky.jiang
 *
 */
public class Battle {
	
	/**
	 * 战斗id
	 */
	private long battleId;
	
	/**
	 * 战斗开始时间
	 */
	private long startTime;
	
	/**
	 * 战斗结束时间
	 */
	private long endTime;
	
	/**
	 * 进攻方士兵种类列表（坦克，轮式战车，飞机）
	 */
	private Set<Integer> attackSoldierTypeList;
	
	/**
	 * 战斗类型
	 */
	private WarType warType;
	
	/**
	 * 获胜方id
	 */
	private long winPlayerId;
	
	/**
	 * 进攻方数据
	 */
	private WarAttacker warAttacker;
	
	/**
	 * 防守方数据
	 */
	private WarDefender warDefender;
	
	/**
	 * 玩家获取的资源信息
	 */
	private Map<Long, WarResourceBean> warResource = new ConcurrentHashMap<Long, WarResourceBean>();
	
	/**
	 * 整个战场开始数据，还原战场信息
	 */
	private ResWarDataMessage resWarDataMessage = new ResWarDataMessage();
	
	/**
	 * 战斗参数
	 */
	private WarFightParam warFightParam;
	
	/**
	 * 战斗执行器
	 */
	private IWarHandler warHandler;
	
	/**
	 * 是否是一次性执行完
	 */
	private boolean execute;
	
	/**
	 * 是否是迁城战斗
	 */
	private boolean moveCity;
	
	public long getOwnerId() {
		return warAttacker.getPlayer().getRoleId();
	}

	public long getBattleId() {
		return battleId;
	}

	public void setBattleId(long battleId) {
		this.battleId = battleId;
	}


	public WarAttacker getWarAttacker() {
		return warAttacker;
	}

	public void setWarAttacker(WarAttacker warAttacker) {
		this.warAttacker = warAttacker;
	}

	public WarDefender getWarDefender() {
		return warDefender;
	}

	public void setWarDefender(WarDefender warDefender) {
		this.warDefender = warDefender;
	}

	public long getWinPlayerId() {
		return winPlayerId;
	}

	public void setWinPlayerId(long winPlayerId) {
		this.winPlayerId = winPlayerId;
	}

	public Map<Long, WarResourceBean> getWarResource() {
		return warResource;
	}

	public void setWarResource(Map<Long, WarResourceBean> warResource) {
		this.warResource = warResource;
	}

	public WarType getWarType() {
		return warType;
	}

	public void setWarType(WarType warType) {
		this.warType = warType;
	}

	public ResWarDataMessage getResWarDataMessage() {
		return resWarDataMessage;
	}

	public void setResWarDataMessage(ResWarDataMessage resWarDataMessage) {
		this.resWarDataMessage = resWarDataMessage;
	}

	public IWarHandler getWarHandler() {
		return warHandler;
	}

	public void setWarHandler(IWarHandler warHandler) {
		this.warHandler = warHandler;
	}

	public WarFightParam getWarFightParam() {
		return warFightParam;
	}

	public void setWarFightParam(WarFightParam warFightParam) {
		this.warFightParam = warFightParam;
	}

	public boolean isExecute() {
		return execute;
	}

	public void setExecute(boolean execute) {
		this.execute = execute;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Set<Integer> getAttackSoldierTypeList() {
		return attackSoldierTypeList;
	}

	public void setAttackSoldierTypeList(Set<Integer> attackSoldierTypeList) {
		this.attackSoldierTypeList = attackSoldierTypeList;
	}

	public boolean isMoveCity() {
		return moveCity;
	}

	public void setMoveCity(boolean moveCity) {
		this.moveCity = moveCity;
	}
	
}
