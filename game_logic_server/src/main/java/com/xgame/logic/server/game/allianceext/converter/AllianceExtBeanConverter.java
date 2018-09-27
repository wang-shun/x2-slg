package com.xgame.logic.server.game.allianceext.converter;

import com.xgame.common.ItemConf;
import com.xgame.config.armyShopItem.ArmyShopItemPir;
import com.xgame.config.armyShopItem.ArmyShopItemPirFactory;
import com.xgame.config.armyShopTreasure.ArmyShopTreasurePir;
import com.xgame.config.armyShopTreasure.ArmyShopTreasurePirFactory;
import com.xgame.logic.server.game.allianceext.bean.AllianceAcitivityBean;
import com.xgame.logic.server.game.allianceext.bean.AllianceAcitivityQuestBean;
import com.xgame.logic.server.game.allianceext.bean.AllianceActivityViewBean;
import com.xgame.logic.server.game.allianceext.bean.AllianceBoxBean;
import com.xgame.logic.server.game.allianceext.bean.AllianceBuildBean;
import com.xgame.logic.server.game.allianceext.bean.AllianceExerciseBean;
import com.xgame.logic.server.game.allianceext.bean.AllianceScienceBean;
import com.xgame.logic.server.game.allianceext.bean.AwardBean;
import com.xgame.logic.server.game.allianceext.bean.GoodsBean;
import com.xgame.logic.server.game.allianceext.bean.MarchInfo;
import com.xgame.logic.server.game.allianceext.bean.PlayerAllianceExtBean;
import com.xgame.logic.server.game.allianceext.entity.AllianceActivity;
import com.xgame.logic.server.game.allianceext.entity.AllianceActivityQuest;
import com.xgame.logic.server.game.allianceext.entity.AllianceBenifit;
import com.xgame.logic.server.game.allianceext.entity.AllianceBox;
import com.xgame.logic.server.game.allianceext.entity.AllianceBuild;
import com.xgame.logic.server.game.allianceext.entity.AllianceGoods;
import com.xgame.logic.server.game.allianceext.entity.AllianceScience;
import com.xgame.logic.server.game.allianceext.entity.AwardInfo;
import com.xgame.logic.server.game.allianceext.entity.ExerciseInfo;
import com.xgame.logic.server.game.allianceext.entity.PlayerAllianceExt;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.entity.WorldMarch;

public class AllianceExtBeanConverter {
	
	public static AllianceBuildBean converterAllianceBuildBean(AllianceBuild allianceBuild){
		AllianceBuildBean allianceBuildBean = new AllianceBuildBean();
		allianceBuildBean.allianceId = allianceBuild.getAllianceId();
		allianceBuildBean.buildTid = allianceBuild.getBuildTid();
		allianceBuildBean.createTime = allianceBuild.getCreateTime();
		allianceBuildBean.level = allianceBuild.getLevel();
		allianceBuildBean.position = allianceBuild.getPosition();
		allianceBuildBean.uid = allianceBuild.getId();
		return allianceBuildBean;
	}
	
	public static AllianceScienceBean converterAllianceScienceBean(AllianceScience allianceScience){
		AllianceScienceBean allianceScienceBean = new AllianceScienceBean();
		allianceScienceBean.allianceId = allianceScience.getAllianceId();
		allianceScienceBean.exp = allianceScience.getExp();
		allianceScienceBean.level = allianceScience.getLevel();
		allianceScienceBean.techTid = allianceScience.getTid();
		allianceScienceBean.uid = allianceScience.getId();
		return allianceScienceBean;
		
	}
	
