package com.xgame.logic.server.game.war.entity.handler.concrete;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.CountryManager;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.mod.ModifyKit;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.war.constant.WarResultType;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.WarAttacker;
import com.xgame.logic.server.game.war.entity.WarDefender;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.war.entity.report.WarBuildingInfo;
import com.xgame.logic.server.game.war.entity.report.WarEndReport;
import com.xgame.logic.server.game.war.entity.report.WarSoldierInfo;
import com.xgame.logic.server.game.war.message.ResWarDataMessage;
import com.xgame.logic.server.game.war.message.ResWarEndMessage;


/**
 * 家园搜索战斗
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class CountrySearchHandler extends AbstractFightHandler {

	@Autowired
	private WarManager warManager;
	
	@Override
	public WarType getWarType() {
		return WarType.COUNTRY_SEARCH;
	}

	@Override
	public Battle init(WarFightParam warFightParam) {
		Battle battle = new Battle();
		battle.setBattleId(WarManager.BATTLEID_GENERATOR.incrementAndGet());
		
		// 初始化进攻方
		WarAttacker warAttacker = new WarAttacker();
		warAttacker.initSoldier(warFightParam.getAttackPlayer(), warFightParam.getAttackSoldiers(), 0);
		warAttacker.setPlayer(warFightParam.getAttackPlayer());
		battle.setWarAttacker(warAttacker);
		battle.setWarType(warFightParam.getBattleType());
		
		// 初始化防守方
		WarDefender warDefender = new WarDefender();
		warDefender.setPlayer(warFightParam.getDefendPlayer());
		warDefender.initCitySoldier(warFightParam.getDefendPlayer());
		
		//初始化建筑,初始化可悲掠夺长裤信息
		warDefender.initBulidings(warFightParam.getDefendPlayer());
		
		double ratio = Double.valueOf(GlobalPirFactory.get(GlobalConstant.INVASTION_RATIO).getValue());
		this.initInvasionResource(warDefender, ratio);
		
		warDefender.setPlayer(warFightParam.getDefendPlayer());
		warDefender.initReinforce(warFightParam.getDefendSoldiers());
		battle.setWarDefender(warDefender);
		battle.setWarFightParam(warFightParam);
		
		// 初始化
		ResWarDataMessage resWarDataMessage = new ResWarDataMessage();
		resWarDataMessage.attackData = battle.getWarAttacker().getWarAttackData();
		resWarDataMessage.defenData = battle.getWarDefender().getWarDefenData();
		resWarDataMessage.battleId = battle.getBattleId();
		resWarDataMessage.battleType = battle.getWarType().getConfigId();
		battle.setResWarDataMessage(resWarDataMessage);
		return battle;
	}

	@Override
	public void fightEnd(Battle battle, WarEndReport warEndReport) {
		
		battle.setWinPlayerId(warEndReport.getWinUid());
		
		//处理进攻方士兵
		dealAttackSoldier(battle.getWarAttacker(), warEndReport.getWarEntityReport().getAttackSoldierBean());
		
		//处理防守方士兵
		dealDefendSoldier(battle.getWarDefender(), warEndReport.getWarEntityReport().getDefendSoldierBean());
		
		// 处理战斗奖励
		dealBattleReward(battle, warEndReport.getWarEntityReport().getBuildingReport());
		
		// 发送战报
		playerMailInfoManager.sendDefensiveEmail(battle.getWarAttacker().getPlayer(), battle.getWarDefender().getPlayer().getId(),battle,String.valueOf(warEndReport.getWinUid()).equals(battle.getWarDefender().getPlayer().getId()));
		
		// 获取战斗结束
		ResWarEndMessage resWarEndMessage = this.converterResWarEndMessage(battle, warEndReport);
		battle.getWarAttacker().getPlayer().send(resWarEndMessage);
		
		// 士兵同步
		Player attackPlayer = battle.getWarAttacker().getPlayer();
		Player defendPlayer = battle.getWarDefender().getPlayer();
	
		// 推送士兵变化
		attackPlayer.getSoldierManager().send(attackPlayer);
		defendPlayer.getSoldierManager().send(defendPlayer);
		
		super.fightEnd(battle, warEndReport);
	}

	/**
	 * 处理伤兵
	 * @param attack
	 * @param attackSoldier
	 */
	@Override
	public void dealAttackSoldier(WarAttacker attack, List<WarSoldierInfo> attackSoldier) {
		if(attackSoldier == null) {
			log.error("进攻方士兵为空.");
			return;
		}
		
		// 送修理厂
		for(WarSoldierInfo warSoldierInfo : attackSoldier) {
			Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, warSoldierInfo.getPlayerId());
			// 返还士兵
			if(warSoldierInfo.getPlusNum() > 0) {
				player.roleInfo().getSoldierData().addSoldier(warSoldierInfo.getUid(), warSoldierInfo.getPlusNum(), SoldierChangeType.COMMON);
			}
			
			int hurtNum = warSoldierInfo.getDeadNum();
			int deadNum = ModifyKit.warInsert(player, warSoldierInfo.getUid(), hurtNum, WarType.COUNTRY_SEARCH, WarResultType.ATTACK, SoldierChangeType.RTS);
			attack.resetSoldier(player.getId(), warSoldierInfo.getUid(), warSoldierInfo.getIndex(), hurtNum - deadNum, deadNum);
		}
		
		Player player = attack.getPlayer();
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		player.getSoldierManager().send(player);
	}
	
	/**
	 * 处理防守方士兵信息
	 * @param warDefender
	 * @param defendSoldier
	 */
	@Override
	public void dealDefendSoldier(WarDefender warDefender, List<WarSoldierInfo> defendSoldier) {
		CountryManager countryManager = warDefender.getPlayer().getCountryManager();
		if(countryManager == null) {
			return;
		}
		
		// 玩家伤兵
		for(WarSoldierInfo warSoldierInfo : defendSoldier) {
			if(warSoldierInfo.getBuildUid() > 0) {
				// 返还士兵
				int hurtNum = warSoldierInfo.getDeadNum();
				int deadNum = warDefender.getPlayer().getCountryManager().getDefebseSoldierControl().soldierToModFactory(warDefender.getPlayer(), warSoldierInfo.getBuildUid(), hurtNum);
				warDefender.resetDefendSoldier(warSoldierInfo.getBuildUid(), warSoldierInfo.getUid(), hurtNum, deadNum);
			}
		}

		
		Player player = warDefender.getPlayer();
		player.getSoldierManager().send(player);
	}
	
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
			Player winPlayer = warAttacker.getPlayer();
			
			WarResourceBean warResourceBean = new WarResourceBean();
			battle.getWarResource().put(winPlayer.getId(), warResourceBean);
			
