package com.xgame.logic.server.game.war.entity.handler.concrete;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.fight.luaj.lib.jse.CoerceJavaToLua;
import com.xgame.logic.server.core.fight.luaj.lib.jse.JsePlatform;
import com.xgame.logic.server.core.fight.luaj.vm2.Globals;
import com.xgame.logic.server.core.fight.luaj.vm2.LuaValue;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleDetail;
import com.xgame.logic.server.game.alliance.enity.AllianceReport;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.mod.ModifyKit;
import com.xgame.logic.server.game.email.MailKit;
import com.xgame.logic.server.game.email.PlayerMailInfoManager;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.WarFinishQueueManager;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.war.constant.WarConstant;
import com.xgame.logic.server.game.war.constant.WarResultType;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.converter.BattleConverter;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.LuaContext;
import com.xgame.logic.server.game.war.entity.WarAttacker;
import com.xgame.logic.server.game.war.entity.WarDefender;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.war.entity.handler.IWarHandler;
import com.xgame.logic.server.game.war.entity.queue.WarFinishQueue;
import com.xgame.logic.server.game.war.entity.report.AllianceBattleReport;
import com.xgame.logic.server.game.war.entity.report.WarBuildingInfo;
import com.xgame.logic.server.game.war.entity.report.WarEndReport;
import com.xgame.logic.server.game.war.entity.report.WarSoldierInfo;
import com.xgame.logic.server.game.war.message.ResWarEndMessage;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.entity.MarchUtils;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.utils.EnumUtils;


/**
 * 战场处理handler
 * @author jacky.jiang
 *
 */
@Slf4j
public abstract class AbstractFightHandler implements IWarHandler {
	
	@Autowired
	private WarManager warManager;
	@Autowired
	private WarFinishQueueManager battlePushQueueManager;
	@Autowired
	protected PlayerMailInfoManager playerMailInfoManager;
	@Autowired
	private WorldLogicManager worldLogicManager;
	@Autowired
	private WarFinishQueueManager battleFinishQueueManager;
	
	@PostConstruct
	public void initHandler() {
		warManager.addWarHandler(this);
	}
	
	public WarManager getWarManager() {
		return warManager;
	}
	