	public static PlayerAllianceExtBean converterPlayerAllianceExtBean(PlayerAllianceExt playerAllianceExt){
		PlayerAllianceExtBean playerAllianceExtBean = new PlayerAllianceExtBean();
		playerAllianceExtBean.playerId = playerAllianceExt.getPlayerId();
		playerAllianceExtBean.activitySocre = playerAllianceExt.getActivityScore();
		playerAllianceExtBean.rewardActivityGold = playerAllianceExt.getRewardActivityGold();
		playerAllianceExtBean.rewardActivityOil = playerAllianceExt.getRewardActivityOil();
		playerAllianceExtBean.rewardActivityRare = playerAllianceExt.getRewardActivityRare();
		playerAllianceExtBean.rewardActivitySteel = playerAllianceExt.getRewardActivitySteel();
		playerAllianceExtBean.scienceGoldCount = playerAllianceExt.getScienceGoldCount();
		playerAllianceExtBean.scienceOilCount = playerAllianceExt.getScienceOilCount();
		playerAllianceExtBean.scienceRareCount = playerAllianceExt.getScienceRareCount();
		playerAllianceExtBean.scienceSteelCount = playerAllianceExt.getScienceSteelCount();
		playerAllianceExtBean.scienceDiamondCount = playerAllianceExt.getScienceDiamondCount();
		return playerAllianceExtBean;
	}
	
	public static AllianceExerciseBean converterAllianceExerciseBean(ExerciseInfo exerciseInfo){
		AllianceExerciseBean allianceExerciseBean = new AllianceExerciseBean();
		allianceExerciseBean.id = exerciseInfo.getId();
		for(ItemConf itemConf : exerciseInfo.getItemConfs()){
			AwardBean awardBean = new AwardBean();
			awardBean.itemId = itemConf.getTid();
			awardBean.num = itemConf.getNum();
			allianceExerciseBean.rewards.add(awardBean);
		}
		return allianceExerciseBean;
	}
	
	public static MarchInfo converterMarchInfoBean(WorldMarch worldMarch,Player player){
		MarchInfo marchInfo = new MarchInfo();
		marchInfo.marchId = worldMarch.getId();
		marchInfo.playerId = player.getId();
		marchInfo.icon = player.roleInfo().getBasics().getHeadImg();
		marchInfo.abbr = player.getAllianceAbbr();
		marchInfo.explorerNum = worldMarch.getExlplorerNum();
		marchInfo.totalWeight = worldMarch.getMaxNum();
		return marchInfo;
	}
	
	public static AllianceAcitivityBean converterAllianceActivityBean(AllianceActivity allianceActivity,long allianceId){
		AllianceAcitivityBean allianceAcitivityBean = new AllianceAcitivityBean();
		allianceAcitivityBean.activityLv = allianceActivity.getActivityLv();
		allianceAcitivityBean.activityScore = allianceActivity.getActivityScore();
		allianceAcitivityBean.allianceId = allianceId;
		allianceAcitivityBean.oil = allianceActivity.getOil();
		allianceAcitivityBean.gold = allianceActivity.getGold();
		allianceAcitivityBean.rare = allianceActivity.getRare();
		allianceAcitivityBean.steel = allianceActivity.getSteel();
		return allianceAcitivityBean;
	}
	
	public static AllianceAcitivityQuestBean converterAllianceActivityQuestBean(AllianceActivityQuest allianceActivityQuest){
		AllianceAcitivityQuestBean allianceAcitivityQuestBean = new AllianceAcitivityQuestBean();
		allianceAcitivityQuestBean.questId = allianceActivityQuest.getQuestId();
		allianceAcitivityQuestBean.count = allianceActivityQuest.getCount();
		return allianceAcitivityQuestBean;
	}
	
	public static AllianceActivityViewBean converterAllianceActivityViewBean(Player player,PlayerAllianceExt playerAllianceExt){
		AllianceActivityViewBean allianceActivityViewBean = new AllianceActivityViewBean();
		allianceActivityViewBean.activityScore = playerAllianceExt.getActivityScore();
		allianceActivityViewBean.headImg = player.getHeadImg();
		allianceActivityViewBean.playerId = player.getId();
		allianceActivityViewBean.playerName = player.getName();
		return allianceActivityViewBean;
	}
	
