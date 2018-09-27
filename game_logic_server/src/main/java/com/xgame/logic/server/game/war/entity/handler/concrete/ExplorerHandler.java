package com.xgame.logic.server.game.war.entity.handler.concrete;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.WarAttacker;
import com.xgame.logic.server.game.war.entity.WarDefender;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.war.entity.report.WarEndReport;
import com.xgame.logic.server.game.war.message.ResWarDataMessage;
import com.xgame.logic.server.game.world.entity.ExplorerInfo;
import com.xgame.logic.server.game.world.entity.MarchCollect;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.ExplorerWorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;

/**
 * 采集处理
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class ExplorerHandler extends AbstractFightHandler {

	@Override
	public WarType getWarType() {
		return WarType.EXPLORER;
	}

	@Override
	public Battle init(WarFightParam warFightParam) {
		Battle battle = new Battle();
		battle.setBattleId(WarManager.BATTLEID_GENERATOR.incrementAndGet());
		
		// 初始化进攻方
		WarAttacker warAttacker = new WarAttacker();
		warAttacker.initSoldier(warFightParam.getAttackPlayer(), Lists.newArrayList(warFightParam.getAttackWorldMarch().getWorldMarchSoldier()), 0);
		warAttacker.setPlayer(warFightParam.getAttackPlayer());
		battle.setWarAttacker(warAttacker);
		battle.setWarType(warFightParam.getBattleType());
		
		// 初始化防守方
		WarDefender warDefender = new WarDefender();
		warDefender.setPlayer(warFightParam.getDefendPlayer());
		warDefender.initReinforce(warFightParam.getDefendSoldiers());
		battle.setWarDefender(warDefender);
		battle.setWarFightParam(warFightParam);
		
		// 初始化
		ResWarDataMessage resWarDataMessage = new ResWarDataMessage();
		resWarDataMessage.attackData = battle.getWarAttacker().getWarAttackData();
		resWarDataMessage.defenData = battle.getWarDefender().getWarDefenData();
		resWarDataMessage.battleId = battle.getBattleId();
		battle.setResWarDataMessage(resWarDataMessage);
		return battle;
	}

	@Override
	public void fightEnd(Battle battle, WarEndReport warEndReport) {
		
		// 处理伤病
		WarAttacker warAttacker = battle.getWarAttacker();
		WarDefender warDefender = battle.getWarDefender();
		
		WarFightParam warFightParam = battle.getWarFightParam();
		WorldMarch attackMarch = warFightParam.getAttackWorldMarch();
		WorldMarch defendMatch = warFightParam.getDefendWorldMarch();
		SpriteInfo targetInfo = warFightParam.getTargetSpriteInfo();
		Player attackPlayer = warFightParam.getAttackPlayer();
		Player defenPlayer = warFightParam.getDefendPlayer();
		battle.setWarFightParam(warFightParam);
		battle.setWinPlayerId(warEndReport.getWinUid());
		
		dealAttackSoldier(warAttacker, warEndReport.getWarEntityReport().getAttackSoldierBean());
		InjectorUtil.getInjector().dbCacheService.update(attackMarch);
		
		//处理防守方士兵
		dealDefendSoldier(warDefender, warEndReport.getWarEntityReport().getDefendSoldierBean());
		InjectorUtil.getInjector().dbCacheService.update(defendMatch);
		
		// 处理采集动作
		if(warEndReport.getWinUid() == attackPlayer.getRoleId()) {
			ResourceSprite resourceSprite = targetInfo.getParam();
			resourceSprite.setOwnerMarchId(attackMarch.getId());
			
			// 最大能采集数量
			ExplorerWorldMarch explorerWorldMarch = (ExplorerWorldMarch)attackMarch.getExecutor();
			
			// 进攻方剩余可采集数量
			ExplorerInfo explorerInfo = explorerWorldMarch.getResoruceExploreInfo(defendMatch.getExlplorerNum(), attackMarch, attackPlayer, resourceSprite);
			if(explorerInfo.getExplorerTime() > 0) {
				attackMarch.setExlplorerNum(defendMatch.getExlplorerNum());
				attackMarch.setExplorerType(resourceSprite.getResourceType());

				// 开始采集
				log.info("进攻方获胜, 开始采集, 采集时间[{}]", explorerInfo.getExplorerTime());
				((ExplorerWorldMarch)attackMarch.executor).doExplorer(attackMarch, attackPlayer, targetInfo, explorerInfo);
			// 抢过来的资源已经超过负载直接回家
			} else {
				// 处理返回
				explorerWorldMarch.handleReturn();
			}

			// 采集碰到玩家，失败，直接回老家
			((ExplorerWorldMarch)defendMatch.executor).backReturn();
			((ExplorerWorldMarch)defendMatch.executor).pushToWorldMarch(defenPlayer, defendMatch);
			
			explorerWorldMarch.pushToWorldMarch(attackPlayer, attackMarch);
			explorerWorldMarch.pushToWorldSprites(targetInfo);
			
		} else {
			// 采集碰到玩家，失败，直接回老家
			((ExplorerWorldMarch)attackMarch.executor).backReturn();
			if (defendMatch.getSoldierNum() <= 0) {
				((ExplorerWorldMarch)defendMatch.executor).backHomeImmediately(defendMatch);
			} else {
				//计算防守方继续采集所需时间
				ResourceSprite resourceSprite = targetInfo.getParam();
				ExplorerInfo explorerInfo = ((ExplorerWorldMarch)defendMatch.getExecutor()).getResoruceExploreInfo(defendMatch.getExlplorerNum(), defendMatch, defenPlayer, resourceSprite);
				if(explorerInfo.getExplorerTime() > 0) {
					// 开始采集
					((ExplorerWorldMarch)defendMatch.executor).doExplorer(defendMatch, defenPlayer, targetInfo, explorerInfo);
					
					// 生成采集信息
					log.info("战斗结束, 继续采集, 采集所需时间[{}]", explorerInfo.getExplorerTime());
				} else {
					MarchCollect marchCollect = new MarchCollect();
					marchCollect.setResourceType(resourceSprite.getResourceType());
					marchCollect.setLevel(resourceSprite.getLevel());
					marchCollect.setCollectNum(Math.min(explorerInfo.getPlusNum(), explorerInfo.getMaxNum()));
					defendMatch.setAttach(marchCollect);
					((ExplorerWorldMarch)defendMatch.executor).handleReturn();
					
					log.info("战斗结束, 返回");
				}
			}
			
			((ExplorerWorldMarch)defendMatch.getExecutor()).pushToWorldSprites(targetInfo);
		}
		
		int resourceType = warFightParam.getResourceType();
		int resourceLevel = warFightParam.getResourceLevel();
		int resourceNum = attackMarch.getExlplorerNum();
		
		//战报
		playerMailInfoManager.sendBattleEmail(battle, EmailTemplet.资源点进攻报告_MAIL_ID, EmailTemplet.资源点防守报告_MAIL_ID, getWarType(),resourceType,resourceLevel,resourceNum);
		super.fightEnd(battle, warEndReport);
	}
}