	/**
	 * 战斗结束返回
	 */
	public void fight(Battle battle) {
		LuaContext luaContext = warManager.getLuaContext();
		ThreadPoolExecutor 	executor = warManager.getBattleExcutor();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					try {
						String report = JsonUtil.toJSON(battle.getResWarDataMessage());
						log.info("--------------REPROT----------------START");
						log.info("report-----------------message:{}", report);
						log.info("--------------REPROT----------------END");
					} catch(Exception e) {
						log.error("获取战斗数据异常:", e);
						e.printStackTrace();
					}
					
					Globals globals = JsePlatform.debugGlobals();
					globals.load(new InputStreamReader(new FileInputStream(new File(luaContext.getPath() + "ServerFight.lua"))),"Server-Battle").call();

					LuaValue func = globals.get(LuaValue.valueOf("battleStart"));
					LuaValue battleMessageData = CoerceJavaToLua.coerce(battle.getResWarDataMessage());
					WarEndReport wer = new WarEndReport();
					LuaValue warReport = CoerceJavaToLua.coerce(wer);
					
					func.invoke(new LuaValue[] {battleMessageData, luaContext.getSysCfgLuaValue(), luaContext.getPjCfgLuaValue(), warReport, LuaValue.valueOf(WarConstant.FPS), LuaValue.valueOf(WarConstant.FIGHT_TIME)});
					
					// 处理战斗结束队列
					WarFightParam warFightParam = battle.getWarFightParam();
					
					// 是否是迁城战斗
					if(!battle.getWarType().isRts()) {
						if(battle.isMoveCity()) {
							WarFinishQueue warFinishQueue = new WarFinishQueue();
							warFinishQueue.setBattle(battle);
							warFinishQueue.setBattleStartTime(0);
						
							// 如果是迁城战斗不需要结束时间，立即计算
							warFinishQueue.setBattleEndTime(0);
							warFinishQueue.setSpriteInfo(warFightParam.getTargetSpriteInfo());
							warFinishQueue.setBattle(battle);
							warFinishQueue.setWarEndReport(wer);
							
							battleFinishQueueManager.addBattleFinishQueue(warFinishQueue);
						} else {
							WarFinishQueue warFinishQueue = new WarFinishQueue();
							warFinishQueue.setBattle(battle);
							warFinishQueue.setBattleStartTime(battle.getStartTime());
							warFinishQueue.setBattleEndTime(battle.getEndTime());
							warFinishQueue.setSpriteInfo(warFightParam.getTargetSpriteInfo());
							warFinishQueue.setBattle(battle);
							warFinishQueue.setWarEndReport(wer);
							
							battleFinishQueueManager.addBattleFinishQueue(warFinishQueue);
						}
					} else {
						battle.getWarHandler().fightEnd(battle, wer);
					}
					
				} catch(Exception e) {
					log.error("战斗执行异常：", e);
					e.printStackTrace();
					dealBattleException(battle);
				}
			}
		});
	}
	
	/**
	 * 处理战斗异常
	 * @param battle
	 */
	private void dealBattleException(Battle battle) {
		WarFightParam warFightParam = battle.getWarFightParam();
		if(warFightParam != null) {
			WorldMarch attackWorldMarch = warFightParam.getAttackWorldMarch();
			if(attackWorldMarch != null) {
				attackWorldMarch.getExecutor().handleReturn();
				MailKit.sendSystemEmail(Long.valueOf(attackWorldMarch.getOwnerUid()), EmailTemplet.战斗异常_MAIL_ID, "战斗异常");
			}
			
			WorldMarch defendMarch = warFightParam.getDefendWorldMarch();
			if(defendMarch != null) {
				defendMarch.getExecutor().handleReturn();
				MailKit.sendSystemEmail(Long.valueOf(defendMarch.getOwnerUid()), EmailTemplet.战斗异常_MAIL_ID, "战斗异常");
			}
			
			List<WorldMarch> attackWorldMarchs = warFightParam.getAttackMarchList();
			if(attackWorldMarchs != null && !attackWorldMarchs.isEmpty()) {
				for(WorldMarch worldMarch2 : attackWorldMarchs) {
					worldMarch2.executor.handleReturn();
					MailKit.sendSystemEmail(Long.valueOf(worldMarch2.getOwnerUid()), EmailTemplet.战斗异常_MAIL_ID, "战斗异常");
				}
			}
			
			List<WorldMarch> defendWorldMarchs = warFightParam.getDefendMarchList();
			if(defendWorldMarchs != null && !defendWorldMarchs.isEmpty()) {
				for(WorldMarch worldMarch2 : defendWorldMarchs) {
					worldMarch2.executor.handleReturn();
					MailKit.sendSystemEmail(Long.valueOf(worldMarch2.getOwnerUid()), EmailTemplet.战斗异常_MAIL_ID, "战斗异常");
				}
			}
			
			log.error("-----------------------------------------------------战斗异常部队返回");
		}
	}
	
	/**
	 * 初始化资源掠夺信息
	 * @param warDefender
	 * @param ratio
	 */
	public void initInvasionResource(WarDefender warDefender, double ratio) {
		Map<Integer, Long> rewardResource = calcDefendResource(warDefender, ratio);
		for(Map.Entry<Integer, Long> resourceBuild : rewardResource.entrySet()) {
			WarBuilding warBuilding = warDefender.getWarBuildingMap().get(resourceBuild.getKey());
			if(warBuilding != null) {
				warBuilding.resourceNum = resourceBuild.getValue();
			}
		}
	}
	
	/**
	 * 计算防守方建筑资源
	 * @param player
	 * @return
	 */
	private Map<Integer, Long> calcDefendResource(WarDefender warDefender, double ratio) {
		Map<Integer, Long> resultMap = new ConcurrentHashMap<>();
		
		// 总的资源数量
		Player player = warDefender.getPlayer();
		long steel = CurrencyUtil.getCurrency(player, CurrencyEnum.STEEL);
		long oil = CurrencyUtil.getCurrency(player, CurrencyEnum.OIL);
		long gold = CurrencyUtil.getCurrency(player, CurrencyEnum.GLOD);
		long rare = CurrencyUtil.getCurrency(player, CurrencyEnum.RARE);
		
		Map<CurrencyEnum, Long> mainCityResource = warDefender.getMainCityResource();
		
		if(player.getCountryManager().getSteelResourceControl() != null) {
			long plusNum = player.getCountryManager().getSteelResourceControl().invasionResource(player, steel, ratio, resultMap);
			steel = plusNum;
		}
		
		if(player.getCountryManager().getOilResourceControl() != null) {
			long plusNum = player.getCountryManager().getOilResourceControl().invasionResource(player, oil, ratio, resultMap);
			steel = plusNum;
		}
		
		if(player.getCountryManager().getMoneyResourceControl() != null) {
			long plusNum = player.getCountryManager().getMoneyResourceControl().invasionResource(player, gold, ratio, resultMap);
			steel = plusNum;
			
		}
		
		if(player.getCountryManager().getRareResourceCountrol() != null) {
			long plusNum = player.getCountryManager().getRareResourceCountrol().invasionResource(player, rare, ratio, resultMap);
			steel = plusNum;
		}
		
		mainCityResource.put(CurrencyEnum.GLOD, gold);
		mainCityResource.put(CurrencyEnum.OIL,  oil);
		mainCityResource.put(CurrencyEnum.RARE,  rare);
		mainCityResource.put(CurrencyEnum.STEEL,  steel);
		
		resultMap.putAll(resultMap);
		return resultMap;
	}
	
	/**
	 * 发送联盟报告
	 * @param attackPlayer
	 * @param defendPlayer
	 * @param warResourceBean
	 */
	public void sendAllianceReport(Player attackPlayer, Player defendPlayer, WarResourceBean warResourceBean) {
		
		if(warResourceBean == null) {
			log.error("联盟报告异常。");
			return;
		}
		
		if (defendPlayer.getAllianceId() > 0 || attackPlayer.getAllianceId() > 0) {
			AllianceBattleReport battleReport = new AllianceBattleReport();
			battleReport.setAttackId(attackPlayer.getRoleId());
			battleReport.setAttackName(attackPlayer.getName());
			battleReport.setDefendId(defendPlayer.getRoleId());
			battleReport.setDefendName(defendPlayer.getName());
			battleReport.setResult(WarResultType.ATTACK.ordinal());

			AllianceBattleDetail allianceBattleDetail = new AllianceBattleDetail();
			allianceBattleDetail.setEarthNum(warResourceBean.rareNum);
			allianceBattleDetail.setMoneyNum(warResourceBean.moneyNum);
			allianceBattleDetail.setOilNum(warResourceBean.oilNum);
			allianceBattleDetail.setSteelNum(warResourceBean.steelNum);
			String info = JsonUtil.toJSON(allianceBattleDetail);
			battleReport.setInfo(info);
			if (defendPlayer.getAllianceId() > 0) {
				AllianceReport allianceReport = InjectorUtil.getInjector().allianceBattleInfoManager.getOrCreate(defendPlayer.getAllianceId());
				allianceReport.addBattleReport(battleReport);
				InjectorUtil.getInjector().dbCacheService.update(allianceReport);
			}

			if (attackPlayer.getAllianceId() > 0) {
				AllianceReport allianceReport = InjectorUtil.getInjector().allianceBattleInfoManager.getOrCreate(attackPlayer.getAllianceId());
				allianceReport.addBattleReport(battleReport);
				InjectorUtil.getInjector().dbCacheService.update(allianceReport);
			}
		}
	}
	
	/**
	 * 转换战斗结束报告
	 * @param battle
	 * @param warEndReport
	 * @return
	 */
	public ResWarEndMessage converterResWarEndMessage(Battle battle, WarEndReport warEndReport) {
		ResWarEndMessage resWarEndMessage = new ResWarEndMessage();
		resWarEndMessage.winUid = warEndReport.getWinUid();
		resWarEndMessage.destroyLevel = warEndReport.getDestroyLevel();
		long attackId = battle.getWarAttacker().getAttackId();
		WorldMarchSoldier worldMarchSoldier = battle.getWarAttacker().getWorldMarchSoldierMap().get(attackId);
		resWarEndMessage.attackSoldierBean = BattleConverter.converterWarSoldierList(worldMarchSoldier.querySoldierList(), battle.getWarAttacker().getPlayer());
		
		// 转换战斗结束数据
		WarDefender warDefender = battle.getWarDefender();
		List<DefendSoldierBean> defendSoldier = new ArrayList<DefendSoldierBean>();
		if(warDefender.getDefendSoldierMap() != null && !warDefender.getDefendSoldierMap().isEmpty()) {
			for(Soldier soldier : warDefender.getDefendSoldierMap().values()) {
				defendSoldier.add(BattleConverter.converterDefendSolderBean(warDefender.getPlayer(), soldier));
			}
		}
		
		if(warDefender.getReinforce() != null && !warDefender.getReinforce().isEmpty()) {
			for(WorldMarchSoldier warMarchSoldier : warDefender.getReinforce().values()) {
				defendSoldier.addAll(BattleConverter.converterDefendSolderBean(warDefender.getPlayer(), warMarchSoldier));
			}
		}
		
		resWarEndMessage.defendSoldierBean.addAll(defendSoldier);
		resWarEndMessage.warResourceBean.addAll(battle.getWarResource().values());
		return resWarEndMessage;
	}
	
	/**
	 * 处理伤兵
	 * @param attack
	 * @param attackSoldier
	 */
	public void dealAttackSoldier(WarAttacker attack, List<WarSoldierInfo> attackSoldier) {
		if(attackSoldier == null) {
			log.error("进攻方士兵为空.");
			return;
		}
		
		// 送修理厂
		for(WarSoldierInfo warSoldierInfo : attackSoldier) {
			Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, warSoldierInfo.getPlayerId());
			// 返还士兵
			int hurtNum = warSoldierInfo.getDeadNum();
			if(hurtNum > 0) {
				int deadNum = ModifyKit.warInsert(player, warSoldierInfo.getUid(), hurtNum, getWarType(), WarResultType.ATTACK, SoldierChangeType.MARCH);
				attack.resetSoldier(player.getId(), warSoldierInfo.getUid(), warSoldierInfo.getIndex(), hurtNum - deadNum, deadNum);
			} else {
				attack.resetSoldier(player.getId(), warSoldierInfo.getUid(), warSoldierInfo.getIndex(), 0, 0);
			}
		}
		
		Player player = attack.getPlayer();
		player.getSoldierManager().send(player);
	}
	
	
	/**
	 * 处理防守方士兵信息
	 * @param warDefender
	 * @param defendSoldier
	 */
	public void dealDefendSoldier(WarDefender warDefender, List<WarSoldierInfo> defendSoldier) {
		// 玩家伤兵
		for(WarSoldierInfo warSoldierInfo : defendSoldier) {
			if(warSoldierInfo.getBuildUid() > 0) {
				// 返还士兵
				int hurtNum = warSoldierInfo.getDeadNum();
				int deadNum = warDefender.getPlayer().getCountryManager().getDefebseSoldierControl().soldierToModFactory(warDefender.getPlayer(), warSoldierInfo.getBuildUid(), hurtNum);
				warDefender.resetDefendSoldier(warSoldierInfo.getBuildUid(), warSoldierInfo.getUid(), hurtNum, deadNum);
			
			} else {
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, warSoldierInfo.getPlayerId());
				
				// 返还士兵
				int hurtNum = warSoldierInfo.getDeadNum();
				int deadNum = ModifyKit.warInsert(player, warSoldierInfo.getUid(), warSoldierInfo.getDeadNum(), WarType.WORLD_CITY, WarResultType.DEFEND, SoldierChangeType.MARCH);
				warDefender.resetReinforceSoldier(warSoldierInfo.getPlayerId(), warSoldierInfo.getUid(), warSoldierInfo.getIndex(), hurtNum, deadNum);
			}
		}
		
		Player player = warDefender.getPlayer();
		player.getSoldierManager().send(player);
	}
	
