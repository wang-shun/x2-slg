package com.xgame.logic.server.game.allianceext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.common.ItemConf;
import com.xgame.config.armyBox.ArmyBoxPir;
import com.xgame.config.armyBox.ArmyBoxPirFactory;
import com.xgame.config.armyBoxReward.ArmyBoxRewardPir;
import com.xgame.config.armyBoxReward.ArmyBoxRewardPirFactory;
import com.xgame.config.armyBuilding.ArmyBuildingPir;
import com.xgame.config.armyBuilding.ArmyBuildingPirFactory;
import com.xgame.config.armyDonate.ArmyDonatePir;
import com.xgame.config.armyDonate.ArmyDonatePirFactory;
import com.xgame.config.armyScience.ArmySciencePir;
import com.xgame.config.armyScience.ArmySciencePirFactory;
import com.xgame.config.armyShopItem.ArmyShopItemPir;
import com.xgame.config.armyShopItem.ArmyShopItemPirFactory;
import com.xgame.config.armyShopTreasure.ArmyShopTreasurePir;
import com.xgame.config.armyShopTreasure.ArmyShopTreasurePirFactory;
import com.xgame.config.item.ItemBox;
import com.xgame.config.item.ItemBoxControl;
import com.xgame.data.sprite.SpriteType;
import com.xgame.drop.DropService;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.IDSequance;
import com.xgame.logic.server.game.alliance.AllianceManager;
import com.xgame.logic.server.game.alliance.PlayerAllianceManager;
import com.xgame.logic.server.game.alliance.constant.AllianceDonateType;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.alliance.enity.eventmodel.DonateEventObject;
import com.xgame.logic.server.game.allianceext.constant.AllianceExtConstant;
import com.xgame.logic.server.game.allianceext.converter.AllianceExtBeanConverter;
import com.xgame.logic.server.game.allianceext.entity.AllianceActivityQuest;
import com.xgame.logic.server.game.allianceext.entity.AllianceBenifit;
import com.xgame.logic.server.game.allianceext.entity.AllianceBox;
import com.xgame.logic.server.game.allianceext.entity.AllianceBuild;
import com.xgame.logic.server.game.allianceext.entity.AllianceExt;
import com.xgame.logic.server.game.allianceext.entity.AllianceGoods;
import com.xgame.logic.server.game.allianceext.entity.AllianceScience;
import com.xgame.logic.server.game.allianceext.entity.AwardInfo;
import com.xgame.logic.server.game.allianceext.entity.ExerciseInfo;
import com.xgame.logic.server.game.allianceext.entity.PlayerAllianceExt;
import com.xgame.logic.server.game.allianceext.entity.SuperMineInfo;
import com.xgame.logic.server.game.allianceext.entity.eventmodel.AllianceBuildCreateEventObject;
import com.xgame.logic.server.game.allianceext.entity.eventmodel.AllianceBuildLvEventObject;
import com.xgame.logic.server.game.allianceext.entity.eventmodel.AllianceBuildMoveEventObject;
import com.xgame.logic.server.game.allianceext.message.ResAcitivityRankMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceAcitivityMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceBoxMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceBuildListMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceBuildLvMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceExerciseMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceScienceDonateMessage;
import com.xgame.logic.server.game.allianceext.message.ResAllianceScienceListMessage;
import com.xgame.logic.server.game.allianceext.message.ResArmyShopItemsMessage;
import com.xgame.logic.server.game.allianceext.message.ResAssignRewardMessage;
import com.xgame.logic.server.game.allianceext.message.ResChangeArmyShopItemMessage;
import com.xgame.logic.server.game.allianceext.message.ResCreateAllianceBuildMessage;
import com.xgame.logic.server.game.allianceext.message.ResMoveAllianceBuildMessage;
import com.xgame.logic.server.game.allianceext.message.ResPlayerAllianceExtMessage;
import com.xgame.logic.server.game.allianceext.message.ResRewardAllianceBoxMessage;
import com.xgame.logic.server.game.allianceext.message.ResViewAllianceExplorerMessage;
import com.xgame.logic.server.game.allianceext.message.ResViewRewardMessage;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.notice.NoticeManager;
import com.xgame.logic.server.game.notice.constant.NoticeConstant;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.SystemTimerTaskHolder;
import com.xgame.logic.server.game.world.SpriteManager;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.AllianceBuildSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.logic.server.game.world.entity.territory.AllianceTerritory;
import com.xgame.utils.EnumUtils;
import com.xgame.utils.TimeUtils;

@Component
public class AllianceExtManager extends CacheProxy<AllianceExt>{
	
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private AllianceManager allianceManager;
	@Autowired
	public PlayerAllianceManager playerAllianceManager;  
	@Autowired
	private SpriteManager spriteManager;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private WorldLogicManager worldLogicManager;
	@Autowired
	private IDSequance idSequance;
	@Autowired
	private PlayerAllianceExtManager playerAllianceExtManager;
	@Autowired
	private NoticeManager noticeManager;
	
	/**
	 * 联盟建筑列表
	 * @param player
	 * @param allianceId
	 */
	public void getAllianceBuildList(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		ResAllianceBuildListMessage resAllianceBuildListMessage = new ResAllianceBuildListMessage();
		Map<String,AllianceBuild> buildInfo = allianceExt.getBuildInfo();
		for(AllianceBuild allianceBuild : buildInfo.values()){
			resAllianceBuildListMessage.allianceBuilds.add(AllianceExtBeanConverter.converterAllianceBuildBean(allianceBuild));
		}
		player.send(resAllianceBuildListMessage);
	}
	
