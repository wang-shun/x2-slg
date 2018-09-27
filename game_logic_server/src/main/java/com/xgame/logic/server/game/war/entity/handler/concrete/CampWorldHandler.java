package com.xgame.logic.server.game.war.entity.handler.concrete;

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
import com.xgame.logic.server.game.world.constant.MarchFightState;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.CampWorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;

/**
 * 扎营战斗
 * @author jacky.jiang
 *
 */
@Component
public class CampWorldHandler extends AbstractFightHandler {

	@Override
	public WarType getWarType() {
		return WarType.CAMP;
	}

	@Override
	public Battle init(WarFightParam warFightParam) {
		
		Battle battle = new Battle();
		battle.setBattleId(WarManager.BATTLEID_GENERATOR.incrementAndGet());
		battle.setWarType(warFightParam.getBattleType());
		
		// 初始化进攻方
		WarAttacker warAttacker = new WarAttacker();
		warAttacker.initSoldier(warFightParam.getAttackPlayer(), Lists.newArrayList(warFightParam.getAttackWorldMarch().getWorldMarchSoldier()), 0);
		warAttacker.setPlayer(warFightParam.getAttackPlayer());
		battle.setWarAttacker(warAttacker);
		
		// 初始化防守方
		WarDefender warDefender = new WarDefender();
		warDefender.setPlayer(warFightParam.getDefendPlayer());
		warDefender.initReinforce(warFightParam.getDefendSoldiers());
		battle.setWarDefender(warDefender);
		battle.setWarFightParam(warFightParam);
		
		// 初始化
		ResWarDataMessage resWarDataMessage = new ResWarDataMessage();
		resWarDataMessage.battleType = battle.getWarType().getConfigId();
		resWarDataMessage.attackData = battle.getWarAttacker().getWarAttackData();
		resWarDataMessage.defenData = battle.getWarDefender().getWarDefenData();
		resWarDataMessage.battleId = battle.getBattleId();
		resWarDataMessage.battleType = battle.getWarType().getConfigId();
		battle.setResWarDataMessage(resWarDataMessage);
		return battle;
	}

	@Override
	public void fightEnd(Battle battle, WarEndReport warEndReport) {
		// 处理伤病
		WarAttacker warAttacker = battle.getWarAttacker();
		WarDefender warDefender = battle.getWarDefender();
		
		WarFightParam warFightParam = battle.getWarFightParam();
		WorldMarch attackWorldMarch = warFightParam.getAttackWorldMarch();
		WorldMarch defenWorldMarch = warFightParam.getDefendWorldMarch();
		SpriteInfo targetInfo = warFightParam.getTargetSpriteInfo();
		battle.setWinPlayerId(warEndReport.getWinUid());
		
		dealAttackSoldier(warAttacker, warEndReport.getWarEntityReport().getAttackSoldierBean());
		InjectorUtil.getInjector().dbCacheService.update(attackWorldMarch);
		
		//处理防守方士兵
		dealDefendSoldier(warDefender, warEndReport.getWarEntityReport().getDefendSoldierBean());
		InjectorUtil.getInjector().dbCacheService.update(defenWorldMarch);
		
		// 根据战报设置出征士兵
		Player attackPlayer = warAttacker.getPlayer();
		
		// 扎营赢了
		if (warEndReport.getWinUid() == attackPlayer.getRoleId()) {
			WorldSprite worldSprite = targetInfo.getParam();
			worldSprite.setOwnerMarchId(attackWorldMarch.getId());
			InjectorUtil.getInjector().dbCacheService.update(targetInfo);
			
			// 进攻方赢了，防守方
			(defenWorldMarch.executor).handleReturn();
			if(attackWorldMarch.getMarchFight() == MarchFightState.FIGHT) {
				attackWorldMarch.getExecutor().handleReturn();
			} else {
				attackWorldMarch.executor.refreshLocation(targetInfo);
			}
			
		//扎营输了直接返回
		} else {
			// 进攻方失败直接回家
			((CampWorldMarch)attackWorldMarch.executor).handleReturn();
			
			// 防守方回家
			if(defenWorldMarch.getSoldierNum() <= 0) {
				defenWorldMarch.executor.handleReturn();
			}
			
			defenWorldMarch.executor.refreshLocation(targetInfo);
		}
		
		//战报
		playerMailInfoManager.sendBattleEmail(battle, EmailTemplet.营地进攻报告_MAIL_ID, EmailTemplet.营地防守报告_MAIL_ID, getWarType());
		super.fightEnd(battle, warEndReport);
	}
}
