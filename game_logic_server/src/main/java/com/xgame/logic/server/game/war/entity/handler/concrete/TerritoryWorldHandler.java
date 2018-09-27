package com.xgame.logic.server.game.war.entity.handler.concrete;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.utils.InjectorUtil;
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
import com.xgame.logic.server.game.world.entity.march.concrete.TerritoryWorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;

/**
 * 领地战斗
 * @author jacky.jiang
 *
 */
@Component
public class TerritoryWorldHandler extends AbstractFightHandler {

	@Override
	public WarType getWarType() {
		return WarType.TERRITORY;
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
		WorldMarch defendWorldMarch = warFightParam.getDefendWorldMarch();
		battle.setWinPlayerId(warEndReport.getWinUid());
		
		// 处理进攻方士兵
		SpriteInfo targetInfo = warFightParam.getTargetSpriteInfo();
		dealAttackSoldier(warAttacker, warEndReport.getWarEntityReport().getAttackSoldierBean());
		InjectorUtil.getInjector().dbCacheService.update(attackWorldMarch);
		
		//处理防守方士兵
		dealDefendSoldier(warDefender, warEndReport.getWarEntityReport().getDefendSoldierBean());
		InjectorUtil.getInjector().dbCacheService.update(defendWorldMarch);
		
		// 根据战报设置出征士兵
		Player attackPlayer = warAttacker.getPlayer();
		
		// 占领
		if(battle.getWinPlayerId() == attackPlayer.getRoleId()) {
			WorldSprite worldSprite = targetInfo.getParam();
			worldSprite.setOwnerMarchId(attackWorldMarch.getId());
			InjectorUtil.getInjector().dbCacheService.update(attackWorldMarch);
			
			// 失败直接会老家
			defendWorldMarch.getExecutor().handleReturn();
			
			// 进攻方开始占领
			if(attackWorldMarch.getMarchFight() == MarchFightState.FIGHT) {
				attackWorldMarch.getExecutor().handleReturn();
			} else {
				int minute = ((TerritoryWorldMarch)defendWorldMarch.executor).getOccupyTimeByMarch(attackWorldMarch.getWorldMarchSoldier());
				if(minute > 0) {
					((TerritoryWorldMarch)attackWorldMarch.getExecutor()).doOccupy(attackWorldMarch, attackPlayer, targetInfo, minute);
				} else {
					attackWorldMarch.executor.handleReturn();
				}
			}
		} else {
			// 采集碰到玩家，失败，直接回老家
			attackWorldMarch.getExecutor().handleReturn();
			
			if (defendWorldMarch.getSoldierNum() <= 0) {
				((TerritoryWorldMarch) defendWorldMarch.executor).handleReturn();
			} else {
				int minute = ((TerritoryWorldMarch)defendWorldMarch.executor).getOccupyTimeByMarch(defendWorldMarch.getWorldMarchSoldier());
				int plusTime = (int)Math.round(minute * (1 - defendWorldMarch.getFinishOccupyRatio()));
				if(plusTime > 0) {
					((TerritoryWorldMarch)defendWorldMarch.getExecutor()).doOccupy(defendWorldMarch, warDefender.getPlayer(), targetInfo, plusTime);
				// 处理返回
				} else {
					attackWorldMarch.executor.handleReturn();
				}
			}
		}
		
		super.fightEnd(battle, warEndReport);
	}

}
