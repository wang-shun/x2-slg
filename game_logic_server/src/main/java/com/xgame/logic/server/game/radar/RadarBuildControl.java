/**
 * 
 */
package com.xgame.logic.server.game.radar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.xgame.config.radar.RadarPir;
import com.xgame.config.radar.RadarPirFactory;
import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.radar.entity.InvestigateData;
import com.xgame.logic.server.game.radar.entity.PlayerRadarBeAttacker;
import com.xgame.logic.server.game.radar.entity.RadarUtil;
import com.xgame.logic.server.game.radar.message.ResInvestigateInfoMessage;
import com.xgame.logic.server.game.radar.message.ResRadarMessage;
import com.xgame.logic.server.game.radar.message.ResRadarTimeEndMessage;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.utils.TimeUtils;

/**
 * 雷达
 * @author ye.yuan
 *
 */
public class RadarBuildControl extends CountryBuildControl {
	
	public void investigate(Player activePlayer,Player player, int startTime, int time,Vector2Bean location,List<Soldier> soldiers, long marchId){
		PlayerRadarBeAttacker radarBeAttacker = new  PlayerRadarBeAttacker(activePlayer, player, startTime, time, location, soldiers,marchId);
		if(RadarUtil.isExpired(radarBeAttacker,TimeUtils.getCurrentTime())){
			return;
		}
		ResRadarMessage radarMessage = new ResRadarMessage();
		radarMessage.radars.add(RadarUtil.toActivePlayerInfo(radarBeAttacker));
		player.send(radarMessage);
		//更新被攻击者雷达数据
		player.refreshRadarInfo(radarBeAttacker);
	}
	
	/**
	 * 战斗开始  清掉预警 
	 * @param player
	 */
	public void clearWarning(Player player,long marchId){
		ResRadarTimeEndMessage timeEndMessage = new ResRadarTimeEndMessage();
		Iterator<PlayerRadarBeAttacker> iterator = player.roleInfo().getRadarData().getAttackers().iterator();
		while (iterator.hasNext()) {
			PlayerRadarBeAttacker attacker = iterator.next();
			if(attacker.getMarchId() == marchId){
				iterator.remove();
				timeEndMessage.marchIds.add(attacker.getMarchId());
			}
		}
		player.send(timeEndMessage);
	}
	
	
	/**
	 * 新增一个攻击者  如果被攻击者有雷达就会调用该方法
	 * @param player
	 * @param attacker
	 */
	public void refresh(Player player,PlayerRadarBeAttacker attacker){
		int t = TimeUtils.getCurrentTime();
		Iterator<PlayerRadarBeAttacker> iterator = player.roleInfo().getRadarData().getAttackers().iterator();
		while (iterator.hasNext()) {
			if(RadarUtil.isExpired(iterator.next(),t)){
				iterator.remove();
			}
		}
		if(attacker!=null){
			Player activePlayer = attacker.getActivePlayer();
			XBuild defianlBuild = getDefianlBuild();
			RadarPir radarPir = RadarPirFactory.get(defianlBuild.getLevel());
			if(radarPir==null){
				Language.ERRORCODE.send(activePlayer, ErrorCodeEnum.E301_RANDER.CODE1);
				return;
			}
			List<Soldier> soldiers = attacker.getSoldiers();
			List<Soldier> list = new ArrayList<>();
			if (Double.valueOf(radarPir.getEnemy_num()) >= 0 && soldiers != null && !soldiers.isEmpty()) {
				int i = 0;
				do {
					Soldier soldier = soldiers.remove(RandomUtils.nextInt(0,
							soldiers.size()));
					double nextDouble = RandomUtils.nextDouble(
							(1 - Double.valueOf(radarPir.getEnemy_num())),
							(1 + Double.valueOf(radarPir.getEnemy_num())));
					soldier.setNum((int) Math.ceil(soldier.getNum()
							* nextDouble));
					list.add(soldier);
					i++;
				} while (i < radarPir.getEnemy_type() && soldiers.size() > 0);
			} else {
				if (soldiers != null && !soldiers.isEmpty()) {
					soldiers.clear();
				}
			}
			
			attacker.setSoldiers(list);
			player.roleInfo().getRadarData().getAttackers().add(attacker);
			
			// 更新
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
	}
	
	/**
	 * 
	 * @param player
	 * @param investigateData
	 */
	public void addInvestigateData(Player player,InvestigateData investigateData){ 
		if (investigateData != null) {
			player.roleInfo().getRadarData().addInvestigateData(investigateData.getPosition().x, investigateData.getPosition().y, investigateData);
			
			// 更新
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
	}
	
	/**
	 * 请求雷达侦查信息
	 * @param player
	 * @param serverId
	 * @param x
	 * @param y
	 * @return
	 */
	public ResInvestigateInfoMessage reqRadarInfo(Player player,int serverId,int x,int y){
		ResInvestigateInfoMessage infoMessage = new ResInvestigateInfoMessage();
		InvestigateData investigateData = player.roleInfo().getRadarData().getInvestigateData(x, y);
		if (investigateData == null) {
			return infoMessage;
		}
		
		if(SpriteType.PLAYER.getType() == investigateData.getSpriteType()){
			Iterator<WarBuilding> iterator = investigateData.getWarBuildList().iterator();
			while (iterator.hasNext()) {
				WarBuilding radarBuild = (WarBuilding) iterator.next();
				infoMessage.enemyBuilds.add(radarBuild);
			}
			
			// 防守方
			Iterator<DefendSoldierBean> iterator2 = investigateData.getRadrSoldiers().iterator();
			while (iterator2.hasNext()) {
				DefendSoldierBean radarDefebse = (DefendSoldierBean) iterator2.next();
				infoMessage.enemySoldiers.add(radarDefebse);
			}
		// 侦查野外玩家基地防守方信息
		} else if(SpriteType.RESOURCE.getType() == investigateData.getSpriteType()) {
			Iterator<DefendSoldierBean> iterator = investigateData.getRadrSoldiers().iterator();
			while (iterator.hasNext()) {
				DefendSoldierBean defendSoldierBean = (DefendSoldierBean) iterator.next();
				infoMessage.enemySoldiers.add(defendSoldierBean);
			}
		}
		return infoMessage;
	}
	
	/**
	 * 发送雷达数据
	 */
	public void send(Player player) {
		ResRadarMessage radarMessage = new ResRadarMessage();
		int t = TimeUtils.getCurrentTime();
		Iterator<PlayerRadarBeAttacker> iterator = player.roleInfo().getRadarData().getAttackers().iterator();
		while (iterator.hasNext()) {
			PlayerRadarBeAttacker radarAttacker = iterator.next();
			if(RadarUtil.isExpired(radarAttacker,t)){
				iterator.remove();
			}else{
				radarMessage.radars.add(RadarUtil.toActivePlayerInfo(radarAttacker));
			}
		}
		player.send(radarMessage);
	}
	
}
