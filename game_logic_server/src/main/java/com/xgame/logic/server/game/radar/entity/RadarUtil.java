package com.xgame.logic.server.game.radar.entity;

import java.util.Iterator;
import java.util.List;

import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.DefebseSoldierBuild;
import com.xgame.logic.server.game.country.structs.BuildInfo;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.defenseSoldier.DefebseSoldierControl;
import com.xgame.logic.server.game.country.structs.build.defenseTower.DefenseTowerBuildControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.radar.bean.ActivePlayerInfo;
import com.xgame.logic.server.game.radar.bean.RadarCorpsData;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.converter.BattleConverter;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;

/**
 * 雷达枚举,根据传入参数 确定侦查或攻击类型  使用针对性的方法
 * 雷达的任何行为触发都是基于玩家,所以雷达行为都在主动者线程里发起
 * @author ye.yuan
 *
 */
public enum RadarUtil {

	,
	;
	
	/**
	 * 过滤雷达 看攻击者是不是已经完成攻击
	 * @param radarAttacker
	 * @return
	 */
	public static boolean isExpired(PlayerRadarBeAttacker radarAttacker,int t){
		if(t>=(radarAttacker.getStartTime()+radarAttacker.getTime())){
			return true;
		}
		return false;
	}
	
	/**
	 * 变成发送数据结构
	 * @param attacker
	 * @return
	 */
	public static ActivePlayerInfo toActivePlayerInfo(PlayerRadarBeAttacker attacker){
		ActivePlayerInfo playerInfo = new ActivePlayerInfo();
		playerInfo.playerLoction = attacker.getLoction();
		playerInfo.playerId = attacker.getActivePlayerId();
		playerInfo.playerImg = attacker.getActivePlayer().roleInfo().getBasics().getHeadImg();
		playerInfo.playerName = attacker.getActivePlayer().getName();
		playerInfo.beginTime = attacker.getStartTime();
		playerInfo.time = attacker.getTime();
		playerInfo.marchId = attacker.getMarchId();
		Iterator<Soldier> iterator = attacker.getSoldiers().iterator();
		while (iterator.hasNext()) {
			Soldier next = iterator.next();
			RadarCorpsData corpsData = new RadarCorpsData();
			corpsData.soldierBrief = new SoldierBean();
//			corpsData.corpsIconName = next.getIcon();
//			corpsData.corpsName=next.getName();
			corpsData.soldierLoction = next.getVector();
			corpsData.soldierBrief.soldierId = next.getSoldierId();
			corpsData.soldierBrief.num = next.getNum();
			corpsData.color = next.getIndex();
			playerInfo.corpsList.add(corpsData);
		}
		return playerInfo;
	}
	
	public static InvestigateData toRadarSoldiers(Player player,List<WorldMarchSoldier> soldierMarchList) {
		InvestigateData investigateData = new InvestigateData();
		Iterator<WorldMarchSoldier> iterator = soldierMarchList.iterator();
		while (iterator.hasNext()) {
			WorldMarchSoldier worldMarchSoldier = iterator.next();
			if(worldMarchSoldier.getPlayerId()==player.getRoleId()) {
				Iterator<Soldier> iterator2 = worldMarchSoldier.querySoldierList().iterator();
				while (iterator2.hasNext()) {
					Soldier soldier = (Soldier) iterator2.next();
					DefendSoldierBean defendSoldierBean = BattleConverter.converterDefendSolderBean(player, soldier);
					investigateData.getRadrSoldiers().add(defendSoldierBean);
				}
				break;
			}
		}
		return investigateData;
	}
	
	/**
	 * 雷达数据转换
	 * @param player
	 * @return
	 */
	public static InvestigateData toRadarBuild(Player player) {
		InvestigateData investigateData = new InvestigateData();
		investigateData.setPosition(player.getLocation().toVectorBean());
		Iterator<BuildInfo> iterator = player.getAllCountryBuildList().iterator();
		while (iterator.hasNext()) {
			BuildInfo buildInfo = iterator.next();
			CountryBuild countryBuild = (CountryBuild)buildInfo.getBuild();
			if(BuildFactory.instanceOf(countryBuild.getSid(),DefenseTowerBuildControl.class)) {
				WarBuilding warBuilding = BattleConverter.valueOf(countryBuild, player);
				investigateData.getWarBuildList().add(warBuilding);
			} else if(BuildFactory.instanceOf(countryBuild.getSid(),DefebseSoldierControl.class)) {
				DefebseSoldierBuild build = (DefebseSoldierBuild)countryBuild;
				WarBuilding warBuilding = BattleConverter.valueOf(countryBuild, player);
				if(build.getSoldierBean()!=null){
					Soldier soldier = player.getSoldierManager().getSoldier(player, build.getSoldierBean().soldierId);
					if(soldier != null){
						DefendSoldierBean defeSoldierBean = BattleConverter.converterDefendSolderBean(player, soldier);
						investigateData.getRadrSoldiers().add(defeSoldierBean);
					}
				}
				investigateData.getWarBuildList().add(warBuilding);
			}
		}
		return investigateData;
	}
	
}
