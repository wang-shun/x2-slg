package com.xgame.logic.server.game.war.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.DefebseSoldierBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.defenseSoldier.DefebseSoldierControl;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.bean.WarDefendData;
import com.xgame.logic.server.game.war.converter.BattleConverter;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;

/**
 * 防守方信息
 * @author jacky.jiang
 *
 */
public class WarDefender {
	
	/**
	 * 防守方玩家
	 */
	private Player player;
	
	/**
	 * 
	 */
	private boolean npc;
	
	// 防守方建筑信息
	private Map<Integer, WarBuilding> warBuildingMap = new ConcurrentHashMap<>();
	
	// 驻军信息
	private Map<Integer, Soldier> defendSoldierMap = new ConcurrentHashMap<>();
	
	// 驻防的部队信息
	private Map<Long, WorldMarchSoldier> reinforce = new ConcurrentHashMap<>();
	
	// 主基地资源信息
	private Map<CurrencyEnum, Long> mainCityResource = new ConcurrentHashMap<CurrencyEnum, Long>();
	
	public WarDefender() {
		
	}

	public void initBulidings(Player defenderPlayer) {
		// 建筑
		Collection<CountryBuild> countryBuilds = defenderPlayer.roleInfo().getBaseCountry().getAllBuild().values();
		if(countryBuilds != null && !countryBuilds.isEmpty()) {
			for(CountryBuild countryBuild : countryBuilds) {
				if(countryBuild.getSid() == BuildFactory.MINE_CAR.getTid()) {
					continue;
				}
				
				GlobalPir globalPir = GlobalPirFactory.get(GlobalConstant.WAR_BUILDING_DISPLAY);
				if(globalPir != null) {
					String str = globalPir.getValue();	
					String[] buildings = str.split(";");
					if(ArrayUtils.contains(buildings, countryBuild.getSid()+"")) {
						continue;
					}
				}
				
				// 战场建筑
				WarBuilding warBuilding = BattleConverter.valueOf(countryBuild, defenderPlayer);
				warBuildingMap.put(warBuilding.building.uid, warBuilding);
			}
		}
	}
	
	public static void main(String[] args) {
		String[] building = new String[]{"1100", "1101"};
		System.out.println(ArrayUtils.contains(building, "1100"));
	}
	
	/**
	 * 初始化野怪建筑
	 * @param countryBuildList
	 */
	public void initMonsterBuildings(List<WarBuilding> countryBuildList) {
		if(countryBuildList != null && !countryBuildList.isEmpty()) {
			for(WarBuilding warBuilding : countryBuildList) {
				warBuildingMap.put(warBuilding.building.uid, warBuilding);
			}
		}
	}
	
	/**
	 * 获取士兵信息
	 * @return
	 */
	public List<Soldier> getSoldierBeans() {
		return Lists.newArrayList(defendSoldierMap.values());
	}
	
	/**
	 * 初始化家园战斗防守方
	 * @param defendPlayer
	 */
	public void initCitySoldier(Player defendPlayer) {
		DefebseSoldierControl defebseSoldierControl = defendPlayer.getCountryManager().getDefebseSoldierControl();
		if(defebseSoldierControl == null) {
			return;
		}
		
		Map<Integer, XBuild> defendSoldier = defendPlayer.getCountryManager().getDefebseSoldierControl().getBuildMap();
		if(defendSoldier != null && !defendSoldier.isEmpty()) {
			Iterator<XBuild> iterator = defendSoldier.values().iterator();
			while (iterator.hasNext()) {
				DefebseSoldierBuild xBuild = (DefebseSoldierBuild) iterator.next();
				if (xBuild == null || xBuild.getSoldierBean() == null) {
					continue;
				}
				
				// 士兵信息
				Soldier soldier = defendPlayer.getSoldierManager().getSoldier(defendPlayer, xBuild.getSoldierBean().soldierId);
				if(soldier ==  null) {
					continue;
				}
				
				// 战场当中的士兵
				Soldier cloneSoldier = soldier.cloneFullSoldier(defendPlayer, xBuild.getSoldierBean().num);
				cloneSoldier.setNum(xBuild.getSoldierBean().num);
				cloneSoldier.setBuildUid(xBuild.getUid());
				cloneSoldier.setPlayerId(defendPlayer.getId());
				
				DefendSoldierBean defenSoldierBean = new DefendSoldierBean();
				defenSoldierBean.buildingUid = xBuild.getUid();
				defendSoldierMap.put(cloneSoldier.getBuildUid(), cloneSoldier);
			}
		}
	}
	