//	/**
//	 * TODO（处理行军负载） 处理战斗奖励
//	 * @param battle
//	 * @param warBuildingInfos
//	 */
//	public void dealBattleReward(Battle battle, List<WarBuildingInfo> warBuildingInfos) {
//		
//		if(battle.getWarAttacker().getAttackId() == battle.getWinPlayerId()) {
//			WarAttacker warAttacker = battle.getWarAttacker();
//			WarDefender warDefender  = battle.getWarDefender();
//			Player losePlayer = warDefender.getPlayer();
//			Player winPlayer = warAttacker.getPlayer();
//			
//			WarResourceBean warResourceBean = new WarResourceBean();
//			battle.getWarResource().put(winPlayer.getId(), warResourceBean);
//			
//			WorldMarch worldMarch = battle.getWarFightParam().getAttackWorldMarch();
//			if(worldMarch == null) {
//				return ;
//			}
//			
//			// 部队负载
//			int weight = worldMarch.getWeight(winPlayer);
//			if(weight <= 0) {
//				return;
//			}
//			
//			// 处理建筑信息，必须是玩家的资源建筑才做处理
//			int totalMass = 0;
//			List<WarBuildingInfo> warBuildingInfoList = new ArrayList<WarBuildingInfo>();
//			for(WarBuildingInfo warBuildingInfo : warBuildingInfos) {
//				if(warBuildingInfo.getResourceType() > 0) {
//					warBuildingInfoList.add(warBuildingInfo);
//					totalMass += MarchUtils.getMass(EnumUtils.getEnum(CurrencyEnum.class, warBuildingInfo.getResourceType()));
//				}
//				
//
//				Map<Integer, CountryBuild> buildMap = losePlayer.roleInfo().getBaseCountry().getAllBuild();
//				if(buildMap != null) {
//					XBuild build = buildMap.get(warBuildingInfo.getUid());
//					if(build != null && build.getSid() == BuildFactory.MAIN.getTid()) {
//						warBuildingInfoList.add(warBuildingInfo);
//					}
//				}
//			}
//			
//			// 计算可以掠夺的资源数量
//			double ratio = Double.valueOf(GlobalPirFactory.get(GlobalConstant.INVASTION_RATIO).getValue());
//			if(warBuildingInfoList != null && !warBuildingInfoList.isEmpty()) {
//				for(WarBuildingInfo warBuildingInfo : warBuildingInfoList) {
//					// 仓库资源
//					if(warBuildingInfo.getResourceType() > 0) {
//						int mass = MarchUtils.getMass(EnumUtils.getEnum(CurrencyEnum.class, warBuildingInfo.getResourceType()));
//						int resourceWeigh = weight / totalMass * mass /totalMass;
//						long resoruce = warDefender.queryDefendResource(warBuildingInfo.getUid());
//						
//						long totalHp = warBuildingInfo.getTotalHp();
//						long attackHp = warBuildingInfo.getPlayerDamange(winPlayer.getRoleId());
//						if(totalHp == 0 || attackHp == 0) {
//							continue;
//						}
//						
//						long rewardResource = resoruce * attackHp / totalHp;
//						
//						rewardResource = Math.max(0, Math.min(resourceWeigh, rewardResource));
//						if(warBuildingInfo.getResourceType() == CurrencyEnum.GLOD.ordinal()) {
//							CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.GLOD, GameLogSource.COUNTRY_WAR);
//							warResourceBean.moneyNum = (int)rewardResource;
//						} else if(warBuildingInfo.getResourceType() == CurrencyEnum.OIL.ordinal()) {
//							CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.OIL, GameLogSource.COUNTRY_WAR);
//							warResourceBean.oilNum = (int)rewardResource;
//						} else if(warBuildingInfo.getResourceType() == CurrencyEnum.STEEL.ordinal()) {
//							CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.STEEL, GameLogSource.COUNTRY_WAR);
//							warResourceBean.steelNum = (int)rewardResource;
//						} else if(warBuildingInfo.getResourceType() == CurrencyEnum.RARE.ordinal()) {
//							CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.RARE, GameLogSource.COUNTRY_WAR);
//							warResourceBean.rareNum = (int)rewardResource;
//						}
//						
//					//主基地资源	
//					} else {
//						long totalHp = warBuildingInfo.getTotalHp();
//						long attackHp = warBuildingInfo.getPlayerDamange(winPlayer.getRoleId());
//						if(totalHp == 0 || attackHp == 0) {
//							continue;
//						}
//						
//						Map<Integer, CountryBuild> buildMap = losePlayer.roleInfo().getBaseCountry().getAllBuild();
//						if(buildMap != null) {
//							XBuild build = buildMap.get(warBuildingInfo.getUid());
//							// 主基地
//							if(build.getSid() == BuildFactory.MAIN.getTid()) {
//								Map<CurrencyEnum, Long> resourceMap = warDefender.getMainCityResource();
//								if(resourceMap != null) {
//									for(java.util.Map.Entry<CurrencyEnum, Long> entry : resourceMap.entrySet()) {
//										long resource = 0;
//										if(entry.getValue() > 0) {
//											resource = entry.getValue() * warBuildingInfo.getPlayerDamange(winPlayer.getRoleId()) / warBuildingInfo.getTotalHp();
//											
//											// 资源信息
//											resource = Math.min(resource, Double.valueOf(entry.getValue() * ratio).intValue());
//											
//											CurrencyUtil.decrement(losePlayer, resource, entry.getKey(), GameLogSource.COUNTRY_WAR);
//											
//											// 返回资源信息
//											if(entry.getKey() == CurrencyEnum.GLOD) {
//												warResourceBean.moneyNum = (int)resource;
//											} else if(entry.getKey() == CurrencyEnum.OIL) {
//												warResourceBean.moneyNum = (int)resource;
//											} else if(entry.getKey() == CurrencyEnum.STEEL) {
//												warResourceBean.moneyNum = (int)resource;
//											} else if(entry.getKey() == CurrencyEnum.RARE) {
//												warResourceBean.moneyNum = (int)resource;
//											}
//										}
//									}
//								}
//							}
//						}
//
//					}
//				}
//				
//				// 同步资源
//				CurrencyUtil.send(losePlayer);
//			}
//		}
//	}
	
	/**
	 * 处理战斗奖励
	 * @param battle
	 * @param warBuildingInfos
	 */
	public void dealBattleReward(Battle battle, List<WarBuildingInfo> warBuildingInfos) {
			if(battle.getWarAttacker().getAttackId() == battle.getWinPlayerId()) {
				WarAttacker warAttacker = battle.getWarAttacker();
				WarDefender warDefender  = battle.getWarDefender();
				Player losePlayer = warDefender.getPlayer();
				
				WarResourceBean warResourceBean = new WarResourceBean();
				for(WorldMarchSoldier worldMarchSoldier : warAttacker.getWorldMarchSoldierMap().values()) {
					Player marchPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarchSoldier.getPlayerId());
					battle.getWarResource().put(marchPlayer.getId(), warResourceBean);
					long weight = 0;
					if(marchPlayer != null) {
						weight = worldMarchSoldier.getWeight(marchPlayer);
					}
					
					// 处理建筑信息，必须是玩家的资源建筑才做处理
					int totalMass = 0;
					List<WarBuildingInfo> warBuildingInfoList = new ArrayList<WarBuildingInfo>();
					for(WarBuildingInfo warBuildingInfo : warBuildingInfos) {
						if(warBuildingInfo.getResourceType() > 0) {
							warBuildingInfoList.add(warBuildingInfo);
							totalMass += MarchUtils.getMass(EnumUtils.getEnum(CurrencyEnum.class, warBuildingInfo.getResourceType()));
						}
						
	
						Map<Integer, CountryBuild> buildMap = losePlayer.roleInfo().getBaseCountry().getAllBuild();
						if(buildMap != null) {
							XBuild build = buildMap.get(warBuildingInfo.getUid());
							if(build != null && build.getSid() == BuildFactory.MAIN.getTid()) {
								warBuildingInfoList.add(warBuildingInfo);
							}
						}
					}
					
					// 计算可以掠夺的资源数量
					double ratio = Double.valueOf(GlobalPirFactory.get(GlobalConstant.INVASTION_RATIO).getValue());
					if(warBuildingInfoList != null && !warBuildingInfoList.isEmpty()) {
						for(WarBuildingInfo warBuildingInfo : warBuildingInfoList) {
							// 仓库资源
							if(warBuildingInfo.getResourceType() > 0) {
								int mass = MarchUtils.getMass(EnumUtils.getEnum(CurrencyEnum.class, warBuildingInfo.getResourceType()));
								int resourceWeigh = Long.valueOf(weight / totalMass * mass /totalMass).intValue();
								long resoruce = warDefender.queryDefendResource(warBuildingInfo.getUid());
								
								long totalHp = warBuildingInfo.getTotalHp();
								long attackHp = warBuildingInfo.getPlayerDamange(marchPlayer.getRoleId());
								if(totalHp == 0 || attackHp == 0) {
									continue;
								}
								
								long rewardResource = resoruce * attackHp / totalHp;
								
								rewardResource = Math.max(0, Math.min(resourceWeigh, rewardResource));
								if(warBuildingInfo.getResourceType() == CurrencyEnum.GLOD.ordinal()) {
									CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.GLOD, GameLogSource.COUNTRY_WAR);
									warResourceBean.moneyNum = (int)rewardResource;
								} else if(warBuildingInfo.getResourceType() == CurrencyEnum.OIL.ordinal()) {
									CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.OIL, GameLogSource.COUNTRY_WAR);
									warResourceBean.oilNum = (int)rewardResource;
								} else if(warBuildingInfo.getResourceType() == CurrencyEnum.STEEL.ordinal()) {
									CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.STEEL, GameLogSource.COUNTRY_WAR);
									warResourceBean.steelNum = (int)rewardResource;
								} else if(warBuildingInfo.getResourceType() == CurrencyEnum.RARE.ordinal()) {
									CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.RARE, GameLogSource.COUNTRY_WAR);
									warResourceBean.rareNum = (int)rewardResource;
								}
								
							//主基地资源	
							} else {
								long totalHp = warBuildingInfo.getTotalHp();
								long attackHp = warBuildingInfo.getPlayerDamange(marchPlayer.getRoleId());
								if(totalHp == 0 || attackHp == 0) {
									continue;
								}
								
								Map<Integer, CountryBuild> buildMap = losePlayer.roleInfo().getBaseCountry().getAllBuild();
								if(buildMap != null) {
									XBuild build = buildMap.get(warBuildingInfo.getUid());
									// 主基地
									if(build.getSid() == BuildFactory.MAIN.getTid()) {
										Map<CurrencyEnum, Long> resourceMap = warDefender.getMainCityResource();
										if(resourceMap != null) {
											for(java.util.Map.Entry<CurrencyEnum, Long> entry : resourceMap.entrySet()) {
												long resource = 0;
												if(entry.getValue() > 0) {
													resource = entry.getValue() * warBuildingInfo.getPlayerDamange(marchPlayer.getRoleId()) / warBuildingInfo.getTotalHp();
													
													// 资源信息
													resource = Math.min(resource, Double.valueOf(entry.getValue() * ratio).intValue());
													
													CurrencyUtil.decrement(losePlayer, resource, entry.getKey(), GameLogSource.COUNTRY_WAR);
													
													// 返回资源信息
													if(entry.getKey() == CurrencyEnum.GLOD) {
														warResourceBean.moneyNum = (int)resource;
													} else if(entry.getKey() == CurrencyEnum.OIL) {
														warResourceBean.moneyNum = (int)resource;
													} else if(entry.getKey() == CurrencyEnum.STEEL) {
														warResourceBean.moneyNum = (int)resource;
													} else if(entry.getKey() == CurrencyEnum.RARE) {
														warResourceBean.moneyNum = (int)resource;
													}
												}
											}
										}
									}
								}
							}
						}
				}
					
					// 同步资源
					CurrencyUtil.send(losePlayer);
			}
				
		}
	}
	
	
	/**
	 * 战斗结束统一处理
	 * @param battle
	 */
	@Override
	public void fightEnd(Battle battle, WarEndReport warEndReport) {
		warManager.removeBattle(battle);
		
		SpriteInfo targetInfo = battle.getWarFightParam().getTargetSpriteInfo();
		if(targetInfo != null) {
			WorldSprite worldSprite = targetInfo.getParam();
			if(worldSprite != null) {
				worldSprite.setFight(false);
				InjectorUtil.getInjector().dbCacheService.update(targetInfo);
			}
		}
	
	}
}
