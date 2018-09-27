package com.xgame.logic.server.game.country.structs.build.camp;

import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.country.structs.build.camp.data.CampData;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.BuildingRewardEventObject;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.soldier.entity.model.SoldierChangeEventObject;
import com.xgame.logic.server.game.soldier.message.ResUpdateSoldierMessage;

/**
 * 军营 2016-8-05 12:22:18
 *
 * @author ye.yuan
 *
 */
@Slf4j
public class CampBuildControl extends CountryBuildControl {

	public int getAttributeNode() {
		return 0;
	}

	@Override
	public void createInitAfter(Player player, XBuild countryBuild) {
		// 生成系统兵种图纸
		player.getArmyShopManager().unlockDesignMap(player, countryBuild);
		player.getCustomWeaponManager().send(player);
	}
	
	public void levelUpAfter(Player player, int uid) {
		// 升级解锁图纸
		CountryBuild countryBuild = player.roleInfo().getBaseCountry().getAllBuild().get(uid);
		if(countryBuild  == null) {
			return;
		}
		
		DesignMap designMap = player.getArmyShopManager().unlockDesignMap(player, countryBuild);
		if(designMap != null) {
			player.getCustomWeaponManager().sendDesignMap(player, designMap);
		}
		
	}
	
	public void outputSuccess(Player player, int buildUid, long soldierId, int num) {
		CampData campData = player.roleInfo().getSoldierData().getCampData();
		SoldierBrief soldierBrief = campData.getSoldierGives().get(soldierId);
		if (soldierBrief == null) {
			soldierBrief = new SoldierBrief();
			soldierBrief.setSoldierId(soldierId);
			campData.getSoldierGives().put(soldierId, soldierBrief);
		}
		soldierBrief.setNum(soldierBrief.getNum() + num);

		InjectorUtil.getInjector().dbCacheService.update(player);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E101_CAMP.CODE5);
	}

	public void giveOutput(Player player, int uid) {
		CountryBuild countryBuild = player.roleInfo().getBaseCountry().getCountryBuildByUid(uid);
		if(countryBuild == null) {
			log.error("建筑不存在.");
			return;
		}
		
		GameLogSource gameLogSource = null;
		CampBuildControl campBuildControl = null;
		if(countryBuild.getSid() == BuildFactory.SUV.getTid()) {
			gameLogSource = GameLogSource.CAMP_OUTPUT_SUV;
		} else if(countryBuild.getSid() == BuildFactory.TANK.getTid()) {
			gameLogSource = GameLogSource.CAMP_OUTPUT_PLANE;
		} else {
			gameLogSource = GameLogSource.CAMP_OUTPUT_TANK;
		}
		campBuildControl = player.getSoldierManager().getCampBuildControlByBuildTid(player, countryBuild.getSid());
		
		ResUpdateSoldierMessage resUpdateSoldierMessage = new ResUpdateSoldierMessage();
		CampData campData = player.roleInfo().getSoldierData().getCampData();
		if(campData!=null){
			Iterator<SoldierBrief> iterator3 = campData.getSoldierGives().values().iterator();
			while (iterator3.hasNext()) {
				SoldierBrief soldierBrief = iterator3.next();
				Soldier soldier = player.getSoldierManager().getOrCreateSoldier(player, soldierBrief.getSoldierId());
				player.roleInfo().getSoldierData().addSoldier(soldierBrief.getSoldierId(), soldierBrief.getNum(), SoldierChangeType.COMMON);
				DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldierBrief.getSoldierId());
				if (soldier != null) {
					if(soldier.isFisrtOutput()) {
						soldier.setFisrtOutput(false);

						// 处理属性计算
//						player.getAttributeAppenderManager().rebuildObject(campBuildControl.getAttributeNode(), soldier.getSoldierId(), true);
					}

					//　返回可收取的列表信息
					SoldierBean soldierBean = new SoldierBean();
					soldierBean.soldierId = soldierBrief.getSoldierId();
					resUpdateSoldierMessage.collectSoldierList.add(soldierBean);

					// 
					SoldierBean campSoldierBean = new SoldierBean();
					campSoldierBean.soldierId = soldier.getSoldierId();
					campSoldierBean.num = soldier.getNum();
					resUpdateSoldierMessage.campSoldierList.add(campSoldierBean);
					
					SoldierChangeEventObject sce = new SoldierChangeEventObject(player, EventTypeConst.SOLDIER_CHANGE, campSoldierBean.soldierId, designMap.getType(), 
																soldier.getNum(), soldier.getNum() + soldierBrief.getNum(), null, null, 
																gameLogSource, designMap.getSystemIndex(), designMap.partBeanIdList());
					sce.setLevel(designMap.getSystemIndex());
					EventBus.getSelf().fireEvent(sce);
				}
				iterator3.remove();
			}
			
			try {
				FightPowerKit.CAMP_POWER.math(player,gameLogSource);
			} catch(Exception e) {
				System.out.println("e:"+e.getMessage());
			}
			InjectorUtil.getInjector().dbCacheService.update(player);
			EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
			player.send(resUpdateSoldierMessage);
			CurrencyUtil.send(player);
			EventBus.getSelf().fireEvent(new BuildingRewardEventObject(player));
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E101_CAMP.CODE1.get());
		} else {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE9.get());
		}
	}
}
