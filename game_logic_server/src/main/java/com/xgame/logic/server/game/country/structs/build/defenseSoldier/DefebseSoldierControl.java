package com.xgame.logic.server.game.country.structs.build.defenseSoldier;

import java.util.Iterator;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum.E001_LOGIN;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum.E132_DEFENS_SOLDIER;
import com.xgame.logic.server.core.language.view.error.ErrorCodeable;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.DefebseSoldierBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.country.structs.build.mod.ModifyKit;
import com.xgame.logic.server.game.country.structs.status.TimeState;
import com.xgame.logic.server.game.defensesoldier.bean.DefensSolderBuildPro;
import com.xgame.logic.server.game.defensesoldier.message.ResDefensAutoSetupMessage;
import com.xgame.logic.server.game.defensesoldier.message.ResDefensSolderMessage;
import com.xgame.logic.server.game.defensesoldier.message.ResDefensSolderSetupMessage;
import com.xgame.logic.server.game.defensesoldier.message.ResUnDefensSolderSetupMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.entity.Soldier;
 

/**
 *
 *2016-11-29  19:36:04
 *@author ye.yuan
 *	
 */
public class DefebseSoldierControl extends CountryBuildControl{
	
	/**
	 * 设置出兵
	 * @param player
	 * @param briefPro
	 * @param buildUid
	 */
	public ResDefensSolderSetupMessage settingSoldier(Player player, long soldierId, int num, int buildUid) {
		
		ResDefensSolderSetupMessage solderSetupMessage = new ResDefensSolderSetupMessage();
		solderSetupMessage.buildUid = buildUid;
		ErrorCodeable codeable = verify(player, buildUid);
		if(codeable!=null) {
			return solderSetupMessage;
		}
		DefebseSoldierBuild build = shutdownSoldier(player, buildUid);
		int soldierNum = PlayerAttributeManager.get().guardMaxNum(player.getId());
		if (num > soldierNum) {
			Language.ERRORCODE.send(player, E132_DEFENS_SOLDIER.CODE3.get());
			return solderSetupMessage;
		}
		
		Soldier soldier = player.getSoldierManager().getSoldier(player, soldierId);
		if (soldier == null) {
			Language.ERRORCODE.send(player, E001_LOGIN.CODE9.get());
			return solderSetupMessage;
		}
		
		if (num > soldier.getNum() || num <= 0) {
			Language.ERRORCODE.send(player, E001_LOGIN.CODE10.get());
			return solderSetupMessage;
		}
		
		SoldierBean soldierBrief = new SoldierBean();
		soldierBrief.num = num;
		soldierBrief.soldierId = soldierId;
		build.setSoldierBean(soldierBrief);
		solderSetupMessage.useSoldier = soldierBrief;
		
		player.roleInfo().getSoldierData().decrementSoldier(soldierId, num, SoldierChangeType.COMMON);
//		player.roleInfo().getSoldierData().addSoldier(soldierId, num, SoldierChangeType.DEFEND);
		player.roleInfo().getSoldierData().addDefenseSoldierNum(buildUid, soldierId, num);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		FightPowerKit.CAMP_POWER.math(player,GameLogSource.SETTING_SOLDIER);
		CurrencyUtil.send(player);
		player.getSoldierManager().send(player);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E132_DEFENS.CODE2.get());
		return solderSetupMessage;
	}
	
	public ResUnDefensSolderSetupMessage unSettingSoldier(Player player,int buildUid){
		ResUnDefensSolderSetupMessage solderSetupMessage = new ResUnDefensSolderSetupMessage();
		solderSetupMessage.buildUid = buildUid;
		ErrorCodeable verify = verify(player, buildUid);
		if(verify!=null){
			Language.ERRORCODE.send(player, verify);
			return solderSetupMessage;
		}
		shutdownSoldier(player, buildUid);
		InjectorUtil.getInjector().dbCacheService.update(player);
		player.getSoldierManager().send(player);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E132_DEFENS.CODE3);
		return solderSetupMessage;
	}
	
	protected CountryBuild newCountryBuild(){
		return new DefebseSoldierBuild();
	}
	
	/**
	 * 下掉士兵
	 * @param player
	 * @param buildUid
	 * @return
	 */
	private DefebseSoldierBuild shutdownSoldier(Player player,int buildUid){
		DefebseSoldierBuild build = getBuild(buildUid);
		//如果有设置兵  还原数据 滚
		if(build.getSoldierBean()!=null){
			SoldierBean soldierBean = build.getSoldierBean();
			player.roleInfo().getSoldierData().addSoldier(soldierBean.soldierId, soldierBean.num, SoldierChangeType.COMMON);
//			player.roleInfo().getSoldierData().decrementSoldier(soldierBean.soldierId, soldierBean.num, SoldierChangeType.DEFEND);
			player.roleInfo().getSoldierData().decrDefenseSoldierNum(buildUid, soldierBean.soldierId, soldierBean.num);
			FightPowerKit.CAMP_POWER.math(player,GameLogSource.SHUTDOWN_SOLDIER);
			CurrencyUtil.send(player);
			build.setSoldierBean(null);
		}
		return build;
	}
	
	/**
	 * 驻防建筑到伤兵
	 * @param player
	 * @param buildUid
	 * @param num
	 */
	public int soldierToModFactory(Player player,int buildUid, int num){
		if(num<=0)return 0;
		XBuild xBuild = buildMap.get(buildUid);
		if(xBuild==null||!(xBuild instanceof DefebseSoldierBuild))
			return 0;
		DefebseSoldierBuild build = (DefebseSoldierBuild)buildMap.get(buildUid);
		if (build.getSoldierBean() == null)
			return 0;
		int n = build.getSoldierBean().num - num;
		long soldierId = build.getSoldierBean().soldierId;
		if (n > 0) {
			build.getSoldierBean().num = n;
			n = num;
		} else {
			n = build.getSoldierBean().num;
			build.setSoldierBean(null);
		}
		
		ResDefensSolderMessage resDefensSolderMessage = new ResDefensSolderMessage();
		resDefensSolderMessage.defensSolderBuilds.add(toDefensSolderBuildPro(build));
		player.send(resDefensSolderMessage);
		
		return ModifyKit.insertDefenseSoldier(player, soldierId, n,buildUid);
	}
	
	/**
	 * 设置自动
	 * @param player
	 * @param status
	 * @param buildUid
	 * @return 
	 */
	public ResDefensAutoSetupMessage settingAuto(Player player,int status,int buildUid){
		ResDefensAutoSetupMessage autoSetupMessage = new ResDefensAutoSetupMessage();
		autoSetupMessage.auto = status;
		autoSetupMessage.buildUid = buildUid;
		ErrorCodeable codeable = verify(player, buildUid);
		if(codeable != null){
			Language.ERRORCODE.send(player, codeable.get());
			return autoSetupMessage;
		}
		AUTO_SETUP setup = AUTO_SETUP.values()[status];
		setup.reset(getBuild(buildUid));
		InjectorUtil.getInjector().dbCacheService.update(player);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E132_DEFENS.CODE1.get());
		return autoSetupMessage;
	}
	
	
	
	/**
	 * 验证是否可以设置防守驻军
	 * @param player
	 * @param buildUid
	 * @return
	 */
	private ErrorCodeable verify(Player player,int buildUid){
		if(player.roleInfo().getBasics().getFightStatus() == TimeState.COUNTRY_FIGHT.ordinal()){
			return E001_LOGIN.CODE5;
		}
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		if(buildingPir==null){
			return E001_LOGIN.CODE18;
		}
		CountryBuild build = getBuild(buildUid);
		if(build==null){
			return E001_LOGIN.CODE7;
		}
		return null;
	}
	
	public void send(Player player) {
		Iterator<XBuild> iterator = buildMap.values().iterator();
		ResDefensSolderMessage resDefensSolderMessage = new ResDefensSolderMessage();
		while (iterator.hasNext()) {
			resDefensSolderMessage.defensSolderBuilds.add(toDefensSolderBuildPro((DefebseSoldierBuild) iterator.next()));
		}
		player.send(resDefensSolderMessage);
	}
	
	public DefensSolderBuildPro toDefensSolderBuildPro(DefebseSoldierBuild defebseSoldierData){
		DefensSolderBuildPro defensSolderBuildPro = new DefensSolderBuildPro();
		defensSolderBuildPro.buildUid = defebseSoldierData.getUid();
		defensSolderBuildPro.autoDefens = defebseSoldierData.isAutoWork()?AUTO_SETUP.AUTO_DEFENS_ON.ordinal():AUTO_SETUP.AUTO_DEFENS_OFF.ordinal();
		defensSolderBuildPro.autoPatch = defebseSoldierData.isAutoPatch()?AUTO_SETUP.AUTO_PATCH_ON.ordinal():AUTO_SETUP.AUTO_PATCH_OFF.ordinal();
		if (defebseSoldierData.getSoldierBean() != null) {
			defensSolderBuildPro.useSoldier = new SoldierBean();
			defensSolderBuildPro.useSoldier.soldierId = defebseSoldierData.getSoldierBean().soldierId;
			defensSolderBuildPro.useSoldier.num = defebseSoldierData.getSoldierBean().num;
		}
		return defensSolderBuildPro;
	}

	
	/**
	 * 设置自动设置参数
	 * @author ye.yuan
	 *
	 */
	enum AUTO_SETUP{
		
		AUTO_PATCH_ON{
			public void reset(DefebseSoldierBuild defebseSoldierData){
				defebseSoldierData.setAutoPatch(true);
			}
		},
		
		AUTO_PATCH_OFF{
			public void reset(DefebseSoldierBuild defebseSoldierData){
				defebseSoldierData.setAutoPatch(false);
			}
		},
		
		AUTO_DEFENS_ON{
			public void reset(DefebseSoldierBuild defebseSoldierData){
				defebseSoldierData.setAutoWork(true);
			}
		},
		
		AUTO_DEFENS_OFF{
			public void reset(DefebseSoldierBuild defebseSoldierData){
				defebseSoldierData.setAutoWork(false);
			}
		},
		
		
		;
		
		public void reset(DefebseSoldierBuild defebseSoldierData){
			
		}
		
	}
	
	
}