	/**
	 * 初始化野外战斗
	 * @param defendPlayer
	 * @param soldiers
	 */
	public void initReinforce(List<WorldMarchSoldier> defendSoldierList) {
		for(WorldMarchSoldier worldMarchSoldier : defendSoldierList) {
			reinforce.put(worldMarchSoldier.getPlayerId(), worldMarchSoldier);
		}
	}
	
	/**
	 * 重置防守驻军
	 * @param buildUid
	 * @param soldierId
	 * @param num
	 * @param hurtNum
	 * @param deadNum
	 */
	public void resetDefendSoldier(int buildUid, long soldierId, int hurtNum, int deadNum) {
		Soldier soldier = this.defendSoldierMap.get(buildUid);
		soldier.setNum(Math.max(0, soldier.getNum() - hurtNum));
		soldier.setInjureNum(soldier.getInjureNum() + hurtNum);
		soldier.setDeadNum(soldier.getDeadNum() + deadNum);
	}
	
	/**
	 * 重置驻扎部队
	 * @param playerId
	 * @param soldierId
	 * @param index
	 * @param num
	 * @param hurtNum
	 * @param deadNum
	 */
	public void resetReinforceSoldier(long playerId, long soldierId, int index, int hurtNum, int deadNum) {
		WorldMarchSoldier worldMarchSoldier = reinforce.get(playerId);
		Soldier soldier = worldMarchSoldier.querySoldierById(soldierId , index);
		soldier.setNum(Math.max(0, soldier.getNum() - hurtNum));
		soldier.setInjureNum(soldier.getInjureNum() + hurtNum);
		soldier.setDeadNum(soldier.getDeadNum() + deadNum);
	}
	
	/**
	 * 防守方信息
	 * @return
	 */
	public WarDefendData getWarDefenData() {
		WarDefendData warDefenData = new WarDefendData();
		if(!npc) {
			warDefenData.defendUid = player.getRoleId();
		}
		
		List<DefendSoldierBean> defendSoldier = new ArrayList<DefendSoldierBean>();
		warDefenData.soldiers = defendSoldier;
		if(defendSoldierMap != null && !defendSoldierMap.isEmpty()) {
			for(Soldier soldier : defendSoldierMap.values()) {
				if(!npc) {
					DesignMap designMap = null;
					if(soldier.getPlayerId() > 0 && soldier.getPlayerId() != player.getId()) {
						Player soldierPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, soldier.getPlayerId());
						designMap = soldierPlayer.getCustomWeaponManager().queryDesignMapById(soldierPlayer, soldier.getSoldierId());
					} else {
						designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
					}
				
					if(designMap == null) {
						continue;
					}
					
					defendSoldier.add(BattleConverter.converterDefendSolderBean(player, soldier));
				} else {
					defendSoldier.add(BattleConverter.converterNpcSoldierBean(soldier));
				}
			}
		}
		
		if(!npc) {
			if(reinforce != null && !reinforce.isEmpty()) {
				for(WorldMarchSoldier warMarchSoldier : reinforce.values()) {
					defendSoldier.addAll(BattleConverter.converterDefendSolderBean(player, warMarchSoldier));
				}
			}
		}
		
		warDefenData.buildings = new ArrayList<WarBuilding>() {
			private static final long serialVersionUID = 1488604955214320698L;

			{
				this.addAll(warBuildingMap.values());
			}
		};
		return warDefenData;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Map<Integer, WarBuilding> getWarBuildingMap() {
		return warBuildingMap;
	}

	public void setWarBuildingMap(Map<Integer, WarBuilding> warBuildingMap) {
		this.warBuildingMap = warBuildingMap;
	}

	public Map<Long, WorldMarchSoldier> getReinforce() {
		return reinforce;
	}

	public void setReinforce(Map<Long, WorldMarchSoldier> reinforce) {
		this.reinforce = reinforce;
	}
	
	
	public Map<Integer, Soldier> getDefendSoldierMap() {
		return defendSoldierMap;
	}

	public void setDefendSoldierMap(Map<Integer, Soldier> defendSoldierMap) {
		this.defendSoldierMap = defendSoldierMap;
	}

	public long queryDefendResource(int buildUid) {
		WarBuilding warBuilding = warBuildingMap.get(buildUid);
		return warBuilding.resourceNum;
	}

	public boolean isNpc() {
		return npc;
	}

	public void setNpc(boolean npc) {
		this.npc = npc;
	}

	public Map<CurrencyEnum, Long> getMainCityResource() {
		return mainCityResource;
	}

	public void setMainCityResource(Map<CurrencyEnum, Long> mainCityResource) {
		this.mainCityResource = mainCityResource;
	}
	
}
