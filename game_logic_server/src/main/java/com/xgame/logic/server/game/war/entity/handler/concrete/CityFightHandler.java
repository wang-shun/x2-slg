package com.xgame.logic.server.game.war.entity.handler.concrete;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
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
import com.xgame.logic.server.game.war.message.ResWarEndMessage;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.ReinforceMarch;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;
import com.xgame.utils.TimeUtils;


/**
 * 攻城战
 * @author jacky.jiang
 *
 */
@Component
public class CityFightHandler extends AbstractFightHandler {
	
	@Override
	public WarType getWarType() {
		return WarType.WORLD_CITY;
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
		warDefender.initCitySoldier(warFightParam.getDefendPlayer());
		
		//初始化建筑,初始化可悲掠夺长裤信息
		warDefender.initBulidings(warFightParam.getDefendPlayer());
		
		// 资源掠夺比率
		double ratio = Double.valueOf(GlobalPirFactory.get(GlobalConstant.INVASTION_RATIO).getValue());
		this.initInvasionResource(warDefender, ratio);
		
		warDefender.initReinforce(warFightParam.getDefendSoldiers());
		battle.setWarDefender(warDefender);
		battle.setWarFightParam(warFightParam);
		battle.setMoveCity(warFightParam.isMoveCity());
		
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
		battle.setWinPlayerId(warEndReport.getWinUid());
		battle.setWarType(this.getWarType());
		
		dealAttackSoldier(warAttacker, warEndReport.getWarEntityReport().getAttackSoldierBean());
		
		//处理防守方士兵
		dealDefendSoldier(warDefender, warEndReport.getWarEntityReport().getDefendSoldierBean());
		
		// 处理战斗奖励
		dealBattleReward(battle, warEndReport.getWarEntityReport().getBuildingReport());
		
		// 发送战报
		playerMailInfoManager.sendBattleEmail(battle, EmailTemplet.基地进攻报告_MAIL_ID, EmailTemplet.基地防守报告_MAIL_ID, getWarType());
		
		// 发送联盟报告
		sendAllianceReport(warAttacker.getPlayer(), warDefender.getPlayer(), battle.getWarResource().get(battle.getWarAttacker().getAttackId()));
					
		// 根据战报设置出征士兵
		ResWarEndMessage resWarEndMessage = converterResWarEndMessage(battle, warEndReport);
		String result = JsonUtil.toJSON(resWarEndMessage);
		
		WarFightParam warFightParam = battle.getWarFightParam();
		warFightParam.getAttackWorldMarch().setAttach(result);
		
		if(warEndReport.getWinUid() == warAttacker.getAttackId()) {
			SpriteInfo defendSpriteInfo = battle.getWarFightParam().getTargetSpriteInfo();
			Player defendPlayer = warDefender.getPlayer();
			defendPlayer.roleInfo().getBasics().setAttackTimer(TimeUtils.getCurrentTimeMillis());
			InjectorUtil.getInjector().dbCacheService.update(defendPlayer);
			
			if(defendSpriteInfo != null && defendSpriteInfo.getParam() != null) {
				PlayerSprite defendSprite = defendSpriteInfo.getParam();
				if(defendSprite != null) {
					defendSprite.setAttackTime(System.currentTimeMillis());
					InjectorUtil.getInjector().dbCacheService.update(defendSpriteInfo);
				}
			}
			
			warFightParam.getAttackWorldMarch().getExecutor().handleReturn();
			
			// 防守方全部立即到家
			for(WorldMarch worldMarch : warFightParam.getDefendMarchList()) {
				if(worldMarch.getSoldierNum() <= 0) {
					worldMarch.getExecutor().backHomeImmediately(worldMarch);
				}
				
				if(!((ReinforceMarch)worldMarch.executor).checkSameAlliancePlayer()) {
					worldMarch.getExecutor().handleReturn();
				}
			}
		} else {
			warDefender.getPlayer().roleInfo().getBasics().setAttackTimer(TimeUtils.getCurrentTimeMillis());
			warFightParam.getAttackWorldMarch().getExecutor().handleReturn();
		}
		
		warDefender.getPlayer().clearRadarWarning(warFightParam.getAttackWorldMarch().getId());
		super.fightEnd(battle, warEndReport);
	}
}