	public static AllianceBoxBean converterAllianceBoxBean(AllianceBox allianceBox,AwardInfo awardInfo){
		AllianceBoxBean allianceBoxBean = new AllianceBoxBean();
		allianceBoxBean.id = allianceBox.getId();
		allianceBoxBean.allianceId = allianceBox.getAllianceId();
		allianceBoxBean.boxTid = allianceBox.getBoxTid();
		allianceBoxBean.startTime = allianceBox.getStartTime();
		allianceBoxBean.endTime = allianceBox.getEndTime();
		if(null != awardInfo){
			allianceBoxBean.rewardFlag = 1;
			allianceBoxBean.boxNum = awardInfo.getBoxNum();
			for(ItemConf itemConf : awardInfo.getItemConfs()){
				allianceBoxBean.rewards.add(AllianceExtBeanConverter.converterAwardBean(itemConf));
			}
		}else{
			allianceBoxBean.rewardFlag = 0;
		}
		return allianceBoxBean;
	}
	
	public static AwardBean converterAwardBean(ItemConf itemConf){
		AwardBean awardBean = new AwardBean();
		awardBean.itemId = itemConf.getTid();
		awardBean.num = itemConf.getNum();
		return awardBean;
	}
	
	public static AwardBean converterAwardBeanByBenifit(AllianceBenifit allianceBenifit){
		AwardBean awardBean = new AwardBean();
		awardBean.itemId = allianceBenifit.getItemId();
		awardBean.num = allianceBenifit.getNum();
		return awardBean;
	}
	
	public static GoodsBean converterGoodsBean(AllianceGoods allianceGoods){
		GoodsBean goodsBean = new GoodsBean();
		goodsBean.id = allianceGoods.getId();
		goodsBean.itemId = allianceGoods.getItemId();
		goodsBean.buyCount = allianceGoods.getBuyCount();
		goodsBean.type = allianceGoods.getType();
		if(allianceGoods.getType() == 1){
			ArmyShopTreasurePir armyShopTreasurePir = ArmyShopTreasurePirFactory.get(allianceGoods.getId());
			if(null != armyShopTreasurePir){
				goodsBean.itemNum = armyShopTreasurePir.getItem_num();
				goodsBean.needArmyLv = armyShopTreasurePir.getNeedArmyLv();
				goodsBean.max = armyShopTreasurePir.getMax();
				goodsBean.gx = armyShopTreasurePir.getGX();
			}
		}else if(allianceGoods.getType() == 0){
			ArmyShopItemPir armyShopItemPir = ArmyShopItemPirFactory.get(allianceGoods.getId());
			goodsBean.itemNum = armyShopItemPir.getItem_num();
			goodsBean.needArmyLv = armyShopItemPir.getNeedArmyLv();
			goodsBean.max = armyShopItemPir.getMax();
			goodsBean.gx = armyShopItemPir.getGX();
		}
		return goodsBean;
	}
	
	public static GoodsBean converterGoodsBean(ArmyShopItemPir armyShopItemPir,AllianceGoods allianceGoods){
		GoodsBean goodsBean = new GoodsBean();
		goodsBean.itemNum = armyShopItemPir.getItem_num();
		goodsBean.needArmyLv = armyShopItemPir.getNeedArmyLv();
		goodsBean.max = armyShopItemPir.getMax();
		goodsBean.gx = armyShopItemPir.getGX();
		if(null != allianceGoods){
			goodsBean.id = allianceGoods.getId();
			goodsBean.itemId = allianceGoods.getItemId();
			goodsBean.buyCount = allianceGoods.getBuyCount();
			goodsBean.type = allianceGoods.getType();
		}else{
			goodsBean.id = armyShopItemPir.getId();
			goodsBean.itemId = Integer.valueOf(armyShopItemPir.getItem_id());
			goodsBean.buyCount = 0;
			goodsBean.type = 0;
		}
		return goodsBean;
	}
}
