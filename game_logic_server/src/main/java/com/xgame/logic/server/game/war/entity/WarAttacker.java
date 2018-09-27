package com.xgame.logic.server.game.war.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.WarAttackData;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import com.xgame.logic.server.game.war.converter.BattleConverter;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;


/**
 * 战斗数据
 * @author jacky.jiang
 *
 */
public class WarAttacker {
	
	/**
	 * 玩家信息
	 */
	private transient Player player;
	
	/**
	 * 战斗携带燃油数量
	 */
	private int oilNum;
	
	/**
	 * 玩家出征士兵信息
	 */
	private Map<Long, WorldMarchSoldier> worldMarchSoldierMap = new ConcurrentHashMap<Long, WorldMarchSoldier>();
	
	/**
	 * 初始化进攻方多人战斗
	 * @param player
	 * @param soldierList
	 * @param oilNum
	 */
	public void initSoldier(Player player, List<WorldMarchSoldier> worldMarchSoldierList, int oilNum) {
		for(WorldMarchSoldier worldMarchSoldier : worldMarchSoldierList) {
			worldMarchSoldierMap.put(worldMarchSoldier.getPlayerId(), worldMarchSoldier);
		}
		this.oilNum = oilNum;
	}
	
	/**
	 * 重置士兵数量
	 * @param playerId
	 * @param soldierId
	 * @param index
	 * @param num
	 * @param hurtNum
	 * @param deadNum
	 */
	public void resetSoldier(long playerId, long soldierId, int index, int hurtNum, int deadNum) {
		WorldMarchSoldier worldMarchSoldier = worldMarchSoldierMap.get(playerId);
		Soldier soldier = worldMarchSoldier.querySoldierById(soldierId , index);
		soldier.setNum(Math.max(0, soldier.getNum() - hurtNum - deadNum));
		soldier.setInjureNum(soldier.getInjureNum() + hurtNum);
		soldier.setDeadNum(soldier.getDeadNum() + deadNum);
	}
	
	
	public WarAttackData getWarAttackData() {
		WarAttackData warAttackData = new WarAttackData();
		warAttackData.attackUid = player.getRoleId();
		List<WarSoldierBean> soList = new ArrayList<WarSoldierBean>();
		warAttackData.soldiers = soList;
		
		Collection<WorldMarchSoldier> collection = worldMarchSoldierMap.values();
		for(WorldMarchSoldier worldMarchSoldier : collection) {
			Player player  = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarchSoldier.getPlayerId());
			if(player != null) {
				warAttackData.soldiers.addAll(BattleConverter.converterWarSoldierBean(player, worldMarchSoldier));
			}
		}
		return warAttackData;
	}
	
	public void resetSoldier() {
		
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getOilNum() {
		return oilNum;
	}

	public void setOilNum(int oilNum) {
		this.oilNum = oilNum;
	}

	public long getAttackId() {
		return player.getId();
	}
	
	public Map<Long, WorldMarchSoldier> getWorldMarchSoldierMap() {
		return worldMarchSoldierMap;
	}

	public void setWorldMarchSoldierMap(Map<Long, WorldMarchSoldier> worldMarchSoldierMap) {
		this.worldMarchSoldierMap = worldMarchSoldierMap;
	}
	
	/**
	 * 查询玩家士兵信息
	 * @param playerId
	 * @return
	 */
	public List<Soldier> queryPlayerSoldier(long playerId) {
		WorldMarchSoldier worldMarchSoldier = worldMarchSoldierMap.get(playerId);
		if(worldMarchSoldier != null) {
			return worldMarchSoldier.querySoldierList();
		}
		return null;
	}
}