//			double ratio = Double.valueOf(GlobalPirFactory.get(GlobalConstant.INVASTION_RATIO).getValue());
//			for(WarBuildingInfo warBuildingInfo : warBuildingInfos) {
//				long resoruce = warDefender.queryDefendResource(warBuildingInfo.getUid());
//				long rewardResource = resoruce * warBuildingInfo.getPlayerDamange(winPlayer.getRoleId()) / warBuildingInfo.getTotalHp();
//				if(warBuildingInfo.getResourceType() == CurrencyEnum.GLOD.ordinal()) {
//					// 金钱
//					CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.GLOD, GameLogSource.COUNTRY_WAR);
//					CurrencyUtil.increase(winPlayer, rewardResource,  CurrencyEnum.GLOD, GameLogSource.COUNTRY_WAR);
//					warResourceBean.moneyNum += (int)rewardResource;
//				} else if(warBuildingInfo.getResourceType() == CurrencyEnum.OIL.ordinal()) {
//					// 石油
//					CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.OIL, GameLogSource.COUNTRY_WAR);
//					CurrencyUtil.increase(winPlayer, rewardResource,  CurrencyEnum.OIL, GameLogSource.COUNTRY_WAR);
//					warResourceBean.oilNum += (int)rewardResource;
//				} else if(warBuildingInfo.getResourceType() == CurrencyEnum.STEEL.ordinal()) {
//					// 钢铁
//					CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.STEEL, GameLogSource.COUNTRY_WAR);
//					CurrencyUtil.increase(winPlayer, rewardResource,  CurrencyEnum.STEEL, GameLogSource.COUNTRY_WAR);
//					warResourceBean.steelNum += (int)rewardResource;
//				} else if(warBuildingInfo.getResourceType() == CurrencyEnum.RARE.ordinal()) {
//					// 稀土
//					CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.RARE, GameLogSource.COUNTRY_WAR);
//					CurrencyUtil.increase(winPlayer, rewardResource,  CurrencyEnum.RARE, GameLogSource.COUNTRY_WAR);
//					warResourceBean.rareNum += (int)rewardResource;
//				}
//				
//				Player defenderPlayer = warDefender.getPlayer();
//				if(defenderPlayer != null) {
//					Map<Integer, CountryBuild> buildMap = defenderPlayer.roleInfo().getBaseCountry().getAllBuild();
//					if(buildMap != null) {
//						XBuild build = buildMap.get(warBuildingInfo.getUid());
//						
//						// 主基地
//						if(build != null && build.getSid() == BuildFactory.MAIN.getTid()) {
//							Map<CurrencyEnum, Long> resourceMap = warDefender.getMainCityResource();
//							if(resourceMap != null) {
//								for(java.util.Map.Entry<CurrencyEnum, Long> entry : resourceMap.entrySet()) {
//									long resource = 0;
//									if(entry.getValue() > 0) {
//										resource = entry.getValue() * warBuildingInfo.getPlayerDamange(winPlayer.getRoleId()) / warBuildingInfo.getTotalHp();
//										// 资源信息
//										resource = Math.min(resource, Double.valueOf(entry.getValue() * ratio).intValue());
//										
//										CurrencyUtil.decrement(losePlayer, rewardResource, entry.getKey(), GameLogSource.COUNTRY_WAR);
//										CurrencyUtil.increase(winPlayer, rewardResource,  entry.getKey(), GameLogSource.COUNTRY_WAR);
//									}
//								}
//							}
//						}
//					}
//				}
//				
//				CurrencyUtil.send(winPlayer);
//				CurrencyUtil.send(losePlayer);
//				
//			}
			
			
			// 计算可以掠夺的资源数量
			double ratio = Double.valueOf(GlobalPirFactory.get(GlobalConstant.INVASTION_RATIO).getValue());
			if(warBuildingInfos != null && !warBuildingInfos.isEmpty()) {
				for(WarBuildingInfo warBuildingInfo : warBuildingInfos) {
					// 仓库资源
					if(warBuildingInfo.getResourceType() > 0) {
						long resoruce = warDefender.queryDefendResource(warBuildingInfo.getUid());
						
						long totalHp = warBuildingInfo.getTotalHp();
						long attackHp = warBuildingInfo.getPlayerDamange(winPlayer.getRoleId());
						if(totalHp == 0 || attackHp == 0) {
							continue;
						}
						
						long rewardResource = resoruce * attackHp / totalHp;
						if(warBuildingInfo.getResourceType() == CurrencyEnum.GLOD.ordinal()) {
							CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.GLOD, GameLogSource.COUNTRY_WAR);
							CurrencyUtil.increase(winPlayer, rewardResource, CurrencyEnum.GLOD, GameLogSource.COUNTRY_WAR);
							warResourceBean.moneyNum = (int)rewardResource;
						} else if(warBuildingInfo.getResourceType() == CurrencyEnum.OIL.ordinal()) {
							CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.OIL, GameLogSource.COUNTRY_WAR);
							CurrencyUtil.increase(winPlayer, rewardResource, CurrencyEnum.OIL, GameLogSource.COUNTRY_WAR);
							warResourceBean.oilNum = (int)rewardResource;
						} else if(warBuildingInfo.getResourceType() == CurrencyEnum.STEEL.ordinal()) {
							CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.STEEL, GameLogSource.COUNTRY_WAR);
							CurrencyUtil.increase(winPlayer, rewardResource, CurrencyEnum.STEEL, GameLogSource.COUNTRY_WAR);
							warResourceBean.steelNum = (int)rewardResource;
						} else if(warBuildingInfo.getResourceType() == CurrencyEnum.RARE.ordinal()) {
							CurrencyUtil.decrement(losePlayer, rewardResource, CurrencyEnum.RARE, GameLogSource.COUNTRY_WAR);
							CurrencyUtil.increase(winPlayer, rewardResource, CurrencyEnum.RARE, GameLogSource.COUNTRY_WAR);
							warResourceBean.rareNum = (int)rewardResource;
						}
						
					//主基地资源	
					} else {
						long totalHp = warBuildingInfo.getTotalHp();
						long attackHp = warBuildingInfo.getPlayerDamange(winPlayer.getRoleId());
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
											resource = entry.getValue() * warBuildingInfo.getPlayerDamange(winPlayer.getRoleId()) / warBuildingInfo.getTotalHp();
											
											// 资源信息
											resource = Math.min(resource, Double.valueOf(entry.getValue() * ratio).intValue());
											
											// 扣减和增加资源
											CurrencyUtil.decrement(losePlayer, resource, entry.getKey(), GameLogSource.COUNTRY_WAR);
											CurrencyUtil.increase(winPlayer, resource, entry.getKey(), GameLogSource.COUNTRY_WAR);
											
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
				
				// 同步资源
				CurrencyUtil.send(losePlayer);
			}
		}
	}
}