	/**
	 * 创建联盟建筑
	 * @param player
	 * @param buildId
	 * @param x
	 * @param y
	 */
	public void createAllianceBuild(Player player,long allianceId, int buildId, int x, int y)  {
		
		//建筑创建前提条件判断
		//1.配置检查
		ArmyBuildingPir armyBuildingPir = ArmyBuildingPirFactory.get(buildId);
		if(armyBuildingPir == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyBuildingPir.class.getSimpleName(),buildId);
			return;
		}
		//2.人员权限
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(!playerAlliance.getAlliancePermission().isCreateAllianceBuild()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		//3.判断是否已有对应联盟建筑
		Map<String,AllianceBuild> buildInfo = allianceExt.getBuildInfo();
		for(AllianceBuild allianceBuild : buildInfo.values()){
			if(allianceBuild.getBuildTid() == buildId){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE4);
				return;
			}
		}
		//4.联盟等级及资金判断
		if(alliance.getLevel() < armyBuildingPir.getNeedLv()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE2);
			return;
		}
		List<Integer> list = armyBuildingPir.getLvUpArmyMoney();
		if(alliance.getCash() < list.get(0)){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE14);
			return;
		}
		//5.坐标有效性判断
		if(x >= 511 || x<=0 || y >= 511 || y<=0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE23);
			return;
		}
		SpriteInfo targetSprite = spriteManager.getGrid(x, y);
		if(!spriteManager.canUseGrid(targetSprite.getIndex())) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE1);
			return;
		}
		AllianceTerritory allianceTerritory = alliance.getAllianceTerritory();
		if(allianceTerritory == null || allianceTerritory.getTerritory() == null || !allianceTerritory.getTerritory().contains(targetSprite.getIndex())){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE3);
			return;
		}
		//创建联盟建筑数据
		AllianceBuild allianceBuild = new AllianceBuild();
		allianceBuild.setId(String.format("%s#%s", alliance.getId(), armyBuildingPir.getID()));
		allianceBuild.setAllianceId(alliance.getId());
		allianceBuild.setBuildTid(armyBuildingPir.getID());
		allianceBuild.setLevel(1);
		allianceBuild.setCreateTime(System.currentTimeMillis());
		Vector2Bean vector2Bean =new Vector2Bean();
		vector2Bean.x = x;
		vector2Bean.y = y;
		allianceBuild.setPosition(vector2Bean);
		//加入联盟建筑信息列表
		allianceExt.getBuildInfo().put(allianceBuild.getId(), allianceBuild);
		this.update(allianceExt);
		//联盟资金扣减
		synchronized(alliance){
			alliance.setCash(alliance.getCash() - list.get(0));
			allianceManager.update(alliance);
		}
		
		//由于超级矿有持续时间 需要放入队列进行管理
		if(armyBuildingPir.getBuildingType() == 4){
			String existTime = armyBuildingPir.getV2();
			SystemTimerTaskHolder.getTimerTask(SystemTimerCommand.ALLIANCE_BUILD).register((int)(Long.valueOf(existTime)/1000),allianceBuild.getId());
		}
		//创建精灵 刷新世界
		AllianceBuildSprite allianceBuildSprite = new AllianceBuildSprite();
		allianceBuildSprite.setAllianceId(playerAlliance.getAllianceId());
		allianceBuildSprite.setBuildUid(allianceBuild.getId());
		allianceBuildSprite.setLevel(allianceBuild.getLevel());
		
		//替换格子上的精灵信息
		long uid = InjectorUtil.getInjector().idSequance.genId();
		SpriteInfo newSpriteInfo = SpriteInfo.valueOf(uid, targetSprite.getIndex(), SpriteType.BUILDING, allianceBuildSprite);
		spriteManager.transferGridToSprite(x, y, newSpriteInfo, true, true);
		
		//推送创建消息 通知联盟成员建筑创建信息 返回创建结果
		ResCreateAllianceBuildMessage resCreateAllianceBuildMessage = new ResCreateAllianceBuildMessage();
		resCreateAllianceBuildMessage.allianceBuild = AllianceExtBeanConverter.converterAllianceBuildBean(allianceBuild);
		sessionManager.writePlayers(alliance.getAllianceMember(), resCreateAllianceBuildMessage);
		//更新世界地图信息
		worldLogicManager.pushToWorldSprites(newSpriteInfo, Lists.newArrayList(player.getId()));
		//建筑创建日志记录
		EventBus.getSelf().fireEvent(new AllianceBuildCreateEventObject(player,allianceBuild.getBuildTid(),allianceBuild.getPosition()));
	}
	
	/**
	 * 移动联盟建筑
	 * @param player
	 * @param buildId
	 * @param x
	 * @param y
	 */
	public void moveAllianceBuild(Player player,long allianceId, String uid, int x, int y) {
		//1.人员权限
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(!playerAlliance.getAlliancePermission().isManagerAllianceBuild()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		//2.建筑所属判断
		AllianceBuild allianceBuild = allianceExt.getBuildInfo().get(uid);
		if(null == allianceBuild){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE6);
			return;
		}
		//3.配置判断
		int buildTid = allianceBuild.getBuildTid();
		ArmyBuildingPir armyBuildingPir = ArmyBuildingPirFactory.get(buildTid);
		if(armyBuildingPir == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyBuildingPir.class.getSimpleName(),buildTid);
			return;
		}
		
		//4.联盟资金判断
		if(alliance.getCash() < armyBuildingPir.getNeedArmyMoney2()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE14);
			return;
		}
		
		//5.坐标有效性判断
		if(x >= 511 || x<=0 || y >= 511 || y<=0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE23);
			return;
		}
		SpriteInfo targetSprite = spriteManager.getGrid(x, y);
		if(!spriteManager.canUseGrid(targetSprite.getIndex())) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE1);
			return;
		}
		AllianceTerritory allianceTerritory = alliance.getAllianceTerritory();
		if(allianceTerritory == null || allianceTerritory.getTerritory() == null || !allianceTerritory.getTerritory().contains(targetSprite.getIndex())){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE3);
			return;
		}
		Vector2Bean originPosition = allianceBuild.getPosition();
		SpriteInfo sourceSprite = spriteManager.getGrid(allianceBuild.getPosition().x,allianceBuild.getPosition().y);
		// 把之前的格子上的东西去掉
		long spriteUid = idSequance.genId();
		WorldSprite worldSprite = new WorldSprite();
		worldSprite.setAllianceId(alliance.getId());
		SpriteInfo newSprite = SpriteInfo.valueOf(spriteUid, sourceSprite.getIndex(), SpriteType.NONE, worldSprite);
		spriteManager.transferGridToSprite(sourceSprite.getX(), sourceSprite.getY(), newSprite, true, true);
		// 在当前格子上放置建筑
		spriteManager.transferGridToSprite(x, y, sourceSprite, true, true);
		
		Vector2Bean vector2Bean =new Vector2Bean();
		vector2Bean.x = x;
		vector2Bean.y = y;
		allianceBuild.setPosition(vector2Bean);
		this.update(allianceExt);
		
		//推送创建消息 通知联盟成员建筑创建信息 返回创建结果
		ResMoveAllianceBuildMessage resMoveAllianceBuildMessage = new ResMoveAllianceBuildMessage();
		resMoveAllianceBuildMessage.allianceBuildBean = AllianceExtBeanConverter.converterAllianceBuildBean(allianceBuild);
		sessionManager.writePlayers(alliance.getAllianceMember(), resMoveAllianceBuildMessage);
		//更新世界地图信息
		worldLogicManager.pushToWorldSprites(newSprite, Lists.newArrayList(player.getId()));
		worldLogicManager.pushToWorldSprites(sourceSprite, Lists.newArrayList(player.getId()));
		//建筑移动日志记录
		EventBus.getSelf().fireEvent(new AllianceBuildMoveEventObject(player,allianceBuild.getBuildTid(),originPosition,allianceBuild.getPosition()));
	}
	
	/**
	 * 联盟建筑升级
	 * @param player
	 * @param allianceId
	 * @param uid
	 */
	public void levelUpAllianceBuild(Player player,long allianceId, String uid){
		//1.人员权限
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(!playerAlliance.getAlliancePermission().isManagerAllianceBuild()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		//2.建筑判断
		AllianceBuild allianceBuild = allianceExt.getBuildInfo().get(uid);
		if(null == allianceBuild){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE6);
			return;
		}
		//3.配置判断
		int buildTid = allianceBuild.getBuildTid();
		ArmyBuildingPir armyBuildingPir = ArmyBuildingPirFactory.get(buildTid);
		if(armyBuildingPir == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyBuildingPir.class.getSimpleName(),buildTid);
			return;
		}
		if(armyBuildingPir.getMaxLv() == allianceBuild.getLevel()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE8);
			return;
		}
		//4.联盟资金判断
		List<Integer> list = armyBuildingPir.getLvUpArmyMoney();
		if(null == list.get(allianceBuild.getLevel())){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyBuildingPir.class.getSimpleName(),buildTid);
		}
		if(alliance.getCash() < list.get(allianceBuild.getLevel())){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE14);
			return;
		}
		int originLevel = allianceBuild.getLevel();
		allianceBuild.setLevel(allianceBuild.getLevel()+1);
		this.update(allianceExt);
		
		//推送创建消息 通知联盟成员建筑创建信息 返回创建结果
		ResAllianceBuildLvMessage resAllianceBuildLvMessage = new ResAllianceBuildLvMessage();
		resAllianceBuildLvMessage.allianceBuildBean = AllianceExtBeanConverter.converterAllianceBuildBean(allianceBuild);
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceBuildLvMessage);
		
		//更新世界地图信息
		SpriteInfo sourceSprite = spriteManager.getGrid(allianceBuild.getPosition().x,allianceBuild.getPosition().y);
		worldLogicManager.pushToWorldSprites(sourceSprite, Lists.newArrayList(player.getId()));
		//建筑移动日志记录
		EventBus.getSelf().fireEvent(new AllianceBuildLvEventObject(player,allianceBuild.getBuildTid(),allianceBuild.getPosition(),originLevel,alliance.getLevel()));
	}
	
	/**
	 * 获取联盟科技列表
	 * @param player
	 * @param allianceId
	 */
	public void getAllianceScienceList(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		
		ResAllianceScienceListMessage resAllianceScienceListMessage = new ResAllianceScienceListMessage();
		Map<String,AllianceScience> technology = allianceExt.getTechnology();
		for(AllianceScience allianceScience : technology.values()){
			resAllianceScienceListMessage.allianceScienceBean.add(AllianceExtBeanConverter.converterAllianceScienceBean(allianceScience));
		}
		player.send(resAllianceScienceListMessage);

		ResPlayerAllianceExtMessage resPlayerAllianceExtMessage = new ResPlayerAllianceExtMessage();
		resPlayerAllianceExtMessage.playerAllianceExtBean = AllianceExtBeanConverter.converterPlayerAllianceExtBean(playerAllianceExt);
		player.send(resPlayerAllianceExtMessage);
	}
	
	/**
	 * 联盟科技升级
	 * @param player
	 * @param allianceId
	 * @param tid
	 */
	public void allianceScienceDonate(Player player,long allianceId,int scienceTid,int type){
		//联盟信息判断
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		//联盟科技大厅建筑判断
		String buildId = String.format("%s#%s", allianceId, AllianceExtConstant.ALLIANCE_SCIENCE_BUILD_ID);
		AllianceBuild allianceBuild = allianceExt.getBuildInfo().get(buildId);
		if(null == allianceBuild){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE9);
			return;
		}
		ArmySciencePir armySciencePir = ArmySciencePirFactory.get(scienceTid);
		if(null == armySciencePir){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmySciencePir.class.getSimpleName(),scienceTid);
			return;
		}
		if(armySciencePir.getNeedLv() > allianceBuild.getLevel()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE10);
			return;
		}
		
		//判断经验是否已满 判断是否最大等级
		String scienceId = String.format("%s#%s", allianceId, scienceTid);
		List<Integer> expList = armySciencePir.getExp();
		AllianceScience allianceScience = allianceExt.getTechnology().get(scienceId);
		if(null != allianceScience){
			allianceScience = new AllianceScience();
			allianceScience.setId(scienceId);
			allianceScience.setLevel(0);
			allianceScience.setExp(0);
			allianceScience.setAllianceId(allianceId);
			allianceScience.setTid(scienceTid);
		}
		//当前等级判断
		if(allianceScience.getLevel() == armySciencePir.getMaxLv()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE8);
			return;
		}
		if(allianceScience.getLevel() >= allianceBuild.getLevel()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE11);
			return;
		}
		
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		
		int addScience = 0;
		int addDonate = 0;
		int donateCount = 0;
		ArmyDonatePir armyDonatePir = null;
		if(type == CurrencyEnum.OIL.ordinal()) {
			armyDonatePir = ArmyDonatePirFactory.get(playerAllianceExt.getScienceOilCount() + 1);
			this.verifyDonate(player, armyDonatePir, playerAllianceExt.getScienceOilCount() + 1, type, armyDonatePir.getCost_oil());
			addScience = armyDonatePir.getAddExp1();
			addDonate = armyDonatePir.getGX2_1();
			donateCount = armyDonatePir.getCost_oil();
			//扣减
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_oil(), GameLogSource.ALLIANCE_DONATE);
			playerAllianceExt.setScienceOilCount(armyDonatePir.getNum());
		} else if(type == CurrencyEnum.RARE.ordinal()) {
			armyDonatePir = ArmyDonatePirFactory.get(playerAllianceExt.getScienceRareCount() + 1);
			this.verifyDonate(player, armyDonatePir, playerAllianceExt.getScienceRareCount() + 1, type, armyDonatePir.getCost_earth());
			addScience = armyDonatePir.getAddExp1();
			addDonate = armyDonatePir.getGX2_1();
			donateCount = armyDonatePir.getCost_earth();
			//扣减
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_earth(), GameLogSource.ALLIANCE_DONATE);
			playerAllianceExt.setScienceRareCount(armyDonatePir.getNum());
		} else if(type == CurrencyEnum.STEEL.ordinal()) {
			armyDonatePir = ArmyDonatePirFactory.get(playerAllianceExt.getScienceSteelCount() + 1);
			this.verifyDonate(player, armyDonatePir, playerAllianceExt.getScienceSteelCount() + 1, type, armyDonatePir.getCost_steel());
			addScience = armyDonatePir.getAddExp1();
			addDonate = armyDonatePir.getGX2_1();
			donateCount = armyDonatePir.getCost_steel();
			//扣减
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_steel(), GameLogSource.ALLIANCE_DONATE);
			playerAllianceExt.setScienceSteelCount(armyDonatePir.getNum());
		} else if(type == CurrencyEnum.GLOD.ordinal()) {
			armyDonatePir = ArmyDonatePirFactory.get(playerAllianceExt.getScienceGoldCount() + 1);
			this.verifyDonate(player, armyDonatePir, playerAllianceExt.getScienceGoldCount() + 1, type, armyDonatePir.getCost_cash());
			addScience = armyDonatePir.getAddExp1();
			addDonate = armyDonatePir.getGX2_1();
			donateCount = armyDonatePir.getCost_cash();
			//扣减
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_cash(), GameLogSource.ALLIANCE_DONATE);
			playerAllianceExt.setScienceGoldCount(armyDonatePir.getNum());
		} else if(type == CurrencyEnum.DIAMOND.ordinal()) {
			armyDonatePir = ArmyDonatePirFactory.get(playerAllianceExt.getScienceDiamondCount() + 1);
			this.verifyDonate(player, armyDonatePir, playerAllianceExt.getScienceDiamondCount() + 1, type, armyDonatePir.getCost_RMB());
			addScience = armyDonatePir.getAddExp1();
			addDonate = armyDonatePir.getGX2_1();
			donateCount = armyDonatePir.getCost_RMB();
			//扣减
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_RMB(), GameLogSource.ALLIANCE_DONATE);
			playerAllianceExt.setScienceDiamondCount(armyDonatePir.getNum());
		} else {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		//升级加经验
		int expCapacity = expList.get(allianceScience.getLevel());
		if(expCapacity <= (allianceScience.getExp() + addScience)){
			if((allianceScience.getLevel()+1) == armySciencePir.getMaxLv() || (allianceScience.getLevel()+1) == allianceBuild.getLevel()){
				allianceScience.setLevel(allianceScience.getLevel()+1);
				allianceScience.setExp(0);
			}else{
				allianceScience.setLevel(allianceScience.getLevel()+1);
				allianceScience.setExp(allianceScience.getExp() + addScience - expCapacity);
			}
		}else{
			allianceScience.setExp(allianceScience.getExp() + addScience);
		}
		allianceExt.getTechnology().put(scienceId, allianceScience);
		InjectorUtil.getInjector().dbCacheService.update(allianceExt);
		
		playerAlliance.addDonate(addDonate);
		InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
		InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
		InjectorUtil.getInjector().dbCacheService.update(player);
		CurrencyUtil.send(player);
		
		if(type != CurrencyEnum.DIAMOND.ordinal() && armyDonatePir.getNum() >= AllianceManager.DONATE_RESOURCE_NOTICE_COUNT) {
			noticeManager.sendAllianceNotice(player, alliance, NoticeConstant.NOTICE_ALLIANCE_DOATE_RESOURCE, player.getName(), type, donateCount);
		} else if(type == CurrencyEnum.DIAMOND.ordinal() && armyDonatePir.getNum() >= AllianceManager.DONATE_DIAMOND_NOTICE_COUNT) {
			noticeManager.sendAllianceNotice(player, alliance, NoticeConstant.NOTICE_ALLIANCE_DOATE_DIAMOND, player.getName(), type, donateCount);
		}
		
		ResAllianceScienceDonateMessage resAllianceScienceDonateMessage = new ResAllianceScienceDonateMessage();
		resAllianceScienceDonateMessage.allianceScienceBean = AllianceExtBeanConverter.converterAllianceScienceBean(allianceScience);
		resAllianceScienceDonateMessage.playerAllianceExtBean = AllianceExtBeanConverter.converterPlayerAllianceExtBean(playerAllianceExt);
		player.send(resAllianceScienceDonateMessage);
		
		ResAllianceScienceListMessage resAllianceScienceListMessage = new ResAllianceScienceListMessage();
		Map<String,AllianceScience> technology = allianceExt.getTechnology();
		for(AllianceScience as : technology.values()){
			resAllianceScienceListMessage.allianceScienceBean.add(AllianceExtBeanConverter.converterAllianceScienceBean(as));
		}
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceScienceListMessage);
		EventBus.getSelf().fireEvent(new DonateEventObject(player, armyDonatePir.getNum(), EnumUtils.getEnum(CurrencyEnum.class, type), donateCount, AllianceDonateType.ALLIANCE_TECH.ordinal()));
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE26,  addScience, addDonate);
	}
	
	/**
	 * 捐献验证
	 * @param player
	 * @param armyDonatePir
	 * @param count
	 * @param type
	 * @param cost
	 */
	private void verifyDonate(Player player,ArmyDonatePir armyDonatePir,int count,int type,int cost){
		if(null == armyDonatePir){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyDonatePir.class.getSimpleName(),count);
			return;
		}
		if(armyDonatePir != null && cost <= -1) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE17);
			return;
		}
		
		if(!CurrencyUtil.verifyFinal(player, type, cost)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE18);
			return;
		}
	}
	
	/**
	 * 玩家演习信息
	 * @param player
	 * @param allianceId
	 */
	public void allianceExerciseInfo(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		
		ResAllianceExerciseMessage resAllianceExerciseMessage = new ResAllianceExerciseMessage();
		resAllianceExerciseMessage.exerciseStartTime = playerAllianceExt.getExerciseStartTime();
		for(ExerciseInfo exerciseInfo : playerAllianceExt.getExerciseRewards().values()){
			resAllianceExerciseMessage.allianceExerciseBean.add(AllianceExtBeanConverter.converterAllianceExerciseBean(exerciseInfo));
		}
		player.send(resAllianceExerciseMessage);
	}
	
	/**
	 * 结束演习
	 * @param player
	 * @param allianceId
	 */
	public void stopAllianceExercise(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		if(playerAllianceExt.getExerciseStartTime() <= 0){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE13);
			return;
		}
		//发奖
		
		//结束队列
		
		//重置数据
		
		ResAllianceExerciseMessage resAllianceExerciseMessage = new ResAllianceExerciseMessage();
		resAllianceExerciseMessage.exerciseStartTime = playerAllianceExt.getExerciseStartTime();
		for(ExerciseInfo exerciseInfo : playerAllianceExt.getExerciseRewards().values()){
			resAllianceExerciseMessage.allianceExerciseBean.add(AllianceExtBeanConverter.converterAllianceExerciseBean(exerciseInfo));
		}
		player.send(resAllianceExerciseMessage);
	}
	
	/**
	 * 超级矿采集列表
	 * @param player
	 * @param allianceId
	 */
	public void viewAllianceExplorer(Player player,long allianceId,String buildId){
		//1.人员权限
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		AllianceBuild allianceBuild = allianceExt.getBuildInfo().get(buildId);
		if(null == allianceBuild){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE6);
			return;
		}
		ResViewAllianceExplorerMessage resViewAllianceExplorerMessage = new ResViewAllianceExplorerMessage();
		SuperMineInfo superMineInfo = allianceExt.getSuperMine();
		for(Map.Entry<Long,Long> entry : superMineInfo.getMarchs().entrySet()){
			WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, entry.getValue());
			Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, entry.getKey());
			resViewAllianceExplorerMessage.marchInfoList.add(AllianceExtBeanConverter.converterMarchInfoBean(worldMarch, targetPlayer));
		}
		resViewAllianceExplorerMessage.resourceLeft = superMineInfo.getResourceLeft();
		player.send(resViewAllianceExplorerMessage);
	}
	
	/**
	 * 军团活跃度信息
	 * @param player
	 * @param allianceId
	 */
	public void allianceActivity(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		
		ResAllianceAcitivityMessage resAllianceAcitivityMessage = new ResAllianceAcitivityMessage();
		resAllianceAcitivityMessage.allianceAcitivityBean = AllianceExtBeanConverter.converterAllianceActivityBean(allianceExt.getActivity(), allianceId);
		for(AllianceActivityQuest allianceActivityQuest : allianceExt.getActiveQuest().values()){
			resAllianceAcitivityMessage.allianceAcitivityQuestBean.add(AllianceExtBeanConverter.converterAllianceActivityQuestBean(allianceActivityQuest));
		}
		player.send(resAllianceAcitivityMessage);
		
		ResPlayerAllianceExtMessage resPlayerAllianceExtMessage = new ResPlayerAllianceExtMessage();
		resPlayerAllianceExtMessage.playerAllianceExtBean = AllianceExtBeanConverter.converterPlayerAllianceExtBean(playerAllianceExt);
		player.send(resPlayerAllianceExtMessage);
	}
	
	/**
	 * 联盟活跃排行榜
	 * @param player
	 * @param allianceId
	 */
	public void activityRank(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		ResAcitivityRankMessage resAcitivityRankMessage = new ResAcitivityRankMessage();
		for(long playerId : alliance.getAllianceMember()){
			Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
			PlayerAllianceExt targetPlayerAllianceExt = playerAllianceExtManager.getOrCreate(playerId);
			resAcitivityRankMessage.alliancePlayerView.add(AllianceExtBeanConverter.converterAllianceActivityViewBean(targetPlayer, targetPlayerAllianceExt));
		}
		player.send(resAcitivityRankMessage);
	}
	
	/**
	 * 领取活跃度奖励
	 * @param player
	 * @param allianceId
	 */
	public void rewardActivityResource(Player player ,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		boolean today = TimeUtils.isToday(playerAlliance.getJoinTime());
		if(today){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE14);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		int oil = allianceExt.getActivity().getOil();
		int gold = allianceExt.getActivity().getGold();
		int rare = allianceExt.getActivity().getRare();
		int steel = allianceExt.getActivity().getSteel();
		
		CurrencyUtil.increaseCurrency(player, gold-playerAllianceExt.getRewardActivityGold(), steel-playerAllianceExt.getRewardActivitySteel(), 
				oil-playerAllianceExt.getRewardActivityOil(), rare-playerAllianceExt.getRewardActivityRare(), GameLogSource.ALLIANCE_ACTIVITY_RESOURCE);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		playerAllianceExt.setRewardActivityGold(gold);
		playerAllianceExt.setRewardActivityOil(oil);
		playerAllianceExt.setRewardActivityRare(rare);
		playerAllianceExt.setRewardActivitySteel(steel);
		
		InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
		
		CurrencyUtil.send(player);
		
		ResPlayerAllianceExtMessage resPlayerAllianceExtMessage = new ResPlayerAllianceExtMessage();
		resPlayerAllianceExtMessage.playerAllianceExtBean = AllianceExtBeanConverter.converterPlayerAllianceExtBean(playerAllianceExt);
		player.send(resPlayerAllianceExtMessage);
	}
	
	/**
	 * 获取玩家联盟扩展信息
	 * @param player
	 * @param allianceId
	 */
	public void getPlayerAllianceExt(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		ResPlayerAllianceExtMessage resPlayerAllianceExtMessage = new ResPlayerAllianceExtMessage();
		resPlayerAllianceExtMessage.playerAllianceExtBean = AllianceExtBeanConverter.converterPlayerAllianceExtBean(playerAllianceExt);
		player.send(resPlayerAllianceExtMessage);
	}
	
	/**
	 * 获取联盟宝箱列表
	 * @param player
	 * @param allianceId
	 */
	public void allianceBox(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		ResAllianceBoxMessage resAllianceBoxMessage = new ResAllianceBoxMessage();
		for(AllianceBox allianceBox : allianceExt.getAllianceBoxs().values()){
			//加入军团后未开启刷新过的宝箱
			if(allianceBox.getStartTime() < playerAlliance.getJoinTime() && (playerAllianceExt.getBoxIds().get(allianceBox.getId()) == null || playerAllianceExt.getBoxIds().get(allianceBox.getId()).getShowFlag() == 0)){
				resAllianceBoxMessage.allianceBoxBean.add(AllianceExtBeanConverter.converterAllianceBoxBean(allianceBox,playerAllianceExt.getBoxIds().get(allianceBox.getId())));
			}
		}
		resAllianceBoxMessage.boxLevel = allianceExt.getAllianceBoxLevel();
		resAllianceBoxMessage.exp = allianceExt.getExp();
		player.send(resAllianceBoxMessage);
	}
	
	/**
	 * 领取宝箱
	 * @param player
	 * @param allianceId
	 * @param boxId
	 */
	public void rewardAllianceBox(Player player,long allianceId,List<Long> boxId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		ResRewardAllianceBoxMessage resRewardAllianceBoxMessage = new ResRewardAllianceBoxMessage();
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		int exp = 0;
		List<ItemConf> itemConfs = new ArrayList<>();
		for(long allianceBoxId : boxId){
			if(playerAllianceExt.getBoxIds().values().contains(allianceBoxId)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE15);
				return;
			}else{
				AllianceBox allianceBox = allianceExt.getAllianceBoxs().get(allianceBoxId);
				if(allianceBox == null){
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE16);
					return;
				}
				AwardInfo awardInfo = this.openAllianceBox(allianceExt.getAllianceBoxLevel(), allianceBox);
				exp += awardInfo.getBoxNum();
				itemConfs.addAll(awardInfo.getItemConfs());
				playerAllianceExt.getBoxIds().put(awardInfo.getId(), awardInfo);
				resRewardAllianceBoxMessage.allianceBoxBean.add(AllianceExtBeanConverter.converterAllianceBoxBean(allianceBox,awardInfo));
			}
		}
		int originLevel = allianceExt.getAllianceBoxLevel();
		while(exp > 0){
			if(originLevel == AllianceExtConstant.ALLIANCE_BOX_MAX_LEVEL){
				allianceExt.setExp(0);
				allianceExt.setAllianceBoxLevel(originLevel);
				exp = 0;
			}else{
				ArmyBoxRewardPir abrp = ArmyBoxRewardPirFactory.get(originLevel+1);
				if(allianceExt.getExp() + exp >= abrp.getNeedExp()){
					originLevel ++;
					exp = allianceExt.getExp() + exp - abrp.getNeedExp();
					allianceExt.setExp(0);
				}else{
					allianceExt.setExp(allianceExt.getExp() + exp);
					allianceExt.setAllianceBoxLevel(originLevel);
					exp = 0;
				}
			}
		}
		//保存道具
		Map<Integer,ItemConf> map = new HashMap<>();
		//数据整理 重复合并
		for(ItemConf itemConf : itemConfs){
			if(map.get(itemConf.getTid()) != null){
				itemConf.setNum(map.get(itemConf.getTid()).getNum() + itemConf.getNum());
				map.put(itemConf.getTid(), itemConf);
			}else{
				map.put(itemConf.getTid(), itemConf);
			}
		}
		for(ItemConf itemConf : map.values()){
			switch(String.valueOf(itemConf.getTid()).length()){
			case 5://植入体
				for(int i=0;i<itemConf.getNum();i++){
					player.getEquipmentManager().addEquipment(itemConf.getTid(), GameLogSource.USE_ARMY_BOX);
				}
				break;
			case 6://配件
				for(int i=0;i<itemConf.getNum();i++){
					player.getCustomWeaponManager().unlockPeijian(player, itemConf.getTid(), GameLogSource.USE_ARMY_BOX);
				}
				break;
			case 7://道具
				ItemKit.addItemAndTopic(player, itemConf.getTid(), itemConf.getNum(), SystemEnum.BOX, GameLogSource.USE_ARMY_BOX);
				break;
			default:
				break;
			}
		}
		//更新联盟扩展信息
		InjectorUtil.getInjector().dbCacheService.update(player);
		InjectorUtil.getInjector().dbCacheService.update(allianceExt);
		InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
		//发送宝箱道具
		player.send(ItemConverter.getBoxItems(Lists.newArrayList(map.values())));
		//更新宝箱列表
		resRewardAllianceBoxMessage.boxLevel = allianceExt.getAllianceBoxLevel();
		resRewardAllianceBoxMessage.exp = allianceExt.getExp();
		player.send(resRewardAllianceBoxMessage);
	}
	
	/**
	 * 使用联盟宝箱
	 * 产出道具 添加积分
	 * @param playerBagManager
	 */
	private AwardInfo openAllianceBox(int allianceBoxLevel,AllianceBox allianceBox) {
		AwardInfo awardInfo = new AwardInfo();
		Map<Integer,ItemConf> itemConfMap = new HashMap<Integer,ItemConf>();
		int boxTid = allianceBox.getBoxTid();
		ArmyBoxPir abp = ArmyBoxPirFactory.get(boxTid);
		List<Integer> boxNums = abp.getAddBoxExp();
		Random random = new Random();
		int boxNum = boxNums.get(random.nextInt(boxNums.size()));
		//获取宝箱点数
		ItemBoxControl control = null;
		ArmyBoxRewardPir abrp = ArmyBoxRewardPirFactory.get(allianceBoxLevel);
		switch(boxTid){
		case 1:
			control = abrp.getLV1();
			break;
		case 2:
			control = abrp.getLV2();
			break;
		case 3:
			control = abrp.getLV3();
			break;
		case 4:
			control = abrp.getLV4();
			break;
		case 5:
			control = abrp.getLV5();
			break;
		default:
			break;
		}
		if (null != control) {
			for(int i = 0;i<control.getCount();i++) {
				//随机经验
				//随机掉落
				ItemBox itemBox = DropService.getDrop(control.getItemBoxs());
				//进行重复性叠加
				if(null != itemConfMap.get(itemBox.getTid())){
					ItemConf itemConf = itemConfMap.get(itemBox.getTid());
					itemConf.setNum(itemConf.getNum()+itemBox.getNum());
					itemConfMap.put(itemBox.getTid(), itemConf);
				}else{
					itemConfMap.put(itemBox.getTid(), new ItemConf(itemBox.getTid(), itemBox.getNum()));
				}
			}
		}
		awardInfo.getItemConfs().addAll(itemConfMap.values());
		awardInfo.setBoxNum(boxNum);
		awardInfo.setId(allianceBox.getId());
		awardInfo.setShowFlag(0);
		return awardInfo;
	}
	
	/**
	 * 刷新联盟宝箱
	 * @param player
	 * @param allianceId
	 */
	public void refreshAllianceBox(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		for(AwardInfo awardInfo : playerAllianceExt.getBoxIds().values()){
			awardInfo.setShowFlag(1);
		}
		InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
		
		ResAllianceBoxMessage resAllianceBoxMessage = new ResAllianceBoxMessage();
		for(AllianceBox allianceBox : allianceExt.getAllianceBoxs().values()){
			if(playerAllianceExt.getBoxIds().get(allianceBox.getId()) == null || playerAllianceExt.getBoxIds().get(allianceBox.getId()).getShowFlag() == 0){
				resAllianceBoxMessage.allianceBoxBean.add(AllianceExtBeanConverter.converterAllianceBoxBean(allianceBox,playerAllianceExt.getBoxIds().get(allianceBox.getId())));
			}
		}
		resAllianceBoxMessage.boxLevel = allianceExt.getAllianceBoxLevel();
		resAllianceBoxMessage.exp = allianceExt.getExp();
		player.send(resAllianceBoxMessage);
	}
	
	/**
	 * 军团宝箱时间截止逻辑
	 * @param allianceId
	 * @param boxId
	 */
	public void removeAllianceBox(long allianceId,long boxId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null != alliance){
			AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
			if(null != allianceExt){
				allianceExt.getAllianceBoxs().remove(boxId);
				InjectorUtil.getInjector().dbCacheService.update(allianceExt);
				for(long playerId : alliance.getAllianceMember()){
					Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
					if(null != player){
						PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(playerId);
						PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(playerId);
						playerAllianceExt.getBoxIds().remove(boxId);
						InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
						//刷新玩家宝箱列表
						ResAllianceBoxMessage resAllianceBoxMessage = new ResAllianceBoxMessage();
						for(AllianceBox allianceBox : allianceExt.getAllianceBoxs().values()){
							//加入军团后未开启刷新过的宝箱
							if(allianceBox.getStartTime() < playerAlliance.getJoinTime() && (playerAllianceExt.getBoxIds().get(allianceBox.getId()) == null || playerAllianceExt.getBoxIds().get(allianceBox.getId()).getShowFlag() == 0)){
								resAllianceBoxMessage.allianceBoxBean.add(AllianceExtBeanConverter.converterAllianceBoxBean(allianceBox,playerAllianceExt.getBoxIds().get(allianceBox.getId())));
							}
						}
						resAllianceBoxMessage.boxLevel = allianceExt.getAllianceBoxLevel();
						resAllianceBoxMessage.exp = allianceExt.getExp();
						player.send(resAllianceBoxMessage);
					}
					
				}
			}
		}
	}
	
	/**
	 * 联盟战事奖励信息
	 * @param player
	 * @param allianceId
	 */
	public void viewAllianceFightReward(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		ResViewRewardMessage resViewRewardMessage = new ResViewRewardMessage();
		for(AllianceBenifit allianceBenifit : allianceExt.getBenifit().values()){
			resViewRewardMessage.awardBeanList.add(AllianceExtBeanConverter.converterAwardBeanByBenifit(allianceBenifit));
		}
		player.send(resViewRewardMessage);
	}
	
	/**
	 *  分配战事奖励
	 * @param player
	 * @param itemId
	 * @param num
	 * @param playerIds
	 */
	public void assignAllianceFightReward(Player player,long allianceId,int itemId,int num,List<Long> playerIds){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(!playerAlliance.getAlliancePermission().isAssignAllianceReward()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		for(long playerId : playerIds){
			if(!alliance.getAllianceMember().contains(playerId)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE17);
				return;
			}
		}
		if(allianceExt.getBenifit().get(itemId).getNum() < playerIds.size()*num){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE18);
			return;
		}
		for(long playerId : playerIds){
			Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
			switch(String.valueOf(itemId).length()){
			case 5://植入体
				for(int i=0;i<num;i++){
					targetPlayer.getEquipmentManager().addEquipment(itemId, GameLogSource.ARMY_FIGHT_REWARD);
				}
				break;
			case 6://配件
				for(int i=0;i<num;i++){
					targetPlayer.getCustomWeaponManager().unlockPeijian(player, itemId, GameLogSource.ARMY_FIGHT_REWARD);
				}
				break;
			case 7://道具
				ItemKit.addItemAndTopic(targetPlayer, itemId, num, SystemEnum.BOX, GameLogSource.ARMY_FIGHT_REWARD);
				break;
			default:
				break;
			}
			InjectorUtil.getInjector().dbCacheService.update(targetPlayer);
		}
		allianceExt.getBenifit().get(itemId).setNum(allianceExt.getBenifit().get(itemId).getNum()-playerIds.size()*num);
		ResAssignRewardMessage resAssignRewardMessage = new ResAssignRewardMessage();
		resAssignRewardMessage.awardBean = AllianceExtBeanConverter.converterAwardBeanByBenifit(allianceExt.getBenifit().get(itemId));
		sessionManager.writePlayers(alliance.getAllianceMember(), resAssignRewardMessage);
	}
	
	/**
	 * 获取军团商店信息
	 * @param player
	 * @param allianceId
	 */
	public void getArmyShopItems(Player player,long allianceId){
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(allianceId);
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() != allianceId){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		ResArmyShopItemsMessage resArmyShopItemsMessage = new ResArmyShopItemsMessage();
		for(AllianceGoods allianceGoods : allianceExt.getTreasureInfo().values()){
			resArmyShopItemsMessage.treasureItems.add(AllianceExtBeanConverter.converterGoodsBean(allianceGoods));
		}
		for(ArmyShopItemPir armyShopItemPir : ArmyShopItemPirFactory.getInstance().getFactory().values()){
			resArmyShopItemsMessage.commonItems.add(AllianceExtBeanConverter.converterGoodsBean(armyShopItemPir, playerAllianceExt.getCommonInfo().get(armyShopItemPir.getId())));
		}
		player.send(resArmyShopItemsMessage);
	}
	
	/**
	 * 兑换军团商店商品
	 * @param player
	 * @param id
	 * @param itemId
	 * @param type
	 */
	public void changeArmyShopItem(Player player,int id,int itemId,int type){
		if(player.getAllianceId() <= 0){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		Alliance alliance = allianceManager.getRefreshAlliance(player.getAllianceId());
		if(null == alliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		AllianceExt allianceExt = this.getRefreshAllianceExt(player.getAllianceId());
		if(null == allianceExt){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE7);
			return;
		}
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(null == playerAlliance){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		if(playerAlliance.getAllianceId() !=  player.getAllianceId()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		if(type == AllianceExtConstant.ALLIANCE_SHOP_GOODS_TYPE_COMMON){
			ArmyShopItemPir armyShopItemPir = ArmyShopItemPirFactory.get(id);
			if(null == armyShopItemPir){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyShopItemPir.class.getSimpleName(),id);
				return;
			}
			AllianceGoods allianceGoods = playerAllianceExt.getCommonInfo().get(id);
			if(null != allianceGoods && armyShopItemPir.getMax() <= allianceGoods.getBuyCount()){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE19);
				return;
			}
			if(playerAlliance.getDonate() < armyShopItemPir.getGX()){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE20);
				return;
			}
			if(null == allianceGoods){
				allianceGoods = new AllianceGoods();
				allianceGoods.setBuyCount(0);
				allianceGoods.setId(id);
				allianceGoods.setItemId(itemId);
				allianceGoods.setType(type);
			}
			//扣贡献
			playerAlliance.useDonate(armyShopItemPir.getGX());
			InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			
			allianceGoods.setBuyCount(allianceGoods.getBuyCount()+1);
			playerAllianceExt.getCommonInfo().put(allianceGoods.getId(), allianceGoods);
			InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
			//加道具
			ItemKit.addItemAndTopic(player, itemId, 1, SystemEnum.ALLIANCESHOP, GameLogSource.ALLIANCE_SHOP);
			//刷新数据
			CurrencyUtil.send(player);
			
			ResChangeArmyShopItemMessage resChangeArmyShopItemMessage = new ResChangeArmyShopItemMessage();
			resChangeArmyShopItemMessage.goodsBean = AllianceExtBeanConverter.converterGoodsBean(armyShopItemPir, allianceGoods);
			player.send(resChangeArmyShopItemMessage);
		}else if (type == AllianceExtConstant.ALLIANCE_SHOP_GOODS_TYPE_TREASURE){
			ArmyShopTreasurePir armyShopTreasurePir = ArmyShopTreasurePirFactory.get(id);
			if(null == armyShopTreasurePir){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyShopTreasurePir.class.getSimpleName(),id);
				return;
			}
			if(playerAlliance.getDonate() < armyShopTreasurePir.getGX()){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE20);
				return;
			}
			AllianceGoods allianceGoods = allianceExt.getTreasureInfo().get(id);
			if(null == allianceGoods){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE21);
				return;
			}
			if(null != allianceGoods && armyShopTreasurePir.getMax() <= allianceGoods.getBuyCount()){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE19);
				return;
			}
			synchronized(allianceExt){
				allianceGoods.setBuyCount(allianceGoods.getBuyCount()+1);
				InjectorUtil.getInjector().dbCacheService.update(allianceExt);
			}
			//扣贡献
			playerAlliance.useDonate(armyShopTreasurePir.getGX());
			InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			//加道具
			ItemKit.addItemAndTopic(player, itemId, 1, SystemEnum.ALLIANCESHOP, GameLogSource.ALLIANCE_SHOP);
			//刷新数据
			CurrencyUtil.send(player);
			
			ResChangeArmyShopItemMessage resChangeArmyShopItemMessage = new ResChangeArmyShopItemMessage();
			resChangeArmyShopItemMessage.goodsBean = AllianceExtBeanConverter.converterGoodsBean(allianceGoods);
			player.send(resChangeArmyShopItemMessage);
		}
	}
	
	/**
	 * 获取玩家联盟数据
	 * @param playerId
	 * @return
	 */
	public AllianceExt getRefreshAllianceExt(long allianceId) {
		AllianceExt allianceExt = InjectorUtil.getInjector().dbCacheService.get(AllianceExt.class, allianceId);
		if(allianceExt == null) {
			return null;
		}
		
		synchronized(allianceExt) {
			boolean update  = allianceExt.refresh();
			if(update) {
				//刷新珍品
				allianceExt.getTreasureInfo().clear();
				List<AllianceGoods> allianceGoodsList = this.wrapAllianceGoods(allianceId);
				for(AllianceGoods allianceGoods : allianceGoodsList){
					allianceExt.getTreasureInfo().put(allianceGoods.getId(), allianceGoods);
				}
				InjectorUtil.getInjector().dbCacheService.update(allianceExt);
			}
		}
		return allianceExt;
	}
	
	/**
	 * 包装军团商品
	 * @param allianceId
	 * @return
	 */
	private List<AllianceGoods> wrapAllianceGoods(long allianceId){
		List<ArmyShopTreasurePir> list = this.getArmyShopTreasurePirByAllianceLevel(allianceId);
		List<ArmyShopTreasurePir> dropList = this.getDrops(list, 3);
		List<AllianceGoods> allianceGoodsList = new ArrayList<>();
		for(ArmyShopTreasurePir astp : dropList){
			AllianceGoods allianceGoods = new AllianceGoods();
			allianceGoods.setId(astp.getId());
			allianceGoods.setItemId(Integer.valueOf(astp.getItem_id()));
			allianceGoods.setBuyCount(0);
			allianceGoods.setType(1);
		}
		return allianceGoodsList;
	}
	
	/**
	 * 根据权重随机获取数据
	 * @param armyShopTreasurePirs
	 * @param num
	 * @return
	 */
	private List<ArmyShopTreasurePir> getDrops(List<ArmyShopTreasurePir> armyShopTreasurePirs,int num){
		List<ArmyShopTreasurePir> newList = Lists.newArrayList(armyShopTreasurePirs);
		if(newList == null) {
			return null;
		}
		int totalValue = 0;
		for(ArmyShopTreasurePir drop : newList) {
			totalValue += drop.getWeight();
		}
		List<ArmyShopTreasurePir> list = new ArrayList<ArmyShopTreasurePir>();
		if(num == newList.size()){
			return newList;
		}
		Random random = new Random();
		for(int i = 0;i < num;i++){
			int randomValue = random.nextInt(totalValue);
			int currentValue = 0;
			for(int j = newList.size() - 1;j >= 0;j--){
				ArmyShopTreasurePir drop = newList.get(j);
				if(randomValue < drop.getWeight() + currentValue) {
					list.add(newList.remove(j));
					totalValue -= drop.getWeight();
					break;
				}
				currentValue += drop.getWeight();
			}
		}
		return list;
	}
	
	/**
	 * 根据军团等级获取珍品商品列表
	 * @param allianceId
	 * @return
	 */
	private List<ArmyShopTreasurePir> getArmyShopTreasurePirByAllianceLevel(long allianceId){
		List<ArmyShopTreasurePir> list = new ArrayList<>();
		Alliance alliance = allianceManager.getRefreshAlliance(allianceId);
		if(alliance != null){
			for(ArmyShopTreasurePir armyShopTreasurePir : ArmyShopTreasurePirFactory.getInstance().getFactory().values()){
				if(alliance.getLevel() >= armyShopTreasurePir.getNeedArmyLv()){
					list.add(armyShopTreasurePir);
				}
			}
		}
		return list;
	}
	
	@Override
	public Class<?> getProxyClass() {
		return AllianceExt.class;
	}

}
